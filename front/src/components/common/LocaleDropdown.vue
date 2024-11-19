<script setup>
import { onMounted, ref } from "vue";
import { useI18n } from "vue-i18n";
import axios from "axios";
import { useLoading } from "@/hooks/useLoadingStore.js";
import DefaultDropdown from "@/components/common/dropdown/DefaultDropdown.vue";

let selectedLocale = ref("");
const i18n = useI18n();
const locales = ref([]);
const defaultLocale = ref("");
const loading = useLoading();

const fetchLocales = async () => {
  const response = await axios.get("/api/locale/support");
  locales.value = response.data;
};

const fetchDefaultLocale = async () => {
  const response = await axios.get("/api/locale/default");
  defaultLocale.value = response.data;
  selectedLocale.value = defaultLocale.value;
};

const changeLocale = async () => {
  const isSame = i18n.locale.value === selectedLocale.value;
  if (isSame) return;
  await changeMessage(selectedLocale.value);
  await axios.get(`/api/locale/change?language=${selectedLocale.value}`);
  i18n.locale.value = selectedLocale.value;
};

const changeMessage = async locale => {
  loading.startMainLoading();
  const messages = await fetchI18nMessage(locale);
  i18n.setLocaleMessage(locale, convertMessagesToMap(messages));
  setTimeout(() => loading.endMainLoading(), 30);
};

const fetchI18nMessage = async locale => {
  const response = await axios.get(`/api/message?locale=${locale}`);
  return response.data;
};

const convertMessagesToMap = messages => {
  return messages.reduce((acc, message) => {
    acc[message.messageCode] = message.messageValue;
    return acc;
  }, {});
};

onMounted(async () => {
  await fetchLocales();
  await fetchDefaultLocale();
});
</script>

<template>
  <DefaultDropdown
    v-model="selectedLocale"
    :items="locales"
    :default-key="defaultLocale"
    div-style="max-width: 100px;"
    _value="title"
    _key="language"
    width="100px"
    height="24px"
    :show-all="false"
    @change="changeLocale"
  />
</template>

<style scoped></style>
