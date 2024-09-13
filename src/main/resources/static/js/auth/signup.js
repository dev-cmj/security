class SignupController extends UtilController {
    constructor() {
        super();
        this.signupForm = document.getElementById("signupForm");
        this.signupFormButton = document.getElementById("signup_form_button");
    }

    initSignupController() {
        this.signupFormButton.addEventListener("click", async (evt) => {
            evt.preventDefault();  // 기본 폼 제출 동작 방지

            if (!this.signupForm.username.value) {
                this.showToastMessage('아이디를 입력해주세요.');
                return;
            }

            if (!this.signupForm.password.value) {
                this.showToastMessage('비밀번호를 입력해주세요.');
                return;
            }


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
                    window.location.href = "/auth/login";
                }
            } catch (error) {
                this.showToastMessage('회원가입에 실패하였습니다.');
            }
        });

        //로그인 상태 확인
        this.checkLoginStatus().then(r => console.log(r));
    }

    //로그인 상태 확인
    async checkLoginStatus() {
        try {
            const response = await fetch("/api/auth/status", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                }
            });

            if (response.ok) {
                const responseData = await response.json();
                if (responseData) {
                    alert('이미 로그인 되어 있습니다.');
                    window.location.href = "/";
                }
            }
        } catch (error) {
            console.log('로그인 상태 확인 실패');
        }
    }
}


// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const signupController = new SignupController();
    signupController.initSignupController();
});
