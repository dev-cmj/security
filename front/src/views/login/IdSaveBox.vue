<script setup>
import Cookies from "js-cookie";
import { onMounted, ref } from "vue";

const props = defineProps({ loginId: { default: "", type: String } });
let idSaveCheck = ref(false);
const emit = defineEmits(["set-login-id"]);

const saveCheckHandle = () => {
  if (idSaveCheck.value) {
    Cookies.set("saveLoginId", props.loginId, { expires: 7 });
  } else {
    Cookies.remove("saveLoginId");
  }
};

onMounted(() => {
  const savedId = Cookies.get("saveLoginId");
  if (savedId) {
    idSaveCheck = true;
    emit("set-login-id", savedId);
  }
});
</script>

<template>
  <div>
    <input
      type="checkbox"
      class="checkbox"
      name="idSaveCheck"
      id="idSaveCheck"
      @change="saveCheckHandle"
      v-model="idSaveCheck"
    />
    <label for="idSaveCheck"></label>
    <div class="checkbox_text">아이디 저장</div>
  </div>
</template>

<style scoped>
input[id="idSaveCheck"] {
  display: none;
}

input[id="idSaveCheck"] + label {
  display: inline-block;
  cursor: pointer;
  position: relative;
  font-size: 13px;
  float: left;
}

input[id="idSaveCheck"] + label:before {
  content: "";
  display: inline-block;
  margin-right: 10px;
  left: 0;
  bottom: 1px;
  width: 18px;
  height: 18px;
  border-radius: 3px;
  border: solid 2px #929594;
  background-color: #ffffff;
}

input[id="idSaveCheck"]:checked + label:before {
  padding-top: 3px;
  text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
  font-weight: 800;
  color: #ffffff;
  background: #929594;
  text-align: center;
  line-height: 18px;
}

.login_input .checkbox {
  float: left;
  width: 18px;
  height: 18px;
  border-radius: 3px;
  border: solid 2px #929594;
  background-color: #929594;
  margin: 4px 10px 0px 0px;
}

.login_input .checkbox_text {
  width: 100px;
  height: 16px;
  font-size: 12px;
  font-weight: normal;
  font-stretch: normal;
  font-style: normal;
  line-height: 19px;
  letter-spacing: normal;
  text-align: left;
  color: #929594;
  margin: 0;
}
</style>
