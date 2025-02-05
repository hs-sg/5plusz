package com.oplusz.festgo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.domain.PostAttachment;
import com.oplusz.festgo.dto.PostCreateDto;
import com.oplusz.festgo.dto.PostUpdateDto;
import com.oplusz.festgo.dto.PostWithAttachmentsDto;
import com.oplusz.festgo.repository.PostDao;
import com.oplusz.festgo.web.PostSearchDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

	private final PostDao postDao;

	// 업로드된 파일을 저장할 기본 디렉토리
	private static final String UPLOAD_DIR = "C:/uploads/";

	/**
	 * 게시글 목록 조회
	 */
	public List<Post> read() {
		log.debug("read()");
		List<Post> list = postDao.selectOrderByIdDesc();
		log.debug("Posts list: {}", list);
		return list;
	}

	/**
	 * 게시글 상세 조회 (조회수 증가)
	 */
	public PostWithAttachmentsDto readById(Integer poId) {
		log.debug("readById(poId={})", poId);

		// 조회수 증가
		postDao.increaseViewCount(poId);

		// 게시글 조회
		Post post = postDao.selectById(poId);
		if (post == null) {
			throw new RuntimeException("게시글이 존재하지 않습니다.");
		}

		// 첨부파일 목록 조회
		List<PostAttachment> attachments = postDao.selectAttachmentsByPostId(poId);

		// DTO 반환
		return new PostWithAttachmentsDto(post, attachments);
	}

	/**
	 * 게시글 조회 (조회수 증가 X, 수정 페이지에서 사용)
	 */
	public PostWithAttachmentsDto getPostWithoutIncreasingViews(Integer poId) {
		log.debug("getPostWithoutIncreasingViews(poId={})", poId);

		// 조회수 증가 없이 게시글 조회
		Post post = postDao.selectById(poId);
		if (post == null) {
			throw new RuntimeException("게시글이 존재하지 않습니다.");
		}

		// 첨부파일 목록 조회
		List<PostAttachment> attachments = postDao.selectAttachmentsByPostId(poId);

		// DTO 반환
		return new PostWithAttachmentsDto(post, attachments);
	}

	/**
	 * 게시글 생성 (첨부파일 포함)
	 */
	@Transactional
	public int create(PostCreateDto dto, List<MultipartFile> files) {
		log.debug("create(dto={}, files={})", dto, files);

		/* [임시 설정] 로그인 기능 없이 테스트하기 위해 역할을 직접 설정
		int mrId = 3; // ⚠️ 테스트할 때 바꿔서 사용 (1: 일반, 2: 사업자, 3: 관리자)

		// 사용자의 역할에 따라 pc_id 설정
		if (mrId == 3) { // 관리자라면?
			if (dto.getPcId() == null || (dto.getPcId() != 1 && dto.getPcId() != 2)) {
				dto.setPcId(1); // 기본값 일반글(1) (잘못된 값이 들어오면 1로 설정)
			}
		} else {
			dto.setPcId(1); // 일반 사용자 및 사업자는 무조건 일반글(1)
		}
*/
		// 게시글 저장 후 poId 가져오기
		int result = postDao.insert(dto);
		if (result == 0) {
			throw new RuntimeException("게시글 저장 실패");
		}

		log.debug("게시글 생성 성공, ID: {}", dto.getPoId()); // poId 확인
		if (dto.getPoId() == null) {
			throw new RuntimeException("poId가 null입니다. 게시글 저장 실패.");
		}

		// 첨부파일 저장
		if (files != null && !files.isEmpty()) {
			List<PostAttachment> attachments = new ArrayList<>();
			for (MultipartFile file : files) {
				try {
					String savedFileName = saveFile(file);
					attachments.add(PostAttachment.builder().poId(dto.getPoId()) // 게시글 ID 설정
							.paAttachments(savedFileName).build());
				} catch (IOException e) {
					log.error("파일 저장 실패", e);
					throw new RuntimeException("파일 저장 실패: " + file.getOriginalFilename());
				}
			}

			// poId가 null이 아닌지 확인 후 저장
			if (!attachments.isEmpty()) {
				postDao.insertAttachment(attachments);
			}
		}

		return result;
	}

	/**
	 * 파일 저장 처리
	 */
	private String saveFile(MultipartFile file) throws IllegalStateException, IOException {
		// 파일 이름 생성 (UUID로 고유 이름 설정)
		String originalFileName = file.getOriginalFilename();
		String savedFileName = UUID.randomUUID() + "_" + originalFileName;

		// 저장 경로 생성
		File saveFile = new File(UPLOAD_DIR, savedFileName);

		// 저장 디렉토리 없으면 생성
		if (!saveFile.getParentFile().exists()) {
			saveFile.getParentFile().mkdirs();
		}

		// 파일 저장
		file.transferTo(saveFile);
		log.debug("파일 저장 완료: {}", saveFile.getAbsolutePath());

		return savedFileName;
	}

	/**
	 * 파일 다운로드 경로 반환
	 */
	public File getFile(String filename) {
		File file = new File(UPLOAD_DIR + filename);
		if (file.exists()) {
			return file;
		} else {
			throw new RuntimeException("파일이 존재하지 않습니다: " + filename);
		}
	}

	// 수정하기 서비스

	@Transactional
	public void updatePost(PostUpdateDto dto, List<MultipartFile> newFiles) {
		log.debug("updatePost(dto={}, newFiles={})", dto, newFiles);

		// 1. 게시글 내용 수정
		int result = postDao.update(dto.toEntity());
		if (result == 0) {
			throw new RuntimeException("게시글 수정 실패");
		}

		// 2. 기존 첨부파일 삭제 (체크한 파일만 삭제)
		if (dto.getRemoveAttachmentIds() != null && !dto.getRemoveAttachmentIds().isEmpty()) {
			log.debug("삭제할 첨부파일 ID 리스트: {}", dto.getRemoveAttachmentIds());
			postDao.deleteAttachmentsByIds(dto.getRemoveAttachmentIds());
		}

		// 3. 새로운 첨부파일 추가
		if (newFiles != null && !newFiles.isEmpty()) {
			List<PostAttachment> attachments = new ArrayList<>();
			for (MultipartFile file : newFiles) {
				try {
					String savedFileName = saveFile(file);
					attachments.add(PostAttachment.builder().poId(dto.getPoId()) // 게시글 ID 설정
							.paAttachments(savedFileName).build());
				} catch (IOException e) {
					log.error("파일 저장 실패", e);
					throw new RuntimeException("파일 저장 실패: " + file.getOriginalFilename());
				}
			}

			if (!attachments.isEmpty()) {
				postDao.insertAttachment(attachments);
			}
		}
	}

	// 기존 첨부파일 목록
	public List<PostAttachment> getAttachments(Integer poId) {
		return postDao.selectAttachmentsByPostId(poId);
	}

	// 삭제하기 서비스
	@Transactional
	public void delete(Integer poId) {
		log.debug("delete(poId={})", poId);

		// 1. 게시글에 연결된 첨부파일 먼저 삭제
		postDao.deleteAttachmentsByPostId(poId);

		// 2. 게시글 삭제
		postDao.deleteById(poId);
	}

	public List<Post> read(PostSearchDto dto) {
		log.debug("read(dto={})", dto);

		// 영속성 계층의 메서드를 호출해서 DB에서 select를 수행하고 결과를 가져옴.
		List<Post> list = postDao.search(dto);
		log.debug("# of search result = {}", list.size());
		return list;
	}

}
