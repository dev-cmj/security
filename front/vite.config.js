import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import VitePluginClean from "vite-plugin-clean";

const dist = "../trashBackup/";
// https://vite.dev/config/
export default defineConfig({
  plugins: [vue(), VitePluginClean({ targetFiles: dist })],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  // 이게 이제 바뀜.
  css: {
    preprocessorOptions: {
      scss: {
        api: "modern-compiler", // or "modern"
      },
    },
  },
  optimizeDeps: {
    include: ["@toast-ui/vue-editor"],
  },
  // build: {
  //   outDir: dist,
  //   rollupOptions: {
  //     input: {
  //       main: fileURLToPath(new URL("./index.html", import.meta.url)),
  //     },
  //   },
  // }, // 빌드 결과물이 생성되는 경로
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:8080",
      },
    },
  },
});
