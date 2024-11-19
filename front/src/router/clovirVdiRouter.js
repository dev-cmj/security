import { createRouter, createWebHistory } from "vue-router";
import sampleRouter from "./sampleRouter.js";

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
  }
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

export default router;
