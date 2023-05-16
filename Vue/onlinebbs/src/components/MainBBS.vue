<template>
  <div>
    <h1>BBS</h1>
    <post-list></post-list>
  </div>
</template>

<script>
import PostList from "@/components/PostContent/PostList.vue";
export default {
  components: {
    PostList,
  },
  name: "MainBBS"
}
</script>

<style scoped>

</style>