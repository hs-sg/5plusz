/**
 * mypage.jsp에 포함됨
 */

document.addEventListener('DOMContentLoaded', () => {
    const btnToggleMyProfile = document.querySelector('button#btnToggleMyProfile');
    const btnToggleFestivalList = document.querySelector('button#btnToggleFestivalList');
    const btnTogglePostList = document.querySelector('button#btnTogglePostList');
    const btnToggleCommentList = document.querySelector('button#btnToggleCommentList');
    const btnToggleSponsorCheckList = document.querySelector('button#btnToggleSponsorCheckList');

    const divMyProfile = document.getElementById('divMyProfile');
    const divFestivalList = document.getElementById('divFestivalList');
    const divPostList = document.getElementById('divPostList');
    const divCommentList = document.getElementById('divCommentList');
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

    btnToggleCommentList.addEventListener('click', () => {
        allBtnAndDivDisable();
        btnToggleCommentList.style.color = 'blue';
        divCommentList.style.display = 'block';
    });
    
    if(role == 3) {
        btnToggleSponsorCheckList.addEventListener('click', () => {
            allBtnAndDivDisable();
            SponsorCheckList();
        });
    }
    
    /* 콜백 함수 --------------------------------------------------------------------------- */
    
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
    
/*    // 날짜 시간이 한자리면 0채워 주기
    function addZero(data) {
        console.log(data.length);
        if(data.length === 1) {
            const newData = `0${data}`;
            return newData;
        }
        return data;
    }*/
    
    // 로그인된 아이디 프로필 가져오기
    function myProfile() {
        btnToggleMyProfile.style.color = 'blue';
        divMyProfile.style.display = 'block';
        const uri = `../api/mypage/profile/${signedInUser}`;
                    
        axios
        .get(uri)
        .then((response) => { getMyProfile(response.data); })
        .catch((error) => { console.log(error); });
    }
    
    // 사업자 아이디 승인목록 불러오기
    function SponsorCheckList() {
        btnToggleSponsorCheckList.style.color = 'blue';
        divSponsorCheckList.style.display = 'block';
        const uri = `../api/mypage/sponcheck/`;
                
        axios
        .get(uri)
        .then((response) => { getSponsorCheckList(response.data); })
        .catch((error) => { console.log(error); });
    }
    
    // 로그인된 아이디 권한별 축제 리스트 가져오기
    function festivalList() {
        btnToggleFestivalList.style.color = 'blue';
        divFestivalList.style.display = 'block';
        switch(role) {
            case `1` : // 일반유저
                console.log("user");
                const UUri = `../api/mypage/ufestivals/${signedInUser}`;
                                        
                axios
                .get(UUri)
                .then((response) => { getUFestivalList(response.data); })
                .catch((error) => { console.log(error); });
                
                break;
                
            case `2` :
                console.log("sponsor");
                const BUri = `../api/mypage/sfestivals/${signedInUser}`;

                axios
                .get(BUri)
                .then((response) => { getSFestivalList(response.data); })
                .catch((error) => { console.log(error); });

                break;
                
            case `3` :
                console.log("admin");
                const AUri = `../api/mypage/afestivals/`;

                axios
                .get(AUri)
                .then((response) => { getAFestivalList(response.data); })
                .catch((error) => { console.log(error); });

                break;
        }
    }
    
    // 모든 버튼을 검정으로 모든 리스트를 안보이게 함!
    function allBtnAndDivDisable() {
        btnToggleMyProfile.style.color = 'black';
        divMyProfile.style.display = 'none';
        btnToggleFestivalList.style.color = 'black';
        divFestivalList.style.display = 'none';
        btnTogglePostList.style.color = 'black';
        divPostList.style.display = 'none';
        if(role == 1 || role == 3) {
            btnToggleCommentList.style.color = 'black';
            divCommentList.style.display = 'none';
        }
        if(role == 3) {
            btnToggleSponsorCheckList.style.color = 'black';
            divSponsorCheckList.style.display = 'none';
        }
    }
    
    /* 로그인한 프로필 출력 */
    function getMyProfile(data) {
        const createdDate = getDate(data.meCreatedTime);
        let html = `
            <table>
                <tr>
                    <th>아이디</th>
                    <td>${data.meUsername}</td> 
                </tr>
                <tr>
                    <th>이메일</th>
                    <td>${data.meEmail}</td> 
                </tr>
                <tr>
                    <th>업체명</th>
                    <td>${data.meSponsor}</td> 
                </tr>
                <tr>
                    <th>아이디생성날짜</th>
                    <td>${createdDate}</td> 
                </tr>
                <tr>
                    <th>권한명</th>
                    <td>${data.mrRoles}</td> 
                </tr>
            </table>
            <div>
                <button class="btnTogglePasswordChange btn btn-outline-primary">비밀번호 변경</button>
                <button class="btnMemberWithdraw btn btn-outline-danger">탈퇴하기</button>
            </div>
            <div class=divPasswordChange my-2 d-flex">
            </div>
        `;
        divMyProfile.innerHTML = html;
        
        const btnTogglePasswordChange = document.querySelector('button.btnTogglePasswordChange');
        const btnMemberWithdraw = document.querySelector('button.btnMemberWithdraw');
        const divPasswordChange = document.querySelector('div.divPasswordChange');
        
        btnTogglePasswordChange.addEventListener('click', () => {
            if(divPasswordChange.innerHTML !== '') {
                divPasswordChange.innerHTML = '';
                return;
            } else {
                let html = `
                    <div>
                        <input id="inputPassword" type="password" placeholder="비밀번호 입력"/>
                        <input id="inputPasswordCheck" type="password" placeholder="비밀번호 확인"/>
                    </div>
                    <div class="divPasswordCheckMessage"></div>
                    <button id="btnPasswordChange" class="btn btn-outline-secondary">변경하기</button>
                `
                divPasswordChange.innerHTML = html;
                
                const btnPasswordChange = document.querySelector('button#btnPasswordChange');
                
                btnPasswordChange.addEventListener('click', () => {
                    passwordChange();
                });
            }
        });
        
        btnMemberWithdraw.addEventListener('click', () => {
            memberWithdraw();
        });
    }
    
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
    
    function memberWithdraw() {
         const result = confirm("계정을 삭제하시겠습니까?/n기존 데이터는 복구되지 않습니다.");
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
    
    function postList() {
        btnTogglePostList.style.color = 'blue';
        divPostList.style.display = 'block';
        let html = '';
        let pageCount = 1;
        
        getPostNum(function(totalPostNum) {
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
                    <button type="button" class="btnPrevious btn btn-outline-dark">Previous</button>
                    <input type="button" class="btnFirstPaging btn btn-outline-dark" value="${pageCount}"/>
                    <input type="button" class="btnSecondPaging btn btn-outline-dark" value="${pageCount+1}"/>
                    <input type="button" class="btnThirdPaging btn btn-outline-dark" value="${pageCount+2}"/>
                    <button type="button" class="btnNext btn btn-outline-dark">Next</button>
                </div>
            `;
            
            divPostList.innerHTML = html;
            
            const inputFirstPaging = document.querySelector('input.btnFirstPaging');
            const inputSecondPaging = document.querySelector('input.btnSecondPaging');
            const inputThirdPaging = document.querySelector('input.btnThirdPaging');
            const btnPrevious = document.querySelector('button.btnPrevious');
            const btnNext = document.querySelector('button.btnNext');
            
            let pageNum = inputFirstPaging.value;
            btnPrevious.disabled = true;
            if(maxPage <= pageCount) btnNext.disabled = true;
            makeingPostList(pageNum);
            
            inputFirstPaging.addEventListener('click', () => {
                pageNum = inputFirstPaging.value;
                makeingPostList(pageNum);
            });
            inputSecondPaging.addEventListener('click', () => {
                pageNum = inputSecondPaging.value;
                makeingPostList(pageNum);
            });
            inputThirdPaging.addEventListener('click', () => {
                pageNum = inputThirdPaging.value;
                makeingPostList(pageNum);
            });
            btnPrevious.addEventListener('click', () => {
                const result = decreasePageBtn(pageCount);
                pageCount = result;
            });
            btnNext.addEventListener('click', () => {
                const result = increasePageBtn(pageCount, maxPage);
                pageCount = result;
            });
        });
    }

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
    
    function increasePageBtn(pageCount, maxPage) {
        pageCount += 3
        console.log(pageCount);
        const btnPrevious = document.querySelector('button.btnPrevious');
        const btnNext = document.querySelector('button.btnNext');
        const inputFirstPaging = document.querySelector('input.btnFirstPaging');
        const inputSecondPaging = document.querySelector('input.btnSecondPaging');
        const inputThirdPaging = document.querySelector('input.btnThirdPaging');
        btnPrevious.disabled = false;
        if(maxPage >= pageCount) btnNext.disabled = true;
        inputFirstPaging.value = pageCount;
        inputSecondPaging.value = pageCount+1;
        inputThirdPaging.value = pageCount+2;
        return pageCount
    }
    
    function decreasePageBtn(pageCount) {
        pageCount -= 3
        console.log(pageCount);
        const btnPrevious = document.querySelector('button.btnPrevious');
        const btnNext = document.querySelector('button.btnNext');
        const inputFirstPaging = document.querySelector('input.btnFirstPaging');
        const inputSecondPaging = document.querySelector('input.btnSecondPaging');
        const inputThirdPaging = document.querySelector('input.btnThirdPaging');
        btnNext.disabled = false;
        if(pageCount <= 2) btnPrevious.disabled = true;
        inputFirstPaging.value = pageCount;
        inputSecondPaging.value = pageCount+1;
        inputThirdPaging.value = pageCount+2;
        return pageCount
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
                    getUSPostList(response.data);
                })
                .catch((error) => { console.log(error)} );
                break;
            case `3`:
                getAPostList
                const aUri = `../api/mypage/aposts/${pageNum}`;
                
                axios
                .get(aUri)
                .then((response) => {
                    if(response.data == '') {
                        notPostList();
                        return;
                    }
                    getAPostList(response.data);
                })
                .catch((error) => { console.log(error)} );
                break;
        }
    }
    
    function notPostList() {
        const tbodyPostList = document.querySelector('tbody.tbodyPostList');
        tbodyPostList.innerHTML = '<p>작성한 게시글이 없습니다.</p>'
    }
    
    function getUSPostList(data)
    {
        let html = ``
        const tbodyPostList = document.querySelector("tbody.tbodyPostList");
        for(const post of data) {
            const date = getDateTime(post.poModifiedTime);
            let addHtml = `
                <tr>
                    <td>${post.poId}</td>
                    <td><a href="/festgo/post/details?poId=${post.poId}">${post.poTitle}</a></td>
                    <td>${post.poAuthor}</td>
                    <td>${date}</td>
                    <td>${post.poViews}</td>
                </tr>
            `
        html += addHtml;
    }
    tbodyPostList.innerHTML = html;
    }
    
    function getAPostList(data)
    {
        let html = ``
        const tbodyPostList = document.querySelector("tbody.tbodyPostList");
        for(const post of data) {
            const date = getDateTime(post.poModifiedTime);
            let addHtml = `
                <tr>
                    <td>${post.poId}</td>
                    <td><a href="/festgo/post/details?poId=${post.poId}">${post.poTitle}</a></td>
                    <td>${post.poAuthor}</td>
                    <td>${date}</td>
                    <td>${post.poViews}</td>
                </tr>
            `
            html += addHtml;
        }
        tbodyPostList.innerHTML = html;
    }
    
    function getUFestivalList(data) {
        let today = new Date();
        let html = ""
        let hostIndex = location.href.indexOf(location.host) + location.host.length;
        let contextPath = location.href.substring(hostIndex, location.href.indexOf('/', hostIndex + 1));
        console.log(hostIndex);
        console.log(contextPath);
        if(data == "") {
            html = `<h>좋아요한 축제가 없습니다<h>`
            divFestivalList.innerHTML = html;
            return;
        }
        for(const festival of data) {
            const period = getDate(festival.feStartDate) + " ~ " + getDate(festival.feEndDate);
            const imgUrl = `${contextPath}/uploads/${festival.feImageMain}`;
            let addHtml = `
            <div class="card my-3 me-4">
                <div class="card-body d-flex">
                    <img class="me-2" src="/festgo/uploads/${festival.feImageMain}" class="rounded float-start" alt="${festival.feImageMain}"/>
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
            console.log(startDate, endDate, today);
            if(today < startDate) addHtml += `<button class="btn btn-secondary disabled">축제종료</button>`;
            if(startDate <= today && today <= endDate) addHtml += `<button class="btn btn-primary disabled">개최중</button>`;
            if(today > endDate) addHtml += `<button class="btn btn-secondary disabled">개최예정</button>`;
            addHtml +=`    
                    </div>
                    <div class="justify-content-end d-inline">
                        <button data-id="${festival.feId}" class="btn btn-outline-danger mx-2">좋아요 해제</button>
                    </div>
                </div>
            </div>
            `;
            html += addHtml;
        }
        divFestivalList.innerHTML = html;
    }
    
    function getSFestivalList(data) {
        let html = ""
        console.log(data);
        if(data == "") {
            html = `<h>등록한 축제가 없습니다<h>`
            divFestivalList.innerHTML = html;
            return;
        }
        for(const festival of data) {
            const period = getDate(festival.feStartDate) + " ~ " + getDate(festival.feEndDate);
            console.log(`/uploads/${festival.feImageMain}`);
            let addHtml = `
            <div class="card my-3 me-4">
                <div class="card-body d-flex">
                    <img class="me-2" src="/festgo/uploads/${festival.feImageMain}" class="rounded float-start" alt="${festival.feImageMain}"/>
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
                        <button data-id="${festival.feId}" class="btnDeleteFestival btn btn-outline-danger mx-2">삭제</button>
                    </div>
                </div>
            </div>
            `;
            html += addHtml;
        }
        divFestivalList.innerHTML = html;
    }
    
    function getAFestivalList(data) {
        let html = ""
        if(data == "") {
            html = `<h>등록된 축제가 없습니다<h>`
            divFestivalList.innerHTML = html;
            return;
        }
        for(const festival of data) {
            const period = getDate(festival.feStartDate) + " ~ " + getDate(festival.feEndDate);
            let addHtml = `
            <div class="card my-3 me-4">
                <div class="card-body d-flex">
                    <img class="me-2" src="/festgo/uploads/${festival.feImageMain}" class="rounded float-start" alt="${festival.feImageMain}"/>
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
        divFestivalList.innerHTML = html;
        
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
            FestivalList()
        })
        .catch((error) => { console.log(error) });
    }
    
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
            FestivalList()
        })    
        .catch((error) => { console.log(error); });
    }

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
            FestivalList()
        })
        console.log(result);
    }
    
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
                <div class="card my-3 me-4">
                    <div class="card-body d-flex">
                        <table>
                            <tr>
                                <th>아이디</th>
                                <td>${requestSponsor.meUsername}</td> 
                            </tr>
                            <tr>
                                <th>이메일</th>
                                <td>${requestSponsor.meEmail}</td> 
                            </tr>
                            <tr>
                                <th>업체명</th>
                                <td>${requestSponsor.meSponsor}</td> 
                            </tr>
                            <tr>
                                <th>생성날짜</th>
                                <td>${createdTime}</td> 
                            </tr>
                            
                        </table>
                    </div>
                    <div class="card-footer d-flex justify-content-end">
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
            SponsorCheckList();
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
});