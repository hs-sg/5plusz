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
            border: 4px solid skyblue;
            border-radius: 15px;
            padding: 10px;
            background-color: #f9f9f9;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        /* 제목과 버튼 배치 */
        .fc-toolbar-chunk {
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .fc-toolbar-title {
            font-size: 1.5rem;
            font-weight: bold;
            margin: 0 10px;
        }

        .fc-prev-button,
        .fc-next-button,
        .fc-today-button {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            margin: 0 5px; /* 버튼 간격 */
            cursor: pointer;
        }

        .fc-prev-button:hover,
        .fc-next-button:hover,
        .fc-today-button:hover {
            background-color: #0056b3;
        }

        /* 일요일 요일과 날짜를 빨간색으로 표시 */
        .fc .fc-col-header-cell.fc-day-sun,
        .fc .fc-day-sun .fc-daygrid-day-number {
            color: red !important; /* 빨간색 */
            text-decoration: none !important; /* 밑줄 제거 */
        }

        /* 기본 스타일 설정 */
        .fc .fc-col-header-cell {
            color: blue;
            text-decoration: none !important;
        }

        .fc .fc-daygrid-day-number {
            color: black;
            text-decoration: none !important;
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
                    left: '', // 왼쪽은 비움
                    center: 'prev title next today', // 다음 버튼 오른쪽에 오늘 버튼 추가
                    right: '' // 오른쪽은 비움
                },
                locale: 'ko',
                events: '/api/festivals',
                dateClick: function(info) {
                    document.querySelectorAll('.fc-daygrid-day.fc-highlighted').forEach(el => el.classList.remove('fc-highlighted'));
                    info.dayEl.classList.add('fc-highlighted');

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
