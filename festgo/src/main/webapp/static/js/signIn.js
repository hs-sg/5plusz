/**
 * header.jspf 파일에 포함
 */

document.addEventListener("DOMContentLoaded", () => {
    const signinModal = new bootstrap.Modal('div#signinModal', {backdrop: true});
    // {backdrop: true} -> 모달이 실행됐을 때 백그라운드를 어둡게 만듬

    const divWarningText = document.querySelector('div#warningText');
        
    const linkSignin = document.querySelector('a#linkSignin');
    if (linkSignin !== null) {
        linkSignin.addEventListener('click', (event) => {
            document.querySelector('input#modalSigninUsername').value = '';
            document.querySelector('input#modalSigninPassword').value = '';
            divWarningText.innerHTML ='';
            event.preventDefault(); //-> 하이퍼링크의 기본 동작(페이지 이동)을 막음.
            signinModal.show();
        });
    }

    const btnSignin = document.querySelector('button#btnSignin');
    btnSignin.addEventListener('click', signin);
	/* --------------------(추가된 부분)-------------------- */
	/*  글쓰기 버튼 클릭 시 로그인 후 이동할 URL 저장 */
	    const postCreateLink = document.querySelector("a.btn-primary[href*='post/create']");
	    if (postCreateLink) {
	        postCreateLink.addEventListener("click", (event) => {
	            if (!isUserLoggedIn()) {  // ⬅️ 유저 로그인 여부 체크하는 함수
	                event.preventDefault(); // 기본 이동 막기
	                sessionStorage.setItem("redirectAfterLogin", postCreateLink.getAttribute("href"));
	                signinModal.show();
	            }
	        });
	    }
    
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
        let uri = '';
        if (window.location.pathname === '/festgo/') {
            uri += './user/signin';    
        } else {
            uri += '../user/signin';
        }
        console.log(uri);
        axios
        .post(uri, data,{ withCredentials: true })  // 쿠키 포함하여 요청)
        .then((response) => {
            if(response.data === 0) {
                divWarningText.innerHTML = '아이디 또는 비밀번호를 확인하세요.';
            } else if(response.data === 2) {
                divWarningText.innerHTML = '가입요청이 승인되지 않은 계정입니다.';
            } else {
                console.log('data=' + response.data + ', username=' + meUsername);
                signinModal.hide();
				
				/* 추가: 세션 쿠키 확인 */
		        console.log("세션 쿠키 확인:", document.cookie);
				/* --------------------(추가된 부분)-------------------- */
				/*  로그인 후 이동할 페이지 확인 */
		        const redirectUrl = sessionStorage.getItem("redirectAfterLogin");
		        sessionStorage.removeItem("redirectAfterLogin");  // 사용 후 삭제

		        if (redirectUrl && redirectUrl !== window.location.pathname) {
		            console.log("🔄 로그인 후 이동할 페이지:", redirectUrl);
		            window.location.href = redirectUrl;  //  해당 URL로 이동
		        } else {
		            console.log("🔄 로그인 후 새로고침!");
		            window.location.replace(window.location.href);//-> 로그인 성공하면 현재 보고있는 페이지로 새로고침
		        }
		    }
		})
   
        .catch((error) => {
            console.log('error');
        });
    }
		/* --------------------(추가된 부분)-------------------- */
		 /*  로그인 여부 확인 함수 */
		    function isUserLoggedIn() {
		        return document.cookie.includes("JSESSIONID");  // 세션 쿠키 기반 체크
		    }
		});