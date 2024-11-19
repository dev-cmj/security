import bootstrap from "@vueform/vueform/dist/bootstrap";
import { defineConfig } from "@vueform/vueform";
import ko from "@vueform/vueform/locales/ko";

export default defineConfig({
  theme: bootstrap,
  locales: { ko },
  locale: "ko",
});
