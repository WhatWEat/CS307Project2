<template>
  <el-container>
    <el-main>
      <!--      展示帖子内容-->
      <!--      帖子内容包括标题、作者、发帖时间、内容-->
      <el-card style="box-shadow: none;">
        <div slot="header"
             style="display: flex; justify-content: space-between; align-items: center;">
          <span class="post-title">{{ post.title }}</span>
          <span class="tag-container">
            <el-tag v-for="tag in tags" :key="tag.type" :type="tag.type"
                    effect="dark" class="tag-item">{{ tag.tag }} </el-tag>
          </span>
          <span>
              <el-button type="success" icon="el-icon-circle-check" circle @click="likePost"
                         v-if="like==='false'"></el-button>
              <el-button type="danger" icon="el-icon-circle-check" circle @click="dislikePost"
                         v-else></el-button>
              <el-button type="success" icon="el-icon-star-off" circle @click="markPost"
                         v-if="marked==='false'"></el-button>
              <el-button type="danger" icon="el-icon-star-off" circle @click="dismarkPost"
                         v-else></el-button>
              <el-button type="primary" icon="el-icon-share" circle @click="sharePost"></el-button>

            </span>
          <span style="float: right">发帖时间：{{ post.time }}</span>
        </div>
        <el-card style="box-shadow: none;">
          <div slot="header"
               style="display: flex; justify-content: space-between; align-items: center;">
            <span>作者: {{ post.author }}</span>
            <span v-if="this.shared!=='0'">
              <el-button type="info" @click="goOriginPost"> 原贴地址 </el-button>
            </span>
            <span style="float: right">
              <el-button type="danger" @click="blockUser"
                         v-if="this.blocked==='false'">屏蔽</el-button>
              <el-button type="danger" @click="cancelBlockUser" v-else>已屏蔽</el-button>
              <el-button type="warning" v-if="this.followed==='false'"
                         @click="followUser">关注</el-button>
              <el-button type="warning" v-else @click="cancelFollowUser">取消关注</el-button>
            </span>
          </div>

          <div style="white-space: pre-wrap;">
            <span>{{ post.content }}</span>
            <div v-if="this.hasVideo || this.hasPicture"
                 style="width: 100%; height: 200px; display: flex; justify-content: center; margin-bottom: 50px">
              <!-- 展示图片 -->
              <img v-if="this.hasPicture" :src="this.filepath" alt="Image"
                   style="max-width: 300px; max-height: 200px"/>

              <!-- 展示视频 -->
              <video controls v-if="this.hasVideo" preload="auto" autoplay :src="this.filepath"
                   style="max-width: 300px; max-height: 200px">

              </video>
            </div>
          </div>
        </el-card>
      </el-card>
    </el-main>
    <el-footer>
      <!--      评论区-->
      <Comment :postid="id"></Comment>
    </el-footer>
  </el-container>
</template>

<script>
import Comment from "@/components/PostContent/Comment.vue";
import axios from "axios";

