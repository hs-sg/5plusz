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
                        <div class="swiper mainVisualSwiper">
                            <div class="swiper-wrapper">
                                <div class="swiper-slide">Slide 1</div>
                                <div class="swiper-slide">Slide 2</div>
                                <div class="swiper-slide">Slide 3</div>
                                <div class="swiper-slide">Slide 4</div>
                                <div class="swiper-slide">Slide 5</div>
                                <div class="swiper-slide">Slide 6</div>
                                <div class="swiper-slide">Slide 7</div>
                                <div class="swiper-slide">Slide 8</div>
                                <div class="swiper-slide">Slide 9</div>
                            </div>
                            <div class="swiper-button-next"></div>
                            <div class="swiper-button-prev"></div>
                            <div class="swiper-pagination"></div>
                        </div>
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
                        <em>좋아요</em> 많은 키워드
                    </h2>
                    <ul>
                        <li>
                            <a href=#>눈꽃</a>
                        </li>
                        <li>
                            <a href=#>산천어</a>
                        </li>
                    </ul>
                </div>
            </section>
            <!--// 추천 축제 -->
            <!-- AJAX로 불러온 축제 정보들이 표시될 영역 -->
            <section>
                <div id="eventDetails" class="my-4 text-center"></div>
            </section>
            <!--// AJAX로 불러온 축제 정보들이 표시될 영역 -->
            <!-- HOT & NEW 축제 -->
            <section>
                <div class="mt-3">
                    <h2>
                        <em>HOT & NEW</em> 축제
                    </h2>
                    <!-- Slider main container -->
                    <div class="swiper festivalSwiper">
                        <!-- Additional required wrapper -->
                        <div class="swiper-wrapper">
                            <!-- Slides -->
                            <div class="swiper-slide">Slide 1</div>
                            <div class="swiper-slide">Slide 2</div>
                            <div class="swiper-slide">Slide 3</div>
                            ...
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
        
        <!-- JSP 페이지 내 스크립트 부분 -->
        <script>
            // JSP EL을 통해 컨텍스트 경로를 자바스크립트 변수에 저장
            var contextPath = '${pageContext.request.contextPath}';
        </script> 
        
        <c:url var="homeJS" value="/js/home.js"/>
        <script src="${ homeJS }"></script>
    </body>
</html>