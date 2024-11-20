import {createApp} from "vue";
import App from "./App.vue";
import {createPinia} from "pinia";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue-3/dist/bootstrap-vue-3.css";
import "bootstrap-vue-next/dist/bootstrap-vue-next.css";
import {BootstrapVue3} from "bootstrap-vue-3";
import "bootstrap";
import BootstrapVueNext from "bootstrap-vue-next";
import router from "./router/clovirVdiRouter.js";
import VueForm from "@vueform/vueform";
import VueFormConfig from "../vueform.config.js";
import PrimeVue from "primevue/config";
import Aura from "@primevue/themes/aura";
import initI18n from "./composables/i18n.js";
import "./assets/css/main.scss";
import {userManageStore} from "@/stores/user/userStore.js";
import axios from "axios";

const loginCheck = async () => {
    const store = userManageStore();
    try {
        axios.defaults.withCredentials = true;
        const user = await axios.get("/api/auth/status");
        store.setCurrentUser(user);
        store.setLoggedIn();
    } catch (error) {
        await store.logout();
    }
}


const init = async () => {
    const i18n = initI18n();
    const app = createApp(App);

    // Register Pinia before calling loginCheck()
    const pinia = createPinia();
    app.use(pinia);

    // Now it's safe to call loginCheck()
    await Promise.all([loginCheck()]);

    app.use(PrimeVue, {
        theme: {
            preset: Aura,
            options: {
                prefix: "p",
                darkModeSelector: ".p-dark",
                cssLayer: false,
            },
        },
    });

    app
        .use(VueForm, VueFormConfig)
        .use(BootstrapVue3)
        .use(BootstrapVueNext)
        .use(i18n)
        .use(router)
        .mount("#app");
};

init();