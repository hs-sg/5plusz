package com.oplusz.festgo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Collections;

import java.util.HashMap;

import java.util.List;

import java.util.Objects;

import java.util.Map;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.domain.PostAttachment;
import com.oplusz.festgo.dto.PostCreateDto;
import com.oplusz.festgo.dto.PostSearchDto;
import com.oplusz.festgo.dto.PostUpdateDto;
import com.oplusz.festgo.dto.PostWithAttachmentsDto;
import com.oplusz.festgo.repository.PostDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

	private final PostDao postDao;

	// 업로드된 파일을 저장할 기본 디렉토리
	private static final String UPLOAD_DIR = "C:/JAVA157/Workspaces/oplusz/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/festgo/uploads/";
	
	/**
	 * 모든 게시글 목록 조회( 페이징 없음 )
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

	    Post post = postDao.selectById(poId);
	    if (post == null) {
	        log.error("게시글이 존재하지 않습니다.");
	        throw new RuntimeException("게시글이 존재하지 않습니다.");
	    }

	    // 첨부파일 리스트 조회를 PostAttachment로 변경
	    List<PostAttachment> attachments = postDao.selectAttachmentsByPostIdWithObject(poId);

	    log.debug("게시글 정보: {}", post);
	    log.debug("첨부파일 리스트: {}", attachments);

	    return new PostWithAttachmentsDto(post, attachments); // Attachments as List<PostAttachment>
	}



	/**
	 * 게시글 조회 (조회수 증가 X, 수정 페이지에서 사용)
	 */
	public PostWithAttachmentsDto getPostWithoutIncreasingViews(Integer poId) {
	    log.debug("getPostWithoutIncreasingViews(poId={})", poId);

	    // 게시글 조회
	    Post post = postDao.selectById(poId);
	    if (post == null) {
	        throw new RuntimeException("게시글이 존재하지 않습니다.");
	    }

	    // 첨부파일 조회를 PostAttachment 객체 리스트로 변경
	    List<PostAttachment> attachments = postDao.selectAttachmentsByPostIdWithObject(poId);

	    return new PostWithAttachmentsDto(post, attachments);  // PostAttachment 리스트로 변경
	}




	/**
	 * 게시글 생성 (첨부파일 포함)
	 */
	@Transactional
	public int create(PostCreateDto dto, List<MultipartFile> files) {
		log.debug("create(dto={}, files={})", dto, files);

		/*
		 * [임시 설정] 로그인 기능 없이 테스트하기 위해 역할을 직접 설정 int mrId = 3; // ⚠️ 테스트할 때 바꿔서 사용 (1:
		 * 일반, 2: 사업자, 3: 관리자)
		 * 
		 * // 사용자의 역할에 따라 pc_id 설정 if (mrId == 3) { // 관리자라면? if (dto.getPcId() == null
		 * || (dto.getPcId() != 1 && dto.getPcId() != 2)) { dto.setPcId(1); // 기본값
		 * 일반글(1) (잘못된 값이 들어오면 1로 설정) } } else { dto.setPcId(1); // 일반 사용자 및 사업자는 무조건
		 * 일반글(1) }
		 */
		// 게시글 저장 후 poId 가져오기
	    int result = postDao.insert(dto);
	    if (result == 0 || dto.getPoId() == null) {
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
			    for (PostAttachment attachment : attachments) {
			        postDao.insertAttachment(Collections.singletonList(attachment)); 
			    }
			}
		}

		return result;
	}

	/**
	 * 파일 저장 처리
	 */
	private String saveFile(MultipartFile file) throws IllegalStateException, IOException {
	    ensureUploadDirectoryExists(); // 디렉토리 존재 확인
	    
	    String originalFileName = file.getOriginalFilename();
	    String savedFileName = UUID.randomUUID() + "_" + originalFileName;
	    
	    File saveFile = new File(UPLOAD_DIR, savedFileName);
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

	    // 2. 기존 첨부파일 삭제 (빈 리스트 및 null 제거 후 실행)
	    List<Integer> removeAttachmentIds = dto.getRemoveAttachmentIds();
	    if (removeAttachmentIds != null) {
	        removeAttachmentIds = removeAttachmentIds.stream()
	            .filter(Objects::nonNull) // null 값 제거
	            .collect(Collectors.toList());

	        if (!removeAttachmentIds.isEmpty()) { // 리스트가 비어있지 않은 경우만 실행
	            postDao.deleteAttachmentsByIds(removeAttachmentIds);
	        }
	    }

	    // 3. 새로운 첨부파일 추가 (빈 파일 방지)
	    if (newFiles != null && !newFiles.isEmpty()) {
	        List<PostAttachment> attachments = new ArrayList<>();
	        for (MultipartFile file : newFiles) {
	            if (!file.isEmpty()) {
	                try {
	                    String savedFileName = saveFile(file);
	                    attachments.add(PostAttachment.builder()
	                            .poId(dto.getPoId())
	                            .paAttachments(savedFileName)
	                            .build());
	                } catch (IOException e) {
	                    log.error("파일 저장 실패", e);
	                    throw new RuntimeException("파일 저장 실패: " + file.getOriginalFilename());
	                }
	            }
	        }

			if (!attachments.isEmpty()) {
			    for (PostAttachment attachment : attachments) {
			        postDao.insertAttachment(Collections.singletonList(attachment)); 
			    }
			}
	    }
	}

	
	// 파일 저장 시 디렉토리 존재 여부 확인 및 생성
	private void ensureUploadDirectoryExists() {
	    File directory = new File(UPLOAD_DIR);
	    if (!directory.exists()) {
	        directory.mkdirs();
	    }
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

	/**
	 * 페이징 목록 조회
	 */

	public Map<String, Object> getPagedPosts(int page, Integer pageSize) {
	    if (pageSize == null || pageSize <= 0) {
	        pageSize = 5; // 기본값 설정
	    }

	    // 공지사항은 항상 고정되도록 먼저 조회
	    List<Post> notices = getNotices();

	    int startRow = (page - 1) * pageSize + 1;
	    int endRow = page * pageSize;

	    Map<String, Object> params = new HashMap<>();
	    params.put("startRow", startRow);
	    params.put("endRow", endRow);

	    // 일반 게시글 조회
	    List<Post> posts = postDao.selectPagedPosts(params);

	    Map<String, Object> result = new HashMap<>();
	    result.put("notices", notices);  // 공지사항은 무조건 포함
	    result.put("posts", posts);
	    result.put("currentPage", page);
	    result.put("pageSize", pageSize);
	    result.put("totalPages", calculateTotalPages(pageSize));

	    return result;
	}
	
//	희성 작성 시작
	
	// 마이페이지 상에 로그인된 아이디가 작성한 글 목록 가져오기
	public List<Post> readByMeUsername(String meUsername) {
		log.debug("readByMeUsername(meUsername={})", meUsername);
		
		List<Post> list = postDao.readByMeUsername(meUsername);
		log.debug("# of search result = {}", list.size());
		
		return list;
	}
	
	// 마이페이지 상에 관리자가 모든글 원하는 갯수대로 가져오기
	public List<Post> readVariableByPageNum(Integer postNumberInList, Integer pageNum) {
		log.debug("readVariableByPageNum(postNumberInList={}, pageNum={})", postNumberInList, pageNum);
		Integer minPostNum = 1 + (postNumberInList * (pageNum - 1));
		Integer maxPostNum = pageNum * postNumberInList;
		
		List<Post> list = postDao.readPostVariable(minPostNum, maxPostNum);
		log.debug("# of search result = {}", list.size());
		
		return list;
	}
	
	// 마이페이지 상에 유저, 스폰서가 작성한 글 원하는 갯수대로 가져오기
	public List<Post> readVariableByPageNumAndUsername(Integer postNumberInList, Integer pageNum, String username) {
		log.debug("readVariableByPageNum(postNumberInList={}, pageNum={}, username={})", postNumberInList, pageNum, username);
		Integer minPostNum = 1 + (postNumberInList * (pageNum - 1));
		Integer maxPostNum = pageNum * postNumberInList;
		
		List<Post> list = postDao.readPostVariableByMeUsername(minPostNum, maxPostNum, username);
		log.debug("# of search result = {}", list.size());
		
		return list;
	}
	
	// 전체 글 갯수 가져오기
	public Integer countAllPosts() {
		log.debug("countAllPosts()");
		
		Integer countPosts = postDao.countAllPosts();
		log.debug("countPosts result = {}", countPosts);
		
		return countPosts;
	}
	
	// 아이디로 글 갯수 가져오기
	public Integer getCountAllPostsByUsername(String username) {
		log.debug("getCountAllPostsByUsername(username={})", username);
		
		Integer countPosts = postDao.countPostsByMeUsername(username);
		log.debug("countPosts result = {}", countPosts);
		
		return countPosts;
	}
	
//	희성 작성 끝


	private int calculateTotalPages(int pageSize) {
	    int totalCount = postDao.countPosts();
	    return (int) Math.ceil((double) totalCount / pageSize);
	}


	
	// 검색 & 페이징

	public Map<String, Object> searchWithPaging(PostSearchDto dto) {
	    log.debug("Executing search query with category: {}, keyword: {}", dto.getCategory(), dto.getKeyword());

	    if (dto.getPageSize() == null || dto.getPageSize() <= 0) {
	        dto.setPageSize(5); // 기본값 설정
	    }

	    int startRow = (dto.getPage() - 1) * dto.getPageSize();

	    // 페이징 및 검색 조건을 매개변수로 전달
	    Map<String, Object> params = new HashMap<>();
	    params.put("category", dto.getCategory());
	    params.put("keyword", dto.getKeyword());
	    params.put("startRow", startRow);
	    params.put("pageSize", dto.getPageSize());

	    // 검색 쿼리 호출
	    List<Post> posts = postDao.searchWithPaging(params);
	    int totalResults = postDao.countSearchResults(params);

	    // 전체 페이지 수 계산
	    int totalPages = (int) Math.ceil((double) totalResults / dto.getPageSize());
	    if (totalPages == 0) {
	        totalPages = 1;
	    }
	    List<Post> notices = getNotices();
	    // 결과 반환
	    Map<String, Object> result = new HashMap<>();
	    result.put("notices", notices); 
	    result.put("posts", posts);
	    result.put("currentPage", dto.getPage());
	    result.put("totalPages", totalPages);
	    result.put("pageSize", dto.getPageSize());

	    return result;
	}

	public List<Post> getNotices() {
    log.debug("Fetching notices");
    return postDao.selectNotices(); // 공지사항만 가져오는 메서드
}
	
	public List<Post> getPagedPosts(Map<String, Object> params) {
	    return postDao.selectPagedPosts(params);
	}

}