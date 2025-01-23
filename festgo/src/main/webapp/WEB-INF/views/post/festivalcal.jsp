<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>축제 달력</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/main.min.css" rel="stylesheet">
    <style>
        #calendar {
            max-width: 90%;
            margin: 20px auto;
        }
        .no-events {
            margin-top: 20px;
            text-align: center;
            color: #888;
        }
        .no-events img {
            max-width: 150px;
            display: block;
            margin: 0 auto;
        }
        .fc-highlighted {
            background-color: #ffecd1 !important; /* 클릭한 날짜 강조 */
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <!-- 헤더 -->
        <c:set var="pageTitle" value="축제 달력" />
        <%@ include file="/WEB-INF/views/fragments/header.jspf" %>
    </div>

    <main>
        <div id="calendar"></div>
        <div id="eventDetails" class="my-4 text-center"></div>
    </main>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/index.global.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const calendarEl = document.getElementById('calendar');
            const eventDetailsEl = document.getElementById('eventDetails');

            const calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                headerToolbar: {
                    left: 'prev,next today', // 이전, 다음, 오늘 버튼만 표시
                    center: 'title', // 중앙에 제목만 표시
                    right: '' // 오른쪽 버튼 삭제
                },
                locale: 'ko',
                events: '/api/festivals', // Spring Controller에서 데이터를 JSON으로 가져옴
                dateClick: function(info) {
                    // 기존 강조 제거
                    document.querySelectorAll('.fc-highlighted').forEach(el => el.classList.remove('fc-highlighted'));
                    // 클릭한 날짜 강조
                    info.dayEl.classList.add('fc-highlighted');

                    // 해당 날짜 이벤트 가져오기
                    fetch('/api/festivals')
                        .then(response => response.json())
                        .then(data => {
                            const dayEvents = data.filter(event => event.startDate === info.dateStr);
                            eventDetailsEl.innerHTML = '';

                            if (dayEvents.length > 0) {
                                dayEvents.forEach(event => {
                                    const eventDiv = document.createElement('div');
                                    eventDiv.innerHTML = `<a href="${event.detailUrl}" class="btn btn-primary m-2">${event.title}</a>`;
                                    eventDetailsEl.appendChild(eventDiv);
                                });
                            } else {
                                eventDetailsEl.innerHTML = `
                                    <div class="no-events">
                                        <p>축제가 없습니다.</p>
                                        <img src="https://via.placeholder.com/150" alt="No events">
                                    </div>`;
                            }
                        });
                }
            });

            calendar.render();
        });
    </script>
</body>
</html>
