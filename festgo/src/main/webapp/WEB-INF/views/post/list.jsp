<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- 로그인된 사용자의 역할 ID 가져오기 --%>
<%
    Integer userRole = (Integer) session.getAttribute("mr_id"); 
    request.setAttribute("userRole", userRole); // userRole을 request 속성으로 추가
%>
<p>현재 로그인된 사용자 역할: <%= userRole %></p>  <!-- 🌟 디버깅용 출력 -->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Fest Go</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
        /* 테이블 스타일 */
        table {
            table-layout: fixed;
            width: 100%;
            border-collapse: collapse;
        }
    
        th, td {
            text-align: center;
            word-wrap: break-word;
            padding: 10px;
        }
    
        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
    
        /* 공지사항 제목 스타일 */
        .notice-title {
            color: #d9534f; /* 빨간색 */
            font-weight: bold; /* 볼드 처리 */
            text-decoration: none; /* 밑줄 제거 */
        }
    
        .notice-title:hover {
            text-decoration: underline; /* 마우스를 올렸을 때 밑줄 */
        }
    
        /* 일반 게시글 제목 스타일 */
        .normal-title {
            color: #000; /* 검정색 텍스트 */
            font-weight: normal; /* 일반 텍스트 */
            text-decoration: none; /* 밑줄 제거 */
        }
    
        .normal-title:hover {
            text-decoration: underline; /* 마우스를 올렸을 때 밑줄 */
        }
    
        /* 공지사항 배지 스타일 */
        .notice-label {
            background-color: #efd1d1; /* 연한 빨간색 배경 */
            color: #f07e7e; /* 텍스트 색상 */
            font-weight: bold;
            padding: 5px;
            border-radius: 4px; /* 둥근 모서리 */
            text-align: center;
        }
    
        /* 배지 스타일 */
        .badge-danger {
            background-color: #efd1d1;
            color: #d9534f;
            font-size: 12px;
            padding: 5px 10px;
            border-radius: 12px;
        }
    
        /* 테이블 행 hover 효과 */
        .table-hover tbody tr:hover {
            background-color: #f1f1f1; /* 행 hover 배경색 */
        }
        
        /* 버튼 고정 위치 */
        .card-header .ms-auto {
            margin-left: auto; /* 글쓰기 버튼을 항상 오른쪽으로 정렬 */
        }
    
        /* 숨기기 버튼 간격 조정 */
        #toggleNotice {
            margin-right: 10px; /* 글쓰기 버튼과 간격 유지 */
        }
        .delete-button-container {
		    text-align: left; /* 버튼을 왼쪽 정렬 */
		    margin-top: 5px; /* 체크박스와의 간격 */
		}
		
		.delete-button {
		    width: 80px; /* 버튼 크기 */
		    padding: 5px 10px;
		    font-size: 12px;
		    background-color: #dc3545;
		    color: white;
		    border: none;
		    border-radius: 5px;
		    cursor: pointer;
		}
		
		.delete-button:hover {
		    background-color: #c82333;
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
                    <div class="d-flex justify-content-between">
                        <!-- 공지사항 숨기기 버튼 (1페이지에서만 표시) -->
                        <c:if test="${currentPage == 1}">
                            <button id="toggleNotice" class="btn btn-outline-primary mb-3">공지 숨기기</button>
                        </c:if>
                        <div class="ms-auto">
                            <c:url var="postCreatePage" value="/post/create"/>
                            <a href="${postCreatePage}" class="btn btn-primary mb-3 ">글쓰기</a>
                        </div>
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
                                	<c:if test="${userRole == 3}">
							            <th style="width: 5%;">
							                <input type="checkbox" id="selectAll"> <!-- ✅ 헤더에 전체 선택 버튼 -->
							            </th>
							        </c:if>
                                	<th style="width: 10%;"></th>
                                    <th>제목</th>
                                    <th>작성자</th>
                                    <th>작성날짜</th>
                                    <th>조회수</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- 공지사항 (1페이지에서만 표시) -->
                                <c:if test="${currentPage == 1}">
                                    <c:forEach var="notice" items="${notices}">
                                        <tr class="notice">
                                        	<c:if test="${userRole == 3}">
							                    <td>
							                        <input type="checkbox" name="deleteIds" value="${notice.poId}"> <!-- ✅ 관리자만 체크박스 표시 -->
							                    </td>
							                </c:if>
                                            <td class="notice-label">
                                                <span class="badge badge-danger">공지</span> <!-- 배지 스타일 -->
                                            </td>
                                            <td>
                                                <c:url var="postDetailsPage" value="/post/details">
                                                    <c:param name="poId" value="${notice.poId}"/>
                                                </c:url>
                                                <a href="${postDetailsPage}" class="notice-title post-link">${notice.poTitle}</a>
                                            </td>
                                            <td>${notice.poAuthor}</td>
                                            <td>${notice.poModifiedTime}</td>
                                            <td>${notice.poViews}</td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                
                                <!-- 일반 게시글 -->
                                <c:forEach var="p" items="${posts}">
                                    <tr class="normal">
                                    	<c:if test="${userRole == 3}">
						                    <td>
						                        <input type="checkbox" name="deleteIds" value="${p.poId}">
						                    </td>
						                </c:if>
                                        <td>${p.poId}</td>
                                        <td>
                                            <c:url var="postDetailsPage" value="/post/details">
                                                <c:param name="poId" value="${p.poId}"/>
                                            </c:url>
                                            <a href="${postDetailsPage}"class="normal-title post-link">${p.poTitle}</a>
                                        </td>
                                        <td>${p.poAuthor}</td>
                                        <td>${p.poModifiedTime}</td>
                                        <td>${p.poViews}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <c:if test="${userRole == 3}">
						    <button id="deleteSelected" class="delete-button">선택 삭제</button>
						</c:if>
                    </div>
                    <!-- 페이징 네비게이션 -->
                    <nav class="pagination-container mt-auto">
                        <ul class="pagination justify-content-center">
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <c:url var="prevPageUrl" value="/post/search">
                                    <c:param name="category" value="${category}"/>
                                    <c:param name="keyword" value="${keyword}"/>
                                    <c:param name="page" value="${currentPage - 1}"/>
                                    <c:param name="pageSize" value="${pageSize}"/>
                                </c:url>
                                <a class="page-link" href="${prevPageUrl}">이전</a>
                            </li>
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
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
                    integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
                    crossorigin="anonymous"></script>
        <script>
            document.getElementById('toggleNotice').addEventListener('click', function () {
                const notices = document.querySelectorAll('.notice');
                const button = this;
                notices.forEach(function (notice) {
                    notice.style.display = notice.style.display === 'none' ? '' : 'none';
                });
                button.textContent = button.textContent === '공지 숨기기' ? '공지 보기' : '공지 숨기기';
            });
        </script>
        <script>
        document.addEventListener('DOMContentLoaded', function () {
            const selectAllCheckbox = document.getElementById('selectAll');
            const deleteButton = document.getElementById('deleteSelected');

            if (selectAllCheckbox) {
                selectAllCheckbox.addEventListener('click', function () {
                    document.querySelectorAll('input[name="deleteIds"]').forEach(cb => cb.checked = this.checked);
                });
            }

            if (deleteButton) {
                deleteButton.addEventListener('click', function () {
                    const selected = Array.from(document.querySelectorAll('input[name="deleteIds"]:checked'))
                                        .map(cb => cb.value);

                    if (selected.length === 0) {
                        alert('삭제할 게시글을 선택하세요.');
                        return;
                    }

                    if (confirm('선택한 게시글을 삭제하시겠습니까?')) {
                    	fetch('${pageContext.request.contextPath}/post/delete-multiple', {  
                    		//  JSP의 `contextPath`를 사용하여 동적으로 경로 설정
                            method: 'POST',
                            headers: { 'Content-Type': 'application/json' },
                            body: JSON.stringify({ postIds: selected })  
                        })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                alert('삭제 완료!');
                                location.reload(); 
                            } else {
                                alert('삭제 실패: ' + data.message);
                            }
                        })
                        .catch(error => {
                            alert('요청 중 오류 발생: ' + error);
                        });
                    }
                });
            }
        });

		</script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
