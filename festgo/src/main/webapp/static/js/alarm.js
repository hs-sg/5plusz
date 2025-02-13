/**
 * header.jspf 에 포함
 */

document.addEventListener('DOMContentLoaded', function() {
    if (alMeUsername !== ''){
        const data = { mrId: alMrId, meUsername: alMeUsername };
        console.log(data);
        let uri = '';
        let alarmNumber;
        
        // 현재 위치에 따라서 uri를 수정
        if (window.location.pathname === '/festgo/') {
            uri += './api/alarmnumber';
        } else {
            uri += '../api/alarmnumber';
        }
        
        // AJAX 요청으로 표시해야할 알람 개수를 받아옴
        axios
        .post(uri, data)
        .then((response) => {
            alarmNumber = response.data;
            console.log('alarmNumber: ', alarmNumber);
            updateNotificationBadge(alarmNumber);
        })
        .catch(error => console.log('요청 실패:', error));
    } 
    
    // 알람 버튼 이벤트 핸들러
    function btnAlarmHandler(mrId, meUsername, filter) {
        const notificationList = document.getElementById('notificationList');
        let uriForAlarmHandler = '';
        console.log('mrId: '+mrId);
        switch(mrId){
        case 3: // 관리자용
            console.log('btnAlarmHandler: mrId=' + mrId + ', meUsername=' + meUsername);
            uriForAlarmHandler = '/festgo/api/alarmadmin';
            axios
            .get(uriForAlarmHandler)
            .then((response) => {
                const jsonData = response.data;
                console.log(jsonData);
                notificationList.innerHTML = '';
                let html;
                switch(filter){
                case 'all':
                    html = makeAlarmCardsForAdmin(jsonData);
                    break;
                case 'festival':
                    jsonData.srNumbers = 0; // 사업자 가입 요청 관련 알람은 표시하지 않음
                    html = makeAlarmCardsForAdmin(jsonData);
                    break;
                case 'community':
                    jsonData.frNumbers = 0; // 축제 등록 요청 관련 알람은 표시하지 않음
                    html = makeAlarmCardsForAdmin(jsonData);
                    break;
                }
                notificationList.innerHTML += html;
                // 알람이 없는 경우 메시지를 출력
                if(notificationList.innerHTML === '') {
                    noAlarm(notificationList);
                }
            })
            .catch(error => console.log(error));
            break;
        case 1: // 일반 사용자,
        case 2: // 사업자용
            console.log('btnAlarmHandler: mrId=' + mrId + ', meUsername=' + meUsername);
            uriForAlarmHandler = '/festgo/api/alarms'; 
            axios
            .get(uriForAlarmHandler, {
                params:{
                    meUsername: meUsername
                }
            })
            .then((response) => {
                const alarms = response.data;
                console.log('alarms: ' + alarms);
                notificationList.innerHTML = '';
                let html;
                let filteredAlarms;
                switch(filter){
                case 'all':
                    html = makeAlarmCardsForUsers(alarms);
                    break;
                case 'festival':
                    // 축제 관련 알람(alCategory: 2)들만 저장
                    filteredAlarms = alarms.filter(a => a.alCategory === 2);
                    console.log('filteredFestAlarms: '+ filteredAlarms);
                    html = makeAlarmCardsForUsers(filteredAlarms);
                    break;
                case 'community':
                    // 축제 관련 알람(alCategory: 2)이 아닌 알람들만 저장
                    filteredAlarms = alarms.filter(a => a.alCategory !== 2);
                    console.log('filteredCommAlarms: '+ filteredAlarms);
                    html = makeAlarmCardsForUsers(filteredAlarms);
                    break;
                }
                notificationList.innerHTML += html;
                addEventListenerToAlarmCard();
                // 알람이 없는 경우 메시지를 출력
                if(notificationList.innerHTML === '') {
                    noAlarm(notificationList);
                }
            })
            .catch(error => console.log(error));
            break;
        }
    }
    
    // 관리자용 알람 카드를 만드는 함수
    function makeAlarmCardsForAdmin(jsonData) {
        const frNumbers = jsonData.frNumbers;
        const srNumbers = jsonData.srNumbers;
        let html = '';
        if (frNumbers !== 0) {
            html += `
                <div class="notification-card card mb-2" id="alarm-cards">
                    <div class="card-body d-flex align-items-center">
                    <i class="bi bi-calendar3-event text-primary fs-4 me-3"></i>
                        <a href="/festgo/user/mypage">
                        <div class="flex-grow-1">
                            <h6 class="mb-1">승인 대기 중인 축제 등록 요청이 ${frNumbers}개 있습니다.</h6>
                            <p class="text-muted small mb-0">마이페이지에서 확인 가능합니다.</p>
                        </div>
                        </a>
                    </div>
                </div>`;
        }
        if (srNumbers !== 0) {
            html += `
                <div class="notification-card card mb-2" id="alarm-cards">
                    <div class="card-body d-flex align-items-center">
                    <i class="bi bi-megaphone-fill text-primary fs-4 me-3"></i>
                        <a href="/festgo/user/mypage">
                        <div class="flex-grow-1">
                            <h6 class="mb-1">승인 대기 중인 사업자 회원가입 요청이 ${srNumbers}개 있습니다.</h6>
                            <p class="text-muted small mb-0">마이페이지에서 확인 가능합니다.</p>
                        </div>
                        </a>
                    </div>
                </div>`;
        }
        
        return html;
    }
    
    // 일반/사업자 회원을 위한 알람을 만드는 함수
    function makeAlarmCardsForUsers(data) {
        let html = '';
        for (const alarm of data) {
            if (alarm.alCategory === 2) { // 축제 등록 관련 알람(보이지 않는 <p>태그에 alId 들어있음)
                html += `
                    <div class="notification-card card mb-2">
                        <div class="card-body d-flex align-items-center">
                        <p class="d-none" id="pAlId">${alarm.alId}</p>
                        <i class="bi bi-calendar3-event text-primary fs-4 me-3"></i>
                            <a href="/festgo/user/mypage">
                            <div class="flex-grow-1">
                                <h6 class="mb-1">${alarm.alarmMessage}</h6>
                                <p class="text-muted small mb-0">마이페이지에서 확인 가능합니다.</p>
                            </div>
                            </a>
                        </div>
                    </div>
                `;
            } else { // 사업자 회원가입 요청 승인 관련 알람(보이지 않는 <p>태그에 alId 들어있음)
                html += `
                    <div class="notification-card card mb-2">
                        <div class="card-body d-flex align-items-center">
                        <p class="d-none" id="pAlId">${alarm.alId}</p>
                        <i class="bi bi-megaphone-fill text-primary fs-4 me-3"></i>
                            <a href="/festgo/user/mypage">
                            <div class="flex-grow-1">
                                <h6 class="mb-1">${alarm.alarmMessage}</h6>
                                <p class="text-muted small mb-0">마이페이지에서 확인 가능합니다.</p>
                            </div>
                            </a>
                        </div>
                    </div>
                `;
            }
        }
        
        return html;
    }
    
    // 알람이 없음 표시를 만드는 함수
    function noAlarm(element) {
        console.log('notificationList.innerHTML: ' + element.innerHTML);
        if(element.innerHTML === '') {
            console.log('알람이 없음');
            element.innerHTML += `
                <div class="empty-state">
                    <div class="empty-state-icon">
                        <i class="bi bi-bell-slash"></i>
                    </div>
                    <h6>알림이 없습니다</h6>
                </div>
            `;
        }
    }
    
    // 필터 버튼 이벤트
    const filterButtons = document.querySelectorAll('.filter-btn');
    filterButtons.forEach(button => {
        button.addEventListener('click', function() {
            // 활성 필터 변경
            filterButtons.forEach(btn => btn.classList.remove('active'));
            this.classList.add('active');
            
            // 필터링 로직
            const filter = this.getAttribute('data-filter');
            btnAlarmHandler(alMrId, alMeUsername, filter);    
        });
    });

    // 알림 배지 상태 관리
    function updateNotificationBadge(count) {
        const badge = document.getElementById('notificationBadge');
        if (count > 0) {
            badge.style.display = 'block';
            badge.textContent = count;
            document.getElementById('markAllRead').disabled = false;
        } else {
            badge.style.display = 'none';
            document.getElementById('markAllRead').disabled = true;
        }
    }

    // 모든 알림 읽음 처리
    document.getElementById('markAllRead')?.addEventListener('click', function() {
        // TODO: 서버에 읽음 처리 요청
        updateNotificationBadge(0);
    });

    // 알림 모달이 열릴 때 이벤트
    const notificationModal = document.getElementById('notificationModal');
    notificationModal?.addEventListener('show.bs.modal', function() {
        // 활성 필터를 기본값(전체)로 변경
        filterButtons.forEach(btn => btn.classList.remove('active'));
        document.getElementById('defaultAlarmFilter').classList.add('active');
        // 알람들을 불러옴
        btnAlarmHandler(alMrId, alMeUsername, 'all');
    });
    
    // 알람 카드들을 찾아서 클릭 이벤트 리스너를 설정
    function addEventListenerToAlarmCard() {
        const alarmCards = document.querySelectorAll('div.notification-card');
        for (const ac of alarmCards) {
            ac.addEventListener('click', updateAlarmStatus);
        }
    }
    
    // 알람 카드의 이벤트 리스너 콜백
    function updateAlarmStatus(event) {
        const pAlId = event.currentTarget.querySelector('p#pAlId');
        const alId = pAlId.innerText;
        console.log('updateAlarmStatus: alId=' + alId);
        uriForUpdateAlarm = '/festgo/api/alarmcheck';
        axios
        .get(uriForUpdateAlarm, {
            params:{ alId: alId }
        })
        .then(response => console.log(response))
        .catch(error => console.log(error));
    }

    // 로그인 모달 자동 실행
    const loginRequired = "<c:out value='${loginRequired}'/>";
    if (loginRequired === "true") {
        const linkSignin = document.getElementById("linkSignin");
        linkSignin.click();
    }
});