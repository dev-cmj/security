import { createRouter, createWebHistory } from "vue-router";
import sampleRouter from "./sampleRouter.js";
import {userManageStore} from "@/stores/user/userStore.js";

const routes = [
  // {
  //   path: "/",
  //   name: "clovirvdi.home",
  //   component: () => import("@/components/sample/radio/Sample1.vue"),
  // },
  {
    path: "/login",
    name: "clovirvdi.login",
    component: () => import("@/views/login/Login.vue"),
  },
  // {
  //   path: "/clovirvdi/sample1",
  //   name: "clovirvdi.sample1",
  //   component: () => import("@/components/sample/radio/Sample1.vue"),
  // },
  // {
  //   path: "/clovirvdi/test",
  //   name: "clovirvdi.test",
  //   component: () => import("@/components/sample/radio/Sample1.vue"),
  // },
];

const router = createRouter({
  history: createWebHistory(),
  routes: routes.concat(sampleRouter),
});

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

export default router;
