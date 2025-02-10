<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${festival.feName} 상세 정보</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
          rel="stylesheet" 
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
          crossorigin="anonymous" />

    <!-- Custom CSS -->
    <style>
        /* 메인 배경 이미지 스타일 */
        .main-visual {
            background: url(${festival.feImageMain}) no-repeat center center;
            background-size: cover;
            background-attachment: fixed;
            height: 400px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 2rem;
            font-weight: bold;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
            position: relative;
        }

        /* 투명 오버레이 효과 */
        .overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.4);
        }

        /* 본문 컨텐츠 */
        .content {
            padding: 50px 0;
        }

        /* 맵 & 정보 컨테이너 */
        .map-info-container {
            display: flex;
            align-items: flex-start;
            gap: 20px;
            flex-wrap: wrap; /* 줄바꿈 허용 */
        }
    
        /* 카카오맵 스타일 */
        #map {
            width: 100%; /* 부모 요소에 맞게 자동 조정 */
            max-width: 500px; /* 최대 크기 지정 */
            height: 400px;
            border: 2px solid #ccc;
            border-radius: 10px;
        }
    
        /* 반응형 스타일 */
        @media (max-width: 768px) {
            .map-info-container {
                flex-direction: column; /* 모바일에서는 세로 정렬 */
                align-items: center; /* 중앙 정렬 */
            }
    
            #map {
                width: 90%; /* 모바일에서는 맵을 더 크게 표시 */
                height: 300px; /* 높이를 줄여서 보기 편하게 */
            }
        }

    </style>
</head>
<body>

    <div class="container-fluid">
        <c:set var="pageTitle" value="축제 상세 정보" />
        <%@ include file="../fragments/header.jspf" %>
    </div>

    <!-- 메인 비주얼 -->
    <div class="main-visual">
        <div class="overlay"></div>
        <div>${festival.feName}</div>
    </div>

    <main class="container content">
    
        <h1 style="margin-bottom: 20px;">${festival.feName}</h1>

        <h3 style="margin-top: 40px;">📅 축제 날짜</h3>
        
        <div id="festival-dday" class="mt-3"></div>
        <div id="festival-date" class="mt-2"></div>

        
        <!-- festival-dday.js 로드 -->
        <c:url var="festivalDday" value="/js/festival-dday.js" />
        <script src="${festivalDday}"></script>
        
        <!-- 날짜 업데이트 스크립트 -->
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                console.log("DOM 로드됨");
                console.log("시작일:", "${festival.feStartDate}");
                console.log("종료일:", "${festival.feEndDate}");
    
                // 날짜와 D-Day를 업데이트
                if ("${festival.feStartDate}" && "${festival.feEndDate}") {
                    updateFestivalDate("${festival.feStartDate}", "${festival.feEndDate}");
                } else {
                    console.error("날짜 데이터가 없습니다.");
                }
            });
        </script>
        
		<h3 style="margin-top: 40px;">📖 축제 내용</h3>
		<p><strong>${festival.feContents}</strong></p>
		
		<h3 style="margin-top: 40px;">📍 축제 위치</h3>
		<p><strong>${festival.feAddress}</strong></p>


        <!-- 카카오맵 표시 -->
        <div id="map" class="mb-3" style="position: relative;">
            <!-- 길찾기 버튼 -->
            <button id="direction-btn" class="btn btn-primary" 
                    style="position: absolute; bottom: 10px; right: 10px; z-index: 10;">
                길찾기
            </button>
        </div>



		<h3 class="mt-3">💰 참가비</h3>
		<p><strong>${festival.feFee}</strong></p>
    </main>

    <!-- 카카오맵 API -->
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=cf94a4eafbce0c713bd14afa38fa62da&libraries=services"></script>
    
	<script>
		document.addEventListener("DOMContentLoaded", function () {
		    const festivalAddress = "${festival.feAddress}"; // 축제 주소
		    const festivalName = "${festival.feName}"; // 축제 이름
		    let festivalLat = "${festival.feLat}"; // 위도
		    let festivalLng = "${festival.feLong}"; // 경도
		
		    const mapContainer = document.getElementById("map"); // 지도 컨테이너
		    const directionBtn = document.getElementById("direction-btn"); // 길찾기 버튼
		
		    const map = new kakao.maps.Map(mapContainer, {
		        center: new kakao.maps.LatLng(37.5665, 126.9780), // 기본 중심 좌표 (서울)
		        level: 3, // 확대 레벨
		    });
		
		    const geocoder = new kakao.maps.services.Geocoder();
		
		    // 📌 위도, 경도가 `null`이면 주소 기반으로 변환
		    if (!festivalLat || !festivalLng || festivalLat === "null" || festivalLng === "null") {
		        geocoder.addressSearch(festivalAddress, function (result, status) {
		            if (status === kakao.maps.services.Status.OK) {
		                festivalLat = result[0].y;
		                festivalLng = result[0].x;
		
		                console.log("변환된 위도:", festivalLat, "경도:", festivalLng);
		
		                setMapMarker(festivalLat, festivalLng);
		            } else {
		                console.error("주소를 위도·경도로 변환할 수 없습니다.");
		            }
		        });
		    } else {
		        // 이미 위도·경도가 있는 경우 그대로 사용
		        setMapMarker(festivalLat, festivalLng);
		    }
		
		    // 지도에 마커 표시 및 길찾기 버튼 기능 추가
		    function setMapMarker(lat, lng) {
		        const coords = new kakao.maps.LatLng(lat, lng);
		
		        // 지도 중심을 마커 위치로 설정
		        map.setCenter(coords);
		
		        // 마커 추가
		        const marker = new kakao.maps.Marker({
		            map: map,
		            position: coords,
		        });
		
		        // 길찾기 버튼 클릭 시 실행
		        directionBtn.addEventListener("click", function () {
		        	const kakaoMapUrl = "https://map.kakao.com/link/to/" + encodeURIComponent(festivalAddress) + "," + lat + "," + lng;
		            window.open(kakaoMapUrl, "_blank");
		        });
		    }
		});
	</script>



    
    <!-- Axios Http JS -->
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>


    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
        crossorigin="anonymous"></script>

</body>
</html>
