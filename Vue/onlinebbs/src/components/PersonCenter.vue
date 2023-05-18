<template>
  <div>
    <h1>个人中心</h1>
    <el-tabs type="border-card" v-model="activeTab">
      <el-tab-pane label="我的发布" name="/person/release"></el-tab-pane>
      <el-tab-pane label="我的转发" name="/person/share"></el-tab-pane>
      <el-tab-pane label="我的收藏" name="/person/mark"></el-tab-pane>
      <el-tab-pane label="我的点赞" name="/person/like"></el-tab-pane>
      <router-view></router-view>
    </el-tabs>

  </div>
</template>

<script>
export default {
  name: "PersonCenter",
  data() {
    return {
      activeTab: '/person'
    }
  },
  watch: {
    activeTab(newTab) {
      this.$router.push(newTab).catch(err => {
        if (err.name !== 'NavigationDuplicated') {
          throw err;
        }
      });
    }
  },
  created() {
    this.activeTab = this.$route.path;
    this.$router.afterEach((to) => {
      this.activeTab = to.path;
    })
  }
}
</script>

<style scoped>

</style>