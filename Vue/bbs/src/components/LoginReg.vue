<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <el-icon>
          <User style="width: 20em; height: 20em; margin-right: 1px"/>
        </el-icon>
        <span>欢迎来到BBS,请先登录</span>
      </div>
    </template>
    <div>
      <LoginForm v-if="showLoginForm" @register="showLoginForm = false" @login="handleLogin"/>
      <RegisterForm v-else @login="showLoginForm = true"/>
    </div>
  </el-card>
</template>
<script>
import LoginForm from './LoginandRegister/LoginForm.vue';
import RegisterForm from './LoginandRegister/RegisterForm.vue';
import {User} from "@element-plus/icons-vue";

export default {
  name: "LoginReg",
  components: {
    User,
    LoginForm,
    RegisterForm,
  },
  data() {
    return {
      showLoginForm: true,
    }
  },
  isLogin: false,
  methods: {
    handleLogin(isLogin) {
      this.isLogin = isLogin;
      this.$router.push('/main');
    }
  }
}
</script>
<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.box-card {
  width: 420px;
}
</style>