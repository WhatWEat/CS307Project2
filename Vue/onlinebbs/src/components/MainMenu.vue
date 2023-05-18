<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" router
           @select="handleSelect">
    <el-menu-item index="/main">BBS</el-menu-item>
    <el-menu-item index="/person">个人中心</el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: "MainMenu",
  data() {
    return {
      activeIndex: '1'
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>
.el-menu-demo {
  background-color: transparent !important;
}
</style>