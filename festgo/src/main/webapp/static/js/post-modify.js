document.addEventListener('DOMContentLoaded', () => {
    const modifyForm = document.querySelector('form#modifyForm');
    const inputId = document.querySelector("input[name='poId']");
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
            e.preventDefault(); // 기본 동작 방지

            const title = inputTitle.value.trim();
            const content = textareaContent.value.trim();

            if (title === '' || content === '') {
                alert('제목과 내용을 반드시 입력해야 합니다.');
                return;
            }

            // 체크된 체크박스의 값을 수집
            const removeFiles = Array.from(document.querySelectorAll('input[name="removeFiles"]:checked')).map(checkbox => checkbox.value);
            console.log('Remove Files:', removeFiles); // 콘솔에 로그 출력

            if (confirm('변경된 내용을 저장할까요?')) {
                // hidden input 추가
                removeFiles.forEach(id => {
                    const hiddenInput = document.createElement('input');
                    hiddenInput.type = 'hidden';
                    hiddenInput.name = 'removeFiles';
                    hiddenInput.value = id;
                    modifyForm.appendChild(hiddenInput);
                });

                modifyForm.method = 'post';
                modifyForm.action = `/festgo/post/update`;
                modifyForm.submit();
            }
            // "취소" 버튼을 누른 경우 아무 작업도 하지 않고 페이지에 머무름
        });
    }
});
document.addEventListener("DOMContentLoaded", function () {
    const deleteButtons = document.querySelectorAll(".deleteFileBtn");

    deleteButtons.forEach(button => {
        button.addEventListener("click", function () {
            const fileId = this.getAttribute("data-file-id");
            const listItem = this.closest(".list-group-item");

            if (confirm("이 파일을 삭제하시겠습니까?")) {
                axios.post(`/festgo/post/delete-attachment`, { fileId: fileId })
                    .then(response => {
                        if (response.data.success) {
                            alert("파일이 삭제되었습니다.");
                            listItem.remove(); // 삭제된 파일을 리스트에서 즉시 제거
                        } else {
                            alert("파일 삭제 실패: " + response.data.message);
                        }
                    })
                    .catch(error => {
                        console.error("파일 삭제 중 오류 발생", error);
                        alert("파일 삭제 중 오류가 발생했습니다.");
                    });
            }
        });
    });

});

