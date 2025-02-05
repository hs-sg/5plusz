// theme-input.js

    document.addEventListener("DOMContentLoaded", function() {
        var themeSelect = document.getElementById("theId");
        var customThemeContainer = document.getElementById("customThemeContainer");
    
        // 셀렉트 박스의 선택값이 변경되면 실행
        themeSelect.addEventListener("change", function() {
            if (this.value === "custom") {
                // "직접 입력" 선택 시 추가 입력란 보이기
                customThemeContainer.style.display = "block";
                // 추가 입력란은 required 속성 설정
                document.getElementById("customTheme").required = true;
            } else {
                // 기존 옵션 선택 시 추가 입력란 숨기기 및 초기화
                customThemeContainer.style.display = "none";
                document.getElementById("customTheme").value = "";
                document.getElementById("customTheme").required = false;
            }
        });
    });
