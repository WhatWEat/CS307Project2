<!-- App.vue -->
<template>
  <div class="container">
<!--    <ColumnMenu></ColumnMenu>-->
<!--    <router-link to="/main">论坛</router-link>-->
    <router-link to="/person">个人中心</router-link>
    <router-view></router-view>
  </div>
</template>

<script>

import ColumnMenu from "@/components/ColumnMenu.vue";

export default {
  components: {ColumnMenu},
  created() {
    axios.get('/user'),{

    }
  }
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 80vh;
}

</style>
