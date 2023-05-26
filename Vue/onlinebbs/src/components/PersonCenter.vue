<template>
  <div>
    <h1>个人中心</h1>
    <el-tabs type="border-card" v-model="activeTab">
      <el-tab-pane label="我的收藏" name="/person/mark"></el-tab-pane>
      <el-tab-pane label="我的点赞" name="/person/like"></el-tab-pane>
      <el-tab-pane label="我的关注" name="/person/subscribe"></el-tab-pane>
      <el-tab-pane label="我的屏蔽" name="/person/block"></el-tab-pane>
      <transition name="fade" mode="out-in">
        <router-view></router-view>
      </transition>
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