<template>
  <el-container>
    <el-main class="inputMain">

      <el-form label-width="100px">
        <el-form-item label="标题">
          <el-input class="input" placeholder="请输入标题" v-model="title"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input class="input" type="textarea" placeholder="请输入内容" v-model="content"
                    rows="15"></el-input>
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

export default {
  name: "WritePost",
  data() {
    return {
      title: '',
      content: '',
    }
  },
  methods: {
    sendPost() {
      if (this.title === '' || this.content === '') {
        this.$message({
          message: '请填写完整信息',
          type: 'warning',
          offset: 280,
          duration: 600
        });
      } else {
        axios.post(`post/create/${this.title}/${this.content}`, null, {
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
    goBack() {
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