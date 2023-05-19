<template>
  <div class="PostList">
    <div class="loading" v-if="loading">
      <el-skeleton :rows="15" animated class="skeleton-with"/>
    </div>
    <div class="posts" v-else>
      <el-card v-for="post in posts" :key="post.id" class="post-card" @click="viewPost(post.id)">
        <!--        帖子时间-->
        <div class="post-time">{{ post.time }}</div>
        <!--        帖子作者-->
        <div class="post-author">{{ post.author }}</div>
          <!-- 帖子标题 -->
          <router-link :to="{ name: 'post-list', params: { id: post.id}}" :title="post.title"
                       class="post-title">
            {{ post.title }}
          </router-link>

      </el-card>
      <!-- 无限滚动组件 -->
      <div v-infinite-scroll="loadMore">
        <el-button v-if="!loading">加载更多</el-button>
        <el-spinner v-if="loading"></el-spinner>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'PostList',
  data() {
    return {
      posts: [{
        id: 1,
        title: 'Post 1',
        time: '2022-12-21',
        author: '王小虎',
      },
        {
          id: 2,
          title: 'Post 211111',
          time: '2022-12-21',
          author: '王小虎',
        },
        {
          id: 3,
          title: 'Post 3',
          time: '2022-12-21',
          author: '王小虎',
        }],
    }
  },
  filters: {
    timeStyle(createTime) {
      return String(createTime).match(/.{10}/)[0];
    }
  },
  methods: {
    getData() {
      axios.get('/post/PostList')
      .then((response) => {
        if (response.status === 200) {
          this.posts = response.data;
          this.loading = false;
          console.log(this.posts);
          console.log('受到信息');
        }
      })
      .catch(function (error) {
        console.log(error);
      });
    }
  },
  beforeMount() {
    // this.loading = true;
    this.loading = false;
    this.getData();
  }
}
</script>

<style scoped>
.skeleton-with {
  width: 1000px;
}

.post-card {
  display: flex;
  align-items: center;
}

.post-time,
.post-author {
  margin-left: 20px;
  display: inline;
}

.post-title {
  margin-left: 40px;
  margin-right: 40px;
  text-decoration: none; /* 去掉下划线 */
  color: inherit; /* 继承父元素的文字颜色 */
}
</style>