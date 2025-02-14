<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- 로그인된 사용자의 역할 ID 가져오기 --%>
<%
    Integer userRole = (Integer) session.getAttribute("mr_id"); 
    request.setAttribute("userRole", userRole); // userRole을 request 속성으로 추가
%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %> 

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Fest Go</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
       <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
        /* Font Faces */
        @font-face {        
            font-family: 'sansMedium';      
            src: url('../font/GmarketSansTTFMedium.ttf') format('truetype');        
        }       
        @font-face {        
            font-family: 'sansLight';       
            src: url('../font/GmarketSansTTFLight.ttf') format('truetype');     
        }       
        
        /* Base Styles */
        body {
            font-family: 'sansMedium';
        }
        
        tbody {
            font-family: 'sansLight';
        }
        
        /* Layout Components */
        .search-container {
            position: sticky;
            top: 0;
            background-color: white;
            padding: 10px 0;
            z-index: 1000;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        
        /* Table Styles */
        .table-container {
            background: #fff;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            margin-bottom: 20px;
        }
        
        table {
            table-layout: fixed;
            width: 100%;
            border-collapse: collapse;
        }
        
        th, td {
            text-align: center;
            word-wrap: break-word;
            padding: 14px;
        }
        
        th {
            background-color: #ffffff;
            font-weight: bold;
            border-top: 2px solid #333;
            border-bottom: 2px solid #333;
        }
        
        td {
            border-bottom: 1px solid #ddd;
        }
        
        /* Table Column Widths */
        th:nth-child(3) { width: 60%; }
        th:nth-child(4) { width: 20%; }
        th:nth-child(5) { width: 10%; }
        th:nth-child(6) { width: 8%; }
        
        /* Table Bottom Border */
        tbody tr:last-child td {
            border-bottom: 2px solid #333 !important;
        }
        
        /* Table Hover Effect */
        .table-hover tbody tr:hover {
            background-color: #f9f9f9;
            transition: background 0.3s ease-in-out;
        }
        
        /* Notice Styles */
        .notice-title {
            color: #d9534f;
            font-weight: bold;
            text-decoration: none;
        }
        
        .notice-title:hover {
            text-decoration: underline;
        }
        
        .notice-label {
            background-color: #efd1d1;
            color: #d9534f;
            font-weight: bold;
            padding: 5px 10px;
            border-radius: 12px;
            display: inline-block;
            text-align: center;
            font-size: 12px;
        }
        
        /* Normal Post Styles */
        .normal-title {
            color: #000;
            font-weight: normal;
            text-decoration: none;
        }
        
        .normal-title:hover {
            text-decoration: underline;
        }
        
        .normal-label {
            background-color: #ddd;
            color: #666;
            font-weight: bold;
            padding: 5px 10px;
            border-radius: 12px;
            display: inline-block;
            text-align: center;
        }
        
        /* Button Styles */
        .card-header .ms-auto {
            margin-left: auto;
        }

        #toggleNotice {
            margin-right: 10px;
        }
        
        .delete-button-container {
            text-align: left;
            margin-top: 5px;
        }
        
        .delete-button {
            width: 80px;
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
        
        /* Switch Button Styles */
        .form-check-input:checked {
            background-color: #479bde !important;
            border-color: #479bde !important;
        }
        
        .form-check-input {
            background-color: #ffffff;
            border-color: #6c757d;
        }
        
        /* Pagination Styles */
        .pagination-container {
            margin-top: 30px;
        }
        
        .pagination .page-link {
            border-radius: 6px;
            margin: 0 4px;
            color: #333;
            border: 1px solid #ddd;
            transition: 0.3s ease-in-out;
        }
        
        .pagination .page-item.active .page-link {
            background-color: #fff;
            border-color: #97d5f4;
            
            
        }
        
        .pagination .page-link:hover {
            background-color: #d5e9f3;
        }
       
        .btn1 {
            height: 36px;  /* 버튼 높이 */
            line-height: 36px;  /* 버튼 높이와 동일한 line-height 설정 */
            text-align: center;
            padding: 0 16px;
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
                            <div class="form-check form-switch mt-2" >
                              <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault">
                              <label class="form-check-label" for="flexSwitchCheckDefault">공지 숨기기</label>
                            </div>
                            <!--  <button id="toggleNotice" class="btn btn-outline-primary mb-3">공지 숨기기</button>-->
                        </c:if>
                        <div class="ms-auto">
                            <c:url var="postCreatePage" value="/post/create"/>
                            <a href="${postCreatePage}" class="btn1 btn btn-primary">글쓰기</a>
                        </div>
                    </div>
                    
                </div>
                <div class="card-body">
                    <div class="table-responsive d-flex flex-column" style="min-height: 400px;">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>

                                	<c:if test="${userRole == 3}">
							            <th style="width: 5%;">
							                <input type="checkbox" id="selectAll"> <!-- ✅ 헤더에 전체 선택 버튼 -->
							            </th>
							        </c:if>
                                	<th style="width: 10%;"></th>
                                    <th style="width: 60%;">제목</th>
                                    <th style="width: 20%;">작성자</th>

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

 
                                            <td>
                                                <span class="notice-label badge">공지</span> <!-- 배지 스타일 -->
                                            </td>
                                            <td style="text-align: left; padding-left: 15px; width: 60%;">
                                                <c:url var="postDetailsPage" value="/post/details">
                                                    <c:param name="poId" value="${notice.poId}"/>
                                                </c:url>
                                                <a href="${postDetailsPage}" class="notice-title post-link" style="font-family:'sansMedium'">${notice.poTitle}</a>
                                            </td>
                                            <td style="width: 20%;">${notice.poAuthor}</td>
                                            <td>${notice.formattedDate}</td>
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
                                        <td>
										   <span class="normal-label badge">일반</span> <!-- 일반 배지 -->
										</td>
                                        <td style="text-align: left; padding-left: 15px; width: 60%;">

                                            <c:url var="postDetailsPage" value="/post/details">
                                                <c:param name="poId" value="${p.poId}"/>
                                            </c:url>
                                            <a href="${postDetailsPage}"class="normal-title post-link" style="font-family:'sansMedium'">${p.poTitle}</a>
                                        </td>
                                        <td style="width: 20%;">${p.poAuthor}</td>
                                        <td>${p.formattedDate}</td>
                                        <td>${p.poViews}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <c:if test="${userRole == 3}">

						    <button id="deleteSelected" class="delete-button">선택 삭제</button>
						</c:if>
                    </div>
                        <!-- 검색 폼 -->
                        <c:url var="postSearchPage" value="/post/search"/>
                        <form action="${postSearchPage}" method="get" class="mt-2 mb-3 d-flex justify-content-center">
                            <div class="row align-items-center">
                                <div class="col-auto">
                                    <select class="form-control form-control-sm" name="category">
                                        <option value="t" ${category == 't' ? 'selected' : ''}>제목</option>
                                        <option value="c" ${category == 'c' ? 'selected' : ''}>내용</option>
                                        <option value="tc" ${category == 'tc' ? 'selected' : ''}>제목+내용</option>
                                        <option value="a" ${category == 'a' ? 'selected' : ''}>작성자</option>
                                    </select>
                                </div>
                                <div class="col-auto">
                                    <input class="form-control form-control-sm" type="text" name="keyword" value="${keyword}" placeholder="검색어 입력" required/>
                                </div>
                               <div class="col-auto">
                                            <!-- ✅ 버튼 크기 조정 -->
                                 <button type="submit" class="btn btn-outline-primary px-2 py-2 d-flex align-items-center justify-content-center">
                                    <i class="fas fa-search fs-6"></i> <!-- ✅ 돋보기 아이콘 크기 조정 -->
                                </button>
                            </div>
                            </div>
                        </form>
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
        document.addEventListener('DOMContentLoaded', function () {
            const toggleSwitch = document.getElementById('flexSwitchCheckDefault');
            const toggleLabel = document.querySelector('label[for="flexSwitchCheckDefault"]');

            toggleSwitch.addEventListener('change', function () {
                const notices = document.querySelectorAll('.notice');
                
                // 공지사항 숨기기 / 보이기
                notices.forEach(notice => {
                    notice.style.display = this.checked ? 'none' : '';
                });

                // 글씨 변경
                toggleLabel.textContent = this.checked ? '공지 보기' : '공지 숨기기';
            });
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
        
    </body>

</html>

