console.log("festival-dday.js 로드됨");

const FestivalDate = {
    formatDate: (isoString) => {
        if (!isoString) return '';
        const date = new Date(isoString);
        return `${date.getFullYear()}.${String(date.getMonth() + 1).padStart(2, '0')}.${String(date.getDate()).padStart(2, '0')}`;
    },

    calculateDday: (startDateStr, endDateStr) => {
        const today = new Date().setHours(0, 0, 0, 0);
        const startDate = new Date(startDateStr).setHours(0, 0, 0, 0);
        const endDate = new Date(endDateStr).setHours(0, 0, 0, 0);

        const diffStart = Math.ceil((startDate - today) / (1000 * 60 * 60 * 24));
        const diffEnd = Math.ceil((endDate - today) / (1000 * 60 * 60 * 24));

        let ddayText, buttonClass;
        if (diffStart > 0) {
            ddayText = `D-${diffStart} 시작 예정`;
            buttonClass = "btn-primary";
        } else if (diffEnd >= 5) {
            ddayText = "축제 진행 중";
            buttonClass = "btn-success";
        } else if (diffEnd >= 0) {
            ddayText = `D-${diffEnd} 종료 예정`;
            buttonClass = "btn-warning";
        } else {
            ddayText = "축제 종료됨";
            buttonClass = "btn-secondary";
        }

        return `<button class="btn ${buttonClass} btn-sm" style="font-weight:bold;">${ddayText}</button>`;
    },

    updateFestivalDate: (startDateStr, endDateStr) => {
        const startDate = FestivalDate.formatDate(startDateStr);
        const endDate = FestivalDate.formatDate(endDateStr);
        const ddayInfo = FestivalDate.calculateDday(startDateStr, endDateStr);

        const dateElement = document.getElementById("festival-date");
        const ddayElement = document.getElementById("festival-dday");

        if (dateElement) {
            dateElement.innerHTML = `<strong>${startDate} ~ ${endDate}</strong>`;
        }

        if (ddayElement) {
            ddayElement.innerHTML = ddayInfo;
        }
    },
};

window.updateFestivalDate = FestivalDate.updateFestivalDate;
console.log("festival-dday.js 초기화 완료");
