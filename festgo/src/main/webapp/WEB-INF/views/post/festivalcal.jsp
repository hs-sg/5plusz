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
        /* 달력 테두리 영역 스타일 */
		#calendar {
		    max-width: 90%;
		    margin: 20px auto;
		    border: 6px solid skyblue; /* 두께를 6px로 변경 */
		    border-radius: 15px;
		    padding: 10px;
		}

		.fc-toolbar-title {
    		font-weight: bold !important;
   			color: skyblue !important;
		}
		
		/* 년/월 타이틀 왼쪽으로 이동 */
		.fc-toolbar-title {
		    text-align: center !important;
		    font-size: 1.6rem !important;
		    font-weight: bold !important;
		    width: 100% !important;
		    display: block !important;
		    position: relative !important;
		    transform: translateX(-85px) !important;  
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
		    transition: all 0.5s cubic-bezier(0.22, 1.61, 0.36, 1); /* 더 강한 애니메이션 */
		    position: relative;
		    overflow: hidden;
		}
		
		/* 마우스를 올렸을 때 효과 */
		.festival-card:hover {
		    transform: translateY(-15px) scale(1.05); /* 더 강하게 떠오르고 확대 */
		    box-shadow: 0px 12px 24px rgba(0, 0, 0, 0.4); /* 그림자 더 강하게 */
		}
		
		/* 카드 클릭 시 약간 줄어드는 효과 */
		.festival-card:active {
		    transform: translateY(-5px) scale(0.97);
		    box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.3);
		}



        .card-title {
            font-weight: bold;
        }

        .festival-list-title {
            font-weight: bold;
            color: purple !important;
        }

        .custom-hr {
            border: none;
            border-top: 2px solid #ccc;
            margin: 0 auto 20px;
            max-width: 90%;
        }
        
        /* 더보기 버튼 스타일 */
        .load-more-btn {
            background-color: #E6E6FA;
            color: #000;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            margin: 20px auto;
            display: block;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .load-more-btn:hover {
            background-color: #D8BFD8;
            transform: translateY(-2px);
        }

        .hidden {
            display: none;
        }
       
       .fc-event-title {
    		font-size: 18px !important;
		}
		
		.fc-event-title {
   			 font-size: 18px !important;
    		 display: flex !important;
   			 justify-content: center !important;
    		 align-items: center !important;
    		 height: 100% !important;
		}

		.fc-daygrid-event {
    		text-align: center !important;
    		margin-top: 10px !important;
		}
		
		.fc-button-primary {
    		background-color: transparent !important;
    		border-color: transparent !important;
    		color: #000 !important;
		}

		.fc-button-primary:hover {
   			 background-color: transparent !important;
    		 border-color: transparent !important;
		}

		.fc-button-primary:disabled {
    		background-color: transparent !important;
   			border-color: transparent !important;
		}

		.fc-button-primary:not(:disabled):active,
		.fc-button-primary:not(:disabled).fc-button-active {
    		background-color: transparent !important;
    		border-color: transparent !important;
		}
		
		.custom-event {
   			 white-space: pre-line !important;
   			 text-align: center;
   			 line-height: 0.8;
   			 font-size: 0.9em;
   			 display: flex;
    		 flex-direction: column;
   			 align-items: center;
   			 gap: 0px;
   			 padding: 0;
   			 margin: 0;
		}

		.custom-event::before {
    		content: attr(data-title);
    		font-weight: bold;
    		font-size: 1em;
    		margin-bottom: -4px;
		}
		
		.custom-event {
		    position: absolute !important;
		    top: 50% !important;
		    left: 50% !important;
		    transform: translate(-50%, -50%) !important;
		    font-size: 1rem !important;
		    font-weight: bold !important;
		    text-align: center !important;
		    display: flex !important;
		    flex-direction: column !important;
		    align-items: center !important;
		    justify-content: center !important;
		    gap: 2px !important;
		    white-space: nowrap !important;
		    color: gray !important;
		}
		
		.fc-daygrid-day {
		    position: relative !important;
		}
		
		.fc-daygrid-day-events {
		    position: absolute !important;
		    top: 50% !important;
		    left: 50% !important;
		    transform: translate(-50%, -50%) !important;
		    width: 100% !important;
		}

		.fc-day-other .custom-event {
		    opacity: 0.3 !important;
		}
		
		/* 공통 리본 스타일 */
		.ongoing-badge, .upcoming-badge, .ended-badge {
		    position: absolute;
		    top: 10px;
		    left: -30px;
		    color: white;
		    padding: 5px 30px;
		    font-size: 0.8rem;
		    font-weight: bold;
		    transform: rotate(-45deg);
		    z-index: 2;
		    box-shadow: 0 2px 4px rgba(0,0,0,0.2);
		    width: 100px; /* 리본 길이 고정 */
		    height: 25px; /* 리본 높이 고정 */
		    display: flex;
		    align-items: center;
		    justify-content: center;
		    text-align: center;
		    line-height: 25px;
		    white-space: nowrap;
		}
		
		/* 개최중 배지 (녹색) */
		.ongoing-badge {
		    background-color: #28a745;
		}
		
		/* 예정 배지 (파란색) */
		.upcoming-badge {
		    background-color: #007bff;
		}
		
		/* 종료 배지 (빨간색) */
		.ended-badge {
		    background-color: #dc3545;
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
        let currentPage = 1;
        const itemsPerPage = 6;
        let currentFestivals = [];
        
        var contextPath = '${pageContext.request.contextPath}';
        var festivalsUrl = '<c:url value="/api/festivals" />';
        
        function createLoadMoreButton() {
            const button = document.createElement('button');
            button.className = 'load-more-btn';
            updateLoadMoreButtonText(button); // 버튼 텍스트 초기화
            button.onclick = () => {
                currentPage++;
                displayFestivals(currentFestivals, false);
            };
            return button;
        }

        function updateLoadMoreButtonText(button) {
            const totalFestivals = currentFestivals.length; // 전체 축제 개수
            const displayedFestivals = Math.min(currentPage * itemsPerPage, totalFestivals); // 현재 표시된 축제 개수
            button.textContent = '('+ displayedFestivals + '/'+ totalFestivals + ')' + ' 더보기';
        }


        function displayFestivals(festivals, resetPage = true) {
            if (resetPage) {
                currentPage = 1;
                eventDetailsEl.innerHTML = '';
            }

            const startIndex = (currentPage - 1) * itemsPerPage;
            const endIndex = startIndex + itemsPerPage;
            const festivalsToShow = festivals.slice(startIndex, endIndex);

            const rowDiv = document.createElement('div');
            rowDiv.classList.add('row', 'row-cols-1', 'row-cols-md-3', 'g-4');

            festivalsToShow.forEach(function (fest) {
                var eventCol = document.createElement('div');
                eventCol.classList.add('col');

                var eventDiv = document.createElement('div');
                eventDiv.classList.add('card', 'h-100', 'festival-card', 'shadow-sm');

                const today = new Date();
                today.setHours(0, 0, 0, 0);
                const startDate = new Date(fest.feStartDate);
                const endDate = new Date(fest.feEndDate);

                // 상태 배지 추가
                let badgeHTML = '';
                if (today >= startDate && today <= endDate) {
                    badgeHTML = '<span class="ongoing-badge">개최중</span>';
                } else if (today < startDate) {
                    badgeHTML = '<span class="upcoming-badge">예정</span>';
                } else if (today > endDate) {
                    badgeHTML = '<span class="ended-badge">종료</span>';
                }

                var imageUrl = fest.feImageMain
                    ? contextPath + '/uploads/' + fest.feImageMain
                    : contextPath + '/resources/images/default-festival.jpg';

                eventDiv.innerHTML =
                    '<div class="position-relative">' +
                    badgeHTML +
                    '<img src="' + imageUrl + '" alt="" ' +
                    'class="card-img-top" style="height: 200px; object-fit: cover;">' +
                    '</div>' +
                    '<div class="card-body">' +
                    '<h5 class="card-title">' + fest.feName + '</h5>' +
                    '<p class="card-text"><strong></strong> ' + fest.feStartDate + ' ~ ' + fest.feEndDate + '</p>' +
                    '<p class="card-text"><strong></strong> ' + fest.feAddress + '</p>' +
                    '</div>';

                eventDiv.onclick = function () {
                    window.location.href = contextPath + '/fest/detail?feId=' + fest.feId;
                };

                eventCol.appendChild(eventDiv);
                rowDiv.appendChild(eventCol);
            });

            const oldButton = eventDetailsEl.querySelector('.load-more-btn');
            if (oldButton) {
                oldButton.remove();
            }

            if (resetPage) {
                eventDetailsEl.innerHTML = '';
            }

            eventDetailsEl.appendChild(rowDiv);

            if (festivals.length > endIndex) {
                const loadMoreButton = createLoadMoreButton();
                updateLoadMoreButtonText(loadMoreButton);
                eventDetailsEl.appendChild(loadMoreButton);
            }
        }



        const calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: ''
            },
            locale: 'ko',
            datesSet: function(info) {
                const start = info.start.toISOString().split('T')[0];
                const end = info.end.toISOString().split('T')[0];
                
                fetch(festivalsUrl + '?start=' + start + '&end=' + end)
                    .then(response => response.json())
                    .then(data => {
                        calendar.removeAllEvents();
                        
                        const eventCounts = {};
                        data.forEach(fest => {
                            const startDate = fest.feStartDate.split('T')[0];
                            const endDate = fest.feEndDate.split('T')[0];
                            
                            let currentDate = new Date(startDate);
                            const lastDate = new Date(endDate);
                            while (currentDate <= lastDate) {
                                const dateStr = currentDate.toISOString().split('T')[0];
                                eventCounts[dateStr] = (eventCounts[dateStr] || 0) + 1;
                                currentDate.setDate(currentDate.getDate() + 1);
                            }
                        });
                        
                        Object.entries(eventCounts).forEach(([date, count]) => {
                            calendar.addEvent({
                                title: count + '개' + '\n⌵',
                                start: date,
                                allDay: true,
                                backgroundColor: 'transparent',
                                borderColor: 'transparent',
                                textColor: 'gray',
                                display: 'block',
                                classNames: ['custom-event']
                            });
                        });
                    })
                    .catch(error => console.error('축제 정보 로딩 실패:', error));
            },
            eventDisplay: 'block',
            displayEventTime: false,
            dateClick: function(info) {
                document.querySelectorAll('.fc-daygrid-day.fc-highlighted')
                    .forEach(el => el.classList.remove('fc-highlighted'));

                info.dayEl.classList.add('fc-highlighted');

                var clickedDate = info.dateStr.split("T")[0];

                fetch(festivalsUrl + '?start=' + clickedDate + '&end=' + clickedDate)
                    .then(response => response.json())
                    .then(data => {
                        console.log("받은 데이터:", data);
                        currentFestivals = data;

                        if (data && data.length > 0) {
                            displayFestivals(data, true);
                        } else {
                            eventDetailsEl.innerHTML = 
                                '<div class="alert alert-info" role="alert">' +
                                '해당 날짜에 진행하는 축제가 없습니다.' +
                                '</div>';
                        }

                        setTimeout(() => {
                            eventDetailsEl.scrollIntoView({ behavior: 'smooth', block: 'start' });
                        }, 300);
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