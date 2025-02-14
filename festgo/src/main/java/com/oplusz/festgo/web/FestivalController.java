package com.oplusz.festgo.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oplusz.festgo.domain.Festival;
import com.oplusz.festgo.domain.FestivalImage;
import com.oplusz.festgo.domain.Theme;
import com.oplusz.festgo.dto.FestivalCreateDto;
import com.oplusz.festgo.repository.ThemeDao;
import com.oplusz.festgo.service.AlarmService;
import com.oplusz.festgo.service.FestivalService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/fest")
public class FestivalController {

	// 축제 서비스 생성
	private final FestivalService festivalService;
	private final AlarmService alarmService;
	// 테마 DAO를 직접 주입받음
	private final ThemeDao themeDao;

    
	@GetMapping("/detail/images/{feId}")
	@ResponseBody
	public ResponseEntity<List<FestivalImage>> getFestivalImages(@PathVariable Integer feId) {
	    List<FestivalImage> images = festivalService.getFestivalImages(feId);

	    if (images == null || images.isEmpty()) {
	        log.error("❌ 축제 이미지 데이터를 찾을 수 없습니다! feId = {}", feId);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 🔴 404 반환
	    }

	    log.info("✅ 축제 이미지 조회 성공! feId = {}, 이미지 개수 = {}", feId, images.size());
	    return ResponseEntity.ok(images);
	}




    
	// 해당 축제 상세보기 서비스
    @GetMapping("/detail")
    public String festivalDetails(@RequestParam("feId") Integer feId, Model model) {
        Festival festival = festivalService.read(feId);
        List<FestivalImage> festivalImages = festivalService.getFestivalImages(feId);
        
        model.addAttribute("festival", festival);
        model.addAttribute("festivalImages", festivalImages);
        return "fest/detail";
    }
	

	@Controller
	@RequestMapping("/uploads")
	public class FileController {

	    private static final String UPLOAD_DIR = "C:\\java157\\workspaces\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\festgo\\uploads";

	    @GetMapping("/{filename:.+}")
	    @ResponseBody
	    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
	        try {
	            Path file = Paths.get(UPLOAD_DIR).resolve(filename);
	            Resource resource = new UrlResource(file.toUri());

	            if (!resource.exists() || !resource.isReadable()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 응답 추가
	            }

	            return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
	                .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
	                .body(resource);
	        } catch (MalformedURLException e) {
	            return ResponseEntity.badRequest().build();
	        }
	    }
	}

	// GET 방식 매핑
	// 새 축제 등록
	@GetMapping("/create")
	public String create(Model model) {
		log.debug("GET create()");

		List<Theme> themes = themeDao.findAllThemes();
		model.addAttribute("themes", themes);
		return "fest/create";
	}

	// POST 방식 매핑 저장 후 홈으로 리턴
	// 새 축제 등록
	@PostMapping("/create")
	public String create(HttpServletRequest request, @RequestParam("feImageMainFile") MultipartFile feImageMainFile,
			@RequestParam("fePosterFile") MultipartFile fePosterFile,
			@RequestParam("fiImagesFiles") List<MultipartFile> fiImagesFiles,
			@RequestParam("feFeeType") String feFeeType, @ModelAttribute FestivalCreateDto dto, HttpSession session,
			BindingResult result) {
		
		if ("free".equals(feFeeType)) {
			dto.setFeFee("무료");
		} else {
			dto.setFeFee("유료");
		}

		log.debug("위도: {}, 경도: {}", dto.getFeLat(), dto.getFeLong());

		if ("free".equals(feFeeType)) {
			dto.setFeFee("무료");
		} else {
			dto.setFeFee("유료");
		}

		// 테마 처리: 만약 "custom" 옵션이 선택되었으면,
		// DTO의 customTheme 값으로 테마를 DB에 저장 후, 생성된 THE_ID를 DTO에 설정
		if ("custom".equals(dto.getTheId())) {
			String customTheme = dto.getCustomTheme();
			if (customTheme == null || customTheme.trim().isEmpty()) {
				result.rejectValue("customTheme", "error.customTheme", "테마를 입력해 주세요.");
				return "fest/create";
			}
			// 새 테마 객체 생성
			Theme newTheme = Theme.builder().theName(customTheme).build();
			// DB에 테마 저장 (매퍼 XML의 useGeneratedKeys 설정에 따라 newTheme.theId가 채워짐)
			themeDao.insertTheme(newTheme);
			// 생성된 THE_ID를 DTO에 설정 (문자열 형태로)
			dto.setTheId(String.valueOf(newTheme.getTheId()));
		}

		// 웹 애플리케이션 내 /uploads 폴더의 절대 경로를 구함
		String uploadDir = request.getServletContext().getRealPath("/uploads/");
		log.debug("업로드 폴더 절대 경로: {}", uploadDir);

		try {
			// 업로드 폴더가 없으면 생성
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
				log.debug("업로드 폴더를 생성했습니다: {}", uploadPath);
			}

			// 메인 이미지 저장 예시
			if (!feImageMainFile.isEmpty()) {
				String fileName = UUID.randomUUID() + "_" + feImageMainFile.getOriginalFilename();
				Path filePath = uploadPath.resolve(fileName);
				// 부모 디렉토리가 없을 경우를 대비해 생성 (이미 위에서 생성했으므로 선택사항)
				Files.createDirectories(filePath.getParent());
				feImageMainFile.transferTo(filePath.toFile());
				dto.setFeImageMain(fileName);
			}

			// 포스터 저장
			if (!fePosterFile.isEmpty()) {
				String fileName = UUID.randomUUID() + "_" + fePosterFile.getOriginalFilename();
				Path filePath = uploadPath.resolve(fileName);
				Files.createDirectories(filePath.getParent());
				fePosterFile.transferTo(filePath.toFile());
				dto.setFePoster(fileName);
			}

			// 추가 이미지 저장
			List<String> savedImages = new ArrayList<>();
			for (MultipartFile file : fiImagesFiles) {
				if (!file.isEmpty()) {
					String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
					Path filePath = uploadPath.resolve(fileName);
					Files.createDirectories(filePath.getParent());
					file.transferTo(filePath.toFile());
					savedImages.add(fileName);
				}
			}
			dto.setFiImages(savedImages);
		} catch (IOException e) {
			log.error("파일 업로드 실패", e);
			throw new RuntimeException("파일 업로드 중 문제가 발생했습니다.");
		}

	    // DTO 저장
	    festivalService.create(dto);
	    
	    // 알람 추가
	    alarmService.create(dto.getFeName(), dto.getMeSponsor(), session.getAttribute("signedInUser").toString());
	    
	    return "redirect:/";
	}

}
