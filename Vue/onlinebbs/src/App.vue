<template>
  <div id="app">
    <div class="menu-container">
      <MainMenu></MainMenu>
    </div>
    <div class="router-container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import MainMenu from "@/components/MainMenu.vue";
import axios from "axios";
export default {
  name: 'App',
  components: {
    MainMenu
  },
  created() {
    axios.get('/user/query').then(function (res){
      console.log(res.data);
    }).catch(err => {
      console.log(err);
    })
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

.menu-container {
  align-self: flex-end;
}

.router-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}
</style>