// date-config.js
    
// datetime-local 형식 (YYYY-MM-DDTHH:MM)으로 포맷팅하는 함수
    function formatDateTimeLocal(date) {
        var year = date.getFullYear();
        var month = (date.getMonth() + 1).toString().padStart(2, '0'); // 월은 0부터 시작하므로 +1
        var day = date.getDate().toString().padStart(2, '0');
        var hours = date.getHours().toString().padStart(2, '0');
        var minutes = date.getMinutes().toString().padStart(2, '0');
        return `${year}-${month}-${day}T${hours}:${minutes}`;
    }
    
    // DOMContentLoaded 이벤트에서 초기값 설정 및 이벤트 리스너 등록
    document.addEventListener("DOMContentLoaded", function() {
        var now = new Date(); // 현재 시간
        var startInput = document.getElementById("feStartDate");
        var endInput = document.getElementById("feEndDate");
    
        // 시작 날짜 기본값을 현재 시간으로 설정
        startInput.value = formatDateTimeLocal(now);
    
        // 종료 날짜 기본값은 시작 날짜 +1일로 설정
        var endDate = new Date(now);
        endDate.setDate(endDate.getDate() + 1);
        endInput.value = formatDateTimeLocal(endDate);
    
        // 종료 날짜의 최소 선택값을 시작 날짜로 설정
        endInput.min = startInput.value;
    
        // 시작 날짜가 변경될 때마다 종료 날짜의 min 속성에 시작 날짜 값을 할당
        startInput.addEventListener("change", function() {
            var startDate = this.value;
            if (startDate) {
                endInput.min = startDate;
            }
        });
    });
	
	/* */
