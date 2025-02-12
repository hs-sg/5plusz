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
    const showPasswordCheckbox = document.querySelector('input#showPasswordCheckbox')
    const divSponsor = document.querySelector('div#divSponsor');
    //const divInputCheckResult = document.querySelector('div#inputCheckResult');
    const checkUsernameResult = document.querySelector('p#checkUsernameResult');
    const checkPasswordResult = document.querySelector('p#checkPasswordResult');
    const checkPasswordCheckResult = document.querySelector('p#checkPasswordCheckResult');
    const checkEmailResult = document.querySelector('p#checkEmailResult');
    const checkSponsorResult = document.querySelector('p#checkSponsorResult');
    const inputMemberRole = document.querySelector('input#memberRole');
    const btnSignUp = document.querySelector('button#signUp');
    
    // [일반], [관리자] 링크를 클릭하면 sponsor 입력란이 화면에서 사라짐. 
    linkGeneral.addEventListener('click', () => {
        divSponsor.classList.add('d-none');
        checkActiveInLinkClassList(linkGeneral, linkBusiness, linkAdmin);
        linkGeneral.classList.add('active');
        cleanUsernamePasswordEmailSponsorInfo();
        inputMemberRole.value = 1;
        btnSignUp.classList.add('disabled');
        IsCheckedToFalse();
        console.log(inputMemberRole.value);
    });
    linkAdmin.addEventListener('click', () => {
        divSponsor.classList.add('d-none');
        checkActiveInLinkClassList(linkGeneral, linkBusiness, linkAdmin);
        linkAdmin.classList.add('active');
        cleanUsernamePasswordEmailSponsorInfo();
        inputMemberRole.value = 3;
        btnSignUp.classList.add('disabled');
        IsCheckedToFalse();
        console.log(inputMemberRole.value);
    });
    
    // [사업자] 링크를 클릭하면 sponsor 입력란이 화면에 보임.
    linkBusiness.addEventListener('click', () => {
        divSponsor.classList.remove('d-none');
        checkActiveInLinkClassList(linkGeneral, linkBusiness, linkAdmin);
        linkBusiness.classList.add('active');
        cleanUsernamePasswordEmailSponsorInfo();
        inputMemberRole.value = 2;
        btnSignUp.classList.add('disabled');
        IsCheckedToFalse();
        console.log(inputMemberRole.value);
    });
    
    // 'change' 이벤트 리스너 설정
    inputUsername.addEventListener('change', checkUsername);
    inputPassword.addEventListener('change', checkPassword);
    inputPasswordCheck.addEventListener('change', checkPasswordCheck);
    inputEmail.addEventListener('change', checkEmail);
    inputSponsor.addEventListener('change', checkSponsor);
    
    // showingPasswordCheckbox를 클릭하면 비밀번호 입력란의 type이 'text'로 변경됨.
    showPasswordCheckbox.addEventListener('click', () => {
        if (inputPassword.type === 'password' && inputPasswordCheck.type === 'password') {
            inputPassword.type = 'text';
            inputPasswordCheck.type = 'text';
        } else {
            inputPassword.type = 'password';
            inputPasswordCheck.type = 'password';
        }
    });
    
    
    /*-------------------함수 선언-------------------*/
    // <a>요소의 클래스에 active 클래스가 있으면 제거함.
    // 파라미터: <a>요소 1개 이상
    function checkActiveInLinkClassList(... rest) {
        function removeActive(x) {
            if (x.classList.contains('active')) {
                x.classList.remove('active');
            }
        }
        rest.forEach(x => removeActive(x));
    }
    
    // 회원가입요청 버튼 활성화에 필요한 정보 중복 체크 결과를 false로 초기화함.
    function IsCheckedToFalse() {
        isUsernameChecked = false;
        isPasswordChecked = false;
        isEmailChecked = false;
        isSponsorChecked = false;
    }
    
    // <input>요소의 테두리 색을 변경함(빨간색).
    function changeInputBorder(x) {
        if(!x.classList.contains('dangerBorder')) {
            x.classList.add('dangerBorder');
        }
    }
    
    
    // 일반/사업자/관리자 카테고리 전환 시 입력 화면을 초기화 시킴.
    function cleanUsernamePasswordEmailSponsorInfo() {
        // username, password, email, sponsor <input>들에 입력된 값을 지우고, 테두리 색을 기본값으로 변경함.
        const inputs = [
            inputUsername, 
            inputPassword, 
            inputPasswordCheck, 
            inputEmail, 
            inputSponsor];
        inputs.forEach(x => {
            x.value = '';
            x.classList.remove('dangerBorder');
        });
        // [가입 요청] 버튼 위에 표시되는 경고 문구<p>들에 입력된 값을 모두 지움.
        const checks = [
            checkUsernameResult, 
            checkPasswordResult, 
            checkPasswordCheckResult, 
            checkEmailResult, 
            checkSponsorResult];
        checks.forEach(x => x.innerHTML = '');
    }
    
    // 중복 체크 결과가 모두 true이면 [가입 요청] 버튼을 활성화 시킴.
    function changeButtonState() {
        console.log(inputMemberRole.value);
        if (parseInt(inputMemberRole.value) === 2) { // 사업자 회원가입인 경우 sponsor 입력여부도 체크.
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
    
    // inputUsername의 value값을 확인,
    // 필수 조건을 충족하지 못하는 경우
    // 1. [가입 요청] 버튼 상단에 경고 메시지 표시.
    // 2. 아이디 입력란의 border-color가 red로 바뀜.
    // <아이디 필수 조건: 필수 입력, 중복 불가, 영소문자(a-z) 및 숫자 이외는 사용 불가, 최소 5자, 최대 20자> 
    function checkUsername() {
        const username = inputUsername.value;
        if (username === '') {
            checkUsernameResult.innerHTML = '아이디: 필수 입력 항목입니다.';
            isUsernameChecked = false;
            changeInputBorder(inputUsername);
            changeButtonState();
            return;
        } else {
            // 조건을 만족하는 아이디가 입력되면 border-color 설정을 기본값으로 되돌림
            inputUsername.classList.remove('dangerBorder'); 
            isUsernameChecked = true;
        }
        
        const regExp = /^(?=.*[a-z])(?=.*\d)[a-z0-9]{5,20}$/;
        if (!regExp.test(username)) {
            checkUsernameResult.innerHTML = '아이디: 5~20자의 영문 소문자, 숫자를 사용해주세요.';
            isUsernameChecked = false;
            changeInputBorder(inputUsername);
            changeButtonState();
            return;
        } else {
            isUsernameChecked = true;
        }
        
        const uri = `./checkusername?username=${username}`;
        axios
        .get(uri)
        .then(handleCheckUsernameResp)
        .catch((error) => {
            console.log(error);
        });
    }
    
    function handleCheckUsernameResp({ data }) {
        console.log(data);
        if (data === 'N') {
            checkUsernameResult.innerHTML = '아이디: 이미 존재하는 아이디입니다.';
            changeInputBorder(inputUsername);
            isUsernameChecked = false;
        } else {
            checkUsernameResult.innerHTML = '';
            isUsernameChecked = true;
        }
        changeButtonState();
    }
    
    // inputPassword의 value 값을 확인,
    // 필수 조건을 충족하지 못하는 경우
    // 1. [가입 요청] 버튼 상단에 경고 메시지 표시.
    // 2. 비밀번호 입력란의 border-color가 red로 바뀜.
    // <비밀번호 필수 조건: 필수 입력, 영문 대소문자, 숫자, 특수문자 포함, 최소 8자, 최대 20자>
    // 사용 가능한 특수문자 32자 : ! " # $ % & ' ( ) * + , - . / : ; < = > ? @ [ ₩ ] ^ _ ` { | } ~
    function checkPassword() {
        const password = inputPassword.value;
        if (password === '') {
            checkPasswordResult.innerHTML = '비밀번호: 필수 입력 항목입니다.';
            isPasswordChecked = false;
            changeInputBorder(inputPassword);
            changeButtonState();
            return;
        } else {
            // 조건을 만족하는 비밀번호가 입력되면 border-color 설정을 기본값으로 되돌림
            inputPassword.classList.remove('dangerBorder'); 
            isPasswordChecked = true;
        }
        
        const regExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!"#$%&'()*+,\-./:;<=>?@\[₩\]\^_`\{\|\}~])[\w!"#$%&'()*+,\-./:;<=>?@\[₩\]\^`\{\|\}~]{8,20}$/;
        if (!regExp.test(password)) {
            checkPasswordResult.innerHTML = '비밀번호: 8~20자의 영문 대/소문자, 숫자, 특수문자를 사용해주세요.';
            isPasswordChecked = false;
            changeInputBorder(inputPassword);
            changeButtonState();
            return;
        } else {
            checkPasswordResult.innerHTML = '';
            isPasswordChecked = true;
        }
        changeButtonState();
    }
    
    // inputPasswordCheck의 value 값이 inputPassword와 같은지 확인하고 같지 않으면
    // 1. [가입 요청] 버튼 상단에 경고 메시지 표시.
    // 2. 비밀번호 입력란의 border-color가 red로 바뀜.
    function checkPasswordCheck() {
        const password = inputPassword.value;
        const passwordCheck = inputPasswordCheck.value;
        
        if (password !== passwordCheck) {
            checkPasswordCheckResult.innerHTML = '비밀번호 확인: 비밀번호가 일치하지 않습니다.'
            isPasswordChecked = false;
            changeInputBorder(inputPasswordCheck);
        } else {
            checkPasswordCheckResult.innerHTML = '';
            isPasswordChecked = true;
            inputPasswordCheck.classList.remove('dangerBorder'); 
        }
        changeButtonState();    
    }
    
    // inputEmail의 value 값을 확인,
    // 필수 조건을 충족하지 못하는 경우
    // 1. [가입 요청] 버튼 상단에 경고 메시지 표시.
    // 2. 이메일 입력란의 border-color가 red로 바뀜.
    function checkEmail() {
        const email = inputEmail.value;
        
        if (email === '') {
            checkEmailResult.innerHTML = '이메일: 필수 입력 항목입니다.';
            isEmailChecked = false;
            changeInputBorder(inputEmail);
            changeButtonState();
            return;
        }
        
        const uri = `./checkemail?email=${encodeURIComponent(email)}`;
        
        axios
        .get(uri)
        .then(handleCheckEmailResp)
        .catch((error) => console.log(error));
        
        function handleCheckEmailResp({ data }) {
            console.log(data);
            if(data === 'N') {
                checkEmailResult.innerHTML = '이메일: 이미 사용중인 이메일입니다.';
                isEmailChecked = false;
                changeInputBorder(inputEmail);
            } else {
                checkEmailResult.innerHTML = '';
                inputEmail.classList.remove('dangerBorder');
                isEmailChecked = true;
            }
            changeButtonState();
        }
    }
    
    // inputSponsor의 value 값을 확인,
    // 필수 조건을 충족하지 못하는 경우
    // 1. [가입 요청] 버튼 상단에 경고 메시지 표시.
    // 2. 비밀번호 입력란의 border-color가 red로 바뀜.
    function checkSponsor() {
        const sponsor = inputSponsor.value;
        
        if (sponsor === '') {
            checkSponsorResult.innerHTML = '업체/기관명: 필수 입력 항목입니다.';
            isSponsorChecked = false;
            changeInputBorder(inputSponsor);
            changeButtonState();
            return;
        } else {
            // 조건을 만족하는 업체/기관명이 입력되면 border-color 설정을 기본값으로 되돌림
            inputSponsor.classList.remove('dangerBorder'); 
            isSponsorChecked = true;
        }
        
        const uri = `./checksponsor?sponsor=${sponsor}`;
        
        axios
        .get(uri)
        .then(handleCheckSponsorResp)
        .catch((error) => {console.log(error);});
        
        function handleCheckSponsorResp({ data }) {
            console.log(data);
            if(data === 'N') {
                checkSponsorResult.innerHTML = '업체/기관명: 이미 사용중인 업체/기관명입니다.';
                isSponsorChecked = false;
                changeInputBorder(inputSponsor);
            } else {
                checkSponsorResult.innerHTML = '';
                inputSponsor.classList.remove('dangerBorder');
                isSponsorChecked = true;
            }
            changeButtonState();
        }
    }

});

/* */