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
        /*
        .modal {
            z-index: 1055 !important;
        }
        .modal-backdrop {
            z-index: 1050 !important;
        }
        */
    
        /* 메인 배경 이미지 스타일 */
        .main-visual {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100vh; /* 화면 전체 */
            background: url('<c:url value="/uploads/${festival.feImageMain}"/>') no-repeat center center;
            background-size: cover;
            transition: opacity 0.5s ease-in-out;
        }
        
        /* 메인이미지가 사라질 때 */
        .main-visual.hidden {
            opacity: 0;
        }
        

        /* 본문 컨텐츠 (고정된 이미지 위로 지나가게 함) */
        .content {
            margin-top: 100vh; /* 메인 이미지 높이만큼 여백 추가 */
            padding: 50px 0;
            background-color: white;
        }
        
        .header-fixed {
            position: fixed;
            top: -100px; /* 화면 위쪽에 숨김 */
            width: 100%;
            background-color: rgba(255, 255, 255, 0.9);
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            transition: top 0.3s ease-in-out; /* 위아래 움직임만 부드럽게 */
            z-index: 999;
        }
        
        /* 스크롤이 일정 이상 내려가면 즉시 헤더 보이기 */
        .header-fixed.visible {
            top: 0; /* 화면 상단에 고정 */
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

    <div class="header-fixed hidden">
        <c:set var="pageTitle" value="축제 상세 정보" />
        <%@ include file="../fragments/header.jspf" %>
    </div>

    <div class="main-visual"></div>

    <main class="container content">
    
            <input type="hidden" class="form-control" id="id" type="text" value="${festival.feId}" readonly />
    
        <h1 style="margin-bottom: 20px;">🎆 ${festival.feName}</h1>

        <h3 style="margin-top: 40px;">📅</h3>
        
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
        <button class="btn btn-outline-secondary mt-2" id="btnToggleReview">리뷰 보기</button>
    </main>
    
    
    <section>
        <!-- 댓글 보기/ 감추기 -->
        <div class="mt-2 collapse" id = "collapseReviews">
            <!-- 댓글 등록 UI -->
        <div class="mt-2 card card-body">
            <div class="row">
                <div class="col-10">
                    <input type="hidden" id="signedInUser" value="${signedInUser != null ? signedInUser : ''}" readonly />
                    <input type="hidden" id="feId" value="${festival.feId}" readonly />
                    <input type="text" class="form-control mt-2" id="reTitle" placeholder="리뷰 제목">
                    <select class="form-select mt-2" id="reGrade">
                        <option value="5">★★★★★</option>
                        <option value="4">★★★★☆</option>
                        <option value="3">★★★☆☆</option>
                        <option value="2">★★☆☆☆</option>
                        <option value="1">★☆☆☆☆</option>
                    </select>
                    <textarea class="form-control mt-2" rows="3" id="reContent" placeholder="리뷰 입력"></textarea>
                </div>
                <div class="col-2">
                    <button class="btn btn-outline-success mt-2" id="btnRegisterReview">등록</button>
                </div>
            </div>
        </div>

            <!-- 댓글 목록을 보여줄 UI -->
            <div class="my-2" id="divReviews"></div>
        </div>
    </section>
    
    <!-- 댓글 업데이트 모달 -->
    <div id="reviewModal" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">리뷰 수정</h5>
                    <button class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="modalReviewId">
                    <label>제목</label>
                    <input type="text" class="form-control" id="modalReviewTitle">
                    <label>별점</label>
                    <select class="form-select" id="modalReviewGrade">
                        <option value="5">★★★★★</option>
                        <option value="4">★★★★☆</option>
                        <option value="3">★★★☆☆</option>
                        <option value="2">★★☆☆☆</option>
                        <option value="1">★☆☆☆☆</option>
                    </select>
                    <label>내용</label>
                    <textarea class="form-control" id="modalReviewText"></textarea>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-outline-secondary" data-bs-dismiss="modal">취소</button>
                    <button class="btn btn-outline-success" id="btnUpdateRv">저장</button>
                </div>
            </div>
        </div>
    </div>

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
    
    <script>
        // 세션에 저장된 로그인 사용자 아이디를 자바스크립트 변수에 저장
        // -> comments.js 파일의 코드틀에서 그 변수를 사용할 수 있도록 하기 위해서 
        // JSP 파일의 <script> 태그 안에서는 EL을 사용할 수 있음
        // (주의) JS 파일에서는 EL을 사용할 수 없음
        const signedInUser = '${signedInUser}';
    </script>
    
    <c:url var="reviewsJS" value="/js/reviews.js" /> 
    <script src="${reviewsJS}"></script>
    
    <c:url var="festivalMainImageScrollJS" value="/js/festival-mainimage-scroll.js" /> 
    <script src="${festivalMainImageScrollJS}"></script> 
    
    <c:url var="headerJspfScrollJS" value="/js/header-jspf-scroll.js" /> 
    <script src="${headerJspfScrollJS}"></script>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
        crossorigin="anonymous"></script>

</body>

</html>


