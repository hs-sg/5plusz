function openImageModal(selectedImage) {
    const carouselInner = document.getElementById("carouselInner");
    carouselInner.innerHTML = ""; // 기존 이미지 초기화

    window.festivalImages.forEach((image, index) => {
        const activeClass = image.fiImages === selectedImage ? "active" : ""; // 클릭한 이미지를 첫 번째로 표시
        const item = `
            <div class="carousel-item ${activeClass}">
                <img src="/uploads/${image.fiImages}" class="d-block w-100" alt="축제 이미지">
            </div>
        `;
        carouselInner.innerHTML += item;
    });

    // 캐러셀 자동 재시작
    new bootstrap.Carousel(document.getElementById('imageCarousel'), {
        interval: 3000, // 3초마다 자동 넘김
        wrap: true
    });
}
