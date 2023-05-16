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
export default {
  name: 'App',
  components: {
    MainMenu
  },
  created() {
  }

}
</script>
<style>
#app {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 80vh;
}

.menu-container {
  align-self: flex-end;
}

.router-container {
  display: flex;
  align-items: flex-start;
  height: 100%;
}
</style>