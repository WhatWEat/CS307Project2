<template>
  <div>
    <h1>个人中心</h1>
    <el-tabs type="border-card" v-model="activeTab">
      <el-tab-pane label="我的回复" name="/create/my-reply"></el-tab-pane>
      <el-tab-pane label="我的帖子" name="/create/my-post"></el-tab-pane>
      <router-view></router-view>
    </el-tabs>

  </div>
</template>

<script>
export default {
  name: "PersonCreate",
  data() {
    return {
      activeTab: '/create'
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