package com.oplusz.festgo.repository;

import java.util.List;

import com.oplusz.festgo.domain.FestivalImage;
import com.oplusz.festgo.dto.FestivalSelectJoinLikesDto;
import com.oplusz.festgo.dto.FestivalSelectJoinRequestDto;
import com.oplusz.festgo.domain.Festival;

public interface FestivalDao {

	// �깉 異뺤젣 �옉�꽦
	int insertFestivals(Festival festivals);

	int insertFestivalImagesBatch(List<FestivalImage> festivalImages);
	
//	희성 작성 시작 ------------------------------------------------------------------------------------------------------------------
	
	// 전체 축제 읽기
	List<Festival> selectFestivalAll();
	
	// fest_request와 조인된 테이블 전체 축제 읽기 -> 마이페이지 관리자에서 사용
	List<FestivalSelectJoinRequestDto> selectFestivalJoinRequestAll();
	
	// fest_request와 조인된 테이블 중 사업자가 개최한 축제 읽기 -> 마이페이지 사업자에서 사용
	List<FestivalSelectJoinRequestDto> selectFestivalJoinRequestBySponsor(String meSponsor);
	
	// likes와 조인된 테이블 중 멤버가 좋아요한 축제 읽기 -> 마이페이지 일반유저에서 사용
	List<FestivalSelectJoinLikesDto> selectFestivalJoinLikesByMemberId(Integer meId);
	
//	희성 작성 끝 ------------------------------------------------------------------------------------------------------------------

}
