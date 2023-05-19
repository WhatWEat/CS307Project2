<!-- LoginForm.vue -->
<template>
  <div class="form-container">
    <h2>登录</h2>
    <form @submit.prevent="submit">
      <el-input v-model="username"
                placeholder="输入用户名"/>
      <el-input type="password" show-password v-model="password"
                placeholder="输入密码"/>
      <div class="form-container1">
        <p>没有账号？<a @click.prevent="$emit('register')">注册</a></p>
        <div>
          <el-button native-type="submit">登录</el-button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      username: '',
      password: '',
    }
  },
  methods: {
    submit(){
      // 请添加登录逻辑
      if(this.username === '' || this.password === ''){
        this.$message({
          message: '请填写完整信息',
          type: 'warning',
          offset:280
        });
        return;
      } else {
        axios.get(`/user/login/${this.username}/${this.password}`,{
          withCredentials: true
        }).then(res => {
          if(res.data === true){
            this.$message({
              message: '登录成功',
              type: 'success',
              offset: 280,
              duration: 1000
            });
            this.$router.push('/main');
          } else {
            this.$message({
              message: '登录失败',
              type: 'warning',
              offset:280,
              duration: 1000
            });
          }
        }).catch(err => {
          console.log(err);
        })
      }
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