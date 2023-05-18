<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>欢迎来到BBS,请先登录</span>
      <i class="el-icon-user"></i>
    </div>
    <div class="text item">
      <LoginForm v-if="showLoginForm" @register="showLoginForm = false" @login="handleLogin"/>
      <RegisterForm v-else @login="showLoginForm = true"/>
    </div>
  </el-card>
</template>

<script>
import LoginForm from './LoginandRegister/LoginForm.vue';
import RegisterForm from './LoginandRegister/RegisterForm.vue';

export default {
  name: "LoginReg",
  components: {
    LoginForm,
    RegisterForm,
  },
  data() {
    return {
      showLoginForm: true,
    }
  },
}
</script>
<style scoped>

.box-card {
  align-self: center;
  width: 420px;
}
</style>