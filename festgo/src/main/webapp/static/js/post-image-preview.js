document.addEventListener("DOMContentLoaded", function () {
    const previewImg = document.getElementById("previewPoContentContainer");

    if (!previewImg) {
        console.error("Preview image element not found!");
        return;
    }

    const datasetImages = previewImg.dataset.images;

    if (datasetImages) {
        const imageUrls = datasetImages.split(",").map(image => image.trim());
        if (imageUrls.length > 0 && imageUrls[0]) {
            const firstImageUrl = "/uploads/" + imageUrls[0]; // 수정: /uploads/ 경로 추가
            previewImg.src = firstImageUrl;
            previewImg.classList.remove("d-none");
        } else {
            console.warn("No valid images found in data-images attribute!");
        }
    } else {
        console.warn("No images data found in preview image element!");
    }
});

/* */