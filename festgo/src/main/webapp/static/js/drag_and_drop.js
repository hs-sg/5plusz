    document.addEventListener('DOMContentLoaded', function() {
        
        // 대표 이미지 관련 변수
        const dropAreaRep = document.getElementById('dropAreaRep');
        const inputRep = document.getElementById('feImageMainFile');
        const previewRep = document.getElementById('previewRep');
        
        // 포스터 관련 변수
        const dropAreaPoster = document.getElementById('dropAreaPoster');
        const inputPoster = document.getElementById('fePosterFile');
        const previewPoster = document.getElementById('previewPoster');
        
        // 추가 이미지 관련 변수 (다중 업로드)
        const dropAreaAdditional = document.getElementById('dropAreaAdditional');
        const inputAdditional = document.getElementById('fiImagesFiles');
        const previewAdditionalContainer = document.getElementById('previewAdditionalContainer');
        
        // 공통: 기본 이벤트 취소 함수
        function preventDefaults(e) {
            e.preventDefault();
            e.stopPropagation();
        }
        
        // 공통: 드래그오버 시 스타일 추가
        function highlight(area) {
            area.classList.add('drag-over');
        }
        
        // 공통: 드래그 종료 시 스타일 제거
        function unhighlight(area) {
            area.classList.remove('drag-over');
        }
        
        // 파일 미리보기 함수 (단일 이미지)
        function previewFile(file, previewElement) {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onloadend = function() {
                previewElement.src = reader.result;
                previewElement.classList.remove('d-none');
            }
        }
        
        // 대표 이미지, 포스터는 단일 파일 처리
        function handleDropSingle(e, fileInput, previewElement) {
            const dt = e.dataTransfer;
            const files = dt.files;
            if (files && files.length > 0) {
                // 최신 브라우저에서는 new DataTransfer()로 파일 리스트를 생성할 수 있음
                const dataTransfer = new DataTransfer();
                dataTransfer.items.add(files[0]);
                fileInput.files = dataTransfer.files;
                previewFile(files[0], previewElement);
            }
        }
        
        // 추가 이미지는 다중 파일 처리
        function handleDropMultiple(e, fileInput, previewContainer) {
            const dt = e.dataTransfer;
            const files = dt.files;
            if (files && files.length > 0) {
                const dataTransfer = new DataTransfer();
                // 미리보기 영역 초기화
                previewContainer.innerHTML = '';
                for (let i = 0; i < files.length; i++) {
                    dataTransfer.items.add(files[i]);
                    // 개별 미리보기 이미지 생성
                    const img = document.createElement('img');
                    img.classList.add('img-preview');
                    img.style.marginRight = '10px';
                    previewFile(files[i], img);
                    previewContainer.appendChild(img);
                }
                fileInput.files = dataTransfer.files;
            }
        }
        
        // --- 대표 이미지 드래그 앤 드롭 설정 ---
        ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
            dropAreaRep.addEventListener(eventName, preventDefaults, false);
        });
        dropAreaRep.addEventListener('dragenter', () => highlight(dropAreaRep), false);
        dropAreaRep.addEventListener('dragover', () => highlight(dropAreaRep), false);
        dropAreaRep.addEventListener('dragleave', () => unhighlight(dropAreaRep), false);
        dropAreaRep.addEventListener('drop', (e) => {
            unhighlight(dropAreaRep);
            handleDropSingle(e, inputRep, previewRep);
        }, false);
        // 클릭 시 파일 선택 다이얼로그 열기
        dropAreaRep.addEventListener('click', () => {
            inputRep.click();
        });
        inputRep.addEventListener('change', function() {
            if (this.files && this.files[0]) {
                previewFile(this.files[0], previewRep);
            }
        });
        
        // --- 포스터 드래그 앤 드롭 설정 ---
        ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
            dropAreaPoster.addEventListener(eventName, preventDefaults, false);
        });
        dropAreaPoster.addEventListener('dragenter', () => highlight(dropAreaPoster), false);
        dropAreaPoster.addEventListener('dragover', () => highlight(dropAreaPoster), false);
        dropAreaPoster.addEventListener('dragleave', () => unhighlight(dropAreaPoster), false);
        dropAreaPoster.addEventListener('drop', (e) => {
            unhighlight(dropAreaPoster);
            handleDropSingle(e, inputPoster, previewPoster);
        }, false);
        dropAreaPoster.addEventListener('click', () => {
            inputPoster.click();
        });
        inputPoster.addEventListener('change', function() {
            if (this.files && this.files[0]) {
                previewFile(this.files[0], previewPoster);
            }
        });
        
        // --- 추가 이미지 드래그 앤 드롭 설정 (다중 파일) ---
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
            // 초기화 후, 선택된 파일들의 미리보기 생성
            previewAdditionalContainer.innerHTML = '';
            for (let i = 0; i < this.files.length; i++) {
                const img = document.createElement('img');
                img.classList.add('img-preview');
                img.style.marginRight = '10px';
                previewFile(this.files[i], img);
                previewAdditionalContainer.appendChild(img);
            }
        });
    });
