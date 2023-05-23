<script setup></script>

<template>
  <div style="width: 100vw; height: 100vh; overflow: hidden; display: flex">
    <div style="flex: 1">
      <el-image
        style="width: 100%; height: 100%"
        fit="cover"
        src="https://www.10wallpaper.com/wallpaper/2560x1440/1712/Beautiful_mountain_lake_reflection_Travel_4K_Ultra_HD_2560x1440.jpg"
      />
    </div>
    <div class="welcome-title">
      <div style="font-size: 30px; font-weight: bold">
        欢迎来到我们的学习平台
      </div>
      <div style="margin-top: 10px">
        在这里你可以学习如何使用 Java，如何搭建网站，并且与 Java 之父密切交流。
      </div>
      <div>在这里你可以同性交友（doge</div>
    </div>
    <div style="width: 400px; background-color: white; z-index: 1">
        <router-view />
    </div>
  </div>
</template>

<style scoped>
.welcome-title {
  position: absolute;
  bottom: 30px;
  left: 30px;
  color: white;
  text-shadow: 0 0 10px black;
}
</style>
