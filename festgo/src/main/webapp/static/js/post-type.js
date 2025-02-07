/**
 * post-type (공지사항, 일반글)
 */

document.addEventListener('DOMContentLoaded', function () {
    const postTypeSelect = document.getElementById('postType');
    const userRoleMeta = document.querySelector('meta[name="user-role"]');
    
    if (!postTypeSelect || !userRoleMeta) {
        console.error('postTypeSelect or userRoleMeta not found');
        return;
    }

    const userRole = parseInt(userRoleMeta.content, 10);

    // 초기화
    postTypeSelect.innerHTML = '';

    if (userRole === 3) {  // 관리자
        postTypeSelect.innerHTML = `
            <option value="2" selected>공지사항</option>
            <option value="1">일반글</option>
        `;
    } else {  // 일반 사용자 및 사업자
        postTypeSelect.innerHTML = `
            <option value="1" selected>일반글</option>
        `;
        postTypeSelect.setAttribute('disabled', 'disabled'); // 선택 변경 불가
    }
});

