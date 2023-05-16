<template>
  <div class="ArticleSection">
    <div class="loading" v-if="loading">
      Loading...
    </div>
    <div class="article" v-else>
      <h1>{{ post.title }}</h1>
      <ul>
        <li>• 分类:{{ postTab }}</li>
        <li>• 发布于:{{ post.create_at | formatDate }}</li>
        <li>
          • 作者:
          <router-link :to="{ name:'user_info',params:{name:post.author.loginname }}">
            {{ post.author }}
          </router-link>
        </li>
      </ul>
      <div>
        <Comment></Comment>
      </div>
    </div>
  </div>
</template>

<script>
import Comment from "@/components/PostContent/Comment.vue";

export default {
  name: "PostContent",
  components: {Comment},
  data() {
    return {
      post: {
        title: 'niu',
        create_at: 2022,
        author: 'test'
      },
      loading: false
    };
  },
}
</script>

<style scoped>

</style>