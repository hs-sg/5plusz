<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>   
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        
        <!-- Bootstrap을 사용하기 위한 meta name"viewport: 설정 -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <title>Fest Go</title>
        
        <!-- Bootstrap CSS 링크 -->
         <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous">
         
    </head>
    <body>
        <div class = "container-fluid">
            <c:set var="pageTitle" value="List"/>
            <%@ include file="../fragments/header.jspf" %>
        </div>
        
        <main>
            <div class="mt-2 card">
                <div class="card-header">
                    <!-- 글쓰기 버튼 추가 -->
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
                                    <option value="t">제목</option>
                                    <option value="c">내용</option>
                                    <option value="tc">제목+내용</option>
                                    <option value="a">작성자</option>
                                </select>
                            </div>
                            <div class="col-7">
                                <input class="form-control" type="text" name="keyword" placeholder="검색어 입력" required/>
                            </div>
                            <div class = "col-2">
                                <input type="submit" value="검색" class="form-control btn btn-outline-secondary"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="card-body">
                <div class="table-responsive">
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
                                            <c:param name="id" value="${p.poId}"/>
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
            </div>
        </main>
         
        
         <!-- Bootstrap JS -->
         <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
            crossorigin="anonymous">
         </script>
    </body>
</html>
