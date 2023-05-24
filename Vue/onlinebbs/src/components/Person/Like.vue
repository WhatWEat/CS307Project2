<template>
<!--  我的点赞-->
  <div>
    <el-table
        :data="tableData"
        style="width: 100%"
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
      <el-table-column
          prop="count"
          label="点赞总数"
          width="80"
          align="center"
      ></el-table-column>
      <el-table-column
          label="取消点赞"
          width="160"
          align="center">
        <template slot-scope="scope">
          <el-switch
              v-model="scope.row.value"
              @change="handleChange(scope.row)"
              active-color="#ff4949"
              inactive-color="#13ce66">
          </el-switch>
        </template>
      </el-table-column>
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
  name: "Like",
  data() {
    return {
      tableData: [],
      currentPage: 1,
      currentSize: 50
    };
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.currentSize = val;
      this.fetchData(); // 当每页条数改变时，获取新的数据
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.fetchData(); // 当页数改变时，获取新的数据
    },
    fetchData() {
      axios.get(`post/findPostByLike/${this.currentPage}/${this.currentSize}`,{
        withCredentials: true
      }).then(res => {
        this.tableData = res.data;
      })
    },
    handleChange(row) {
      if(row.type===true){
        axios.post(`/post/userDislikePost/${row.id}`, null, {
          withCredentials: true
        })
      } else {
        axios.post(`/post/userLikePost/${row.id}`, null, {
          withCredentials: true
        })
      }
      console.log(row);
    }
  },
  mounted() {
    this.fetchData(); // 在组件挂载后，获取第一页的数据
  }
}
</script>

<style scoped>

</style>
}