<template>
  <div id="app">
    <router-link to="/main">论坛</router-link>
    <router-link to="/person">个人主页</router-link>
    <router-view></router-view>
  </div>
</template>

<script>
import axios from "axios";
import LoginReg from "@/components/LoginReg.vue";

export default {
  name: 'App',
  components: {
    LoginReg
  },
  created() {
    axios.get('/user').then(function (response) {
      console.log(response.data);
    }).catch(function (error) {
      console.log(error);
    });
  }

}
</script>

<style>
#app {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 80vh;
}
</style>

