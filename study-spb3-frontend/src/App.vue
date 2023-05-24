<script setup>
import { useStore } from "@/stores";
import router from "@/router";
import { get } from "@/net";

const store = useStore();

if (store.auth.userInfo == null) {
  get(
    "/api/user/me",
    (message) => {
      store.auth.userInfo = message;

      router.push("/index");
    },
    () => {
      router.push("");
    }
  );
}
</script>

<template>
  <router-view />
</template>

<style scoped></style>