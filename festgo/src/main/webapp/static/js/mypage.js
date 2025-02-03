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
    
    btnToggleMyProfile.addEventListener('click', () => {
        allBtnAndDivDisable();
        btnToggleMyProfile.style.color = 'blue';
        divMyProfile.style.display = 'block';
        
        const uri = `../api/mypage/profile/${signedInUser}`;
            
        axios
        .get(uri)
        .then((response) => { getMyProfile(response.data); })
        .catch((error) => { console.log(error); });
    });

    btnToggleFestivalList.addEventListener('click', () => {
        allBtnAndDivDisable();
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
            
    });

    btnTogglePostList.addEventListener('click', () => {
        allBtnAndDivDisable();
        btnTogglePostList.style.color = 'blue';
        divPostList.style.display = 'block';
    });

    btnToggleCommentList.addEventListener('click', () => {
        allBtnAndDivDisable();
        btnToggleCommentList.style.color = 'blue';
        divCommentList.style.display = 'block';
    });

    btnToggleSponsorCheckList.addEventListener('click', () => {
        allBtnAndDivDisable();
        btnToggleSponsorCheckList.style.color = 'blue';
        divSponsorCheckList.style.display = 'block';
    });
    
    /* 콜백 함수 --------------------------------------------------------------------------- */
    
    function allBtnAndDivDisable() {
        btnToggleMyProfile.style.color = 'black';
        divMyProfile.style.display = 'none';
        btnToggleFestivalList.style.color = 'black';
        divFestivalList.style.display = 'none';
        btnTogglePostList.style.color = 'black';
        divPostList.style.display = 'none';
        btnToggleCommentList.style.color = 'black';
        divCommentList.style.display = 'none';
        if(role == 3) {
            btnToggleSponsorCheckList.style.color = 'black';
            divSponsorCheckList.style.display = 'none';
        }
    }
    
    /* 로그인한 프로필 출력 */
    function getMyProfile(data) {
        const createdDate = data.meCreatedTime[0] + "년 " + data.meCreatedTime[1] + "월 " + data.meCreatedTime[2] + "일 ";
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
        `;
        divMyProfile.innerHTML = html;
    }
    
    function getUFestivalList(data) {
        let html = ""
        if(data == "") {
            html = `<h>좋아요한 축제가 없습니다<h>`
            divFestivalList.innerHTML = html;
            return;
        }
        for(const festival of data) {
            const period = festival.feStartDate[0] + "년 " + festival.feStartDate[1] + "월 " + festival.feStartDate[2] + "일 ~ " +
                                        festival.feEndDate[0] + "년 " + festival.feEndDate[1] + "월 " + festival.feEndDate[2] + "일";
            let addHtml = `
            <div class="card my-3 me-4">
                <em class="card-body">
                    <img class="me-2" src="data:image/jpeg;base64,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" class="rounded float-start" alt="${festival.feImageMain}"/>
                </em>
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
            const period = festival.feStartDate[0] + "년 " + festival.feStartDate[1] + "월 " + festival.feStartDate[2] + "일 ~ " +
                            festival.feEndDate[0] + "년 " + festival.feEndDate[1] + "월 " + festival.feEndDate[2] + "일";
            let addHtml = `
            <div class="card my-3 me-4">
                <em class="card-body">
                    <img class="me-2" src="data:image/jpeg;base64,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" class="rounded float-start" alt="${festival.feImageMain}"/>
                </em>
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
            const period = festival.feStartDate[0] + "년 " + festival.feStartDate[1] + "월 " + festival.feStartDate[2] + "일 ~ " +
                            festival.feEndDate[0] + "년 " + festival.feEndDate[1] + "월 " + festival.feEndDate[2] + "일";
            let addHtml = `
            <div class="card my-3 me-4">
                <div class="card-body d-flex">
                    <img class="me-2" src="data:image/jpeg;base64,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" class="rounded float-start" alt="${festival.feImageMain}"/>
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
                    <div class="justify-content-end">
                        <h2><span class="badge text-bg-secondary">4</span></h2>
                    </div>
                </div>
                <div class="card-footer d-flex justify-content-end">
            `;
            html += addHtml;
            if(festival.frApproval == 1) {
                addHtml = `
                <button data-id="${festival.feId}" class="btnApproveFestival btn btn-outline-success mx-2">승인</button>
                <button data-id="${festival.feId}" class="btnRefuseFestival btn btn-outline-secondary mx-2">거절</button>`
                html += addHtml;
            }
            addHtml = `
            <button data-id="${festival.feId}" class="btnDeleteFestival btn btn-outline-danger mx-2">삭제</button>
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
    
    function deleteFestival(event) {
        console.log(event.target);
        
        const result = confirm("축제를 삭제할까요?")
        console.log(result);
    }
    function approveFestival(event) {
        console.log(event.target);
        
        const result = alert("축제승인완료!")
        console.log(result);
    }
    function refuseFestival(event) {
        console.log(event.target);
        
        const result = confirm("거절사유를 입력해주세요!")
        console.log(result);
    }
});