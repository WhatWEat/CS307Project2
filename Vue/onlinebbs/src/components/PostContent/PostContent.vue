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
              <el-button type="primary" icon="el-icon-circle-check" circle @click="likePost"
                         v-if="!post.like"></el-button>
              <el-button type="primary" icon="el-icon-share" circle></el-button>
              <el-button type="primary" icon="el-icon-star-off" circle v-if="!post.marked"></el-button>
            </span>
          <span style="float: right">发帖时间：{{ post.time }}</span>
        </div>
        <el-card style="box-shadow: none;">
          <div slot="header"
               style="display: flex; justify-content: space-between; align-items: center;">
            <span>作者: {{ post.author }}</span>
            <span style="float: right">
              <el-button type="danger">屏蔽</el-button>
              <el-button type="warning">关注</el-button>
            </span>

          </div>
          <div style="white-space: pre-wrap;">
            <span>{{ post.content }}</span>
          </div>
        </el-card>
      </el-card>
    </el-main>
    <el-footer>
      <!--      评论区-->
      <Comment></Comment>
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
      post: {
        title: 'niu',
        time: '',
        author: 'test',
        share: false,
        like: false,
        marked: false,
      },
      tags: [
        {
          tag: 'test1',
          type: 'success'
        },
        {
          tag: 'test2',
          type: 'info'
        },
        {
          tag: 'test3',
          type: 'warning'
        },
        {
          tag: 'test4',
          type: 'danger'
        },
      ],
      loading: false
    };
  },
  methods: {
    getPost() {
      axios.get(`/post/findByID/${this.id}`, {
        withCredentials: true
      }).then(
          res => {
            this.post = res.data;
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
        if (res.data === true) {
          this.post.like = true;
          this.$message({
            message: '点赞成功',
            type: 'success',
            offset: 280,
            duration: 1000
          });
          this.$router.push('/main');
        } else {
          this.$message({
            message: '点赞失败',
            type: 'warning',
            offset: 280,
            duration: 1000
          });
        }
      })
    }
  },
  mounted() {
    this.getPost();
    this.getTags();
  }
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