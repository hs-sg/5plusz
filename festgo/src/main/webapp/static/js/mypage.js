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
        if(btnToggleMyProfile.style.color == 'blue') {
            btnToggleMyProfile.style.color = 'black';
            divMyProfile.style.display = 'none';
        } else {
            btnToggleMyProfile.style.color = 'blue';
            divMyProfile.style.display = 'block';
            
            const uri = `../api/mypage/profile/${signedInUser}`;
            
            axios
            .get(uri)
            .then((response) => { getMyProfile(response.data); })
            .catch((error) => { console.log(error); });
        }
    });

    btnToggleFestivalList.addEventListener('click', () => {
        if(btnToggleFestivalList.style.color == 'blue') {
            btnToggleFestivalList.style.color = 'black';
            divFestivalList.style.display = 'none';
        } else {
            btnToggleFestivalList.style.color = 'blue';
            divFestivalList.style.display = 'block';
            
            switch(role) {
                case 1 : // 일반유저
                    const UUri = `../api/mypage/ufestivals/${signedInUser}`;
                    
                    axios
                    .get(UUri)
                    .then((response) => { getUFestivalList(response.data); })
                    .catch((error) => { console.log(error); });
                    
                    break;
                    
                case 2 :
                    const BUri = `../api/mypage/bfestivals/${signedInUser}`;

                    axios
                    .get(BUri)
                    .then((response) => { getBFestivalList(response.data); })
                    .catch((error) => { console.log(error); });

                    break;
                    
                case 3 :
                    const AUri = `../api/mypage/afestivals/${signedInUser}`;

                    axios
                    .get(AUri)
                    .then((response) => { getAFestivalList(response.data); })
                    .catch((error) => { console.log(error); });

                    break;

            }
            
        }
    });

    btnTogglePostList.addEventListener('click', () => {
        if (btnTogglePostList.style.color == 'blue') {
            btnTogglePostList.style.color = 'black';
        } else {
            btnTogglePostList.style.color = 'blue';
        }
    });

    btnToggleCommentList.addEventListener('click', () => {
        if (btnToggleCommentList.style.color == 'blue') {
            btnToggleCommentList.style.color = 'black';
        } else {
            btnToggleCommentList.style.color = 'blue';
        }
    });

    btnToggleSponsorCheckList.addEventListener('click', () => {
        if (btnToggleSponsorCheckList.style.color == 'blue') {
            btnToggleSponsorCheckList.style.color = 'black';
        } else {
            btnToggleSponsorCheckList.style.color = 'blue';
        }
    });
    
    /* 콜백 함수 --------------------------------------------------------------------------- */
    
    function divAllColorBlack() {
        divMyProfile.style.color = 'black';
        divFestivalList.style.color = 'black';
        divPostList.style.color = 'black';
        divCommentList.style.color = 'black';
        if(role == 3) {
            divSponsorCheckList.style.color = 'black';
        }
    }
    
    /* 로그인한 프로필 출력 */
    function getMyProfile(data) {
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
                    <td>${data.meCreatedTime}</td> 
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
        
    }
    
    function getBFestivalList(data) {
        
    }
    
    function getAFestivalList(data) {
        
    }
});