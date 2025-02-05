/**
 * home.jsp 파일에 포함
 */
document.addEventListener('DOMContentLoaded', () => {
    
    const mainVisualSwiper = new Swiper(".mainVisualSwiper", {
        spaceBetween: 30,
        centeredSlides: true,
        autoplay: {
            delay: 2500,
            disableOnInteraction: false,
        },
        pagination: {
            el: ".swiper-pagination",
            clickable: true,
        },
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
    });
        
    const festivalSwiper = new Swiper('.festivalSwiper', {
        // Optional parameters
        direction: 'horizontal',
        loop: true,

        // If we need pagination
        pagination: {
            el: '.swiper-pagination',
        },

        // Navigation arrows
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },

        // And if we need scrollbar
        scrollbar: {
            el: '.swiper-scrollbar',
        },
    });
    
//------------------------- 축제 검색 결과 관련 ------------------------- 
    const eventDetailsEl = document.getElementById('eventDetails');
    const formSearchFestival = document.getElementById('searchFestival');
    
    // form 태그의 기본 동작 대신 fetch를 활용 
    formSearchFestival.addEventListener('submit', function(event) {
        event.preventDefault(); // 폼 제출 기본 동작 방지
        
        const formData = new FormData(this); // 폼 데이터를 FormData 객체로 변환
        console.log(...formData.entries());
        const jsonData = {};
        
        formData.forEach((value, key) => {
            jsonData[key] = value;
        });
        
        // 검색 조건을 입력하지 않았을 경우 경고 메세지를 출력
        if (jsonData.month === '' && jsonData.lcId === '' && jsonData.theId === '' && jsonData.keyword === '') {
            eventDetailsEl.innerHTML =
                '<div class="alert alert-info" role="alert">' +
                '검색 조건을 선택 혹은 입력해주세요.' +
                '</div>';
            return;
        }

        // 축제 상세정보 불러오는 fetch() 메서드
        fetch('api/search', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jsonData)            
        })
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

                    eventDiv.innerHTML =
                        '<img src="' + contextPath + '/uploads/' + fest.feImageMain + '" alt="' + fest.feName + '" ' +
                        'class="card-img-top" style="height: 200px; object-fit: cover;">' +
                        '<div class="card-body">' +
                        '<h5 class="card-title">' + fest.feName + '</h5>' +
                        '<p class="card-text"><strong>기간:</strong> ' + fest.feStartDate + ' ~ ' + fest.feEndDate + '</p>' +
                        '<p class="card-text"><strong>위치:</strong> ' + fest.feAddress + '</p>' +
                        '<a href="#" class="btn btn-primary">자세히 보기</a>' +
                        '</div>';

                    eventCol.appendChild(eventDiv);
                    rowDiv.appendChild(eventCol);
                });

                eventDetailsEl.appendChild(rowDiv);
            } else {
                eventDetailsEl.innerHTML =
                    '<div class="alert alert-info" role="alert">' +
                    '검색 조건을 만족하는 축제가 없습니다.' +
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
    });    
            
});



