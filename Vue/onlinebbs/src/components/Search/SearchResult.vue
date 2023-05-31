<template>
  <div class="posts">
    <el-card v-for="post in posts" :key="post.id" class="post-card">
            <span style="margin-right: 50px;float: left;">
              <el-button type="danger" icon="el-icon-finished" v-if="post.shared==='0'"
                         size="small">原创</el-button>
              <el-button type="primary" icon="el-icon-document-copy" v-else
                         size="small">转载</el-button>
            </span>
      <!--        帖子时间-->
      <span class="post-time"><i class="el-icon-magic-stick"></i>{{ post.time }}</span>
      <!--        帖子作者-->
      <div
          style="flex-grow: 1; align-items: center; justify-content: space-between;display: inline">
            <span class="post-author">
              <el-button type="info" icon="el-icon-user">{{ post.author }}</el-button>
            </span>
        <!-- 帖子标题 -->
        <router-link :to="{ name: 'post-list', params: { id: post.id}}" :title="post.title"
                     class="post-title">
          {{ post.title }}
        </router-link>
      </div>

    </el-card>
  </div>
</template>

<script>
export default {
  props: ['result'],
  name: "SearchResult",
  data() {
    return {
      posts: [],
    }
  },
  filters: {
    timeStyle(createTime) {
      return String(createTime).match(/.{10}/)[0];
    }
  },
}
</script>

<style scoped>
.PostList {
  height: 70vh;
  overflow-y: auto;
}

.skeleton-with {
  width: 1000px;
}

.post-card {
  width: 800px;
  display: flex;
  align-items: center;
}

.post-time,
.post-author {
  margin-right: 20px;
}

.post-title {
  margin-left: 20px;
  text-decoration: none; /* 去掉下划线 */
  color: inherit; /* 继承父元素的文字颜色 */
}
</style>