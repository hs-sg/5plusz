package com.oplusz.festgo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.dto.PostCreateDto;
import com.oplusz.festgo.repository.PostDao;

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
	 * 게시글 생성 (첨부파일 포함)
	 */
	public int create(PostCreateDto dto, MultipartFile file) {
		log.debug("create(dto={}, file={})", dto, file);

		try {
			// 파일 저장 처리
			if (file != null && !file.isEmpty()) {
				String savedFileName = saveFile(file);
				dto.setPaAttachments(savedFileName); // 파일 경로를 dto 에 저장
			}

		} catch (IOException e) {
			log.error("파일 저장 실패", e);
			return 0;
		}
		// 게시글 저장
		int result = postDao.insert(dto);
		log.debug("게시글 생성 결과:{}", result);
		return result;
	}

	/**
	 * 파일 저장 처리
	 * 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	private String saveFile(MultipartFile file) throws IllegalStateException, IOException {
		// 파일 이름 생성 (UUID로 고유 이름설정)
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
		log.debug("파일 저장 완료:{}", saveFile.getAbsolutePath());

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
			throw new RuntimeException("파일이 존재하지 않습니다:" + filename);
		}
	}

}
