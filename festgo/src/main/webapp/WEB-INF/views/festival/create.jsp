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
    </head>
    <body>
    
         <div class="container-fluid">
            <c:set var="pageTitle" value="새 축제 작성" />
            <%@ include file="../fragments/header.jspf" %>
        </div>
        
        <main>
            <div class="mt-2 card">
                <div class="card-header">
                    <h2>새 축제 작성</h2>
                </div>
            <div class="card-body">
                <form method="post">
                    <div class="mt-2">
                        <input type="text" class="form-control" name="title" placeholder="제목" required autofocus />
                    </div>
                    <div class="mt-2">
                        <textarea rows="5" class="form-control" name="content" placeholder="내용" required></textarea>        
                    </div>
                    <div class="mt-2">
                        <input type="text" class="form-control" name="author" required/>
                    </div>
                    <div class="mt-2">
                        <input type="submit" value="저장" class="form-control btn btn-outline-success" />
                    </div>
                </form>
            </div>
        </div>
        </main>
    	
    	
        <!-- Bootstrap JS -->
         <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
             integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
             crossorigin="anonymous"></script>
	</body>
</html>