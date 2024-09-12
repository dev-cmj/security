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

                if (!this.loginForm.username.value) {
                    this.showToastMessage('아이디를 입력해주세요.');
                    return;
                }

                if (!this.loginForm.password.value) {
                    this.showToastMessage('비밀번호를 입력해주세요.');
                    return;
                }


                const formData = {
                    username: this.loginForm.username.value,
                    password: this.loginForm.password.value
                };

                const response = await fetch("/api/auth/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(formData)
                });

                if (response.status === 200) {
                    this.showToastMessage('로그인에 성공했습니다.');
                } else {
                    const errorMessage = await response.text();
                    this.showToastMessage(errorMessage);
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
