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
    const divShowMoreFestival = document.getElementById('showMoreFestival');
    const btnShowMoreFestival = document.getElementById('btnShowMoreFestival');
    let jsonDataForSearch;
    let isBtnShowMoreFestivalHaveEventListener = false;
    
    // form 태그의 기본 동작 대신 fetch를 사용하여 서버로 AJAX요청을 보냄. 
    formSearchFestival.addEventListener('submit', function(event) {
        event.preventDefault(); // 폼 제출 기본 동작 방지
        
        const formData = new FormData(this); // 폼 데이터를 FormData 객체로 변환
        console.log("폼 데이터: ", ...formData.entries());
        const jsonData = {};
        
        formData.forEach((value, key) => {
            jsonData[key] = value;
        });
        jsonData['startIndexNum'] = 0; // 더보기 버튼에 사용할 startIndexNum 변수를 json에 추가
        console.log("json으로 변환된 폼 데이터: ", jsonData);
        // 검색 조건을 입력하지 않았을 경우 경고 메세지를 출력
        if (jsonData.month === '' && jsonData.lcId === '' && jsonData.theId === '' && jsonData.keyword === '') {
            eventDetailsEl.innerHTML =
                '<div class="alert alert-info" role="alert">' +
                '검색 조건을 선택 혹은 입력해주세요.' +
                '</div>';
            return;
        }
        
        jsonDataForSearch = jsonData;
        showSearchResult();  
    });
    
    // 축제 검색 결과를 표시하는 메서드
    function showSearchResult() {
        fetchFestival(jsonDataForSearch)
        .then(dataLength => showMoreFestival(dataLength));
    } 
            
    // 검색한 조건으로 축제 상세정보 불러오는 fetch() 메서드
   function fetchFestival(jsonData) {
        return fetch('api/search', {
                method: 'post',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(jsonData)
            })
            .then(response => response.json())
            .then(data => festivalDataToCard(data))
            .then(data => {return data.length})
            .catch(error => {
                console.error("축제 정보를 불러오는 중 오류 발생:", error);
                eventDetailsEl.innerHTML =
                    '<div class="alert alert-danger" role="alert">' +
                    '축제 정보를 불러오는데 실패했습니다.' +
                    '</div>';
            });
    }
            
    // fetch로 불러온 축제 상세정보(json 객체)을 이용해서 축제 카드를 추가하는 메서드
    function festivalDataToCard(data) {
        return new Promise(function(resolve, reject) {
            console.log("받은 데이터:", data);
                    
            if (jsonDataForSearch.startIndexNum === 0) eventDetailsEl.innerHTML = ''; // 기존 내용 초기화

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
            
            resolve(data);  
        });
    }

    // 더보기 버튼을 만들지 판단하는 메서드
    function showMoreFestival(dataLength) {
        console.log("축제카드 개수:", dataLength);

        const jsonData = jsonDataForSearch;
        if (dataLength === 12) { // 불러온 축제카드 개수가 최대치(12개)인 경우 
            console.log("jsonData: ", jsonData); 
            let startIndexNum = jsonData.startIndexNum;
            const uri = './api/reloadData'; // AJAX 요청을 보낼 uri
            axios
            .post(uri, jsonData)
            .then(response => handleReloadData(response)) // 더보기 버튼이 필요한지 판단
            .then(plusIndex => { //
                startIndexNum += plusIndex;
                console.log("startIndexNum: ", startIndexNum); 
                if (startIndexNum === 12 && isBtnShowMoreFestivalHaveEventListener === false) {
                    jsonDataForSearch.startIndexNum = startIndexNum;
                    isBtnShowMoreFestivalHaveEventListener = true;
                    btnShowMoreFestival.addEventListener('click', function(event) {    
                        showSearchResult();
                    });
                } else {
                    jsonDataForSearch.startIndexNum = startIndexNum;
                }
            })
            .catch((error) => {
                console.log(error);
            });
        } else {
            divShowMoreFestival.classList.add('d-none');
        }
    }
    
    
    // 현재 검색 조건을 만족하지만 아직 출력 안 된 축제가 있을 경우 
    // (1) 더보기 버튼을 보여주고
    // (2) 축제를 추가로 출력하기 위해 sql에 사용되는 offset값에 12를 더함.
    function handleReloadData(response) {
        return new Promise(function(resolve, reject) {
            const numberOfRestFestival = response.data;
            let plusIndex = 0;
            console.log("검색 조건을 만족하지만 아직 출력 안 된 축제 개수: ", numberOfRestFestival);
            if (numberOfRestFestival >= 1) {
                divShowMoreFestival.classList.remove('d-none'); //-> (1)
                plusIndex = 12; //-> (2)
                resolve(plusIndex);
            }
            resolve(plusIndex);
        });
    }
            
});



