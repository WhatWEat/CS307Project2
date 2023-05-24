<template>
  <div>
    <el-table
        :data="tableData"
        style="width: 100%"
        @cell-click="goPost"
    >
      <el-table-column
          prop="time"
          label="发表时间"
          width="180"
          align="center"
      ></el-table-column>
      <el-table-column
          prop="author"
          label="作者"
          width="180"
          align="center"
      ></el-table-column>
      <el-table-column
          prop="title"
          label="帖子标题"
          width="220"
          align="center"
          :formatter="row => row.title.length > 11 ? row.title.substr(0,11) + '...' : row.title"
      ></el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="50"
        layout="total, sizes, prev, pager, next, jumper"
    >
    </el-pagination>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Share",
  data() {
    return {
      tableData: [
      ], //你需要把这里替换成你的帖子数据
      currentPage: 1,
      currentSize: 50,
    };
  },
  methods: {
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
      axios.get(`/post/findPostByShare/${this.currentPage}/${this.currentSize}`,{
        withCredentials: true
      }).then(res => {
        this.tableData = res.data;
      }).catch(err => {
        console.log(err);
      });
    },
    goPost(row,column,cell,event){
      this.$router.push(`/post-list/${row.id}`);
    },
  },
  mounted() {
    this.fetchData(); // 在组件挂载后，获取第一页的数据
  }
}
</script>

<style scoped>

</style>