<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>   
<!DOCTYPE html>
<html>
		<head>
		    <meta charset="UTF-8"/>
		    <meta name="viewport" content="width=device-width, initial-scale=1">
		    <title>Fest Go</title>
		    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
		          rel="stylesheet" 
		          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
		          crossorigin="anonymous">
		    <style> /* 헤더부분 페이징 처리시 위치 고정*/
			    table {
			        table-layout: fixed; /* 테이블 레이아웃 고정 */
			        width: 100%; /* 테이블 너비 고정 */
			        border-collapse: collapse; /* 테이블 경계선 정리 */
			    }
			
			    th, td {
			        text-align: center; /* 중앙 정렬 */
			        word-wrap: break-word; /* 긴 단어를 자동으로 줄 바꿈 */
			        padding: 10px; /* 내부 여백 */
			    }
			
			    th {
			        background-color: #f2f2f2; /* 헤더 배경색 */
			        font-weight: bold;
			    }
			
			    /* 열 너비 고정 */
			    th:nth-child(1), td:nth-child(1) { /* 번호 열 */
			        width: 10%;
			    }
			    th:nth-child(2), td:nth-child(2) { /* 제목 열 */
			        width: 40%;
			    }
			    th:nth-child(3), td:nth-child(3) { /* 작성자 열 */
			        width: 20%;
			    }
			    th:nth-child(4), td:nth-child(4) { /* 작성 날짜 열 */
			        width: 20%;
			    }
			    th:nth-child(5), td:nth-child(5) { /* 조회수 열 */
			        width: 10%;
			    }
			</style>
		    
		</head>
		<body>
		    <div class="container-fluid">
		        <c:set var="pageTitle" value="게시판 목록"/>
		        <%@ include file="../fragments/header.jspf" %>
		    </div>
		
		    <main class="container mt-3">
		        <div class="card">
		            <div class="card-header">
		                <!-- 글쓰기 버튼 -->
		                <div class="d-flex justify-content-end">
		                    <c:url var="postCreatePage" value="/post/create"/>
		                    <a href="${postCreatePage}" class="btn btn-primary">글쓰기</a>
		                </div>
		
		                <!-- 검색 폼 -->
		                <c:url var="postSearchPage" value="/post/search"/>
		                <form action="${postSearchPage}" method="get" class="mt-2">
		                    <div class="row">
		                        <div class="col-3">
		                            <select class="form-control" name="category">
		                                <option value="t" ${category == 't' ? 'selected' : ''}>제목</option>
		                                <option value="c" ${category == 'c' ? 'selected' : ''}>내용</option>
		                                <option value="tc" ${category == 'tc' ? 'selected' : ''}>제목+내용</option>
		                                <option value="a" ${category == 'a' ? 'selected' : ''}>작성자</option>
		                            </select>
		                        </div>
		                        <div class="col-7">
		                            <input class="form-control" type="text" name="keyword" value="${keyword}" placeholder="검색어 입력" required/>
		                        </div>
		                        <div class="col-2">
		                            <input type="submit" value="검색" class="btn btn-outline-secondary"/>
		                        </div>
		                    </div>
		                </form>
		            </div>
		
		            <div class="card-body">
		                <div class="table-responsive d-flex flex-column" style="min-height: 400px;">
		                    <table class="table table-striped table-hover">
		                        <thead class="table-primary">
		                            <tr>
		                                <th>번호</th>
		                                <th>제목</th>
		                                <th>작성자</th>
		                                <th>작성날짜</th>
		                                <th>조회수</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                            <c:forEach items="${posts}" var="p">
		                                <tr>
		                                    <td>${p.poId}</td>
		                                    <td>
		                                        <c:url var="postDetailsPage" value="/post/details">
		                                            <c:param name="poId" value="${p.poId}"/>
		                                        </c:url>
		                                        <a href="${postDetailsPage}">${p.poTitle}</a>
		                                    </td>
		                                    <td>${p.poAuthor}</td>
		                                    <td>${p.poModifiedTime}</td>
		                                    <td>${p.poViews}</td>
		                                </tr>
		                            </c:forEach>
		                        </tbody>
		                    </table>
		                </div>
		
		                <!-- 페이징 네비게이션 -->
		                <nav class="pagination-container mt-auto">
		                    <ul class="pagination justify-content-center">
		                       <!-- 이전 페이지 -->
		                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
		                                <c:url var="prevPageUrl" value="/post/search">
		                                    <c:param name="category" value="${category}"/>
		                                    <c:param name="keyword" value="${keyword}"/>
		                                    <c:param name="page" value="${currentPage - 1}"/>
		                                    <c:param name="pageSize" value="${pageSize}"/>
		                                </c:url>
		                                <a class="page-link" href="${prevPageUrl}">이전</a>
		                            </li>
		                            
		                            <!-- 페이지 번호 -->
		                            <c:forEach var="i" begin="1" end="${totalPages}">
		                                <c:url var="pageUrl" value="/post/search">
		                                    <c:param name="category" value="${category}"/>
		                                    <c:param name="keyword" value="${keyword}"/>
		                                    <c:param name="page" value="${i}"/>
		                                    <c:param name="pageSize" value="${pageSize}"/>
		                                </c:url>
		                                <li class="page-item ${currentPage == i ? 'active' : ''}">
		                                    <a class="page-link" href="${pageUrl}">${i}</a>
		                                </li>
		                            </c:forEach>
		                            
		                            <!-- 다음 페이지 -->
		                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
		                                <c:url var="nextPageUrl" value="/post/search">
		                                    <c:param name="category" value="${category}"/>
		                                    <c:param name="keyword" value="${keyword}"/>
		                                    <c:param name="page" value="${currentPage + 1}"/>
		                                    <c:param name="pageSize" value="${pageSize}"/>
		                                </c:url>
		                                <a class="page-link" href="${nextPageUrl}">다음</a>
		                            </li>
		                    </ul>
		                </nav>
		            </div>
		        </div>
		    </main>
		    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
		    <!-- Bootstrap JS -->
		    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
		            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
		            crossorigin="anonymous">
		    </script>
		</body>
		</html>
