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
            border: 4px solid skyblue;
            border-radius: 15px;
            padding: 10px;
        }

        /* 클릭한 날짜 강조 스타일 */
        .fc-daygrid-day.fc-highlighted {
            background-color: #ffecd1 !important;
            border: 2px solid #ffa726 !important;
        }

        /* 날짜 셀 호버 효과 강화 */
        .fc .fc-daygrid-day {
            transition: all 0.3s cubic-bezier(0.34, 1.56, 0.64, 1);
            position: relative;
            z-index: 1;
        }

        .fc .fc-daygrid-day:hover {
            transform: translateY(-8px);
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            z-index: 2;
            background-color: #f8f9fa;
        }

        /* 호버 시 날짜 텍스트 강조 */
        .fc .fc-daygrid-day:hover .fc-daygrid-day-number {
            font-weight: bold;
            transform: scale(1.1);
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
            transition: transform 0.3s ease;
        }

        .fc .fc-day-sun .fc-daygrid-day-number {
            color: red !important;
            text-decoration: none !important;
        }

        /* 이벤트 스타일 */
        .fc-event {
            margin: 1px 2px !important;
            padding: 2px !important;
            font-size: 12px !important;
            text-align: center !important;
            border-radius: 4px !important;
        }

        /* 이벤트가 있는 날짜 셀 스타일 */
        .fc-daygrid-day-events {
            margin-top: 2px !important;
        }

        /* 축제 카드 스타일 */
        .festival-card {
            border: 2px solid #E6E6FA;
            border-radius: 10px;
            cursor: pointer;
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .festival-card:hover {
            transform: translateY(-5px);
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
        }

        .card-title {
            font-weight: bold;
        }

        .festival-list-title {
            font-weight: bold;
        }

        .custom-hr {
            border: none;
            border-top: 2px solid #ccc;
            margin: 0 auto 20px;
            max-width: 90%;
        }
       .fc-event-title {
    font-size: 18px !important;
}
    </style>
</head>
<body>
    <div class="container-fluid">
        <c:set var="pageTitle" value="축제 달력" />
        <%@ include file="/WEB-INF/views/fragments/header.jspf" %>
    </div>
    <main>
        <div id="calendar"></div>
        <h3 class="text-center my-3 festival-list-title">축제 리스트</h3>
        <hr class="custom-hr">
        <div id="eventDetails" class="my-4 text-center"></div>
    </main>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- FullCalendar JS -->
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/index.global.min.js"></script>
    <!-- Axios Http JS -->    
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>  
    
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
            // events 속성 대신 직접 데이터를 가져와서 처리
            datesSet: function(info) {
                // 현재 보이는 달력의 시작일과 종료일
                const start = info.start.toISOString().split('T')[0];
                const end = info.end.toISOString().split('T')[0];
                
                // 해당 기간의 축제 데이터를 가져옴
                fetch(festivalsUrl + '?start=' + start + '&end=' + end)
                    .then(response => response.json())
                    .then(data => {
                        // 기존 이벤트 모두 제거
                        calendar.removeAllEvents();
                        
                        // 날짜별 축제 개수를 계산
                        const eventCounts = {};
                        data.forEach(fest => {
                            const startDate = fest.feStartDate.split('T')[0];
                            const endDate = fest.feEndDate.split('T')[0];
                            
                            // 시작일부터 종료일까지의 각 날짜에 카운트 추가
                            let currentDate = new Date(startDate);
                            const lastDate = new Date(endDate);
                            while (currentDate <= lastDate) {
                                const dateStr = currentDate.toISOString().split('T')[0];
                                eventCounts[dateStr] = (eventCounts[dateStr] || 0) + 1;
                                currentDate.setDate(currentDate.getDate() + 1);
                            }
                        });
                        
                     // 각 날짜별 카운트를 이벤트로 추가
                        Object.entries(eventCounts).forEach(([date, count]) => {
                            calendar.addEvent({
                                title: count + '개 ∨',  
                                start: date,
                                allDay: true,
                                backgroundColor: 'transparent', // 배경색 제거
                                borderColor: 'transparent', // 테두리 제거
                                textColor: 'gray', // 텍스트 색상을 검은색으로 변경
                                display: 'block'
                            });
                        });
                    })
                    .catch(error => console.error('축제 정보 로딩 실패:', error));
            },
            eventDisplay: 'block',
            displayEventTime: false,
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
                                eventDiv.classList.add('card', 'h-100', 'festival-card', 'shadow-sm');
                                
                                var imageUrl = fest.feImageMain 
                                    ? contextPath + '/uploads/' + fest.feImageMain 
                                    : contextPath + '/resources/images/default-festival.jpg';
                                
                                eventDiv.innerHTML = 
                                    '<img src="' + imageUrl + '" alt="" ' +
                                    'class="card-img-top" style="height: 200px; object-fit: cover;">' +
                                    '<div class="card-body">' +
                                        '<h5 class="card-title">' + fest.feName + '</h5>' +
                                        '<p class="card-text"><strong></strong> ' + fest.feStartDate + ' ~ ' + fest.feEndDate + '</p>' +
                                        '<p class="card-text"><strong></strong> ' + fest.feAddress + '</p>' +
                                    '</div>';
                                
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