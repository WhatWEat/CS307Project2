<template>
  <div>
    <h1>个人中心</h1>
    <el-tabs type="border-card">
      <el-tab-pane label="我的收藏">
        <Like/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import Like from './Person/Like.vue'

export default {
  name: "PersonCenter",
  components: {
    Like
  }
}
</script>

<style scoped>

</style>