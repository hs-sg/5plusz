document.addEventListener("DOMContentLoaded", function () {
    const festivalIdElement = document.getElementById("festivalId");

    if (!festivalIdElement) {
        console.error("❌ festivalId 요소를 찾을 수 없습니다.");
        return;
    }

    const festivalId = festivalIdElement.value;
    console.log(`🔍 요청할 festivalId: ${festivalId}`);

    // 서버에서 축제 이미지 리스트 가져오기
    fetch(`/festgo/fest/detail/images/${festivalId}`)
        .then(response => {
            console.log(`📢 서버 응답 상태: ${response.status}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (!Array.isArray(data)) {
                throw new Error("❌ Invalid JSON format received");
            }
            console.log("🎉 성공적으로 받은 이미지 리스트:", data);

            const imageContainer = document.getElementById("festivalImagesContainer");
            if (!imageContainer) {
                console.error("❌ 이미지 컨테이너를 찾을 수 없습니다.");
                return;
            }

            imageContainer.innerHTML = ""; // 기존 내용 초기화
            data.forEach((image) => {
                imageContainer.innerHTML += `
                    <div class="col-md-4 mb-3">
                        <img src="${contextPath}/uploads/${image.fiImages}" 
                             class="img-thumbnail festival-img"
                             alt="축제 이미지"
                             data-bs-toggle="modal" 
                             data-bs-target="#imageModal"
                             onclick="openImageModal('${image.fiImages}')">
                    </div>
                `;

            });
        })
        .catch(error => console.error("❌ 이미지를 불러오는 중 오류 발생:", error));
});
