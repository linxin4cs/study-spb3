<script setup>
import { Lock, User } from "@element-plus/icons-vue";
</script>

<template>
  <div style="margin: 20px; text-align: center">
    <div style="margin-top: 150px">
      <div style="font-size: 25px">登录</div>
      <div style="font-size: 14px; color: grey">
        在进入系统前请先输入用户名和密码进行登录
      </div>
      <div style="margin-top: 50px">
        <el-input type="text" placeholder="用户名/邮箱">
          <template #prefix>
            <el-icon>
              <User />
            </el-icon>
          </template>
        </el-input>
        <el-input style="margin-top: 10px" type="text" placeholder="密码">
          <template #prefix>
            <el-icon>
              <Lock />
            </el-icon>
          </template>
        </el-input>
      </div>
      <el-row style="margin-top: 5px">
        <el-col :span="12" style="text-align: left">
          <el-checkbox label="记住我" />
        </el-col>
        <el-col :span="12" style="text-align: right">
          <el-link>忘记密码？</el-link>
        </el-col>
      </el-row>
      <div>
        <el-button style="width: 270px; margin-top: 40px" type="success" plain>
          登录
        </el-button>
      </div>
      <el-divider>
        <span style="color: grey; font-size: 13px">没有账号</span>
      </el-divider>
      <div>
        <el-button style="width: 270px" type="warning" plain
          >注册账号
        </el-button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
