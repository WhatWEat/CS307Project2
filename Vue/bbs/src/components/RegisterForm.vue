<!-- RegisterForm.vue -->
<template>
  <div class="form-container">
    <h2>注册</h2>
    <form @submit.prevent="submit">
      <label for="email">邮箱:</label>
      <input type="email" id="email" v-model="email" required>
      <label for="password">密码:</label>
      <input type="password" id="password" v-model="password" required>
      <button type="submit">注册</button>
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
