/**
 * signinpage.jsp 파일에 포함
 */

document.addEventListener("DOMContentLoaded", () => {
    // 로그인에 사용하는 form 태그 요소
    const formSignIn = document.querySelector('form#formSignIn');
    
    // 로그인 버튼 이벤트 리스너 설정
    const btnSignin = document.querySelector('button#btnSignin');
    btnSignin.addEventListener('click', signin);
    
    function signin(event) {
        event.preventDefault();
        
        const meUsername = document.querySelector('input#modalSigninUsername').value;
        const mePassword = document.querySelector('input#modalSigninPassword').value;
        
        // 아이디, 패스워드 입력란이 비어있으면 함수 종료.
        if (meUsername === '' || mePassword === '') {
            alert('아이디 또는 비밀번호를 입력해주세요.')
            return;
        }

        formSignIn.submit;
    }
})