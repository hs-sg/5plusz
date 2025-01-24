/**
 * header.jspf 파일에 포함
 */

document.addEventListener("DOMContentLoaded", () => {
    const signinModal = new bootstrap.Modal('div#signinModal', {backdrop: true});
    // {backdrop: true} -> 모달이 실행됐을 때 백그라운드를 어둡게 만듬

    const divWarningText = document.querySelector('div#warningText');
        
    const linkSignin = document.querySelector('a#linkSignin');
    linkSignin.addEventListener('click', (event) => {
        document.querySelector('input#modalSigninUsername').value = '';
        document.querySelector('input#modalSigninPassword').value = '';
        divWarningText.innerHTML ='';
        event.preventDefault(); //-> 하이퍼링크의 기본 동작(페이지 이동)을 막음.
        signinModal.show();
    });

    const btnSignin = document.querySelector('button#btnSignin');
    btnSignin.addEventListener('click', signin);
    
    /* --------------------(콜백) 함수 선언-------------------- */
    // btnSignin 버튼의 클릭 이벤트 리스너 콜백
    function signin(event) {
        event.preventDefault();
        
        const meUsername = document.querySelector('input#modalSigninUsername').value;
        const mePassword = document.querySelector('input#modalSigninPassword').value;
        
        // 아이디, 패스워드 입력란이 비어있으면 함수 종료.
        if (meUsername === '' || mePassword === '') {
            alert('아이디 또는 비밀번호를 입력해주세요.')
            return;
        }
        
        const data = { meUsername, mePassword };
        const uri = '../festgo/user/signin';
        
        axios
        .post(uri, data)
        .then((response) => {
            if(response.data === 0) {
                divWarningText.innerHTML = '아이디 또는 비밀번호를 확인하세요.';
            } else {
                console.log('data=' + response.data + ', username=' + meUsername);
                signinModal.hide();
                window.location.replace('../festgo/');
            }
        })
        .catch((error) => {
            console.log('error');
        })
    }
});
