import { createApp } from "vue";
import App from "./App.vue";
import { createPinia } from "pinia";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue-3/dist/bootstrap-vue-3.css";
import "bootstrap-vue-next/dist/bootstrap-vue-next.css";
import { BootstrapVue3 } from "bootstrap-vue-3";
import "bootstrap";
import BootstrapVueNext from "bootstrap-vue-next";
import router from "./router/clovirVdiRouter.js";
import VueForm from "@vueform/vueform";
import VueFormConfig from "../vueform.config.js";
import PrimeVue from "primevue/config";
import Aura from "@primevue/themes/aura";
import initI18n from "./composables/i18n.js";
import "./assets/css/main.scss";
import { userManageStore} from "@/stores/user/userStore.js";

const init = async () => {
  const i18n = initI18n();
  const app = createApp(App);

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

  // app.use(PrimeVue, { theme: "none" });
  app
    .use(createPinia())
    .use(VueForm, VueFormConfig)
    .use(BootstrapVue3)
    .use(BootstrapVueNext)
    .use(i18n)
    .use(router)
    .mount("#app");
};

router.beforeEach((to, from, next) => {
  const userStore = userManageStore();

  if (to.path !== "/login" && !userStore.isLoggedIn()) {
    console.log("로그인이 필요합니다.");
    return next({
      path: "/login",
      query: { redirect: to.fullPath }, // 원래 요청했던 경로 저장
    });
  }

  if (to.path === "/login" && userStore.isLoggedIn()) {
    console.log("이미 로그인된 사용자입니다.");
    return next("/");
  }

  next(); // 모든 경우 진행
});

init();
