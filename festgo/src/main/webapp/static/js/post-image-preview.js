document.addEventListener("DOMContentLoaded", function () {
    const contentDiv = document.getElementById("content");
    const previewContainer = document.getElementById("previewPoContentContainer");

    if (!contentDiv || !previewContainer) return;

    // 게시글 내용에서 <img> 태그 찾기 (이미지 태그 있는 경우)
    let content = contentDiv.innerHTML;
    const imgRegex = /<img[^>]+src=["'](.*?)["']/g;
    let match;
    let images = [];

    while ((match = imgRegex.exec(content)) !== null) {
        images.push(match[1]); // src 값 저장
    }

    // 기존 내용에서 이미지 태그 제거 (텍스트만 남기기)
    contentDiv.innerHTML = content.replace(imgRegex, '');

    // 이미지가 있으면 미리보기 추가
    images.forEach((src) => {
        let imgElement = document.createElement("img");
        imgElement.src = src;
        imgElement.classList.add("img-thumbnail", "me-2");
        imgElement.style.maxWidth = "200px";
        imgElement.style.height = "auto";
        previewContainer.appendChild(imgElement);
    });

    // JSTL에서 전달된 이미지 리스트 처리 (첨부된 이미지 있는 경우)
    let datasetImages = previewContainer.dataset.images;
    if (datasetImages) {
        datasetImages.split(",").forEach(image => {
            image = image.trim();
            if (image) {
                let imgElement = document.createElement("img");
                imgElement.src = "/attachments/" + image;
                imgElement.classList.add("img-thumbnail", "me-2");
                imgElement.style.maxWidth = "200px";
                imgElement.style.height = "auto";
                previewContainer.appendChild(imgElement);
            }
        });
    }
});
