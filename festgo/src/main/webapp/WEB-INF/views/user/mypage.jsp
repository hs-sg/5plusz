<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정 -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Fest Go</title>

        <!-- Bootstrap CSS 링크. -->
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
        crossorigin="anonymous">
    </head>
    <body>
	<div class="container-fluid">
		<c:set var="pageTitle" value="마이페이지" />
		<%@ include file="../fragments/header.jspf"%>
    </div>
        <main>
            <div class="row">
				<div class="col-3">
					<button class="btn" id="btnToggleMyProfile">내 프로필</button>
					<br />
					<button class="btn" id="btnToggleFestivalList">축제목록</button>
					<br />
					<button class="btn" id="btnTogglePostList">글목록</button>
					<br />
					<button class="btn" id="btnToggleCommentList">댓글목록</button>
					<br />
					<c:if test="${member.mrId eq 3 }">
                        <button class="btn" id="btnToggleSponsorCheckList">사업자 승인목록</button>
                    </c:if>
				</div>
				<div class="col">
	                <section>
	                    <div id="divMyProfile">
	                    </div>
	                    <div id="divFestivalList">
	                    </div>
	                    <div id="divPostList">
	                    </div>
	                    <div id="divCommentList">
	                    </div>
	                    <c:if test="${member.mrId eq 3 }">
		                    <div id="divSponsorCheckList">
		                    </div>
	                    </c:if>
	                </section>
                </div>
			</div>
		</main>
        <!-- Bootstrap JS -->
        <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
	
        <!-- Axios Http JS -->
        <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
        
        <script>
            const signedInUser = '${signedInUser}';
            const role = '${member.mrId}';
        </script>

        <c:url var="mypageJS" value="/js/mypage.js" />
        <script src="${mypageJS}"></script>
    </body>
</html>