export default {
  name: "PostContent",
  components: {Comment},
  props: ['id'],
  data() {
    return {
      hasPicture: false,
      hasVideo: false,
      filepath: '',
      shared: '0',
      like: 'false',
      marked: 'false',
      followed: 'false',
      blocked: 'false',
      post: {},
      tags: [],
      loading: false
    };
  },
  methods: {
    getPost() {

      this.hasPicture = true;
      axios.get(`/post/findByID/${this.id}`, {
        withCredentials: true
      }).then(
          res => {
            this.filepath = res.data.file;
            this.post = res.data;
            this.shared = this.post.shared;
            this.like = this.post.like;
            this.marked = this.post.marked;
            this.followed = this.post.followed;
            if(this.filepath !== '' && this.filepath!=='0') {
              let filename = this.filepath.split('.');
              this.filepath = 'http://localhost:8088/' + this.filepath;
              let index = filename[filename.length-1];
              if(index === 'png' || index === 'jpg' || index === 'jpeg' || index === 'gif') {
                this.hasPicture = true;
                this.hasVideo = false;
              } else {
                this.hasPicture = false;
                this.hasVideo = true;
              }
            }
            console.log(this.post);
          }
      )
    },
    getTags() {
      axios.get(`/post/getTags/${this.id}`, {
        withCredentials: true
      }).then(
          res => {
            this.tags = res.data;
          })
    },
    likePost() {
      axios.post(`/post/userLikePost/${this.id}`, null, {
        withCredentials: true
      }).then(res => {
        this.like = 'true';
        this.$message({
          message: '点赞成功',
          type: 'success',
          offset: 280,
          duration: 1000
        });
      })
    },
    dislikePost() {
      axios.post(`/post/userDislikePost/${this.id}`, null, {
        withCredentials: true
      }).then(res => {
        this.like = 'false';
        this.$message({
          message: '取消点赞',
          type: 'warning',
          offset: 280,
          duration: 1000
        });
      })
    },
    markPost() {
      axios.post(`/post/userFavoritePost/${this.id}`, null, {
        withCredentials: true
      }).then(res => {
        this.marked = 'true';
        this.$message({
          message: '点赞成功',
          type: 'success',
          offset: 280,
          duration: 1000
        });
      })
    },
    dismarkPost() {
      axios.post(`/post/userCancelFavoritePost/${this.id}`, null, {
        withCredentials: true
      }).then(res => {
        this.marked = 'false';
        this.$message({
          message: '取消收藏',
          type: 'warning',
          offset: 280,
          duration: 1000
        });
      })
    },
    sharePost() {
      axios.post(`/post/sharePost/${this.id}`, null, {
        withCredentials: true
      }).then(res => {
        this.share = 'true';
        this.$message({
          message: '分享成功',
          type: 'success',
          offset: 280,
          duration: 1000
        });
      })
    },
    followUser() {
      axios.post(`/user/followUser/${this.post.author}`, null, {
        withCredentials: true
      }).then(res => {
        this.followed = 'true';
        this.$message({
          message: '关注成功',
          type: 'success',
          offset: 280,
          duration: 1000
        });
      })
    },
    cancelFollowUser() {
      axios.post(`/user/cancelFollowUser/${this.post.author}`, null, {
        withCredentials: true
      }).then(res => {
        this.followed = 'false';
        this.$message({
          message: '取消关注',
          type: 'warning',
          offset: 280,
          duration: 1000
        });
      })
    },
    blockUser() {
      let name = document.cookie.split('=')[1];
      if (name === this.post.author) {
        this.$message({
          message: '不能屏蔽自己',
          type: 'warning',
          offset: 280,
          duration: 1000
        });
        return;
      }
      axios.post(`/user/blockUser/${this.post.author}`, null, {
        withCredentials: true
      }).then(res => {
      });
      this.blocked = 'true';
    },
    cancelBlockUser() {
      axios.post(`/user/cancelBlockUser/${this.post.author}`, null, {
        withCredentials: true
      }).then(res => {
      });
      this.blocked = 'false';
    },
    goOriginPost() {
      this.$router.push(`/post-list/${this.post.shared}`);
      // this.$router.go(0);
    }
  },
  mounted() {
    this.getPost();
    this.getTags();
  },
  beforeRouteUpdate(to, from, next) {
    // 执行你想要的操作
    console.log('Component updated due to route change');
    // 继续路由导航
    next();
    console.log(to.path);
    try {
      this.id = to.path.split('/')[2];
    } catch (e) {
      console.log('postContent有点问题');
    }

    this.getTags();
    this.getPost();
  },
}
</script>

<style scoped>
.post-title {
  margin-right: 10px;
  font-weight: bold;
  font-size: 20px;
}

.tag-container {
  display: flex;
  justify-content: center;
}

.tag-item {
  margin-right: 5px;
}
</style>