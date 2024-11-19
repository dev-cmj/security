const sampleRouter = [
  {
    path: "/",
    name: "sampleMain",
    component: () => import("../components/sample/SampleContent.vue"),
  },
  {
    path: "/radio",
    name: "radioSample",
    component: () => import("../components/sample/radio/SampleRadio.vue"),
  },
  {
    path: "/button",
    name: "buttonSample",
    component: () => import("../components/sample/button/SampleButton.vue"),
  },
  {
    path: "/select",
    name: "selectSample",
    component: () => import("../components/sample/select/SampleSelect.vue"),
  },
  {
    path: "/tree",
    name: "tree",
    component: () => import("../components/common/tree/BaseTree.vue"),
  },
  {
    path: "/modal",
    name: "modal",
    component: () => import("../components/sample/modal/TestModal.vue"),
  },
  {
    path: "/alert",
    name: "alert",
    component: () => import("../components/sample/alert/SampleAlert.vue"),
  },

  {
    path: "/loading",
    name: "loading",
    component: () => import("../components/sample/loading/SampleLoading.vue"),
  },
];

export default sampleRouter;
