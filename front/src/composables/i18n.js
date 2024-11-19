import { createI18n } from "vue-i18n";

const initI18n = () => {
  return createI18n({
    locale: "",
    legacy: false,
    messages: {},
  });
};
export default initI18n;
