function updateLikeUI(liked, likeCount) {
    const likeIcon = document.getElementById("likeIcon");
    const likeCountSpan = document.getElementById("likeCount");

    // 좋아요 개수 업데이트
    likeCountSpan.innerText = likeCount; 

    // 좋아요가 눌러진 상태라면 ❤️, 아니라면 🤍
    likeIcon.innerText = likeCount > 0 ? "❤️" : "🤍"; 
}


function fetchLikeStatus(festivalId) {
    axios.get(`/festgo/festival/${festivalId}/likes`)
        .then(response => {
            updateLikeUI(response.data.liked, response.data.likeCount);
        })
        .catch(error => {
            console.error("좋아요 상태 불러오기 실패:", error);
        });
}

document.addEventListener("DOMContentLoaded", function () {
    fetchLikeStatus(227); // 페이지 로드 시 좋아요 상태 조회
});
