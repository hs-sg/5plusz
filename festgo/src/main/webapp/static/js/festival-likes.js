document.addEventListener("DOMContentLoaded", function () {
    updateLikeStatus();
});

function updateLikeStatus() {
    const festivalId = document.getElementById("festivalId").value;
    const contextPath = document.getElementById("contextPath").value;

    axios.get(contextPath + "/festival/" + festivalId + "/likes")
        .then(response => {
            if (response.data) {
                updateLikeUI(response.data.liked, response.data.likeCount);
            }
        })
        .catch(error => {
            console.error("좋아요 상태 불러오기 실패:", error);
        });
}


document.addEventListener("DOMContentLoaded", function () {
    updateLikeStatus();
});

function toggleLike() {
    const festivalId = document.getElementById("festivalId").value;
    const meIdElement = document.getElementById("meId");
    const meId = meIdElement ? meIdElement.value : null;

    if (!meId) {
        alert("로그인이 필요합니다.");
        return;
    }

    const contextPath = document.getElementById("contextPath").value; // ✅ 수정된 contextPath 참조
    
    axios.post(contextPath + "/festival/" + festivalId + "/like")
        .then(response => {
            if (response.data) {
                updateLikeUI(response.data.liked, response.data.likeCount);
            }
        })
        .catch(error => {
            console.error("좋아요 요청 실패:", error);
            if (error.response && error.response.status === 400) {
                alert("로그인이 필요합니다.");
            }
        });
}