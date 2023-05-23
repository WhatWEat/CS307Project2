<template>
  <el-container>
    <el-main>
      <div class="PostList" v-infinite-scroll="loadMorePosts" infinite-scroll-disabled="loading"
           infinite-scroll-distance="10">


        <div class="loading" v-if="loading">
          <el-skeleton :rows="15" animated class="skeleton-with"/>
        </div>
        <div class="posts" v-else>
          <el-card v-for="post in posts" :key="post.id" class="post-card">
            <span style="margin-right: 50px;float: left;">
              <el-button type="primary" icon="el-icon-finished" v-if="post.share===0"
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


      </div>
    </el-main>
    <el-footer>
      <div class="write">
        <el-button type="primary" icon="el-icon-edit" @click="goWrite">发发我的</el-button>
        <el-button type="success" icon="el-icon-search" @click="goSearch">搜搜你的</el-button>
      </div>
    </el-footer>
  </el-container>
</template>

<script>
import axios from "axios";

export default {
  name: 'PostList',
  data() {
    return {
      hasMorePosts: true,
      currentPage: 1,
      loading: true,
      posts: [],
    }
  },
  filters: {
    timeStyle(createTime) {
      return String(createTime).match(/.{10}/)[0];
    }
  },
  methods: {
    getData() {
      axios.get('/post/findAllPost/1/20', {
        withCredentials: true
      })
      .then((response) => {
        if (response.status === 200) {
          this.posts = response.data;
          this.loading = false;
          // console.log(this.posts);
          console.log('受到信息');
        }
      })
      .catch(function (error) {
        console.log(error);
      });
    },
    loadMorePosts() {
      this.currentPage += 1;
      axios.get('/post/findAllPost/' + this.currentPage + '/20', {
        withCredentials: true
      })
      .then((response) => {
        if (response.status === 200) {
          this.posts = this.posts.concat(response.data);
          this.loading = false;
          // console.log(this.posts);
          console.log('更多信息');
        } else {
          this.hasMorePosts = false;
        }
      })
      .catch(function (error) {
        console.log(error);
      });
    },
    goWrite() {
      this.$router.push('/write');
    },
    goSearch() {

    },
  },
  beforeMount() {
    this.loading = true;
    this.getData();
  }
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
  margin-right: 50px;
}

.post-title {
  margin-left: 100px;
  margin-right: 40px;
  text-decoration: none; /* 去掉下划线 */
  color: inherit; /* 继承父元素的文字颜色 */
}
</style>