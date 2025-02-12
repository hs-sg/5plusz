/**
 * mypage.jsp에 포함됨
 */

document.addEventListener('DOMContentLoaded', () => {
    const btnToggleMyProfile = document.querySelector('button#btnToggleMyProfile');
    const btnToggleFestivalList = document.querySelector('button#btnToggleFestivalList');
    const btnTogglePostList = document.querySelector('button#btnTogglePostList');
    const btnToggleReviewList = document.querySelector('button#btnToggleReviewList');
    const btnToggleSponsorCheckList = document.querySelector('button#btnToggleSponsorCheckList');

    const divMyProfile = document.getElementById('divMyProfile');
    const divFestivalList = document.getElementById('divFestivalList');
    const divPostList = document.getElementById('divPostList');
    const divReviewList = document.getElementById('divReviewList');
    const divSponsorCheckList = document.getElementById('divSponsorCheckList');
    
    myProfile();
    
    btnToggleMyProfile.addEventListener('click', () => {
        allBtnAndDivDisable();
        myProfile();
    });
    
    btnToggleFestivalList.addEventListener('click', () => {
        allBtnAndDivDisable();
        festivalList();
    });

    btnTogglePostList.addEventListener('click', () => {
        allBtnAndDivDisable();
        postList();
    });
    
    if(role == 1 || role == 3) {
        btnToggleReviewList.addEventListener('click', () => {
            allBtnAndDivDisable();
            reviewList();
        })
    }
    
    if(role == 3) {
        btnToggleSponsorCheckList.addEventListener('click', () => {
            allBtnAndDivDisable();
            sponsorCheckList();
        });
    }
    
/* 콜백 함수 --------------------------------------------------------------------------- */

// ---------------- 왼쪽 버튼 부분 시작--------------------------------------
  
    // 로그인된 아이디 프로필 가져오기
    function myProfile() {
        btnToggleMyProfile.style.textDecoration = 'underline';
        btnToggleMyProfile.style.fontWeight = 'bold';
        divMyProfile.style.display = 'block';
        const uri = `../api/mypage/profile/${signedInUser}`;
                    
        axios
        .get(uri)
        .then((response) => { getMyProfile(response.data); })
        .catch((error) => { console.log(error); });
    }
    
    // 로그인된 아이디 권한별 축제 리스트 가져오기
    function festivalList() {
        btnToggleFestivalList.style.textDecoration = 'underline';
        btnToggleFestivalList.style.fontWeight = 'bold';
        divFestivalList.style.display = 'block';
        const eachNumber = 6;
        let html = ``;
        switch(role) {
            case `1` : // 일반유저
                html = `
                <div id="divUFestivalList"></div>`
                divFestivalList.innerHTML = html;
                getUFestivalEachNumber(eachNumber);
                break;
                
            case `2` :
                
            html = `
            <div class="divFestivalbtn">
                        <input type="button" class="btnAllSFestival btn" value="전체"/>
                        <input type="button" class="btnPostSFestival btn" value="게시중"/>
                        <input type="button" class="btnWaitSFestival btn" value="승인대기"/>
                        <input type="button" class="btnRefuseSFestival btn" value="거절됨"/>
            </div>
                        <div id="divSFestivalList"></div>
            `;

            divFestivalList.innerHTML = html;

            const btnAllSFestival = document.querySelector('input.btnAllSFestival');
            const btnPostSFestival = document.querySelector('input.btnPostSFestival');
            const btnWaitSFestival = document.querySelector('input.btnWaitSFestival');
            const btnRefuseSFestival = document.querySelector('input.btnRefuseSFestival');

            getSFestivalEachNumberAndCategory(eachNumber, -1);

            btnAllSFestival.addEventListener('click', ()=>{
                getSFestivalEachNumberAndCategory(eachNumber, -1);
            });
            btnPostSFestival.addEventListener('click', ()=>{
                getSFestivalEachNumberAndCategory(eachNumber, 0);
            });
            btnWaitSFestival.addEventListener('click', ()=>{
                getSFestivalEachNumberAndCategory(eachNumber, 1);
            });
            btnRefuseSFestival.addEventListener('click', ()=>{
                getSFestivalEachNumberAndCategory(eachNumber, 2);
            });
            break;
                
            case `3` :
                html = `
                <div class="divFestivalbtn">
                            <input type="button" class="btnAllAFestival btn" value="전체"/>
                            <input type="button" class="btnPostAFestival btn" value="게시중"/>
                            <input type="button" class="btnWaitAFestival btn" value="승인대기"/>
                            <input type="button" class="btnRefuseAFestival btn" value="거절됨"/>
                </div>
                <div class="container">     
                        <div id="divAFestivalList" class="row"></div>
                </div>
                `;
                
                divFestivalList.innerHTML = html;
                
                const btnAllAFestival = document.querySelector('input.btnAllAFestival');
                const btnPostAFestival = document.querySelector('input.btnPostAFestival');
                const btnWaitAFestival = document.querySelector('input.btnWaitAFestival');
                const btnRefuseAFestival = document.querySelector('input.btnRefuseAFestival');
                
                getAFestivalEachNumberAndCategory(eachNumber, -1);
                
                btnAllAFestival.addEventListener('click', ()=>{
                    getAFestivalEachNumberAndCategory(eachNumber, -1);
                });
                btnPostAFestival.addEventListener('click', ()=>{
                    getAFestivalEachNumberAndCategory(eachNumber, 0);
                });
                btnWaitAFestival.addEventListener('click', ()=>{
                    getAFestivalEachNumberAndCategory(eachNumber, 1);
                });
                btnRefuseAFestival.addEventListener('click', ()=>{
                    getAFestivalEachNumberAndCategory(eachNumber, 2);
                });
                break;
        }
    }
    
    // 로그인된 아이디 권한별 작성글 가져오기
    function postList() {
        btnTogglePostList.style.textDecoration = 'underline';
        btnTogglePostList.style.fontWeight = 'bold';
        divPostList.style.display = 'block';
        let html = '';
        let pageCount = 1;
        
        getPostNum(
            function(totalPostNum) {
                const maxPage = Math.ceil(totalPostNum / 10);
                console.log(`maxPage=${maxPage}`);
                html = `
                    <div class="table-responsive m-2">
                        <table class="table table-striped table-hover">
                            <thead class="table-primary">
                                <tr>
                                    <th>번호</th>
                                    <th>제목</th>
                                    <th>작성자</th>
                                    <th>작성날짜</th>
                                    <th>조회수</th>
                                </tr>
                            </thead>
                            <tbody class="tbodyPostList">
                            </tbody>
                        </table>
                    </div>
                    <div class="btn-group" role="group">
                        <button type="button" class="pbtnPrevious btn btn-outline-dark">Previous</button>
                        <input type="button" class="pbtnFirstPaging btn btn-outline-dark" value="${pageCount}"/>
                        <input type="button" class="pbtnSecondPaging btn btn-outline-dark" value="${pageCount+1}"/>
                        <input type="button" class="pbtnThirdPaging btn btn-outline-dark" value="${pageCount+2}"/>
                        <button type="button" class="pbtnNext btn btn-outline-dark">Next</button>
                    </div>
                `;
                
                divPostList.innerHTML = html;
                
                const pinputFirstPaging = document.querySelector('input.pbtnFirstPaging');
                const pinputSecondPaging = document.querySelector('input.pbtnSecondPaging');
                const pinputThirdPaging = document.querySelector('input.pbtnThirdPaging');
                const pbtnPrevious = document.querySelector('button.pbtnPrevious');
                const pbtnNext = document.querySelector('button.pbtnNext');
                
                let pageNum = pinputFirstPaging.value;
                pbtnPrevious.disabled = true;
                if(maxPage <= 3) pbtnNext.disabled = true;
                makeingPostList(pageNum);
                
                pinputFirstPaging.addEventListener('click', () => {
                    pageNum = pinputFirstPaging.value;
                    makeingPostList(pageNum);
                });
                pinputSecondPaging.addEventListener('click', () => {
                    pageNum = pinputSecondPaging.value;
                    makeingPostList(pageNum);
                });
                pinputThirdPaging.addEventListener('click', () => {
                    pageNum = pinputThirdPaging.value;
                    makeingPostList(pageNum);
                });
                pbtnPrevious.addEventListener('click', () => {
                    const result = pdecreasePageBtn(pageCount);
                    pageCount = result;
                });
                pbtnNext.addEventListener('click', () => {
                    const result = pincreasePageBtn(pageCount, maxPage);
                    pageCount = result;
                });
            }
        );
    }

    // 로그인 아이디 권한별 리뷰 목록 가져오기
    function reviewList() {
        btnToggleReviewList.style.textDecoration = 'underline';
        btnToggleReviewList.style.fontWeight = 'bold';
        divReviewList.style.display = 'block';
        let html = '';
        let pageCount = 1;
        
        getReviewNum(function(totalReviewNum) {
            const maxPage = Math.ceil(totalReviewNum / 10);
            console.log(`maxPage=${maxPage}`);
            html = `
                <div class="table-responsive m-2">
                    <table class="table table-striped table-hover">
                        <thead class="table-primary">
                            <tr>
                                <th>번호</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>작성날짜</th>
                                <th>평점</th>
                            </tr>
                        </thead>
                        <tbody class="tbodyReviewList">
                        </tbody>
                    </table>
                </div>
                <div class="btn-group" role="group">
                    <button type="button" class="rbtnPrevious btn btn-outline-dark">Previous</button>
                    <input type="button" class="rbtnFirstPaging btn btn-outline-dark" value="${pageCount}"/>
                    <input type="button" class="rbtnSecondPaging btn btn-outline-dark" value="${pageCount+1}"/>
                    <input type="button" class="rbtnThirdPaging btn btn-outline-dark" value="${pageCount+2}"/>
                    <button type="button" class="rbtnNext btn btn-outline-dark">Next</button>
                </div>
            `;
            
            divReviewList.innerHTML = html;
            
            const rinputFirstPaging = document.querySelector('input.rbtnFirstPaging');
            const rinputSecondPaging = document.querySelector('input.rbtnSecondPaging');
            const rinputThirdPaging = document.querySelector('input.rbtnThirdPaging');
            const rbtnPrevious = document.querySelector('button.rbtnPrevious');
            const rbtnNext = document.querySelector('button.rbtnNext');
            
            let pageNum = rinputFirstPaging.value;
            rbtnPrevious.disabled = true;
            if(maxPage <= pageCount) rbtnNext.disabled = true;
            makeingReviewList(pageNum);
            
            rinputFirstPaging.addEventListener('click', () => {
                pageNum = rinputFirstPaging.value;
                makeingReviewList(pageNum);
            });
            rinputSecondPaging.addEventListener('click', () => {
                pageNum = rinputSecondPaging.value;
                makeingReviewList(pageNum);
            });
            rinputThirdPaging.addEventListener('click', () => {
                pageNum = rinputThirdPaging.value;
                makeingReviewList(pageNum);
            });
            rbtnPrevious.addEventListener('click', () => {
                const result = rdecreasePageBtn(pageCount);
                pageCount = result;
            });
            rbtnNext.addEventListener('click', () => {
                const result = rincreasePageBtn(pageCount, maxPage);
                pageCount = result;
            });
        });
    }
    
    // 사업자 아이디 승인목록 불러오기
    function sponsorCheckList() {
        btnToggleSponsorCheckList.style.textDecoration = 'underline';
        btnToggleSponsorCheckList.style.fontWeight = 'bold';
        divSponsorCheckList.style.display = 'block';
        const uri = `../api/mypage/sponcheck/`;
                
        axios
        .get(uri)
        .then((response) => { getSponsorCheckList(response.data); })
        .catch((error) => { console.log(error); });
    }

    // 모든 버튼을 검정으로 모든 리스트를 안보이게 함!
    function allBtnAndDivDisable() {
        btnToggleMyProfile.style.textDecoration = 'none';
        btnToggleFestivalList.style.textDecoration = 'none';
        btnTogglePostList.style.textDecoration = 'none';
        btnToggleMyProfile.style.fontWeight = 'normal';
        btnToggleFestivalList.style.fontWeight = 'normal';
        btnTogglePostList.style.fontWeight = 'normal';
        divMyProfile.style.display = 'none';
        divFestivalList.style.display = 'none';
        divPostList.style.display = 'none';
        if(role == 1 || role == 3) {
            btnToggleReviewList.style.textDecoration = 'none';
            btnToggleReviewList.style.fontWeight = 'normal';
            divReviewList.style.display = 'none';
        }
        if(role == 3) {
            btnToggleSponsorCheckList.style.textDecoration = 'none';
            btnToggleSponsorCheckList.style.fontWeight = 'normal';
            divSponsorCheckList.style.display = 'none';
        }
    }
    
// ---------------- 왼쪽 버튼 부분 끝--------------------------------------

// ---------------- 내프로필 기능 부분 시작--------------------------------------    

    /* 로그인한 프로필 출력 */
    function getMyProfile(data) {
        const createdDate = getDate(data.meCreatedTime);
        let html =  `
        <div class="subindex_row">
            <div class="myprofile_box">
                <ul class="myprofile_row">
                    <li>
                        <div class="row_item id">
                            <span class="item_text">아이디</span>
                            <br class="middle280">
                            <span class="middle_data">${data.meUsername}</span>
                        </div>
                    </li>
                    <li>
                        <div class="row_item email">
                            <span class="item_text">이메일</span>
                            <br class="middle280">
                            <span class="middle_data">${data.meEmail}</span>
                        </div>
                    </li>
                    `
                    if(role == `2`) {
                        html += `
                        <li>
                            <div class="row_item sponsor">
                                <span class="item_text">업체명</span>
                                <br class="middle280">
                                <span class="middle_data">${data.meSponsor}</span>
                            </div>
                        </li>
                        `    
                    }
                    html += `
                    <li>
                        <div class="row_item createdtime">
                            <span class="item_text">가입일</span>
                            <br class="middle280">
                            <span class="middle_data">${createdDate}</span>
                        </div>
                    </li>
                    <li>
                        <div class="row_item authority">
                            <span class="item_text">&nbsp;권&nbsp;한&nbsp;</span>
                            <br class="middle280">
                            <span class="middle_data">${data.mrRoles}</span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="divPasswordChange my-2 d-flex">
            <div class="modal fade" id="passwordChangeModal" tabindex="-1" aria-labelledby="passwordChangeModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="passwordChangeModalLabel">비밀번호 변경</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div>
                                <input id="inputPassword" type="password" class="form-control" placeholder="비밀번호 입력"/>
                                <input id="inputPasswordCheck" type="password" class="form-control mt-2" placeholder="비밀번호 확인"/>
                            </div>
                            <div class="divPasswordCheckMessage my-2"></div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-secondary" id="btnPasswordChange">변경하기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="div-buttons">
            <button class="btnTogglePasswordChange btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#passwordChangeModal">비밀번호 변경</button>
            <button class="btnMemberWithdraw btn btn-outline-danger">탈퇴하기</button>
        </div>
        `
        divMyProfile.innerHTML = html;
        
        const btnTogglePasswordChange = document.querySelector('button.btnTogglePasswordChange');
        const btnMemberWithdraw = document.querySelector('button.btnMemberWithdraw');
        const divPasswordChange = document.querySelector('div.divPasswordChange');
        
        btnPasswordChange.addEventListener('click', () => {
            passwordChange();
        });
        
        /*console.log('divPasswordChange.innerHTML: '+divPasswordChange.innerHTML);
        btnTogglePasswordChange.addEventListener('click', () => {
            console.log('divPasswordChange.innerHTML: '+divPasswordChange.innerHTML);
            if(divPasswordChange.innerHTML == '') {
                console.log('divPasswordChange.innerHTML: '+divPasswordChange.innerHTML);
                let addHtml = `
                
                `;
                divPasswordChange.innerHTML += addtml;

                const btnPasswordChange = document.querySelector('button#btnPasswordChange');


            } else {

                divPasswordChange.innerHTML = '';
            }
        });*/
        
        btnMemberWithdraw.addEventListener('click', () => {
            memberWithdraw();
        });
    }
    
    // 로그인된 아이디의 비밀번호 바꾸기
    function passwordChange() {
        let html = '';
        const divPasswordCheckMessage = document.querySelector('div.divPasswordCheckMessage');
        const inputPassword = document.querySelector('input#inputPassword').value;
        const inputPasswordCheck = document.querySelector('input#inputPasswordCheck').value;
        const regExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!"#$%&'()*+,\-./:;<=>?@\[₩\]\^_`\{\|\}~])[\w!"#$%&'()*+,\-./:;<=>?@\[₩\]\^`\{\|\}~]{8,20}$/;

        console.log(window.location.href);
        if(inputPassword === '' || inputPasswordCheck === '') {
            html = '<p class="p-danger">비밀번호를 입력해주세요!</p>';
        } else if (inputPassword !== inputPasswordCheck) {
            html = '<p class="p-danger">비밀번호가 같지 않습니다!</p>';
        } else if (!regExp.test(inputPassword)) {
            html = '비밀번호: 8~20자의 영문 대/소문자, 숫자, 특수문자를 사용해주세요.';
        } else {
            const meUsername = signedInUser;
            const mePassword = inputPassword;
            const data = { meUsername, mePassword };
            console.log(data);
            const uri = `../api/mypage/chapass`;
            axios
            .put(uri, data)
            .then((response) => {
                console.log(response);
                alert("비밀번호 변경완료 \n다시 로그인해주세요!");
                const uri2 = `../user/signout`;
                axios
                .get(uri2)
                .then((response) => {
                    console.log(response);
                    window.location.href = '../';
                })
                .catch((error) => { console.log(error); });
            })
            .catch((error) => { console.log(error); });
        }
        divPasswordCheckMessage.innerHTML = html;
    }
    
    // 로그인된 아이디 탈퇴하기
    function memberWithdraw() {
         const result = confirm("계정을 삭제하시겠습니까?\n기존 데이터는 복구되지 않습니다.");
         if(!result) return;
         const uri = `../api/mypage/delmem/${signedInUser}`;
         
         axios
         .delete(uri)
         .then((response) => {
            console.log(response);
            alert("계정을 삭제했습니다.")
            const uri2 = `../user/signout`;
            axios
            .get(uri2)
            .then((response) => {
                console.log(response);
                window.location.href = '../';
            })
            .catch((error) => { console.log(error); });
         })
    }
    
// ---------------- 내프로필 기능 부분 끝--------------------------------------    

// ---------------- 축제 기능 부분 시작 ---------------------------------------

    function getAFestivalEachNumberAndCategory(eachNumber, category) {
        const aUri = `../api/mypage/afestivals/`;
                        
        axios
        .get(aUri, { params:   
            {category: category, eachNumber: eachNumber}})
        .then((response) => { getAFestivalList(response.data, eachNumber, category); })
        .catch((error) => { console.log(error); });
    }
    
    function getSFestivalEachNumberAndCategory(eachNumber, category) {
        const sUri = `../api/mypage/sfestivals/`;
                        
        axios
        .get(sUri, { params:   
            {category: category, eachNumber: eachNumber}})
        .then((response) => { getSFestivalList(response.data, eachNumber, category); })
        .catch((error) => { console.log(error); });
    }
    
    function getUFestivalEachNumber(eachNumber) {
        const uUri = `../api/mypage/ufestivals/`;
                        
        axios
        .get(uUri, { params:   
            {eachNumber: eachNumber}})
        .then((response) => { getUFestivalList(response.data, eachNumber); })
        .catch((error) => { console.log(error); });
    }

    function getUFestivalList(data, eachNumber) {
        const divUFestivalList = document.querySelector("div#divUFestivalList");
        eachNumber += 6;
        let today = new Date();
        let html = ""
        if(data == "") {
            html += `<h>좋아요한 축제가 없습니다<h>`
            divFestivalList.innerHTML = html;
            return;
        }
        for(const festival of data) {
            const period = getDate(festival.feStartDate) + " ~ " + getDate(festival.feEndDate);
            let addHtml = `
            <div class="card my-3 me-4">
                <div class="card-body d-flex">
                    <img class="me-2" src="/festgo/uploads/${festival.feImageMain}" class="img-thumbnail float-start" 
                    alt="${festival.feImageMain}" style="width: 300px; height: 300px;"/>
                    <table class="inline">
                        <tr>
                            <th>축제명<th>
                            <td>${festival.feName }</td>
                        </tr>
                        <tr>
                            <th>연락처<th>
                            <td>${festival.fePhone }</td>
                        </tr>
                        <tr>
                            <th>주최자<th>
                            <td>${festival.meSponsor }</td>
                        </tr>
                        <tr>
                            <th>축제기간<th>
                            <td>${period}</td>
                        </tr>
                    </table>
                </div>
                <div class="card-footer d-flex justify-content-between">
                    <div class="justify-content-start d-inline">
            `;
            const startDate = new Date(festival.feStartDate[0], festival.feStartDate[1], festival.feStartDate[2]);
            const endDate = new Date(festival.feEndDate[0], festival.feEndDate[1], festival.feEndDate[2]);
            if(today < startDate) addHtml += `<button class="btn btn-secondary disabled">축제종료</button>`;
            if(startDate <= today && today <= endDate) addHtml += `<button class="btn btn-primary disabled">개최중</button>`;
            if(today > endDate) addHtml += `<button class="btn btn-secondary disabled">개최예정</button>`;
            addHtml +=`    
                    </div>
                    <div class="justify-content-end d-inline">
                        <a href="/festgo/fest/detail?feId=${festival.feId}"><button class="btnDetailFestival btn btn-outline-primary mx-2">상세보기</button></a>
                        <button data-id="${festival.feId}" like-state="1" class="btnLikeFestival btn btn-outline-danger mx-2">좋아요 해제</button>
                    </div>
                </div>
            </div>
            `;
            html += addHtml;
        }
        html += `<button each-number="${eachNumber}" class="btnEachNumber btn">더보기</button>`;
        divUFestivalList.innerHTML = html;

        const btnEachNumber = document.querySelector('button.btnEachNumber');
        btnEachNumber.addEventListener('click', () => {
            const uUri = `../api/mypage/ufestivals/`;
                                                
            axios
            .get(uUri, { params:   
                {eachNumber: eachNumber}})
            .then((response) => { getUFestivalList(response.data, eachNumber); })
            .catch((error) => { console.log(error); });
        });
        
        const btnLikeFestivals = document.querySelectorAll('button.btnLikeFestival');
        for(const btn of btnLikeFestivals) {
            btn.addEventListener('click', btnLikeFestival);
        }
    }
    
    function btnLikeFestival(event) { 
        const feId = event.target.getAttribute("data-id");
        const likeState = event.target.getAttribute("like-state");
        
        if(likeState == 0) {
            const uri = `../api/mypage/likefest/${feId}`;
            axios
            .get(uri)
            .then(() => {
                event.target.setAttribute("like-state", "1");
                event.target.innerHTML = '좋아요 해제';
            })
            .catch((error) => { console.log(error); });
        } else if(likeState == 1) {
            const uri = `../api/mypage/nlikefest/${feId}`;
            axios
            .delete(uri)
            .then(() => {
                event.target.setAttribute("like-state", "0");
                event.target.innerHTML = '좋아요';
            })
            .catch((error) => { console.log(error); });
        }
    }
    
    function getSFestivalList(data, eachNumber, category) {
        const divSFestivalList = document.querySelector("div#divSFestivalList");
        eachNumber += 6;
        let html = "";
        if(data == "") {
            html += `<h>등록한 축제가 없습니다<h>`
            divFestivalList.innerHTML = html;
            return;
        }
        for(const festival of data) {
            const period = getDate(festival.feStartDate) + " ~ " + getDate(festival.feEndDate);
            let addHtml = `
            <div class="card my-3 me-4">
                <div class="card-body d-flex">
                    <img class="me-2" src="/festgo/uploads/${festival.feImageMain}" class="img-thumbnail float-start" 
                    alt="${festival.feImageMain}" style="width: 300px; height: 300px;"/>
                    <table class="inline">
                        <tr>
                            <th>축제명<th>
                            <td>${festival.feName }</td>
                        </tr>
                        <tr>
                            <th>축제기간<th>
                            <td>${period}</td>
                        </tr>
            `;
            if(festival.frApproval == 2) {
                addHtml += `
                        <tr>
                            <th>거절사유<th>
                            <td>${festival.frCause }</td>
                        </tr>
                `;
            }
            addHtml += `
                    </table>
                </div>
                <div class="card-footer d-flex justify-content-between">
                    <div class="justify-content-start d-inline">
            `;
            switch(festival.frApproval) {
                case 0 :
                    addHtml += `
                        <button class="btn btn-success disabled">게시중</button>`;
                    break;
                case 1 :
                    addHtml += `
                        <button class="btn btn-primary disabled">승인대기</button>`;
                    break;
                case 2 :
                    addHtml += `
                        <button class="btn btn-secondary disabled">거절됨</button>`;
                    break;
            }
            addHtml +=`    
                    </div>
                    <div class="justify-content-end d-inline">
                    <a href="/festgo/fest/detail?feId=${festival.feId}"><button class="btnDetailFestival btn btn-outline-primary mx-2">상세보기</button></a>
                        <button data-id="${festival.feId}" class="btnDeleteFestival btn btn-outline-danger mx-2">삭제</button>
                    </div>
                </div>
            </div>
            `;
            html += addHtml;
        }
        html += `<button each-number="${eachNumber}" category="${category}"class="btnEachNumber btn">더보기</button>`;
        divSFestivalList.innerHTML = html;
        
        const btnEachNumber = document.querySelector('button.btnEachNumber');
        btnEachNumber.addEventListener('click', () => {
            const sUri = `../api/mypage/sfestivals/`;
                                                
            axios
            .get(sUri, { params:   
                {category: category, eachNumber: eachNumber}})
            .then((response) => { getSFestivalList(response.data, eachNumber, category); })
            .catch((error) => { console.log(error); });
        });
    }
    
    function getAFestivalList(data, eachNumber, category) {
        const divAFestivalList = document.querySelector("div#divAFestivalList");
        eachNumber += 6;    
        let html = "";       
        if(data == "") {
            html += `<h>등록된 축제가 없습니다<h>`
            divFestivalList.innerHTML = html;
            return;
        }
        for(const festival of data) {
            const period = getDate(festival.feStartDate) + " ~ " + getDate(festival.feEndDate);
            let addHtml = `
            <div class="card col-6 mt-4">
                <img src="/festgo/uploads/${festival.feImageMain}" class="card-img-top" 
                    alt="${festival.feImageMain}" style="width: 300px; height: 300px;"/>
                <div class="card-body">
                    <table class="inline">
                        <tr>
                            <th>축제명<th>
                            <td>${festival.feName }</td>
                        </tr>
                        <tr>
                            <th>연락처<th>
                            <td>${festival.fePhone }</td>
                        </tr>
                        <tr>
                            <th>주최자<th>
                            <td>${festival.meSponsor }</td>
                        </tr>
                        <tr>
                            <th>축제기간<th>
                            <td>${period}</td>
                        </tr>
                    </table>
                </div>
                <div class="card-footer d-flex justify-content-between">
                    <div class="justify-content-start d-inline">`
            switch(festival.frApproval) {
                case 0 :
                    addHtml += `
                        <button class="btn btn-success disabled">게시중</button>`;
                    break;
                case 1 :
                    addHtml += `
                        <button class="btn btn-primary disabled">승인대기</button>`;
                    break;
                case 2 :
                    addHtml += `
                        <button class="btn btn-secondary disabled">거절됨</button>`;
                    break;
            }
            addHtml +=`    
                    </div>
                    <div class="justify-content-end d-inline">
                        <a href="/festgo/fest/detail?feId=${festival.feId}"><button class="btnDetailFestival btn btn-outline-primary mx-2">상세보기</button></a>
                        <button data-id="${festival.feId}" class="btnDeleteFestival btn btn-outline-danger mx-2">삭제</button>
            `;
            if(festival.frApproval == 1) {
                addHtml += `
                <button data-id="${festival.feId}" class="btnApproveFestival btn btn-outline-success mx-2">승인</button>
                <button data-id="${festival.feId}" class="btnToggleRefuseFestival btn btn-outline-secondary mx-2"
                    data-bs-toggle="collapse" data-bs-target="#collapseRefuseFestival${festival.feId}" aria-expanded="false" aria-controls="collapseRefuseFestival${festival.feId}">
                        거절
                    </button>
                </div>
                </div>
                <div class="collapse" id="collapseRefuseFestival${festival.feId}">
                    <div class="card card-body">
                        <div class="input-group mb-3">
                            <input data-id="${festival.feId}" type="text" class="inputRefuseFestivalText form-control" placeholder="거절사유를 입력해주세요" aria-describedby="button-addon2">
                            <button data-id="${festival.feId}" class="btnRefuseFestival btn btn-outline-secondary" type="button" id="button-addon2">작성완료</button>
                        </div>
                    </div>
                </div>
                `
            } else {
                addHtml += `</div>`
            }
            addHtml += `
                </div>
            </div>   
            `;
            html += addHtml;
        }
        html += `<button each-number="${eachNumber}" category="${category}"class="btnEachNumber btn">더보기</button>`;
        divAFestivalList.innerHTML = html;
        const btnDeleteFestivals = document.querySelectorAll('button.btnDeleteFestival');
        for(const btn of btnDeleteFestivals) {
            btn.addEventListener('click', deleteFestival);
        }
        const btnApproveFestivals = document.querySelectorAll('button.btnApproveFestival');
        for(const btn of btnApproveFestivals) {
            btn.addEventListener('click', approveFestival);
        }
        const btnRefuseFestivals = document.querySelectorAll('button.btnRefuseFestival');
        for(const btn of btnRefuseFestivals) {
            btn.addEventListener('click', refuseFestival);
        }
        const btnEachNumber = document.querySelector('button.btnEachNumber');
        btnEachNumber.addEventListener('click', () => {
            const aUri = `../api/mypage/afestivals/`;
                                                
            axios
            .get(aUri, { params:   
                {category: category, eachNumber: eachNumber}})
            .then((response) => { getAFestivalList(response.data, eachNumber, category); })
            .catch((error) => { console.log(error); });
        });
    }

    
    // 축제 승인 함수
    function approveFestival(event) {
        console.log(event.target);
                        
        const result = confirm("축제등록을 승인할까요?");
        if(!result) {
            return;
        }
        
        const feId = event.target.getAttribute("data-id");
        const uri = `../api/mypage/festapp/${feId}`
        
        axios
        .get(uri)
        .then((response) => {
            console.log(response);
            alert("축제 승인완료");
            festivalList()
        })
        .catch((error) => { console.log(error) });
    }
    
    // 축제 거절 함수
    function refuseFestival(event) {
        console.log(event.target);
        
        const feId = event.target.getAttribute("data-id");
        const frCause = document.querySelector(`input[data-id="${feId}"]`).value;
        if(frCause == "") {
            alert("내용을 입력해주세요")
            return;
        }
        const result = confirm("등록을 거절하시겠습니까?");
        if(!result) {
            return;
        }
        
        const data = { frCause, feId };
        const uri = `../api/mypage/festref/`
        
        axios
        .put(uri, data)
        .then((response) => {
            console.log(response);
            alert("축제 거절완료");
            festivalList()
        })    
        .catch((error) => { console.log(error); });
    }

    // 축제 삭제 함수
    function deleteFestival(event) {
        console.log(event.target);
        
        const result = confirm("축제를 삭제할까요?")
        if(!result) return;
        
        const feId = event.target.getAttribute("data-id")
        const uri = `../api/mypage/festdel/${feId}`
        
        axios
        .delete(uri)
        .then((response) => {
            console.log(response);
            alert("축제 삭제완료");
            festivalList()
        })
        console.log(result);
    }

// ---------------- 축제 기능 부분 끝 -----------------------------------------

// ---------------- 작성글 기능 부분 시작 -------------------------------------

    function getPostNum(callback) {
        switch(role) {
            case `1`:
            case `2`:
                const usUri = `../api/mypage/cntaposts/${signedInUser}`;
                axios
                .get(usUri)
                .then((response) => {
                    callback(response.data); // 콜백 함수로 데이터 전달
                })
                .catch((error) => { 
                    console.log(error);
                    callback(null); // 오류 발생 시 null 반환
                });
                break;
            case `3`:
                const aUri = `../api/mypage/cntaposts/`;
                axios
                .get(aUri)
                .then((response) => {
                    callback(response.data); // 콜백 함수로 데이터 전달
                })
                .catch((error) => { 
                    console.log(error);
                    callback(null); // 오류 발생 시 null 반환
                });
                break;
        }
    }
    
    function makeingPostList(pageNum) {    
        switch(role) {
            case `1`:
            case `2`:
                const usUri = `../api/mypage/usposts/${pageNum}`;
                
                axios
                .get(usUri)
                .then((response) => {
                    if(response.data == '') {
                        notPostList();
                        return;
                    }
                    getPostList(response.data);
                })
                .catch((error) => { console.log(error)} );
                break;
            case `3`:
                const aUri = `../api/mypage/aposts/${pageNum}`;
                
                axios
                .get(aUri)
                .then((response) => {
                    if(response.data == '') {
                        notPostList();
                        return;
                    }
                    getPostList(response.data);
                })
                .catch((error) => { console.log(error)} );
                break;
        }
    }
    
    function notPostList() {
        const tbodyPostList = document.querySelector('tbody.tbodyPostList');
        tbodyPostList.innerHTML = '<p>작성한 게시글이 없습니다.</p>'
    }
    
    function getPostList(data)
    {
        let html = ``
        const tbodyPostList = document.querySelector("tbody.tbodyPostList");
        for(const post of data) {
            const date = getDateTime(post.poModifiedTime);
            let addHtml = `
                <tr>
            `
            if(post.pcId == 1) addHtml += `<td>${post.poId}</td>`
            else if(post.pcId == 2) addHtml += `<td>공지</td>`
            addHtml += `  
                    <td>
                        <a href="/festgo/post/details?poId=${post.poId}">${post.poTitle}</a>
            `
            if(role == 1 || role == 2){
                addHtml+= `
                        <a href="/festgo/post/modify?poId=${post.poId}">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="gray" 
                            class="bi bi-pencil" viewBox="0 0 16 16">
                                <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325"/>
                            </svg>
                        </a>
                `
            }
            addHtml += `
                        <a data-id="${post.poId}" class="btnDeletePost" style="cursor: pointer;">
                            <svg data-id="${post.poId}" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="gray" 
                            class="bi bi-trash" viewBox="0 0 16 16">
                                <path data-id="${post.poId}" d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>
                                <path data-id="${post.poId}" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>
                            </svg>
                        </a>
                    </td>
                    <td>${post.poAuthor}</td>
                    <td>${date}</td>
                    <td>${post.poViews}</td>
                </tr>
            `
            html += addHtml;
            }
        tbodyPostList.innerHTML = html;
        
        const btnDeletePosts = document.querySelectorAll('a.btnDeletePost');
        for(const btn of btnDeletePosts) {
            btn.addEventListener('click', (event) => {
                deletePost(event);
            });
        } 
    }
    
    function deletePost(event) {
        const result = confirm("글을 삭제하시겠습니까?");
        if(!result) {
            return;
        }
        const poId = event.target.getAttribute("data-id");
        const uri = `../api/mypage/delpost/${poId}`;
        console.log(poId, uri);
        
        axios
        .delete(uri)
        .then(() => {
            alert("삭제완료했습니다.")
            postList();
        })
        .catch((error) => { console.log(error); });
    }

// ---------------- 작성글 기능 부분 끝 ---------------------------------------

// ---------------- 리뷰 기능 부분 시작 ---------------------------------------

    function getReviewNum(callback) {
        switch(role) {
            case `1`:
                const uUri = `../api/mypage/cntreviews/${signedInUser}`;
                axios
                .get(uUri)
                .then((response) => {
                    callback(response.data); // 콜백 함수로 데이터 전달
                })
                .catch((error) => { 
                    console.log(error);
                    callback(null); // 오류 발생 시 null 반환
                });
                break;
            case `3`:
                const aUri = `../api/mypage/cntreviews/`;
                axios
                .get(aUri)
                .then((response) => {
                    callback(response.data); // 콜백 함수로 데이터 전달
                })
                .catch((error) => { 
                    console.log(error);
                    callback(null); // 오류 발생 시 null 반환
                });
                break;
        }
    }
    
    function makeingReviewList(pageNum) {    
        switch(role) {
            case `1`:
                const uUri = `../api/mypage/ureviews/${pageNum}`;
                
                axios
                .get(uUri)
                .then((response) => {
                    if(response.data == '') {
                        notReviewList();
                        return;
                    }
                    getReviewList(response.data);
                })
                .catch((error) => { console.log(error)} );
                break;
            case `3`:
                const aUri = `../api/mypage/areviews/${pageNum}`;
                
                axios
                .get(aUri)
                .then((response) => {
                    if(response.data == '') {
                        notReviewList();
                        return;
                    }
                    getReviewList(response.data);
                })
                .catch((error) => { console.log(error)} );
                break;
        }
    }
        
    function notReviewList() {
        const tbodyReviewList = document.querySelector('tbody.tbodyReviewList');
        tbodyReviewList.innerHTML = '<p>작성한 리뷰가 없습니다.</p>'
    }
    
    function getReviewList(data)
    {
        let html = ``
        const tbodyReviewList = document.querySelector("tbody.tbodyReviewList");
        for(const review of data) {
            const date = getDateTime(review.reModifiedTime);
            let addHtml = `
                <tr>
                    <td>${review.reId}</td>
                    <td>
                        <a href="/festgo/fest/detail?feId=${review.feId}">${review.reTitle}</a>
            `
            if(role == 1){
                addHtml+= `
                        <a href="/festgo/fest/detail?feId=${review.feId}">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="gray" 
                            class="bi bi-pencil" viewBox="0 0 16 16">
                                <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325"/>
                            </svg>
                        </a>
                `
            }
            addHtml += `
                        <a data-id="${review.reId}" class="btnDeleteReview" style="cursor: pointer;">
                            <svg data-id="${review.reId}" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="gray" 
                            class="bi bi-trash" viewBox="0 0 16 16">
                                <path data-id="${review.reId}" d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>
                                <path data-id="${review.reId}" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>
                            </svg>
                        </a>
                    </td>
                    <td>${review.reAuthor}</td>
                    <td>${date}</td>
                    <td>${review.reGrade}</td>
                </tr>
            `
            html += addHtml;
            }
        tbodyReviewList.innerHTML = html;
    
        const btnDeleteReviews = document.querySelectorAll('a.btnDeleteReview');
        for(const btn of btnDeleteReviews) {
            btn.addEventListener('click', (event) => {
                deleteReview(event);
            });
        } 
    }

    function deleteReview(event) {
        const result = confirm("리뷰를 삭제하시겠습니까?");
        if(!result) {
            return;
        }
        const reId = event.target.getAttribute("data-id");
        const uri = `../api/mypage/delreview/${reId}`;
        
        axios
        .delete(uri)
        .then(() => {
            alert("삭제완료했습니다.")
            reviewList();
        })
        .catch((error) => { console.log(error); });
    }

// ---------------- 리뷰 기능 부분 끝 -----------------------------------------

// ---------------- 사업자승인 기능 부분 시작 ---------------------------------

    function getSponsorCheckList(data) {
        let html = "";
        if(data == "") {
            html = `<h>승인대기 아이디가 없습니다.<h>`
            divSponsorCheckList.innerHTML = html;
            return;
        }
        for(const requestSponsor of data) {
            const createdTime = requestSponsor.meCreatedTime[0] + "년 " + requestSponsor.meCreatedTime[1] + "월 "
                                 + requestSponsor.meCreatedTime[2] + "일 " + requestSponsor.meCreatedTime[4] + "시 "
                                 + requestSponsor.meCreatedTime[5] + "분";
            let addHtml = `
                <div class="subindex_row card">
                    <div class="sponrequest_box">
                        <ul class="sponrequest_row" style="margin-bottom: 0">
                            <li>
                                <div class="row_item id">
                                    <span class="item_text">아이디</span>
                                    <br class="middle280">
                                    <span class="middle_data">${requestSponsor.meUsername}</span>
                                </div>
                            </li>
                            <li>
                                <div class="row_item email">
                                    <span class="item_text">이메일</span>
                                    <br class="middle280">
                                    <span class="middle_data">${requestSponsor.meEmail}</span>
                                </div>
                            </li>
                            <li>
                                <div class="row_item sponsor">
                                    <span class="item_text">업체명</span>
                                    <br class="middle280">
                                    <span class="middle_data">${requestSponsor.meSponsor}</span>
                                </div>
                            </li>
                            <li>
                                <div class="row_item createdtime">
                                    <span class="item_text">가입일</span>
                                    <br class="middle280">
                                    <span class="middle_data">${createdTime}</span>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="card-footer d-flex justify-content-end my-3">
                    <button data-id="${requestSponsor.meId}" class="btnApproveSponsor btn btn-outline-success mx-2">승인</button>
                    <button data-id="${requestSponsor.meId}" class="btnToggleRefuseSponsor btn btn-outline-secondary mx-2"
                    data-bs-toggle="collapse" data-bs-target="#collapseRefuseSponsor${requestSponsor.meId}" aria-expanded="false" aria-controls="collapseRefuseSponsor${requestSponsor.meId}">
                        거절
                    </button>
                </div>
                    <div class="collapse" id="collapseRefuseSponsor${requestSponsor.meId}">
                        <div class="card card-body">
                            <div class="input-group mb-3">
                                <input data-id="${requestSponsor.meId}" type="text" class="inputRefuseSponsorText form-control" placeholder="거절사유를 입력해주세요" aria-describedby="button-addon2">
                                <button data-id="${requestSponsor.meId}" class="btnRefuseSponsor btn btn-outline-secondary" type="button" id="button-addon2">작성완료</button>
                            </div>
                        </div>
                    </div>
                </div>
            `;
            html += addHtml;
        }
        
        divSponsorCheckList.innerHTML = html;
        
        const btnApproveSponsors = document.querySelectorAll('button.btnApproveSponsor');
        for(const btn of btnApproveSponsors) {
            btn.addEventListener('click', approveSponsor);
        }
        
        const btnRefuseSponsors = document.querySelectorAll('button.btnRefuseSponsor');
        for(const btn of btnRefuseSponsors) {
            btn.addEventListener('click', RefuseSponsor);
        }
    }
    
    function approveSponsor(event) {
        console.log(event.target);
                
        const result = confirm("업체등록을 승인할까요?");
        if(!result) {
            return;
        }
        
        const meId = event.target.getAttribute("data-id");
        const uri = `../api/mypage/sponapp/${meId}`
        
        axios
        .get(uri)
        .then((response) => {
            console.log(response);
            alert("업체 승인완료");
            sponsorCheckList();
        })
        .catch((error) => { console.log(error) });
        console.log(result);
    }
    
    function RefuseSponsor(event) {
        console.log(event.target);
        const meId = event.target.getAttribute("data-id");
        const srCause = document.querySelector(`input[data-id="${meId}"]`).value;
        if(srCause == "") {
            alert("내용을 입력해주세요")
            return;
        }
        const result = confirm("등록을 거절하시겠습니까?");
        if(!result) {
            return;
        }
        
        const data = { srCause, meId };
        const uri = `../api/mypage/sponref/`
        
        axios
        .put(uri, data)
        .then((response) => {
            console.log(response);
            alert("업체 거절완료");
            SponsorCheckList();
        })    
        .catch((error) => { console.log(error); });
    }

// ---------------- 사업자승인 기능 부분 끝 -----------------------------------

// ---------------- 기타 기능 부분 시작 ---------------------------------------
    
    // json 시간에서 문자열로 날짜만 가져오기
    function getDate(jsonTime){
        return jsonTime[0] + "년 " + jsonTime[1] + "월 " + jsonTime[2] + "일"
    }
    
    // json 시간에서 문자열로 날짜시간 가져오기
    function getDateTime(jsonTime){
        const date = new Date(jsonTime[0], jsonTime[1], jsonTime[2], jsonTime[3], jsonTime[4], jsonTime[5]);
        const formattedDate = `${date.getFullYear()}. ${date.getMonth()}. ${date.getDay()} ${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
        return formattedDate;
    }

    function pincreasePageBtn(pageCount, maxPage) {
        pageCount += 3
        console.log(pageCount);
        const pbtnPrevious = document.querySelector('button.pbtnPrevious');
        const pbtnNext = document.querySelector('button.pbtnNext');
        const pinputFirstPaging = document.querySelector('input.pbtnFirstPaging');
        const pinputSecondPaging = document.querySelector('input.pbtnSecondPaging');
        const pinputThirdPaging = document.querySelector('input.pbtnThirdPaging');
        pbtnPrevious.disabled = false;
        if(maxPage >= pageCount) pbtnNext.disabled = true;
        pinputFirstPaging.value = pageCount;
        pinputSecondPaging.value = pageCount+1;
        pinputThirdPaging.value = pageCount+2;
        return pageCount
    }

    function pdecreasePageBtn(pageCount) {
        pageCount -= 3
        console.log(pageCount);
        const pbtnPrevious = document.querySelector('button.pbtnPrevious');
        const pbtnNext = document.querySelector('button.pbtnNext');
        const pinputFirstPaging = document.querySelector('input.pbtnFirstPaging');
        const pinputSecondPaging = document.querySelector('input.pbtnSecondPaging');
        const pinputThirdPaging = document.querySelector('input.pbtnThirdPaging');
        pbtnNext.disabled = false;
        if(pageCount <= 2) pbtnPrevious.disabled = true;
        pinputFirstPaging.value = pageCount;
        pinputSecondPaging.value = pageCount+1;
        pinputThirdPaging.value = pageCount+2;
        return pageCount
    }

    function rincreasePageBtn(pageCount, maxPage) {
        pageCount += 3
        console.log(pageCount);
        const rbtnPrevious = document.querySelector('button.rbtnPrevious');
        const rbtnNext = document.querySelector('button.rbtnNext');
        const rinputFirstPaging = document.querySelector('input.rbtnFirstPaging');
        const rinputSecondPaging = document.querySelector('input.rbtnSecondPaging');
        const rinputThirdPaging = document.querySelector('input.rbtnThirdPaging');
        rbtnPrevious.disabled = false;
        if(maxPage >= pageCount) rbtnNext.disabled = true;
        rinputFirstPaging.value = pageCount;
        rinputSecondPaging.value = pageCount+1;
        rinputThirdPaging.value = pageCount+2;
        return pageCount
    }

    function rdecreasePageBtn(pageCount) {
        pageCount -= 3
        console.log(pageCount);
        const rbtnPrevious = document.querySelector('button.rbtnPrevious');
        const rbtnNext = document.querySelector('button.rbtnNext');
        const rinputFirstPaging = document.querySelector('input.rbtnFirstPaging');
        const rinputSecondPaging = document.querySelector('input.rbtnSecondPaging');
        const rinputThirdPaging = document.querySelector('input.rbtnThirdPaging');
        rbtnNext.disabled = false;
        if(pageCount <= 2) rbtnPrevious.disabled = true;
        rinputFirstPaging.value = pageCount;
        rinputSecondPaging.value = pageCount+1;
        rinputThirdPaging.value = pageCount+2;
        return pageCount
    }
    
    /*    // 날짜 시간이 한자리면 0채워 주기
    function addZero(data) {
        console.log(data.length);
        if(data.length === 1) {
            const newData = `0${data}`;
            return newData;
        }
        return data;
    }*/

// ---------------- 기타 기능 부분 끝 -----------------``````------------------
});