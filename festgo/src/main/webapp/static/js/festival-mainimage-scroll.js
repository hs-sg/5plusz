document.addEventListener("DOMContentLoaded", function () {
    const mainVisual = document.querySelector(".main-visual");
    const header = document.querySelector(".header-fixed");
    const scrollTrigger = window.innerHeight * 0.9; // 메인 이미지가 사라지는 스크롤 기준 (화면 80% 지점)

    window.addEventListener("scroll", function () {
        const scrollY = window.scrollY;

        if (scrollY > scrollTrigger) {
            mainVisual.classList.add("hidden"); // 메인 이미지 숨기기
            header.classList.remove("hidden"); // 헤더 보이기
        } else {
            mainVisual.classList.remove("hidden"); // 메인 이미지 보이기
            header.classList.add("hidden"); // 헤더 숨기기
        }
    });
});