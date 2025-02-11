<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
    
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
         
        <!-- Bootstrap을 사용하기 위한 meta name="viewport"설정 -->
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
            <c:set var = "pageTitle" value = "홈 페이지" />
            <%@ include file="./fragments/header.jspf" %>
        </div>
        <main>
            <!-- 쇼케이스 섹션: 최신 축제, 추천 축제 -->
            <section class="pt-3">
                <div class="mainVisual">
                    <div class="innerVisual">
                        <swiper-container 
                            class="mainSwiper" 
                            pagination="true" 
                            pagination-dynamic-bullets="true">
                            <swiper-slide>
                                <c:url var="linkMainVisual1" 
                                    value="/fest/detail?feId=${ festivalsForMainVisual1.feId }"></c:url>
                                <a href="${ linkMainVisual1 }">
                                    <c:url var="mainVisual1Img" 
                                        value="/uploads/${ festivalsForMainVisual1.feImageMain }"></c:url>
                                    <img src="${ mainVisual1Img }" alt="">
                                </a>
                                <div
                                    class="swiper-slide-container-box theme">
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
                                <div
                                    class="swiper-slide-container-box theme">
                                    <div class="main-slide-title">
                                        <span class="sort theme">공지</span>
                                        <h2>FestGo 메인 비쥬얼 슬라이드 테스트</h2>
                                    </div>
                                </div>
                            </swiper-slide>
                        </swiper-container>                        
                    </div>           
                </div>
            </section>
            <!--// 쇼케이스 섹션  -->
            <!-- 검색 -->
            <section class="pt-3">
                <form class="row gx-3 gy-2 align-items-center" id="searchFestival">
                    <div class="col-sm-3">
                        <div class="input-group">
                            <div class="input-group-text">@</div>
                            <select class="form-select" id="selectTime" name="month">
                                <option value="" selected>시기</option>
                                <% // 드롭박스에 시기(1~12월)를 입력
                                for (int i = 1; i <= 12; i++) { %>
                                <option value="<%=i%>"><%=i%>월</option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    
                    <div class="col-sm-3">
                        <div class="input-group">
                            <div class="input-group-text">@</div>
                            <select class="form-select" id="selectLocation" name="lcId">
                                <option value="" selected>지역</option>
                                <c:forEach var="lc" items="${ locations }">
                                    <option value="${ lc.lcId }">${ lc.lcName }</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    
                    <div class="col-sm-3">
                        <div class="input-group">
                            <div class="input-group-text">@</div>
                            <select class="form-select" id="selectTheme" name="theId">
                                <option value="" selected>테마</option>
                                <c:forEach var="the" items="${ themes }">
                                    <option value="${ the.theId }">${ the.theName }</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    
                    <div class="col-sm-3">
                        <div class="searchArea">
                            <input class="searchLine" id="searchText" name="keyword" title="검색"
                                value placeholder="검색어를 입력해주세요."/>
                            <button type="submit" id="searchBtn"></button>
                        </div>
                    </div>
                </form>
            </section>
            <!--// 검색  -->
            <!-- 추천 축제 -->
            <section>
                <div class="keyword mt-3">
                    <h2>
                        <em>추천</em> 키워드
                    </h2>
                    <ul>
                        <c:forEach var="t" items="${ themesInFestival }">
                            <li>
                                <a href=# class="linkRecommendTheme" theme-id="${ t.theId }">${ t.theName }</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </section>
            <!--// 추천 축제 -->
            <!-- AJAX로 불러온 축제 정보들이 표시될 영역 -->
            <section>
                <div id="eventDetails" class="my-4 text-center"></div>
                <%-- 더보기 버튼용 div --%>
                <div id="showMoreFestival" class="d-none mb-5 d-grid gap-2 col-6 mx-auto">
                    <button id="btnShowMoreFestival" type="button" class="btn btn-outline-secondary">더보기 +</button>
                </div>
            </section>
            <!--// AJAX로 불러온 축제 정보들이 표시될 영역 -->
            <!-- HOT & NEW 축제 -->
            <section>
                <div class="mt-3">
                    <h2>새로 등록된 축제</h2>
                    <!-- Slider main container -->
                    <div class="swiper festivalSwiper">
                        <!-- Additional required wrapper -->
                        <div class="swiper-wrapper">
                            <!-- Slides -->
                            <c:forEach var="fn" items="${ festivalsForNewVisual }">
                                <div class="swiper-slide">
                                    <c:url var="linkNewFestival" value="/fest/detail?feId=${ fn.feId }"></c:url>
                                    <a href="${ linkNewFestival }" class="d-flex">
                                        <c:url var="linkNewFestivalImg" value="/uploads/${ fn.feImageMain }"></c:url>
                                        <img src="${ linkNewFestivalImg }" alt="">
                                        <div>${ fn.feName }</div>
                                    </a>
                                </div>
                            </c:forEach>
                        </div>
        
                        <!-- If we need navigation buttons -->
                        <div class="swiper-button-prev"></div>
                        <div class="swiper-button-next"></div>
        
                        <!-- If we need scrollbar -->
                        <div class="swiper-scrollbar"></div>
                    </div>
                </div>
            </section>
            <!--// HOT & NEW 축제 -->
            
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
        
        <!-- 권한 없음 알림 표시용 -->
		<script>
		document.addEventListener("DOMContentLoaded", () => {
		    const roleRequired = "<c:out value='${roleRequired}'/>";
		    if (roleRequired === "true") {
		        alert("권한이 없습니다.\n홈페이지로 이동합니다.")
		    }
		});
		</script>
    </body>
</html>