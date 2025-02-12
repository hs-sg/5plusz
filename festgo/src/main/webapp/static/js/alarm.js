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
            document.getElementById('notificationBadge').innerHTML = alarmNumber;
            updateNotificationBadge(alarmNumber);
        })
        .catch(error => console.log('요청 실패:', error));
        
        const btnAlarm = document.getElementById('btnAlarm');
        btnAlarm.addEventListener('click', (event) => {
            btnAlarmHandler(alMrId, alMeUsername);
        });
    } 
    
    // 알람 버튼 이벤트 핸들러
    function btnAlarmHandler(mrId, meUsername) {
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
                const frNumbers = jsonData.frNumbers;
                const srNumbers = jsonData.srNumbers;
                notificationList.innerHTML = '';
                let html = '';
                if (frNumbers !== 0) {
                    html += `
                        <div class="notification-card card mb-2">
                            <div class="card-body d-flex align-items-center">
                            <i class="bi bi-megaphone-fill text-primary fs-4 me-3"></i>
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
                        <div class="notification-card card mb-2">
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
                notificationList.innerHTML += html;
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
                let html = '';
                for (const a of alarms) {
                    html += `
                        <div class="notification-card card mb-2">
                            <div class="card-body d-flex align-items-center">
                            <i class="bi bi-megaphone-fill text-primary fs-4 me-3"></i>
                                <a href="/festgo/user/mypage">
                                <div class="flex-grow-1">
                                    <h6 class="mb-1">${a.alarmMessage}</h6>
                                    <p class="text-muted small mb-0">마이페이지에서 확인 가능합니다.</p>
                                </div>
                                </a>
                            </div>
                        </div>
                    `;
                }
                notificationList.innerHTML += html;
            })
            .catch(error => console.log(error));
            break;
        }
    }
    
    // 필터 버튼 이벤트
    const filterButtons = document.querySelectorAll('.filter-btn');
    filterButtons.forEach(button => {
        button.addEventListener('click', function() {
            // 활성 필터 변경
            filterButtons.forEach(btn => btn.classList.remove('active'));
            this.classList.add('active');
            
            // TODO: 필터링 로직 구현
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
        // TODO: 서버에서 최신 알림 데이터 가져오기
    });

    // 로그인 모달 자동 실행
    const loginRequired = "<c:out value='${loginRequired}'/>";
    if (loginRequired === "true") {
        const linkSignin = document.getElementById("linkSignin");
        linkSignin.click();
    }
});