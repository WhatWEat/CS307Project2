<template>
  <el-container>
    <el-main class="inputMain">

      <el-form label-width="100px">
        <el-form-item label="标题">
          <el-input class="input" placeholder="请输入标题" v-model="title"
          maxlength="15" show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input class="input" type="textarea" placeholder="请输入内容" v-model="content"
                    rows="15"></el-input>
        </el-form-item>
        <el-form-item label="分类" >
          <new-tags :dynamic-tags="tags"></new-tags>
        </el-form-item>
        <el-form-item label="附件">
          <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :file-list="fileList"
              list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-check" @click="sendPost">发这个</el-button>
          <el-button type="danger" icon="el-icon-close" @click="goBack">不发了</el-button>
        </el-form-item>
      </el-form>

    </el-main>
  </el-container>

</template>

<script>
import axios from "axios";
import newTags from "@/components/PostContent/newTags.vue";

export default {
  name: "WritePost",
  components: {newTags},
  data() {
    return {
      title: '',
      content: '',
      tags: ['原创'],
    }
  },
  methods: {
    sendPost() {
      if (this.title === '' || this.content === '') {
        this.$message({
          message: '请填写完整信息',
          type: 'warning',
          offset: 280,
          duration: 600,
        });
      } else {
        axios.post(`post/create`, {
          title: this.title,
          content: this.content,
          categories: this.tags,
        }, {
          withCredentials: true
        }).then(res => {
          if (res.data === true) {
            this.$message({
              message: '发帖成功',
              type: 'success',
              offset: 280,
              duration: 600
            });
            this.$router.push('/main');
          } else {
            this.$message({
              message: '发帖失败,稍后再试',
              type: 'warning',
              offset: 280,
              duration: 600
            });
          }
        }).catch(err => {
          console.log(err);
        });
      }
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    goBack() {
      console.log(this.tags);
      this.$router.push('/main');
    },
  }
}
</script>

<style scoped>
.input {
  width: 80vh;
}

.inputMain {
  margin-top: 20vh;
}


</style>