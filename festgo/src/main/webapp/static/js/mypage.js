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
        divAllClose();
        if (divMyProfile.style.display == 'none') {
            divMyProfile.style.display = 'block';
        } else {
            divMyProfile.style.display = 'none';
        }
    });

    btnToggleFestivalList.addEventListener('click', () => {
        divAllClose();
        if (divFestivalList.style.display == 'none') {
            divFestivalList.style.display = 'block';
        } else {
            divFestivalList.style.display = 'none';
        }
    });

    btnTogglePostList.addEventListener('click', () => {
        divAllClose();
        if (divPostList.style.display == 'none') {
            divPostList.style.display = 'block';
        } else {
            divPostList.style.display = 'none';
        }
    });

    btnToggleCommentList.addEventListener('click', () => {
        divAllClose();
        if (divCommentList.style.display == 'none') {
            divCommentList.style.display = 'block';
        } else {
            divCommentList.style.display = 'none';
        }
    });

    btnToggleSponsorCheckList.addEventListener('click', () => {
        divAllClose();
        if (divSponsorCheckList.style.display == 'none') {
            divSponsorCheckList.style.display = 'block';
        } else {
            divSponsorCheckList.style.display = 'none';
        }
    });
    
    function divOpenClose() {
        divMyProfile.style.display = 'none';
        divFestivalList.style.display = 'none';
        divPostList.style.display = 'none';
        divCommentList.style.display = 'none';
        divSponsorCheckList.style.display = 'none';
    }
});