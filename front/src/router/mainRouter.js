import {createRouter, createWebHistory} from "vue-router";

const routes = [
    {
        path: "/login",
        name: "login",
        component: () => import("@/components/common/layout/login/Login.vue"),
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes: routes,
});

export default router;