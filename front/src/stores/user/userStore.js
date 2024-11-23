import {defineStore} from "pinia";
import {ref} from "vue";
import axios from "axios";
import router from "@/router/clovirVdiRouter.js";
import JSEncrypt from "jsencrypt";

export const userManageStore = defineStore("userStore", () => {
    const currentUser = ref(null);
    const loggedIn = ref(false);
    const needToChangePassword = ref(false);
    const publicKey = ref(null);

    // Fetch public key for encryption
    const getPublicKey = async () => {
        if (!publicKey.value) {
            const response = await axios.get('/api/auth/public-key');
            publicKey.value = response.data;
        }
        return publicKey.value;
    };

    // Encrypt password using public key
    const encryptPassword = async (password) => {
        const encrypt = new JSEncrypt();
        const key = await getPublicKey();
        encrypt.setPublicKey(key);
        return encrypt.encrypt(password);
    };

    // Login method with password encryption
    const login = async (username, password) => {
        try {
            if (!username || !password) {
                throw new Error("아이디와 비밀번호는 필수 입력 값입니다.");
            }

            // Encrypt password
            const encryptedPassword = await encryptPassword(password);

            axios.defaults.withCredentials = true;
            await axios.post("/api/auth/login", {
                username,
                password: encryptedPassword,
            });

            loggedIn.value = true;
            needToChangePassword.value = false; // 임시로 false 처리 (추후 service API 호출)

            if (needToChangePassword.value) {
                await router.push("/clovirvdi/member/changePassword");
            } else {
                await router.push("/");
            }

            return true;
        } catch (error) {
            throw error;
        }
    };

    const logout = async () => {
        try {
            await axios.post("/api/auth/logout");
            loggedIn.value = false;
            currentUser.value = null;
            await router.push("/login");
        } catch (error) {
            throw error;
        }
    };

    const signup = async (username, password, name, email) => {
        try {

            const encryptedPassword = await encryptPassword(password);

            await axios.post("/api/auth/signup", {
                username,
                password: encryptedPassword,
                name,
                email,
            });

            alert("회원가입이 완료되었습니다.");
            return true;
        } catch (error) {
            throw error;
        }
    };

    const setLoggedIn = () => {
        loggedIn.value = true;
    };

    const setLoggedOut = () => {
        loggedIn.value = false;
    };

    const setCurrentUser = (user) => {
        currentUser.value = user;
    };

    const setNeedToChangePassword = () => {
        // Service API 호출
    };

    const getCurrentUser = () => {
        return currentUser.value;
    };

    const isLoggedIn = () => {
        return loggedIn.value;
    };

    const getNeedToChangePassword = () => {
        return needToChangePassword.value;
    };

    return {
        login,
        logout,
        signup,
        setLoggedIn,
        setLoggedOut,
        setCurrentUser,
        setNeedToChangePassword,
        getCurrentUser,
        isLoggedIn,
        getNeedToChangePassword,
        getPublicKey,
    };
});