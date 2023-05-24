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
        layout="sizes, prev, pager, next, jumper"
    >
    </el-pagination>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Release",
  data() {
    return {
      tableData: [],
      currentPage: 1,
      pageSize: 50,
    };
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.fetchData();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.fetchData(); // 当页数改变时，获取新的数据
    },
    fetchData() {
      // 在这里实现获取数据的逻辑，例如从你的后端API获取数据
      // 然后将获取的数据赋值给 this.tableData
      axios.get(`/post/findPostByWrite/${this.currentPage}/${this.pageSize}`,{
        withCredentials: true,
      }).then(res => {
        this.tableData = res.data;
        // console.log(this.tableData);
      }).catch(err => {
        console.log(err);
      });
    },
    goPost(row,column,cell,event){
      this.$router.push(`/post-list/${row.id}`);
    }
  },
  mounted() {
    this.fetchData(); // 在组件挂载后，获取第一页的数据
  }
}
</script>

<style scoped>

</style>