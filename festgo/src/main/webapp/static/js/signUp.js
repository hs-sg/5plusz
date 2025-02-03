/**
 * signup.jsp 파일에 포함
 */

document.addEventListener("DOMContentLoaded", () => {
    // 회원가입에 필요한 정보의 중복 체크 결과(T/F)를 변수에 저장 
    let isUsernameChecked = false;
    let isPasswordChecked = false;
    let isEmailChecked = false;
    let isSponsorChecked = false;
    
    const linkGeneral = document.querySelector('a#linkGeneral');
    const linkBusiness = document.querySelector('a#linkBusiness');
    const linkAdmin = document.querySelector('a#linkAdmin');
    const inputUsername = document.querySelector('input#username');
    const inputPassword = document.querySelector('input#password');
    const inputPasswordCheck = document.querySelector('input#passwordCheck');
    const inputEmail = document.querySelector('input#email');
    const inputSponsor = document.querySelector('input#sponsor');
    const divSponsor = document.querySelector('div#divSponsor');
    const divInputCheckResult = document.querySelector('div#inputCheckResult');
    const inputMemberRole = document.querySelector('input#memberRole');
    const btnSignUp = document.querySelector('button#signUp');
    
    // [일반], [관리자] 링크를 클릭하면 sponsor 입력란이 화면에서 사라짐. 
    linkGeneral.addEventListener('click', () => {
        divSponsor.classList.add('d-none');
        checkActiveInLinkClassList(linkGeneral, linkBusiness, linkAdmin);
        linkGeneral.classList.add('active');
        cleanUsernamePasswordEmailSponsorInput();
        inputMemberRole.value = 1;
        console.log(inputMemberRole.value);
    });
    linkAdmin.addEventListener('click', () => {
        divSponsor.classList.add('d-none');
        checkActiveInLinkClassList(linkGeneral, linkBusiness, linkAdmin);
        linkAdmin.classList.add('active');
        cleanUsernamePasswordEmailSponsorInput();
        inputMemberRole.value = 3;
        console.log(inputMemberRole.value);
    });
    
    // [사업자] 링크를 클릭하면 sponsor 입력란이 화면에 보임.
    linkBusiness.addEventListener('click', () => {
        divSponsor.classList.remove('d-none');
        checkActiveInLinkClassList(linkGeneral, linkBusiness, linkAdmin);
        linkBusiness.classList.add('active');
        cleanUsernamePasswordEmailSponsorInput();
        inputMemberRole.value = 2;
        console.log(inputMemberRole.value);
    });
    
    /*-------------------함수 선언-------------------*/
    // 링크에 active 클래스가 있으면 제거함.
    // 파라미터: <a>요소 1개 이상
    function checkActiveInLinkClassList(... rest) {
        function removeActive(x) {
            if (x.classList.contains('active')) {
                x.classList.remove('active');
            }
        }
        rest.forEach(x => removeActive(x));
    }
    
    // username, password, email, sponsor input 태그에 입력된 값을 지움.
    function cleanUsernamePasswordEmailSponsorInput() {
        inputUsername.value = '';
        inputPassword.value = '';
        inputPasswordCheck.value = '';
        inputEmail.value = '';
        inputSponsor.value = '';
    }
    
    // 중복 체크 결과가 모두 true이면 [가입 요청] 버튼을 활성화 시킴.
    function changeButtonState() {
        if (inputMemberRole === 2) { // 사업자 회원가입인 경우 sponsor 입력여부도 체크.
            if (isUsernameChecked && isPasswordChecked && isEmailChecked && isSponsorChecked) {
                // 버튼 활성화 - class 속성들 중에서 'disabled'를 제거.
                btnSignUp.classList.remove('disabled');
            } else {
                // 버튼 비활성화 - class 속성에 'disabled'를 추가.
                btnSignUp.classList.add('disabled');
            }
        } else {
            if (isUsernameChecked && isPasswordChecked && isEmailChecked) {
                // 버튼 활성화 - class 속성들 중에서 'disabled'를 제거.
                btnSignUp.classList.remove('disabled');
            } else {
                // 버튼 비활성화 - class 속성에 'disabled'를 추가.
                btnSignUp.classList.add('disabled');
            }
        }
    }

});