<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
         
        <!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정 -->
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Fest Go</title>
        
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
        
        <!-- Swiper CSS -->
        <link href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" rel="stylesheet"/>
            
        <c:url var="homeCSS" value="/css/home.css"/>
        <link href="${ homeCSS }" rel="stylesheet"/>
    </head>
    <body>
        <div class="container-fluid">
            <c:set var="pageTitle" value="홈 페이지" />
            <%@ include file="./fragments/header.jspf" %>
        </div>
        <main>
    <!-- 쇼케이스 섹션 (화살표 추가) -->
<section class="pt-3 my-5">
    <div class="mainVisual">
        <div class="innerVisual">
            <swiper-container 
                class="mainSwiper" 
                pagination="true" 
                pagination-dynamic-bullets="true" 
                navigation="true">  <!-- 🔹 네비게이션(화살표) 활성화 -->

                <swiper-slide>
                    <c:url var="linkMainVisual1" 
                        value="/fest/detail?feId=${ festivalsForMainVisual1.feId }"></c:url>
                    <a href="${ linkMainVisual1 }">
                        <c:url var="mainVisual1Img" 
                            value="/uploads/${ festivalsForMainVisual1.feImageMain }"></c:url>
                        <img src="${ mainVisual1Img }" alt="">
                    </a>
                    <div class="swiper-slide-container-box theme">
					    <div class="main-slide-title">
					        <span class="sort theme">NEW</span>
					        <h2>축제는 FestGo에서 찾아보세요!</h2>
					    </div>
					</div>

                </swiper-slide>

                <swiper-slide>
                    <c:url var="linkMainVisual2" 
                        value="/fest/detail?feId=${ festivalsForMainVisual2.feId }"></c:url>
                    <a href="${ linkMainVisual2 }">
                        <c:url var="mainVisual2Img" 
                            value="/uploads/${ festivalsForMainVisual2.feImageMain }"></c:url>
                        <img src="${ mainVisual2Img }" alt="">
                    </a>
                    <div class="swiper-slide-container-box theme">
					    <div class="main-slide-title">
					        <span class="sort theme">NEW</span>
					        <h2>축제는 FestGo에서 찾아보세요!</h2>
					    </div>
					</div>

                </swiper-slide>

            </swiper-container>  
        </div>           
    </div>
</section>
                      
            </div>           
        </div>
    </section>

    <section class="my-5">
    <div class="keyword p-3">
        <h2 class="mb-3 text-center keyword-title">
            <em>추천 축제</em> 키워드
        </h2>
        <ul class="d-flex flex-wrap justify-content-center">
            <c:forEach var="t" items="${themesInFestival}">
                <li class="mx-2 my-2">
                    <a href="#" class="linkRecommendTheme px-3 py-2 theme-button">
                        #${t.theName}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>
</section>


    
    <!-- 검색 섹션 (위쪽 여백 추가) -->
<section class="pt-5 pb-4">
    <h2 class="mb-4 search-title">축제 검색</h2>
    <form class="row gx-3 gy-2 align-items-center" id="searchFestival">
    <div class="col-sm-3">
        <div class="input-group">
            <div class="input-group-text">📆</div>  <!-- @ 대신 시계 이모지 사용 -->
            <select class="form-select" id="selectTime" name="month">
                <option value="" selected>시기 선택</option>
                <% for (int i = 1; i <= 12; i++) { %>
                <option value="<%= i %>"><%= i %>월</option>
                <% } %>
            </select>
        </div>
    </div>

    <div class="col-sm-3">
        <div class="input-group">
            <div class="input-group-text">📍</div>  <!-- @ 대신 위치 이모지 사용 -->
            <select class="form-select" id="selectLocation" name="lcId">
                <option value="" selected>지역 선택</option>
                <c:forEach var="lc" items="${locations}">
                    <option value="${lc.lcId}">${lc.lcName}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="col-sm-3">
        <div class="input-group">
            <div class="input-group-text">🎨</div>  <!-- @ 대신 팔레트 이모지 사용 -->
            <select class="form-select" id="selectTheme" name="theId">
                <option value="" selected>테마 선택</option>
                <c:forEach var="the" items="${themes}">
                    <option value="${the.theId}">${the.theName}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="col-sm-3">
        <div class="searchArea">
            <input class="searchLine" id="searchText" name="keyword" 
                   placeholder="검색어를 입력해주세요" />
            <button type="submit" id="searchBtn"></button>
        </div>
    </div>
</form>
</section>
    <!-- AJAX로 불러온 축제 정보들이 표시될 영역 -->
    <section class="my-5">
        <div id="eventDetails" class="my-4 text-center"></div>
        <div id="showMoreFestival" class="d-none mb-5 d-grid gap-2 col-6 mx-auto">
            <button id="btnShowMoreFestival" type="button" class="btn btn-outline-secondary px-4 py-3">
                더보기 +
            </button>
        </div>
    </section>

    <section class="my-5">
    <div class="mt-3">
        <h2 class="mb-4 keyword-title">새로 등록된 축제</h2>
        <div class="swiper festivalSwiper">
            <div class="swiper-wrapper">
                <c:forEach var="fn" items="${festivalsForNewVisual}">
                    <div class="swiper-slide">
                        <c:url var="linkNewFestival" value="/fest/detail?feId=${fn.feId}"></c:url>
                        <a href="${linkNewFestival}">
                            <c:url var="linkNewFestivalImg" value="/uploads/${fn.feImageMain}"></c:url>
                            <img src="${linkNewFestivalImg}" alt="${fn.feName}">
                            <div class="festival-name">${fn.feName}</div>
                        </a>
                    </div>
                </c:forEach>
            </div>
            <div class="swiper-button-prev"></div>
            <div class="swiper-button-next"></div>
            <div class="swiper-scrollbar"></div>
        </div>
    </div>
</section>
</main>

        
        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
             integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
             crossorigin="anonymous"></script>

        <!-- Axios Http JS -->    
        <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
        
        <!-- Swiper JS -->
        <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-element-bundle.min.js"></script>
        
        <!-- JSP 페이지 내 스크립트 부분 -->
        <script>
            // JSP EL을 통해 컨텍스트 경로를 자바스크립트 변수에 저장
            var contextPath = '${pageContext.request.contextPath}';
        </script> 
        
        <c:url var="homeJS" value="/js/home.js"/>
        <script src="${ homeJS }"></script>
    </body>
</html>
