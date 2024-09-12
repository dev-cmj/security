class SignupController extends UtilController {
    constructor() {
        super();
        this.signupForm = document.getElementById("signupForm");
        this.signupFormButton = document.getElementById("signup_form_button");
    }

    initSignupController() {
        this.signupFormButton.addEventListener("click", async (evt) => {
            evt.preventDefault();  // 기본 폼 제출 동작 방지

            const formData = {
                username: this.signupForm.username.value,
                password: this.signupForm.password.value,
                email: this.signupForm.email.value
            };

            try {
                const response = await fetch("/api/auth/signup", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(formData)
                });

                if (!response.ok) {
                    const responseData = await response.json();
                    this.showToastMessage(responseData.message);
                } else {
                    this.showToastMessage("회원가입에 성공했습니다.");
                    setTimeout(() => {
                        window.location.href = "/auth/login";
                    }, 1000);
                }
            } catch (error) {
                this.showToastMessage('회원가입에 실패하였습니다.');
            }
        });
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const signupController = new SignupController();
    signupController.initSignupController();
});
