<template>
  <div>
    <el-table
        :data="tableData"
        style="width: 100%"
        @cell-click="goPost"
    >
      <el-table-column
          prop="replying_time"
          label="回复时间"
          width="180"
          :formatter="formatDate"
      ></el-table-column>
      <el-table-column
          prop="content"
          label="回复内容"
          width="300"
          align="center"
          :formatter="row => row.content.length > 20 ? row.content.substr(0,20) + '...' : row.content"
      ></el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="50"
        layout="sizes, prev, pager, next, jumper"
    >
    </el-pagination>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "myReply",
  data() {
    return {
      tableData: [
        {
          date: '2016-05-02',
          title: '帖子标题',
          content: '王小虎'
        },
        {
          date: '2016-05-04',
          title: '帖子标题',
          content: '王小虎',
        },
        {
          date: '2016-05-01',
          title: '帖子标题',
          content: '王小虎',
        },
        {
          date: '2016-05-03',
          title: '帖子标题',
          content: '王小虎',
        }
      ], //你需要把这里替换成你的帖子数据
      currentPage: 1,
      currentSize: 50,
    };
  },
  methods: {
    formatDate(row, column) {
      let timestamp = row.replying_time;
      let date = new Date(timestamp);

      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();

      return year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.currentSize = val;
      this.fetchData();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.fetchData(); // 当页数改变时，获取新的数据
    },
    fetchData() {
      axios.get(`reply/findReplyByUser/${this.currentPage}/${this.currentSize}`,{
        withCredentials: true
      })
      .then(response => {
        this.tableData = response.data;
        console.log(this.tableData);
      })
      .catch(error => {
        console.log(error);
      });
    },
    goPost(row,column,cell,event){
      if(row.postID == null){
      } else {
        this.$router.push(`/post-list/${row.postID}`);
      }
    }
  },
  mounted() {
    this.fetchData(); // 在组件挂载后，获取第一页的数据
  }
}
</script>

<style scoped>

</style>