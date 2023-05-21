<template>
  <div id="app">
    <div class="menu-container">
      <MainMenu></MainMenu>
    </div>
    <div class="background">
      <background></background>
    </div>
    <div class="router-container">
      <transition name="fade" mode="out-in">
        <router-view></router-view>
      </transition>
    </div>
  </div>
</template>

<script>
import MainMenu from "@/components/MainMenu.vue";
import background from "@/components/BackGround/BackGround.vue";

export default {
  name: 'App',
  components: {
    background,
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

.background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
}

.menu-container {
  align-self: flex-end;
  z-index: 2;
}

.router-container {
  display: flex;
  align-items: flex-start;
  height: 100%;
  z-index: 2;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity .3s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>