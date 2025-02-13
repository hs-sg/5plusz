document.addEventListener("DOMContentLoaded", function () {
    const festivalId = document.getElementById("festivalId").value; // JSP에서 hidden input으로 전달받기

    fetch(`/festival/${festivalId}/images`)
        .then(response => response.json())
        .then(data => {
            console.log("이미지 리스트:", data);
            window.festivalImages = data; // 전역 변수에 저장

            // 이미지 리스트를 동적으로 삽입
            const imageContainer = document.getElementById("festivalImagesContainer");
            if (imageContainer) {
                imageContainer.innerHTML = ""; // 기존 이미지 초기화
                data.forEach((image) => {
                    imageContainer.innerHTML += `
                        <div class="col-md-4 mb-3">
                            <img src="/uploads/${image.fiImages}" 
                                 class="img-thumbnail festival-img"
                                 alt="축제 이미지"
                                 data-bs-toggle="modal" 
                                 data-bs-target="#imageModal"
                                 onclick="openImageModal('${image.fiImages}')">
                        </div>
                    `;
                });
            }
        })
        .catch(error => console.error("이미지를 불러오는 중 오류 발생:", error));
});
