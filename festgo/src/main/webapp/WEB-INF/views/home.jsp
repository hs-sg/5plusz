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
            
        <style>
            .searchArea #searchText {
                border: none;
                border-bottom: 1px solid #1A1A24;
            }
            .searchArea #searchBtn {
                width: 20px;
                height: 20px;
                background: url(https://kfescdn.visitkorea.or.kr/kfes/resources/img/search_box_btn.png) no-repeat center;
                background-size: 20px 20px;
                border: none;
            }
            .keyword a {
                color: #80808a;
                display: flex;
                align-items: center;
                justify-content: center;
                box-sizing: border-box;
                padding: 0 16px;
                height: 100%;
                border-radius: 99px;
                border: 1px solid #80808a;
            }
            .keyword ul {
                flex-wrap: wrap;
                gap: 8px;
                display: flex;
                margin-top: 15px;
                list-style: none;
            }
            .keyword li {
                text-align: -webkit-match-parent;
            }
            .swiper {
                width: 600px;
                height: 300px;
            }
        </style>
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
                    <!--<div class="bg-info" id="backgroundImage"></div>-->
                    <div class="innerVisual">
                        <div id="carouselExampleIndicators" class="carousel slide">
                            <div class="carousel-indicators">
                                <button type="button"
                                    data-bs-target="#carouselExampleIndicators"
                                    data-bs-slide-to="0" class="active"
                                    aria-current="true" aria-label="Slide 1"></button>
                                <button type="button"
                                    data-bs-target="#carouselExampleIndicators"
                                    data-bs-slide-to="1"
                                    aria-label="Slide 2"></button>
                                <button type="button"
                                    data-bs-target="#carouselExampleIndicators"
                                    data-bs-slide-to="2"
                                    aria-label="Slide 3"></button>
                            </div>
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <!-- <img src="..." class="d-block w-100" alt="..."> -->
                                    <svg class="bd-placeholder-img bd-placeholder-img-lg d-block w-100"
                                        width="800" height="400">
                                        <rect width="100%" height="100%" style="fill:#555"></rect>
                                        <text x="50%" y="50%" fill="#333" dx="-3em">축제 Visual 1</text>
                                    </svg>
                                </div>
                                <div class="carousel-item">
                                    <!-- <img src="..." class="d-block w-100" alt="..."> -->
                                    <svg class="bd-placeholder-img bd-placeholder-img-lg d-block w-100"
                                        width="800" height="400">
                                        <rect width="100%" height="100%" style="fill:#555"></rect>
                                        <text x="50%" y="50%" fill="#333" dx="-3em">축제 Visual 2</text>
                                    </svg>
                                </div>
                                <div class="carousel-item">
                                    <!-- <img src="..." class="d-block w-100" alt="..."> -->
                                    <svg class="bd-placeholder-img bd-placeholder-img-lg d-block w-100"
                                        width="800" height="400">
                                        <rect width="100%" height="100%" style="fill:#555"></rect>
                                        <text x="50%" y="50%" fill="#333" dx="-3em">축제 Visual 3</text>
                                    </svg>
                                </div>
                            </div>
                            <button class="carousel-control-prev"
                                type="button"
                                data-bs-target="#carouselExampleIndicators"
                                data-bs-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Previous</span>
                            </button>
                            <button class="carousel-control-next"
                                type="button"
                                data-bs-target="#carouselExampleIndicators"
                                data-bs-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Next</span>
                            </button>
                        </div>
                    </div>                
                </div>
            </section>
            <!--// 쇼케이스 섹션  -->
            <!-- 검색 -->
            <section class="pt-3">
                <form class="row gx-3 gy-2 align-items-center">
                    <div class="col-sm-3">
                        <div class="input-group">
                            <div class="input-group-text">@</div>
                            <select class="form-select" id="selectTime">
                                <option selected>시기</option>
                                <option value="1">1월</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="input-group">
                            <div class="input-group-text">@</div>
                            <select class="form-select" id="selectRegion">
                                <option selected>지역</option>
                                <option value="1">서울</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="input-group">
                            <div class="input-group-text">@</div>
                            <select class="form-select" id="selectTheme">
                                <option selected>테마</option>
                                <option value="1">눈꽃</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="searchArea">
                            <input id="searchText" name="searchText" class="searchLine" title="검색"
                                value placeholder="검색어를 입력해주세요."/>
                            <button type="button" id="searchBtn"></button>
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
            <!-- HOT & NEW 축제 -->
            <section>
                <div class="mt-3">
                    <h2>
                        <em>HOT & NEW</em> 축제
                    </h2>
                    <!-- Slider main container -->
                    <div class="swiper">
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
        
        <c:url var="homeJS" value="/js/home.js"/>
        <script src="${ homeJS }"></script>
    </body>
</html>