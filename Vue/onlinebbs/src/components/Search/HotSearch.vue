<template>
  <el-container>
    <el-main>
      <div class="PostList" v-infinite-scroll="loadMorePosts" infinite-scroll-disabled="loading"
           infinite-scroll-distance="10">


        <div class="loading" v-if="loading">
          <el-skeleton :rows="15" animated class="skeleton-with"/>
        </div>
        <div class="posts" v-else>
          <el-card v-for="(post,index) in posts" :key="post.id" class="post-card">
            <span style="margin-right: 50px;align-items: center;">
              <el-button type="danger" icon="el-icon-s-flag" v-if="index === 0 "
                         size="small">热热热</el-button>
              <el-button type="warning" icon="el-icon-s-flag" v-else-if="index === 1 "
                         size="small">第二名</el-button>
              <el-button type="success" icon="el-icon-s-flag" v-else-if="index === 2 "
                         size="small">第三名</el-button>
              <el-button type="info" v-else
                         size="small">NO.{{index+1}}</el-button>
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
  </el-container>
</template>

<script>
import axios from "axios";
export default {
  name: "HotSearch",
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
      axios.get('/post/hotList/1/20', {
        withCredentials: true
      })
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
    },
    loadMorePosts() {
      this.currentPage += 1;
      axios.get('/post/hotList/' + this.currentPage + '/20', {
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
      this.$router.push('/search')
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
  margin-right: 20px;
}

.post-title {
  margin-left: 20px;
  text-decoration: none; /* 去掉下划线 */
  color: inherit; /* 继承父元素的文字颜色 */
}
</style>