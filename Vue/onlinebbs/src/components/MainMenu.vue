<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" router
           @select="handleSelect">
    <el-menu-item index="/main">BBS</el-menu-item>
    <el-menu-item index="/hot">
      <span style="color: #ff0062; cursor: pointer">热搜榜</span>
    </el-menu-item>
    <el-menu-item index="/person">个人中心</el-menu-item>
    <el-menu-item index="/create">创作中心</el-menu-item>
    <el-menu-item index="/login">
      <span style="color: #008cff; cursor: pointer" @click="logout">退出登录</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
import axios from "axios";

export default {
  name: "MainMenu",
  data() {
    return {
      activeIndex: '1'
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      // axios.get('/post/getCookie',{
      //   withCredentials: true
      // })
      console.log(key, keyPath);
    },
    logout() {
      // 处理退出登录的逻辑
      document.cookie = 'session_id=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
      // this.$router.push('/login').catch(err => {});
      console.log('logout');
    },
  }
}
</script>

<style scoped>
.el-menu-demo {
  background-color: transparent !important;
}
</style>