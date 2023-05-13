<!-- RegisterForm.vue -->
<template>
  <div class="form-container">
    <h2>注册</h2>
    <form @submit.prevent="submit">
      <el-input type="email" v-model="email"
                placeholder="输入你的邮箱" class="w-20 m-2"/>
      <el-input type="password" show-password v-model="password"
                placeholder="输入密码" class="w-20 m-2"/>
      <el-button native-type="submit">注册</el-button>
    </form>
    <p>已有账号？<a @click.prevent="$emit('login')">登录</a></p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
    submit() {
      // 请添加注册逻辑
      console.log(this.email, this.password);
    }
  }
}
</script>
<style>
.form-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  /*height: 100vh; !* 可选，使容器充满整个视口 *!*/
}

.form-container h2 {
  text-align: center;
}

.form-container p {
  align-self: flex-start;
}
</style>