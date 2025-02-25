<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <!-- Bootstrap을 사용하기 위한 meta viewport 설정 -->
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Fest Go</title>
        
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
              rel="stylesheet" 
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
              crossorigin="anonymous" />
        
        <!-- Swiper CSS -->
        <link href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" rel="stylesheet"/>
        
        <!-- 기존 CSS 파일 링크 (경로가 이미 있으므로 그대로 사용) -->
        <c:url var="homeCSS" value="/css/home.css"/>
        <link href="${homeCSS}" rel="stylesheet"/>
        <!-- 또는 static 폴더를 사용 중이라면 -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home.css">
    </head>
    <body>
        <div class="container-fluid">
            <c:set var="pageTitle" value="Fest Go" />
            <%@ include file="./fragments/header.jspf" %>
        </div>
        <main>
            <!-- 쇼케이스 섹션 (화살표 추가) -->
            <section class="pt-3 mb-6">
                <div class="mainVisual">
                    <div class="innerVisual">
                        <swiper-container 
                            class="mainSwiper" 
                            pagination="true" 
                            pagination-dynamic-bullets="true" 
                            navigation="true">
                            
                            <swiper-slide>
                                <img src="${pageContext.request.contextPath}/static/images/main1.jpg" alt="main1" />
                            </swiper-slide>
                            <swiper-slide>
                                <img src="${pageContext.request.contextPath}/static/images/main2.jpg" alt="main2" />
                            </swiper-slide>
                            <!--  
                            <swiper-slide>
                                <c:url var="linkMainVisual1" 
                                    value="/fest/detail?feId=${festivalsForMainVisual1.feId}" />
                                <a href="${linkMainVisual1}">
                                    <c:url var="mainVisual1Img" 
                                        value="/uploads/${festivalsForMainVisual1.feImageMain}" />
                                    <img class="mainSlide" src="${mainVisual1Img}" alt="축제 이미지 1">
                                </a>
                                <div class="swiper-slide-container-box theme">
                                </div>
                            </swiper-slide>
                            
                            <swiper-slide>
                                <c:url var="linkMainVisual2" 
                                    value="/fest/detail?feId=${festivalsForMainVisual2.feId}" />
                                <a href="${linkMainVisual2}">
                                    <c:url var="mainVisual2Img" 
                                        value="/uploads/${festivalsForMainVisual2.feImageMain}" />
                                    <img class="mainSlide" src="${mainVisual2Img}" alt="축제 이미지 2">
                                </a>
                                <div class="swiper-slide-container-box theme">
                                </div>
                            </swiper-slide>
                            -->
                        </swiper-container>
                    </div>
                </div>
            </section>
                          
            <section class="my-5">
                <div class="keyword pt-3 ps-3 pe-3">
                    <h2 class="mb-3 text-center keyword-title">
                        <em>추천 축제</em> 키워드
                    </h2>
                    <ul class="d-flex flex-wrap justify-content-start">
                        <c:forEach var="t" items="${themesInFestival}">
                            <li class="mx-2 my-2">
                                <a href="#" class="linkRecommendTheme px-3 py-2 theme-button" theme-id="${ t.theId }">
                                    #${t.theName}
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </section>
    
            <!-- 검색 섹션 -->
            <section class="pb-4">
                <h2 class="mb-4 search-title">축제 검색</h2>
                <form class="row gx-3 gy-2 align-items-center" id="searchFestival">
                    <div class="col-sm-3">
                        <div class="input-group">
                            <div class="input-group-text">📆</div>
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
                            <div class="input-group-text">📍</div>
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
                            <div class="input-group-text">🎨</div>
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
                                    <c:url var="linkNewFestival" value="/fest/detail?feId=${fn.feId}" />
                                    <a href="${linkNewFestival}">
                                        <c:url var="linkNewFestivalImg" value="/uploads/${fn.feImageMain}" />
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
        
        <!-- 컨텍스트 경로를 위한 스크립트 -->
        <script>
            var contextPath = '${pageContext.request.contextPath}';
        </script>
        
        <c:url var="homeJS" value="/js/home.js"/>
        <script src="${homeJS}"></script>
        
        <!-- 권한 없음 알림 표시용 -->
        <script>
            document.addEventListener("DOMContentLoaded", () => {
                const roleRequired = "<c:out value='${roleRequired}'/>";
                if (roleRequired === "true") {
                    alert("권한이 없습니다.\n홈페이지로 이동합니다.");
                }
            });
        </script>
    </body>
</html>
