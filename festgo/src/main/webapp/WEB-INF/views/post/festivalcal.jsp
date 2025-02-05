<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>축제 달력</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FullCalendar CSS -->
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/main.min.css" rel="stylesheet">
    <style>
        /* 달력 영역 스타일 */
        #calendar {
            max-width: 90%;
            margin: 20px auto;
            border: 4px solid skyblue; /* 하늘색 테두리, 두께 4px */
            border-radius: 15px;       /* 모서리 둥글게 */
            padding: 10px;
        }
        /* 클릭한 날짜 강조 스타일 */
        .fc-daygrid-day.fc-highlighted {
            background-color: #ffecd1 !important;
            border: 2px solid #ffa726 !important;
        }
        /* 기본 스타일 설정 */
        .fc .fc-col-header-cell {
            color: blue;
            text-decoration: none !important;
        }
        .fc .fc-col-header-cell.fc-day-sun {
            color: red !important;
            text-decoration: none !important;
        }
        .fc .fc-daygrid-day-number {
            color: black;
            text-decoration: none !important;
        }
        .fc .fc-day-sun .fc-daygrid-day-number {
            color: red !important;
            text-decoration: none !important;
        }
        /* 축제 카드 스타일 - 연보라 테두리와 둥근 모서리, 마우스 오버 효과 추가 */
        .festival-card {
            border: 2px solid #E6E6FA;  /* 연보라 색 테두리 (lavender) */
            border-radius: 10px;          /* 둥근 모서리 */
            cursor: pointer;              /* 카드 전체 클릭 가능 */
            transition: transform 0.3s, box-shadow 0.3s;
        }
        .festival-card:hover {
            transform: translateY(-5px);
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
        }
        /* 축제 이름(카드 제목) 글씨 굵게 */
        .card-title {
            font-weight: bold;
        }
        /* 축제 리스트 텍스트 스타일 (두껍게) */
        .festival-list-title {
            font-weight: bold;
        }
        /* hr 스타일 - 필요에 따라 여백 및 선 스타일 조정 */
        .custom-hr {
            border: none;
            border-top: 2px solid #ccc;
            margin: 0 auto 20px;
            max-width: 90%;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <!-- 헤더 (필요시 include) -->
        <c:set var="pageTitle" value="축제 달력" />
        <%@ include file="/WEB-INF/views/fragments/header.jspf" %>
    </div>
    <main>
        <div id="calendar"></div>
        <!-- 달력 바로 밑에 "축제 리스트" 텍스트와 수평선 추가 -->
        <h3 class="text-center my-3 festival-list-title">축제 리스트</h3>
        <hr class="custom-hr">
        <!-- AJAX로 불러온 축제 정보들이 표시될 영역 -->
        <div id="eventDetails" class="my-4 text-center"></div>
    </main>
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- FullCalendar JS -->
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/index.global.min.js"></script>
    <!-- Axios Http JS -->    
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>  
    <!-- JSP 페이지 내 스크립트 부분 -->
    <script>
    document.addEventListener('DOMContentLoaded', function () {
        const calendarEl = document.getElementById('calendar');
        const eventDetailsEl = document.getElementById('eventDetails');
        
        // 컨텍스트 경로 (예: /festgo)
        var contextPath = '${pageContext.request.contextPath}';
        // 축제 정보를 불러올 API URL
        var festivalsUrl = '<c:url value="/api/festivals" />';
        
        const calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: ''
            },
            locale: 'ko',
            events: festivalsUrl,
            dateClick: function(info) {
                // 기존 강조 제거
                document.querySelectorAll('.fc-daygrid-day.fc-highlighted')
                    .forEach(el => el.classList.remove('fc-highlighted'));
                // 클릭한 날짜 강조
                info.dayEl.classList.add('fc-highlighted');
                
                // ISO 8601 형식에서 날짜 부분만 추출
                var clickedDate = info.dateStr.split("T")[0];
                
                // 클릭한 날짜에 해당하는 축제 정보를 fetch()로 호출
                fetch(festivalsUrl + '?start=' + clickedDate + '&end=' + clickedDate)
                .then(response => response.json())
                .then(data => {
                    console.log("받은 데이터:", data);
                    eventDetailsEl.innerHTML = ''; // 기존 내용 초기화
                    
                    if (data && data.length > 0) {
                        const rowDiv = document.createElement('div');
                        rowDiv.classList.add('row', 'row-cols-1', 'row-cols-md-3', 'g-4');
                        
                        data.forEach(function(fest) {
                            var eventCol = document.createElement('div');
                            eventCol.classList.add('col');
                            
                            var eventDiv = document.createElement('div');
                            // "festival-card" 클래스에 연보라 테두리와 둥근 모서리, 마우스 오버 효과 적용됨
                            eventDiv.classList.add('card', 'h-100', 'festival-card', 'shadow-sm');
                            
                            // 이미지 URL: 업로드 폴더에 있는 파일을 가리킴
                            var imageUrl = fest.feImageMain 
                                ? contextPath + '/uploads/' + fest.feImageMain 
                                : contextPath + '/resources/images/default-festival.jpg';
                            console.log("생성된 이미지 URL:", imageUrl);
                            
                            eventDiv.innerHTML = 
                                '<img src="' + imageUrl + '" alt="" ' +
                                'class="card-img-top" style="height: 200px; object-fit: cover;">' +
                                '<div class="card-body">' +
                                    '<h5 class="card-title">' + fest.feName + '</h5>' +
                                    '<p class="card-text"><strong></strong> ' + fest.feStartDate + ' ~ ' + fest.feEndDate + '</p>' +
                                    '<p class="card-text"><strong></strong> ' + fest.feAddress + '</p>' +
                                '</div>';
                            
                            // 카드 전체 클릭 시, 상세 페이지로 이동 (축제 ID를 쿼리 파라미터로 전달)
                            eventDiv.onclick = function() {
                                window.location.href = contextPath + '/fest/details?feId=' + fest.feId;
                            };
                            
                            eventCol.appendChild(eventDiv);
                            rowDiv.appendChild(eventCol);
                        });
                        
                        eventDetailsEl.appendChild(rowDiv);
                    } else {
                    	eventDetailsEl.innerHTML = 
                            '<div class="alert alert-info" role="alert">' +
                            '해당 날짜에 진행하는 축제가 없습니다.' +
                            '</div>';
                    }
                })
                .catch(error => {
                    console.error("축제 정보를 불러오는 중 오류 발생:", error);
                    eventDetailsEl.innerHTML = 
                        '<div class="alert alert-danger" role="alert">' +
                        '축제 정보를 불러오는데 실패했습니다.' +
                        '</div>';
                });
            }
        });
        
        calendar.render();
    });
    </script>
</body>
</html>
