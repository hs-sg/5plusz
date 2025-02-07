package com.oplusz.festgo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.oplusz.festgo.dto.FestivalSearchDto;
import com.oplusz.festgo.domain.Festival;
import com.oplusz.festgo.domain.FestivalImage;
import com.oplusz.festgo.dto.FestivalCalendarDto;
import com.oplusz.festgo.dto.FestivalSelectJoinLikesDto;
import com.oplusz.festgo.dto.FestivalSelectJoinRequestDto;

@Mapper
public interface FestivalDao {

    // 새로운 축제 등록 (매퍼 XML의 insertFestivals 사용)
	Integer insertFestivals(Festival festival);

    // 새로운 축제 등록 시 다중 이미지 처리 (매퍼 XML의 insertFestivalImagesBatch 사용)
    Integer insertFestivalImagesBatch(@Param("festivalImages") List<?> festivalImages);
    
    Integer insertFestivalImage(FestivalImage image);
    
    Festival selectFestivalById(Integer feId);

    // 특정 기간 내 축제 데이터 조회 (매퍼 XML의 findFestivalsBetween 사용)
    List<FestivalCalendarDto> findFestivalsBetween(@Param("startDate") String startDate,
                                                    @Param("endDate") String endDate);

	// 축제 검색
    List<FestivalCalendarDto> selectFestivalForSearch(FestivalSearchDto dto);
    
    // 검색한 축제 개수
    int selectFestivalForReload(FestivalSearchDto dto);
    
//	희성 작성 시작 ------------------------------------------------------------------------------------------------------------------
	
	// 전체 축제 읽기
	List<Festival> selectFestivalAll();
	
	// fest_request와 조인된 테이블 전체 축제 읽기 -> 마이페이지 관리자에서 사용
	List<FestivalSelectJoinRequestDto> selectFestivalJoinRequestAll();
	
	// fest_request와 조인된 테이블 fr_approval로 분류된 축제 읽기 -> 마이페이지 관리자에서 사용
	List<FestivalSelectJoinRequestDto> selectFestivalJoinRequestByFrApproval(Integer frApproval);
	
	// fest_request와 조인된 테이블 fr_approval로 분류된 축제 읽기 -> 마이페이지 사업자에서 사용
	List<FestivalSelectJoinRequestDto> selectFestivalJoinRequestBySponsorAndFrApproval(Integer frApproval, String meSponsor);
	
	// fest_request와 조인된 테이블 중 사업자가 개최한 축제 읽기 -> 마이페이지 사업자에서 사용
	List<FestivalSelectJoinRequestDto> selectFestivalJoinRequestBySponsor(String meSponsor);
	
	// likes와 조인된 테이블 중 멤버가 좋아요한 축제 읽기 -> 마이페이지 일반유저에서 사용
	List<FestivalSelectJoinLikesDto> selectFestivalJoinLikesByMemberId(Integer meId);
	
	// fe_id로 축제 삭제
	Integer deleteFestivalByFeId(Integer feId);
	
//	희성 작성 끝 ------------------------------------------------------------------------------------------------------------------

}


