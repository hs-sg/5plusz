/**
 * header.jspf 파일에 포함
 */

document.addEventListener("DOMContentLoaded", () => {
    
    const modalElement = document.getElementById("signinModal");
    const signinModal = new bootstrap.Modal(modalElement, { backdrop: false });
    // {backdrop: true} -> 모달이 실행됐을 때 백그라운드를 어둡게 만듬
    // 모달 내부 클릭 이벤트 전파 중단 추가 (충돌방지)
        document.querySelector('.modal-content').addEventListener('click', function(e) {
            e.stopPropagation();
        });

        const modalInputs = document.querySelectorAll('.modal input');
        modalInputs.forEach(input => {
            input.addEventListener('click', function(e) {
                e.stopPropagation();
            });
        });
        
    const divWarningText = document.querySelector('div#warningText');

    const linkSignin = document.querySelector('a#linkSignin');
    if (linkSignin !== null) {
        linkSignin.addEventListener('click', (event) => {
            document.querySelector('input#modalSigninUsername').value = '';
            document.querySelector('input#modalSigninPassword').value = '';
            divWarningText.innerHTML = '';
            event.preventDefault(); //-> 하이퍼링크의 기본 동작(페이지 이동)을 막음.
            signinModal.show();
        });
    }

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
        let uri = '';
        if (window.location.pathname === '/festgo/') {
            uri += './user/signin';
        } else {
            uri += '../user/signin';
        }
        console.log(uri);
        axios
            .post(uri, data, { withCredentials: true })  // 쿠키 포함하여 요청)
            .then((response) => {
                if (response.data === 0) {
                    divWarningText.innerHTML = '아이디 또는 비밀번호를 확인하세요.';
                } else if (response.data === 2) {
                    divWarningText.innerHTML = '가입요청이 승인되지 않은 계정입니다.';
                } else {
                    console.log('data=' + response.data + ', username=' + meUsername);
                    /* --------------------(추가된 부분)-------------------- */
                    sessionStorage.setItem("signedInUser", meUsername);  // 세션 저장

                    signinModal.hide();
                    document.body.classList.remove("modal-open");
                    document.querySelectorAll(".modal-backdrop").forEach(el => el.remove());

                    const redirectUrl = sessionStorage.getItem("redirectAfterLogin");
                    

                    if (redirectUrl && redirectUrl !== window.location.pathname) {
                        window.location.href = redirectUrl;
                    } else {
                        window.location.reload();
                    }
                    sessionStorage.removeItem("redirectAfterLogin");
                }
            })
            .catch(error => console.log('로그인 요청 실패:', error));
    }



    /* --------------------(추가된 부분)-------------------- */
    /*  로그인 여부 확인 함수 */
    
    function checkAndShowLoginModal(targetUrl) {
        sessionStorage.setItem("redirectAfterLogin", targetUrl);

        fetch(`/festgo/user/check-login`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`); // 더 자세한 에러 메시지
                }
                return response.json();
            })
            .then(data => {
                if (!data.isLoggedIn) {
                    signinModal.show(); // 로그인 안 되어 있으면 모달 표시
                } else {
                    window.location.href = targetUrl; // 이미 로그인되어 있으면 바로 이동
                }
            })
            .catch(error => {
                console.error("로그인 상태 확인 오류:", error);
                alert("로그인 상태를 확인하는 도중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
                sessionStorage.removeItem("redirectAfterLogin"); // 오류 발생 시 redirectAfterLogin 제거
            });
    }


     /*  특정 페이지 이벤트 리스너 등록 */
        if (window.location.pathname.includes("/post")) {
            document.addEventListener('click', (e) => {
                const postLink = e.target.closest('.post-link');
                if (postLink) {
                    e.preventDefault();
                    checkAndShowLoginModal(postLink.href);
                }

                const createLink = e.target.closest('a[href*="/post/create"]');
                if (createLink) {
                    e.preventDefault();
                    checkAndShowLoginModal(createLink.href);
                }
            });
        }
        
    });
