package com.oplusz.festgo.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/attachments")
public class PostAttachmentController {

    // ✅ Windows 환경에서는 경로 구분자를 \\ 로 설정하는 것이 안전함!
    private static final String UPLOAD_DIR = "C:\\uploads\\"; // 또는 "C:/uploads/"

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            // 파일 이름 디코딩
            String decodedFilename = URLDecoder.decode(filename, "UTF-8");
            Path filePath = Paths.get(UPLOAD_DIR, decodedFilename).normalize();

            log.info("📂 요청된 파일 경로: {}", filePath.toAbsolutePath());

            // 1️⃣ 파일 존재 확인
            if (!Files.exists(filePath)) {
                log.error("❌ 파일이 존재하지 않습니다: {}", filePath.toAbsolutePath());
                return ResponseEntity.notFound().build();
            }

            // 2️⃣ 파일 읽기 가능 여부 확인
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                log.error("❌ 파일을 읽을 수 없습니다: {}", filePath.toAbsolutePath());
                return ResponseEntity.status(403).build();
            }

            // 3️⃣ MIME 타입 설정
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // 4️⃣ Content-Disposition 설정 (파일 다운로드 가능)
            String encodedFilename = URLEncoder.encode(decodedFilename, "UTF-8").replace("+", "%20");
            String contentDisposition = "attachment; filename=\"" + encodedFilename + "\"; filename*=UTF-8''" + encodedFilename;

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);

        } catch (Exception e) {
            log.error("🚨 파일 제공 실패: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
