package com.oplusz.festgo.dto;

import java.util.List;

import com.oplusz.festgo.domain.Festival;
import com.oplusz.festgo.domain.FestivalImage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FestivalWithImagesDto {
	// 축제 생성 및 축제 이미지 생성
	private Festival festival;
	private List<FestivalImage> images;
}
