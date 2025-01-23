/**
 * mypage.jsp에 포함됨
 */

document.addEventListener('DOMContentLoaded', () => {
    const btnToggleMyProfileList = document.querySelector('button#btnToggleMyProfileList');
    const btnToggleFestivalList = document.querySelector('button#btnToggleFestivalList');
    const btnTogglePostList = document.querySelector('button#btnTogglePostList');
    const btnToggleCommentList = document.querySelector('button#btnToggleCommentList');
    const btnToggleSponsorCheckList = document.querySelector('button#btnToggleSponsorCheckList');

    const divMyProfile = document.getElementById('divMyProfile');
    const test = document.getElementById('test');

    btnToggleMyProfileList.addEventListener('click', () => {
        if (divMyProfile.style.display == 'none') {
            divMyProfile.style.display = 'block';
        } else {
            divMyProfile.style.display = 'none';
        }
    });

    btnToggleFestivalList.addEventListener('click', () => {
        if (divMyProfile.style.display == 'none') {
            divMyProfile.style.display = 'block';
        } else {
            divMyProfile.style.display = 'none';
        }
    });

    btnTogglePostList.addEventListener('click', () => {
        if (divMyProfile.style.display == 'none') {
            divMyProfile.style.display = 'block';
        } else {
            divMyProfile.style.display = 'none';
        }
    });

    btnToggleCommentList.addEventListener('click', () => {
        if (divMyProfile.style.display == 'none') {
            divMyProfile.style.display = 'block';
        } else {
            divMyProfile.style.display = 'none';
        }
    });

    btnToggleSponsorCheckList.addEventListener('click', () => {
        if (divMyProfile.style.display == 'none') {
            divMyProfile.style.display = 'block';
        } else {
            divMyProfile.style.display = 'none';
        }
    });
});