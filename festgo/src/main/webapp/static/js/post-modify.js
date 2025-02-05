
document.addEventListener('DOMContentLoaded', () => {
    const modifyForm = document.querySelector('form#modifyForm');
    const inputId = document.querySelector("input[name='poId']"); // poId 가져오기
    const inputTitle = document.querySelector('input#title');
    const textareaContent = document.querySelector('textarea#content');
    const btnDelete = document.querySelector('button#btnDelete');
    const btnUpdate = document.querySelector('button#btnUpdate');

    // 삭제 버튼 클릭 이벤트
    if (btnDelete) {
        btnDelete.addEventListener('click', (e) => {
            if (inputId && inputId.value) {
                if (confirm('정말 삭제할까요?')) {
                    location.href = `/festgo/post/delete?poId=${inputId.value}`;
                }
            } else {
                alert("삭제할 게시글 ID를 찾을 수 없습니다.");
            }
        });
    }

    // 업데이트 버튼 클릭 이벤트
    if (btnUpdate) {
        btnUpdate.addEventListener('click', (e) => {
            const title = inputTitle.value.trim();
            const content = textareaContent.value.trim();

            if (title === '' || content === '') {
                alert('제목과 내용을 반드시 입력해야 합니다.');
                return;
            }

            if (confirm('변경된 내용을 저장할까요?')) {
                modifyForm.method = 'post';
                modifyForm.action = `/festgo/post/update`; // 절대 경로 설정
                modifyForm.submit();
            }
        });
    }
});
