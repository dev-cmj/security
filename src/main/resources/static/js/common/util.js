class UtilController {
    showToastMessage(message, isClose = true, duration = 3000, dismissListener = null) {
        Toastify({
            text: message,
            duration: duration,
            close: isClose,
            position: "center",
            stopOnFocus: true,
            style: {
                background: "linear-gradient(to right, #00b09b, #96c93d)",
            },
            callback: dismissListener
        }).showToast();
    }

    setLocalStorage(key, value) {
        localStorage.setItem(key, value);
    }

    getLocalStorage(key) {
        return localStorage.getItem(key);
    }

    async sendAuthorize() {
        try {
            const response = await fetch("/member/authorize", {
                method: "POST",
                headers: {
                    "Authorization": this.getLocalStorage("Authorization")
                }
            });

            if (!response.ok) {
                // 인증 실패 처리
                this.showToastMessage("인증에 실패하였습니다.");
                return false;
            }

            const responseValue = await response.json();
            await this.sendReissue(responseValue);
            return true;

        } catch (error) {
            this.showToastMessage("인증 요청 중 오류가 발생했습니다.");
            return false;
        }
    }

    async sendReissue(responseValue) {
        try {
            const response = await fetch("/member/reissue", {
                method: "POST",
                headers: {
                    "Authorization": responseValue.accessToken
                }
            });

            if (response.ok) {
                const responseData = await response.json();
                this.setLocalStorage("Authorization", responseData.grantType + responseData.accessToken);
            } else {
                this.showToastMessage("토큰 재발급에 실패하였습니다.");
            }
        } catch (error) {
            this.showToastMessage("토큰 재발급 요청 중 오류가 발생했습니다.");
        }
    }
}
