<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>새 축제 작성</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
          rel="stylesheet" 
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
          crossorigin="anonymous" />
</head>
<body>
        <div class="container-fluid">
            <c:set var="pageTitle" value="포스트 작성" />
            <%@ include file="../fragments/header.jspf" %>
        </div>
        
    <div class="container">
        <h2 class="mt-5">새 축제 작성</h2>
        <form action="/create" method="post">
            <!-- 축제 이름 -->
            <div class="mb-3">
                <label for="feName" class="form-label">축제 이름</label>
                <input type="text" class="form-control" id="feName" name="feName" placeholder="축제 이름" required>
            </div>

            <!-- 시작 날짜 -->
            <div class="mb-3">
                <label for="feStartDate" class="form-label">축제 시작 날짜</label>
                <input type="datetime-local" class="form-control" id="feStartDate" name="feStartDate" required>
            </div>

            <!-- 종료 날짜 -->
            <div class="mb-3">
                <label for="feEndDate" class="form-label">축제 종료 날짜</label>
                <input type="datetime-local" class="form-control" id="feEndDate" name="feEndDate" required>
            </div>

            <!-- KAKAO MAP -->
            <div class="mb-3">
                <label for="sample6_postcode" class="form-label">우편번호</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="sample6_postcode" name="postcode" placeholder="우편번호" readonly>
                        <button type="button" class="btn btn-outline-secondary" onclick="sample6_execDaumPostcode()">우편번호 찾기</button>
                    </div>
            </div>
            
            <div class="mb-3">
                <label for="sample6_address" class="form-label">주소</label>
                <input type="text" class="form-control" id="sample6_address" name="address" placeholder="주소" readonly>
            </div>
            
            <div class="mb-3">
                <label for="sample6_detailAddress" class="form-label">상세주소</label>
                <input type="text" class="form-control" id="sample6_detailAddress" name="detailAddress" placeholder="상세주소">
            </div>
            
            <div class="mb-3">
                <label for="sample6_extraAddress" class="form-label">참고항목</label>
                <input type="text" class="form-control" id="sample6_extraAddress" name="extraAddress" placeholder="참고항목" readonly>
            </div>


            <!-- 전화번호 -->
            <div class="mb-3">
                <label for="fePhone" class="form-label">전화번호</label>
                <input type="text" class="form-control" id="fePhone" name="fePhone" placeholder="전화번호" required>
            </div>

            <!-- 후원자 -->
            <div class="mb-3">
                <label for="meSponsor" class="form-label">작성자</label>
                <input type="text" class="form-control" id="meSponsor" name="meSponsor" placeholder="후원자" required>
            </div>

            <!-- 참가비 -->
            <div class="mb-3">
                <label for="feFee" class="form-label">축제비용</label>
                <input type="text" class="form-control" id="feFee" name="feFee" placeholder="참가비" required>
            </div>

            <!-- 테마 ID -->
            <div class="mb-3">
                <label for="theId" class="form-label">테마</label>
                <input type="number" class="form-control" id="theId" name="theId" placeholder="테마 ID" required>
            </div>

            <!-- 내용 -->
            <div class="mb-3">
                <label for="feContents" class="form-label">내용</label>
                <textarea class="form-control" id="feContents" name="feContents" rows="5" placeholder="내용" required></textarea>
            </div>

            <!-- 홈페이지 -->
            <div class="mb-3">
                <label for="feHomepage" class="form-label">홈페이지 링크</label>
                <input type="url" class="form-control" id="feHomepage" name="feHomepage" placeholder="홈페이지 URL">
            </div>

            <!-- 대표 이미지 -->
            <div class="mb-3">
                <label for="feImageMain" class="form-label">축제 대표 이미지</label>
                <input type="text" class="form-control" id="feImageMain" name="feImageMain" placeholder="대표 이미지 파일명">
            </div>

            <!-- 포스터 -->
            <div class="mb-3">
                <label for="fePoster" class="form-label">축제 포스터</label>
                <input type="text" class="form-control" id="fePoster" name="fePoster" placeholder="포스터 파일명">
            </div>

            <!-- 이미지 파일들 -->
            <div class="mb-3">
                <label for="festivalImages" class="form-label">축제 추가 이미지 (쉼표로 구분)</label>
                <textarea class="form-control" id="festivalImages" name="festivalImages" rows="2" placeholder="이미지1.jpg, 이미지2.jpg"></textarea>
            </div>

            <!-- 제출 버튼 -->
            <div class="mb-3">
                <button type="submit" class="btn btn-primary">축제 등록</button>
            </div>
        </form>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
        
        <!-- KAKAO MAP -->
        <input type="text" id="sample6_postcode" placeholder="우편번호">
        <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
        <input type="text" id="sample6_address" placeholder="주소"><br>
        <input type="text" id="sample6_detailAddress" placeholder="상세주소">
        <input type="text" id="sample6_extraAddress" placeholder="참고항목">

        <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
        <script>
            function sample6_execDaumPostcode() {
                new daum.Postcode({
                    oncomplete: function(data) {
                        // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
        
                        // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                        // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                        var addr = ''; // 주소 변수
                        var extraAddr = ''; // 참고항목 변수
        
                        //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                        if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                            addr = data.roadAddress;
                        } else { // 사용자가 지번 주소를 선택했을 경우(J)
                            addr = data.jibunAddress;
                        }
        
                        // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                        if(data.userSelectedType === 'R'){
                            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                                extraAddr += data.bname;
                            }
                            // 건물명이 있고, 공동주택일 경우 추가한다.
                            if(data.buildingName !== '' && data.apartment === 'Y'){
                                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                            }
                            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                            if(extraAddr !== ''){
                                extraAddr = ' (' + extraAddr + ')';
                            }
                            // 조합된 참고항목을 해당 필드에 넣는다.
                            document.getElementById("sample6_extraAddress").value = extraAddr;
                        
                        } else {
                            document.getElementById("sample6_extraAddress").value = '';
                        }
        
                        // 우편번호와 주소 정보를 해당 필드에 넣는다.
                        document.getElementById('sample6_postcode').value = data.zonecode;
                        document.getElementById("sample6_address").value = addr;
                        // 커서를 상세주소 필드로 이동한다.
                        document.getElementById("sample6_detailAddress").focus();
                    }
                }).open();
            }
        </script>
</body>
</html>
