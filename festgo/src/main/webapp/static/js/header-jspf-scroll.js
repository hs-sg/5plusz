document.addEventListener("DOMContentLoaded", function () {
    const header = document.querySelector(".header-fixed");
    const mainVisual = document.querySelector(".main-visual");
    const scrollTrigger = mainVisual.clientHeight * 0.6; // 60% 지점에서 트리거

    window.addEventListener("scroll", function () {
        const scrollY = window.scrollY;

        if (scrollY >= scrollTrigger) {
            // 일정 스크롤을 내리면 헤더 보이기 (즉시 변경)
            header.classList.add("visible");
            mainVisual.classList.add("hidden"); // 메인 이미지 점점 사라짐
        } else {
            // 다시 올라가면 헤더 숨기고 메인 이미지 복원
            header.classList.remove("visible");
            mainVisual.classList.remove("hidden");
        }
    });
});
