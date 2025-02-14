function openImageModal(selectedImage) {
    const carouselInner = document.getElementById("carouselInner");
    carouselInner.innerHTML = ""; // 기존 이미지 초기화

    // JSP에서 표시된 모든 이미지 가져오기
    const images = Array.from(document.querySelectorAll('.festival-img'))
        .map(img => img.getAttribute('src'));

    console.log("🔄 캐러셀에 추가할 이미지 목록:", images); // 콘솔 확인

    images.forEach((image, index) => {
        const isActive = (index === 0) ? 'active' : ''; // 🔥 첫 번째 이미지만 active 설정
        const div = document.createElement('div');
        div.className = `carousel-item ${isActive}`;

        div.innerHTML = `
            <img src="${image}" 
                 class="d-block w-100"
                 alt="Festival Image"
                 style="max-height: 500px; object-fit: contain;">
        `;
        carouselInner.appendChild(div);
    });

    console.log("📸 carouselInner 내용:", carouselInner.innerHTML);

    // Bootstrap 모달 열기
    const modalEl = document.getElementById('imageModal');
    const modal = new bootstrap.Modal(modalEl);
    modal.show();

    // ✅ 모달이 열린 후에 캐러셀 초기화 (setTimeout 추가)
    setTimeout(() => {
        const carouselEl = document.getElementById('imageCarousel');
        new bootstrap.Carousel(carouselEl, {
            interval: 3000,
            wrap: true
        });
        console.log("🎠 캐러셀 초기화 완료!");
    }, 100);
}
