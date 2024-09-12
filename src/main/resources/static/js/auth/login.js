class LoginController extends UtilController {
    constructor() {
        super();
        this.loginForm = document.getElementById("loginForm");
        this.loginButton = document.getElementById("login_form_button");
    }

    initLoginController() {
        this.loginButton.addEventListener("click", async (evt) => {
            evt.preventDefault();  // 폼이 바로 제출되지 않도록 방지

            try {
                const formData = {
                    username: this.loginForm.username.value,
                    password: this.loginForm.password.value
                };

                // fetch API를 사용하여 JSON 데이터를 POST로 전송
                const response = await fetch("/api/auth/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",  // JSON으로 전송
                    },
                    body: JSON.stringify(formData)  // 객체를 JSON 문자열로 변환
                });

                const responseValue = await response.json();

                if (!response.ok) {
                    this.showToastMessage(responseValue.message || '로그인에 실패하였습니다.');
                } else {
                    this.setLocalStorage("Authorization", responseValue.grantType + responseValue.accessToken);
                    window.location = document.referrer;  // 이전 페이지로 이동
                }

            } catch (error) {
                this.showToastMessage('로그인 요청 중 오류가 발생했습니다.');
            }
        });
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const loginController = new LoginController();
    loginController.initLoginController();
});
