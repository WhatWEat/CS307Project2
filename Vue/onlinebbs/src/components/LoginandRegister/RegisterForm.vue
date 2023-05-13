<!-- RegisterForm.vue -->
<template>
  <div class="form-container">
    <h2>注册</h2>
    <form @submit.prevent="submit">
      <el-input type="email" v-model="email"
                placeholder="输入你的邮箱"/>
      <el-input type="password" show-password v-model="password"
                placeholder="输入密码"/>
      <div class="form-container1">
        <p>已有账号？<a @click.prevent="$emit('login')">登录</a></p>
        <div>
          <el-button native-type="submit">注册</el-button>
        </div>
      </div>
    </form>
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
  justify-content: center;
}
.form-container1 {
  display: flex;
  flex-direction: row;
  margin-top: 10px;
}
.form-container h2 {
  text-align: center;
  margin-top: 10px;
}
.form-container1 div{
  margin-left: 50px;
}
</style>