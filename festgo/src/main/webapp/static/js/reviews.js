document.addEventListener('DOMContentLoaded', () => {
    // 리뷰 보기/감추기 버튼
    const btnToggleReview = document.querySelector('#btnToggleReview');
    console.log("btnToggleReview:", btnToggleReview); // 디버깅용 로그 추가
    
    // Collapse 초기화할 요소
    const collapseElement = document.querySelector('#collapseReviews');
    console.log("collapseElement:", collapseElement); // 디버깅용 로그 추가
    
    if (!btnToggleReview || !collapseElement) {
        console.error("btnToggleReview 또는 collapseElement를 찾을 수 없습니다.");
        return; // 요소가 없으면 실행 중지
    }

    // Bootstrap Collapse 인스턴스 생성
    let bsCollapse = new bootstrap.Collapse(collapseElement, { toggle: false });

    // 버튼 클릭 이벤트 리스너
    btnToggleReview.addEventListener('click', () => {
        try {
            bsCollapse.toggle(); // Collapse 토글
            console.log("리뷰 보기 버튼 클릭됨");
            
            // 버튼 텍스트 변경 및 리뷰 불러오기
            if (btnToggleReview.textContent.trim() === '리뷰 보기') {
                btnToggleReview.textContent = '리뷰 감추기';
                getAllReviews(); // 리뷰 목록 불러오기
            } else {
                btnToggleReview.textContent = '리뷰 보기';
            }
        } catch (error) {
            console.error('리뷰 토글 에러:', error);
        }
    });

    // Collapse 이벤트 리스너 추가
    collapseElement.addEventListener('shown.bs.collapse', () => {
        console.log('리뷰 패널이 열렸습니다.');
    });

    collapseElement.addEventListener('hidden.bs.collapse', () => {
        console.log('리뷰 패널이 닫혔습니다.');
    });

    
    const modalElement = document.getElementById("signinModal");
    const signinModal = new bootstrap.Modal(modalElement, { backdrop: false });

    // 리뷰 등록 버튼 이벤트
        const btnRegisterReview = document.querySelector('#btnRegisterReview');
        if (btnRegisterReview) {
            btnRegisterReview.addEventListener('click', registerReview);
    }

    // 리뷰 수정 모달
    const reviewModalElement = document.querySelector('#reviewModal');
    let reviewModal = null;
    if (reviewModalElement) {
        reviewModal = new bootstrap.Modal(reviewModalElement, { backdrop: true });
    }

    // 리뷰 업데이트 버튼 이벤트
    const btnUpdateRv = document.querySelector('#btnUpdateRv');
    if (btnUpdateRv) {
        btnUpdateRv.addEventListener('click', updateReview);
    }

    // 현재 로그인한 사용자 (안전하게 가져오기)
    let signedInUser = '';
    const userElement = document.querySelector('#signedInUser');
    if (userElement) {
        signedInUser = userElement.value;
    }
	console.log("현재 로그인한 사용자:", signedInUser);

    // 리뷰 등록 함수
    function registerReview() {
        const feId = document.querySelector('#feId').value;
        const reTitle = document.querySelector('#reTitle').value.trim();
        const reGrade = parseInt(document.querySelector('#reGrade').value) || 3;
        const reContent = document.querySelector('#reContent').value.trim();

        if (!reTitle || !reContent) {
            alert('제목과 내용을 입력하세요!');
            return;
        }

        fetch('/festgo/user/check-login')
            .then(response => response.json())
            .then(loginData => {  // data -> loginData로 변경
                // 응답 형식이 { isLoggedIn: boolean } 또는 boolean인 경우를 모두 처리
                const isLoggedIn = typeof loginData === 'boolean' ? loginData : loginData.isLoggedIn;
                
                if (!isLoggedIn) {
                    // 현재 페이지 URL 저장
                    sessionStorage.setItem("redirectAfterLogin", window.location.href);
                    
                    // 기존 모달 관련 요소 정리
                    document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());
                    document.body.classList.remove('modal-open');
                    document.body.style.removeProperty('overflow');
                    
                    // 로그인 모달 표시
                    if (signinModal) {
                        signinModal.show();
                        
                        // 모달 내부 클릭 이벤트 처리
                        const modalContent = document.querySelector('.modal-content');
                        if (modalContent) {
                            modalContent.addEventListener('click', function(e) {
                                e.stopPropagation();
                            }, { once: true }); // 이벤트 리스너가 한 번만 실행되도록 설정
                        }
                        
                        // 모달 입력 필드 클릭 이벤트 처리
                        document.querySelectorAll('.modal input').forEach(input => {
                            input.addEventListener('click', function(e) {
                                e.stopPropagation();
                            }, { once: true }); // 이벤트 리스너가 한 번만 실행되도록 설정
                        });
                    } else {
                        console.error('로그인 모달을 찾을 수 없습니다.');
                    }
                    return;
                }

                // 로그인 상태이면 리뷰 등록
                const reviewData = { feId, reTitle, reContent, reGrade };  // data -> reviewData로 변경
                        axios.post('../api/review', reviewData, { withCredentials: true })
                            .then(response => {
                                if (response.data === 1) {
                                    alert('리뷰가 성공적으로 등록되었습니다.');
                                    document.querySelector('#reTitle').value = '';
                                    document.querySelector('#reContent').value = '';
                                    getAllReviews();
                                }
                            })
                            .catch(error => console.error('리뷰 등록 오류:', error));
                    })
                    .catch(error => {
                        console.error('로그인 상태 확인 오류:', error);
                        alert('로그인 상태를 확인할 수 없습니다.');
                    });
    }


    // 모든 리뷰를 가져오는 함수
    function getAllReviews() {
        const feIdElement = document.querySelector('#id');
        if (!feIdElement) return;

        const feId = feIdElement.value;
        axios.get(`../api/review/all/${feId}`)
            .then(response => {
                console.log('리뷰 목록 응답:', response.data);
                makeCommentElements(response.data);
            })
            .catch(error => console.error('리뷰 불러오기 오류:', error.response ? error.response.data : error));
    }

    // 리뷰 데이터를 HTML 요소로 변환하여 화면에 표시하는 함수
    function makeCommentElements(data) {
        const divReviews = document.querySelector('#divReviews');
        if (!divReviews) return;

        let html = `<ul class="list-group list-group-flush">`;

        data.forEach(review => {
            const modifiedTime = new Date(review.reModifiedTime).toLocaleString();
            html += `
            <li class="list-group-item d-flex justify-content-between align-items-start">
                <div>
                    <h5 class="fw-bold">${review.reTitle}</h5>
                    <p class="text-warning">${'★'.repeat(review.reGrade)}${'☆'.repeat(5 - review.reGrade)}</p>
                    <div class="text-secondary" style="font-size: 0.825rem;">
                        <span>${review.reAuthor}</span>
                        <span>${modifiedTime}</span>
                    </div>
                    <div>${review.reContent}</div>
                </div>`;

            if (signedInUser === review.reAuthor) {
                html += `
                <div>
                    <button class="btnDeleteReview btn btn-outline-danger btn-sm"
                            data-id="${review.reId}">삭제</button>
							<button class="btnUpdateReview btn btn-outline-primary btn-sm" data-id="${review.reId}">
							    수정
							</button>
                </div>`;
            }

            html += `</li>`; 
        });

        html += `</ul>`;
        divReviews.innerHTML = html;

        document.querySelectorAll('.btnDeleteReview').forEach(btn => {
            btn.addEventListener('click', deleteReview);
        });

        document.querySelectorAll('.btnUpdateReview').forEach(btn => {
            btn.addEventListener('click', showCommentModal);
        });
    }

    // 댓글 삭제 함수
    function deleteReview(event) {
        if (!confirm('리뷰를 정말 삭제하시겠습니까?')) return;

        const reviewId = event.target.getAttribute('data-id');
        axios.delete(`../api/review/${reviewId}`)
            .then(() => {
                alert('리뷰가 삭제되었습니다.');
                getAllReviews();
            })
            .catch(error => console.error('리뷰 삭제 오류:', error.response ? error.response.data : error));
    }

    // 리뷰 수정 모달을 띄우는 함수
    function showCommentModal(event) {
		const reviewId = event.target.getAttribute('data-id');

		if (!reviewId) {
		    console.error("리뷰 ID가 없습니다!");
		    return;
		}

		console.log("수정할 리뷰 ID:", reviewId);

        axios.get(`../api/review/${reviewId}`)
            .then(response => {
				console.log("불러온 리뷰 데이터:", response.data);
                const modalReviewId = document.querySelector('#modalReviewId');
                const modalReviewTitle = document.querySelector('#modalReviewTitle');
                const modalReviewGrade = document.querySelector('#modalReviewGrade');
                const modalReviewText = document.querySelector('#modalReviewText');

                if (modalReviewId && modalReviewTitle && modalReviewGrade && modalReviewText) {
                    modalReviewId.value = response.data.reId;
                    modalReviewTitle.value = response.data.reTitle;
                    modalReviewGrade.value = response.data.reGrade;
                    modalReviewText.value = response.data.reContent;
					
					console.log("불러온 리뷰 데이터:", response.data);
					
                    if (reviewModal) reviewModal.show();
                }
            })
            .catch(error => console.error('리뷰 수정 데이터 불러오기 오류:', error.response ? error.response.data : error));
    }

    // 리뷰 수정 함수
    function updateReview() {
        const modalReviewId = document.querySelector('#modalReviewId');
        const modalReviewTitle = document.querySelector('#modalReviewTitle');
        const modalReviewGrade = document.querySelector('#modalReviewGrade');
        const modalReviewText = document.querySelector('#modalReviewText');

		if (!modalReviewId || !modalReviewTitle || !modalReviewGrade || !modalReviewText) {
		        console.error("수정할 입력 요소가 없습니다!");
		        return;
		    }

        const reviewId = modalReviewId.value;
        const reTitle = modalReviewTitle.value.trim();
        const reGrade = parseInt(modalReviewGrade.value) || 3;
        const reContent = modalReviewText.value.trim();

        if (!reTitle || !reContent) {
            alert('제목과 내용을 입력하세요!');
            return;
        }

        const data = { reTitle, reGrade, reContent };
		
		console.log("수정 요청 데이터:", data);

        axios.put(`../api/review/${reviewId}`, data)
            .then(() => {
                if (reviewModal) reviewModal.hide();
                getAllReviews();
            })
            .catch(error => console.error('리뷰 수정 오류:', error.response ? error.response.data : error));
    }
});
