package com.oplusz.festgo.web;

import com.oplusz.festgo.domain.FestivalImage;
import com.oplusz.festgo.service.FestivalImageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/festival")
public class FestivalImageController {
    private final FestivalImageService festivalImageService;

    public FestivalImageController(FestivalImageService festivalImageService) {
        this.festivalImageService = festivalImageService;
    }

    // 특정 축제의 이미지 가져오기
    @GetMapping("/{feId}/images")
    public List<FestivalImage> getFestivalImages(@PathVariable int feId) {
        return festivalImageService.getFestivalImages(feId);
    }
    
    // 다중 이미지 업로드 (예제, 실제 업로드 로직 필요)
    @PostMapping("/{feId}/images")
    public void uploadFestivalImages(@PathVariable int feId, @RequestBody List<String> images) {
        for (String imagePath : images) {
            FestivalImage festivalImage = new FestivalImage();
            festivalImage.setFeId(feId);
            festivalImage.setFiImages(imagePath);
            festivalImageService.addFestivalImage(festivalImage);
        }
    }
}
