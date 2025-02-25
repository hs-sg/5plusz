<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

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
        
            @font-face {        
                font-family: 'sansMedium';      
                src: url('../font/GmarketSansTTFMedium.ttf') format('truetype');        
            }       
            @font-face {        
                font-family: 'sansLight';       
                src: url('../font/GmarketSansTTFLight.ttf') format('truetype');     
            }  
            @font-face {        
            font-family: 'dohyeon';     
            src: url('../font/BMDOHYEON_ttf.ttf') format('truetype');       
            }   
            
            body {
                font-family: 'sansMedium';
            }
        
            .modal {
                z-index: 1055 !important;
                pointer-events: auto; /* 클릭 가능하도록 설정 */
            }
            
            .modal-backdrop {
                z-index: 1050 !important; /* 모달보다 낮게 */
                pointer-events: none; /* 백드롭이 클릭 이벤트를 차단하지 않도록 */
            }    
            
            /* 메인 배경 이미지 스타일 */
            .main-visual {
                z-index: 1050 !important;
                top: 0;
                left: 0;
                width: 100%;
                height: 100vh; /* 화면 전체 */
                background: url('<c:url value="/uploads/${festival.feImageMain}"/>') no-repeat center center;
                background-size: cover;
                transition: opacity 0.5s ease-in-out;
            }
            
            .festival-contents {
                white-space: pre-wrap;
                margin: 0;
                padding: 0;
                line-height: 1.5;
                display: inline-block;
            }
            
            .festival-poster-container {
                display: flex;
                justify-content: flex-start; /* 왼쪽 정렬 */
                align-items: center; /* 세로 중앙 정렬 */
                margin: 20px 0;
            }
            
            .festival-poster-img {
                width: 100%;
                max-width: 300px; /* 크기를 300px로 조절 */
                height: auto;
                border-radius: 10px; /* 둥근 모서리 */
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); /* 살짝 그림자 */
            }
            
            .icon {
                font-size: 30px; /* 아이콘 크기 조정 */
                margin-right: 5px; /* 텍스트와 간격 조정 */
            }
            
            .homepage-link {
                color: #007bff; /* 파란색 */
                text-decoration: none; /* 밑줄 제거 */
            }
            
            .homepage-link:hover {
                text-decoration: underline; /* 마우스 올리면 밑줄 표시 */
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
            
        .review-container {
            max-width: 900px;
        }
        
        /* 리뷰 카드 */
        .review-card {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            text-align: left; /* 중앙 정렬 방지 */
        }
        
        /* 리뷰 목록 */
        .review-list {
            max-height: 400px;
            overflow-y: auto;
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 10px;
            background: white;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            text-align: left; /* 중앙 정렬 방지 */
        }
        
        /* 캐러셀 화살표 스타일 조정 */
        .carousel-control-prev-icon,
        .carousel-control-next-icon {
            filter: invert(100%); /* 화살표 색상을 흰색으로 변경 */
            background-color: rgba(0, 0, 0, 0.5); /* 반투명 배경 추가 */
            border-radius: 50%; /* 동그란 버튼 모양 */
            width: 50px;
            height: 50px;
        }
        
        /* 화살표 버튼 크기 조정 */
        .carousel-control-prev,
        .carousel-control-next {
            width: 8%; /* 버튼 크기 키우기 */
        }
        
        .festival-img {
            justify-content: space-around;
            width: 300px;
            max-width: 300px;
            height:300px;
            border-radius: 10px;
            border: 2px solid #ddd; /* 테두리 추가 */
            transition: border-color 0.3s ease; /* 호버 효과를 부드럽게 */
        }
        
        .festival-img:hover {
            border-color: #007bff; /* 호버 시 테두리 색상 변경 */
        }
        
        .additional-images-count {
            font-size: 16px;
            color: white;
            background: rgba(0, 0, 0, 0.7); /* 반투명 검정 배경 */
            border-radius: 10px;
            padding: 8px 12px;
            position: absolute;
            bottom: -40px; /* 이미지 아래로 위치 조정 */
            left: 50%;
            transform: translateX(-50%); /* 정확한 중앙 정렬 */
            text-align: center;
            white-space: nowrap; /* 텍스트 줄바꿈 방지 */
        }

           
        /* 반응형: 모바일에서도 같은 정렬 유지 */
        @media (max-width: 768px) {
            .review-container {
                max-width: 100%;
                padding: 10px;
            }
            .review-card,
            .review-list {
                max-width: 100%;
            }
        }
        
            .like-container {
            display: flex;
            align-items: center;
            justify-content: start;
            gap: 10px; /* 하트와 숫자 사이 여백 */
        }
        
        #likeIcon {
            font-size: 36px; /* 하트 크기 키우기 */
            cursor: pointer;
        }
        
        #likeCount {
            font-size: 24px; /* 숫자 크기 키우기 */
            font-weight: bold;
        }
        
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <c:set var="pageTitle" value="축제 상세정보" />
            <%@ include file="../fragments/header.jspf" %>
        </div>
        
        <input type="hidden" id="festivalId" value="${festival.feId}" />

        <div class="main-visual" style="background-image: url('${pageContext.request.contextPath}/uploads/${festival.feImageMain}')"></div>
        
        <hr style="border: 1px solid #ddd; width: 100%;">

        <main class="container content">
            <input type="hidden" class="form-control" id="id" type="text" value="${festival.feId}" readonly />
        
            <h1 style="margin-bottom: 20px; margin-top: 40px;">🎆 ${festival.feName}</h1>

            <h3 style="margin-top: 40px;">📅 일정</h3>
            
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
            
            <script>
                const contextPath = '${pageContext.request.contextPath}';
            </script>

            <h3 style="margin-top: 40px;">🖼️ 축제 상세 이미지</h3>
            <c:if test="${not empty festivalImages}">
                <div class="container mt-4 mb-4">
                    <div class="row row-cols-1 row-cols-md-3 g-4">
                        <c:forEach var="image" items="${festivalImages}" varStatus="status">
                            <c:if test="${status.index < 3}">
                                <div class="col position-relative text-center">
                                    <div class="image-wrapper">
                                        <img src="${pageContext.request.contextPath}/uploads/${image.fiImages}" 
                                             class="festival-img"
                                             alt="축제 이미지 ${status.index + 1}"
                                             data-bs-toggle="modal"
                                             data-bs-target="#imageModal">
                                    </div>                                    
                                    <!-- 남은 사진 개수 표시 -->
                                    <c:if test="${status.index == 2 && fn:length(festivalImages) > 3}">
                                        <div class="additional-images-count position-absolute">
                                            +${fn:length(festivalImages) - 3}장
                                        </div>
                                    </c:if>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            
                <!-- 이미지 모달 -->
                <div class="modal fade" id="imageModal" tabindex="-1">
                    <div class="modal-dialog modal-lg modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">축제 이미지</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <div class="modal-body">
                                <div id="carouselExampleIndicators" class="carousel slide">
                                    <div class="carousel-inner">
                                        <c:forEach var="image" items="${festivalImages}" varStatus="status">
                                            <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                                <img src="${pageContext.request.contextPath}/uploads/${image.fiImages}" 
                                                     class="d-block w-100" 
                                                     alt="축제 이미지 ${status.index + 1}">
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Previous</span>
                                    </button>
                                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Next</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
            
            <!-- 좋아요 버튼 UI -->
            <input type="hidden" id="contextPath" value="${pageContext.request.contextPath}" />
            <input type="hidden" id="festivalId" value="${festival.feId}" />
            <input type="hidden" id="meId" value="${sessionScope.meId}" />
            
            <div class="like-container text-center">
                <button id="likeBtn" class="btn btn-outline-none" onclick="toggleLike()">
                    <span id="likeIcon">${isLiked ? '❤️' : '🤍'}</span>
                </button>
                <span id="likeCount">${likeCount}</span>
            </div>
            
            <hr style="border: 1px solid #ddd; width: 100%;">
            
            <h3 class="mb-3">📄 상세 내용</h3>
            <p class="festival-contents">${festival.feContents}</p>
            
            <hr style="border: 1px solid #ddd; width: 100%;">
            
            <div class="festival-poster-container text-center">
                <img src="${pageContext.request.contextPath}/uploads/${festival.fePoster}" 
                     class="festival-poster-img" 
                     alt="축제 포스터">
            </div>
            
            <div class="row">
                <div class="festival-info">
                    <p><span class="icon">📍</span> ${festival.feAddress}</p>
                    <p><span class="icon">💰</span> ${festival.feFee}</p>
                    <p><span class="icon">📢</span> ${festival.meSponsor}</p>
                    <p><span class="icon">📞</span> ${festival.fePhone}</p>
                    <p><span class="icon">🌐</span> 
                        <a href="${festival.feHomepage}" target="_blank" class="homepage-link">${festival.feHomepage}</a>
                    </p>
                </div>
            </div>
            
            <hr style="border: 1px solid #ddd; width: 100%;">
            
            <h3 style="margin-top: 40px;">🗺️ 찾아오시는 길</h3>

            <!-- 카카오맵 표시 -->
            <div id="map" class="mb-3" style="position: relative;">
                <!-- 길찾기 버튼 -->
                <button id="direction-btn" class="btn btn-primary"  
                        style="position: absolute; bottom: 10px; right: 10px; z-index: 10; font-family:'sansMedium';">
                    길찾기
                </button>
            </div>
            
            <hr style="border: 1px solid #ddd; width: 100%;">
            
            <button class="btn btn-outline-secondary mt-2" id="btnToggleReview">리뷰 보기</button>
        
            <div class="row mt-4">  <!-- 같은 줄 맞추기 -->
                <div class="col-md-8">  
                    <div class="collapse" id="collapseReviews">
                        <!-- 평균 별점 -->
                        <p class="text-warning text-start"><strong id="avgGrade">축제 한줄평 ⭐</strong></p>
        
                        <!-- 리뷰 등록 UI -->
                        <div class="card card-body review-card text-start">
                            <div class="row">
                                <div class="col-md-9">
                                    <input type="hidden" id="signedInUser" value="${signedInUser != null ? signedInUser : ''}" readonly />
                                    <input type="hidden" id="feId" value="${festival.feId}" readonly />
                                    <input type="text" class="form-control mt-2" id="reTitle" placeholder="리뷰 제목">
                                    <select class="form-select mt-2" id="reGrade">
                                        <option value="5">⭐⭐⭐⭐⭐</option>
                                        <option value="4">⭐⭐⭐⭐</option>
                                        <option value="3">⭐⭐⭐</option>
                                        <option value="2">⭐⭐</option>
                                        <option value="1">⭐</option>
                                    </select>
                                    <textarea class="form-control mt-2" rows="3" id="reContent" placeholder="리뷰 입력"></textarea>
                                </div>
                                <div class="col-md-3 d-flex align-items-end">
                                    <button class="btn btn-outline-success w-100 mt-2" id="btnRegisterReview">등록</button>
                                </div>
                            </div>
                        </div>
        
                        <!-- 리뷰 목록 -->
                        <div class="card card-body review-card mt-3 text-start">
                            <div id="divReviews" class="review-list"></div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

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
                            <option value="5">⭐⭐⭐⭐⭐</option>
                            <option value="4">⭐⭐⭐⭐</option>
                            <option value="3">⭐⭐⭐</option>
                            <option value="2">⭐⭐</option>
                            <option value="1">⭐</option>
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
        
            // 📌 위도, 경도가 null이면 주소 기반으로 변환
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
    
    <c:url var="festivalLikesJS" value="/js/festival-likes.js" /> 
    <script src="${festivalLikesJS}"></script>
    
    <c:url var="festivalLikesUIJS" value="/js/festival-likes-ui.js" /> 
    <script src="${festivalLikesUIJS}"></script>


    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
        crossorigin="anonymous"></script>

</body>







</html>


