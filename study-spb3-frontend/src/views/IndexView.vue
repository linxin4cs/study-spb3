<script setup>
import { get } from "@/net";
import { ElMessage } from "element-plus";
import router from "@/router";
import { useStore } from "@/stores";

const store = useStore();

const logout = () => {
  get("/api/auth/logout", (message) => {
    ElMessage.success(message);

    store.auth.userInfo = null;

    router.push("/");
  });
};
</script>

<template>
  <div>欢迎进入学习平台</div>
  <el-button @click="logout" type="danger" plain>退出登录</el-button>
</template>

<style scoped></style>
