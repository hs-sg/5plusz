document.addEventListener('DOMContentLoaded', function() {
    // 대표 이미지 관련 요소
    const dropAreaRep = document.getElementById('dropAreaRep');
    const inputRep = document.getElementById('feImageMainFile');
    const previewRep = document.getElementById('previewRep');
    
    // 포스터 관련 요소
    const dropAreaPoster = document.getElementById('dropAreaPoster');
    const inputPoster = document.getElementById('fePosterFile');
    const previewPoster = document.getElementById('previewPoster');
    
    // 추가 이미지 관련 요소 (다중 업로드)
    const dropAreaAdditional = document.getElementById('dropAreaAdditional');
    const inputAdditional = document.getElementById('fiImagesFiles');
    const previewAdditionalContainer = document.getElementById('previewAdditionalContainer');
    
    // 공통: 기본 이벤트 취소
    function preventDefaults(e) {
        e.preventDefault();
        e.stopPropagation();
    }
    
    // 공통: 드래그 시 스타일 추가/제거
    function highlight(area) {
        area.classList.add('drag-over');
    }
    
    function unhighlight(area) {
        area.classList.remove('drag-over');
    }
    
    // 단일 파일 미리보기 처리 (대표 이미지, 포스터)
    function previewFile(file, previewElement, removeContainer, fileInput) {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = function() {
            previewElement.src = reader.result;
            previewElement.classList.remove('d-none');
            
            // removeContainer(또는 미리보기 이미지의 부모 컨테이너)에 position: relative 설정
            removeContainer.style.position = removeContainer.style.position || 'relative';
            
            // 제거 버튼이 없으면 생성
            if (!removeContainer.querySelector('.remove-btn')) {
                const removeBtn = document.createElement('button');
                removeBtn.textContent = '❌';
                removeBtn.classList.add('btn', 'btn-sm', 'btn-outline-danger', 'mt-2', 'remove-btn');
                removeBtn.addEventListener('click', function(e) {
                    e.preventDefault();
                    // 파일 입력 초기화
                    fileInput.value = "";
                    // 미리보기 이미지 초기화
                    previewElement.src = "";
                    previewElement.classList.add('d-none');
                    // 제거 버튼 삭제
                    removeBtn.remove();
                });
                removeContainer.appendChild(removeBtn);
            }
        };
    }
    
    // 단일 파일 드롭 처리
    function handleDropSingle(e, fileInput, previewElement, removeContainer) {
        const dt = e.dataTransfer;
        const files = dt.files;
        if (files && files.length > 0) {
            // 최신 브라우저에서는 DataTransfer 객체를 사용하여 파일을 재설정할 수 있습니다.
            const dataTransfer = new DataTransfer();
            dataTransfer.items.add(files[0]);
            fileInput.files = dataTransfer.files;
            previewFile(files[0], previewElement, removeContainer, fileInput);
        }
    }
    
    // 다중 파일 미리보기 및 드롭 처리 (추가 이미지)
    function handleDropMultiple(e, fileInput, previewContainer) {
        const dt = e.dataTransfer;
        const files = dt.files;
        if (files && files.length > 0) {
            const dataTransfer = new DataTransfer();
            // 미리보기 영역 초기화
            previewContainer.innerHTML = '';
            for (let i = 0; i < files.length; i++) {
                dataTransfer.items.add(files[i]);
                // 각 파일 미리보기를 위한 개별 컨테이너 생성
                const container = document.createElement('div');
                container.style.display = 'inline-block';
                container.style.position = 'relative';
                container.style.marginRight = '10px';
                container.style.marginBottom = '10px';

                const img = document.createElement('img');
                img.classList.add('img-preview');
                img.style.display = 'block';
                img.style.marginBottom = '5px';
                container.appendChild(img);
                
                // FileReader로 미리보기 이미지 생성
                const reader = new FileReader();
                reader.readAsDataURL(files[i]);
                reader.onloadend = function() {
                    img.src = reader.result;
                };

                // 제거 버튼 생성 (다중 파일)
                const removeBtn = document.createElement('button');
                removeBtn.innerHTML = '❌';
                removeBtn.classList.add('remove-btn');
                removeBtn.addEventListener('click', function(e) {
                    e.preventDefault();
                    container.remove();
                    // 모든 이미지가 제거되면 파일 입력 초기화
                    if (previewContainer.children.length === 0) {
                        fileInput.value = "";
                    }
                });
                container.appendChild(removeBtn);
                previewContainer.appendChild(container);
            }
            fileInput.files = dataTransfer.files;
        }
    }

    
    // 대표 이미지 영역 설정
    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
        dropAreaRep.addEventListener(eventName, preventDefaults, false);
    });
    dropAreaRep.addEventListener('dragenter', () => highlight(dropAreaRep), false);
    dropAreaRep.addEventListener('dragover', () => highlight(dropAreaRep), false);
    dropAreaRep.addEventListener('dragleave', () => unhighlight(dropAreaRep), false);
    dropAreaRep.addEventListener('drop', (e) => {
        unhighlight(dropAreaRep);
        handleDropSingle(e, inputRep, previewRep, previewRep.parentElement);
    }, false);
    dropAreaRep.addEventListener('click', () => {
        inputRep.click();
    });
    inputRep.addEventListener('change', function() {
        if (this.files && this.files[0]) {
            handleDropSingle({dataTransfer: {files: this.files}}, inputRep, previewRep, previewRep.parentElement);
        }
    });
    
    // 포스터 영역 설정
    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
        dropAreaPoster.addEventListener(eventName, preventDefaults, false);
    });
    dropAreaPoster.addEventListener('dragenter', () => highlight(dropAreaPoster), false);
    dropAreaPoster.addEventListener('dragover', () => highlight(dropAreaPoster), false);
    dropAreaPoster.addEventListener('dragleave', () => unhighlight(dropAreaPoster), false);
    dropAreaPoster.addEventListener('drop', (e) => {
        unhighlight(dropAreaPoster);
        handleDropSingle(e, inputPoster, previewPoster, previewPoster.parentElement);
    }, false);
    dropAreaPoster.addEventListener('click', () => {
        inputPoster.click();
    });
    inputPoster.addEventListener('change', function() {
        if (this.files && this.files[0]) {
            handleDropSingle({dataTransfer: {files: this.files}}, inputPoster, previewPoster, previewPoster.parentElement);
        }
    });
    
    // 추가 이미지 영역 설정 (다중 파일)
    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
        dropAreaAdditional.addEventListener(eventName, preventDefaults, false);
    });
    dropAreaAdditional.addEventListener('dragenter', () => highlight(dropAreaAdditional), false);
    dropAreaAdditional.addEventListener('dragover', () => highlight(dropAreaAdditional), false);
    dropAreaAdditional.addEventListener('dragleave', () => unhighlight(dropAreaAdditional), false);
    dropAreaAdditional.addEventListener('drop', (e) => {
        unhighlight(dropAreaAdditional);
        handleDropMultiple(e, inputAdditional, previewAdditionalContainer);
    }, false);
    dropAreaAdditional.addEventListener('click', () => {
        inputAdditional.click();
    });
    inputAdditional.addEventListener('change', function() {
        previewAdditionalContainer.innerHTML = '';
        handleDropMultiple({dataTransfer: {files: this.files}}, inputAdditional, previewAdditionalContainer);
    });
});
