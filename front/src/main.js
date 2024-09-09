import './assets/main.css'

import store from "@/common/vuex.js";
import {createApp} from 'vue'
import App from './App.vue'
import initI18n from "@/common/i18n.js";
import BootstrapVueNext from "bootstrap-vue-next";
import {createPinia} from "pinia";
import {BootstrapVue3} from "bootstrap-vue-3";
import router from "@/router/mainRouter.js";

createApp(App).mount('#app')

const init = async () => {
    const i18n = initI18n();
    const app = createApp(App);

    app.use(store)
        .use(BootstrapVue3)
        .use(createPinia())
        .use(BootstrapVueNext)
        .use(i18n)
        .use(router)  // 라우터 연결
        .mount("#app");
};


init();