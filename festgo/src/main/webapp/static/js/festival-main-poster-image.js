    // 폼 제출 시 파일 선택 여부 확인
    document.getElementById('festivalForm').addEventListener('submit', function(event) {
        let isValid = true;
    
        // 대표 이미지 검증
        const repInput = document.getElementById('feImageMainFile');
        const repError = document.getElementById('errorRep');
        if (repInput.files.length === 0) {
            repError.textContent = '축제 대표 이미지는 필수입니다.';
            repError.style.display = 'block'; // 오류 메시지 보이도록 설정
            isValid = false;
        } else {
            repError.textContent = '';
            repError.style.display = 'none'; // 오류 메시지 숨기기
        }
    
        // 포스터 이미지 검증
        const posterInput = document.getElementById('fePosterFile');
        const posterError = document.getElementById('errorPoster');
        if (posterInput.files.length === 0) {
            posterError.textContent = '축제 포스터는 필수입니다.';
            posterError.style.display = 'block';
            isValid = false;
        } else {
            posterError.textContent = '';
            posterError.style.display = 'none';
        }
    
        // 하나라도 검증 실패 시 폼 제출 중단
        if (!isValid) {
            event.preventDefault();
        }
    });
        
