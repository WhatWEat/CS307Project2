<template>
  <!--  我的关注-->
  <div>
    <el-table
        :data="tableData"
        style="width: 100%"
    >
      <el-table-column
          prop="time"
          label="注册时间"
          width="180"
          align="center"
      ></el-table-column>
      <el-table-column
          prop="username"
          label="用户名"
          width="180"
          align="center"
      ></el-table-column>
      <el-table-column
          prop="countUserFans"
          label="粉丝数量"
          width="180"
          align="center"
      ></el-table-column>
      <el-table-column
          label="取消关注"
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
  name: "Subscribe",
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
      axios.get(`/user/findFollowList/${this.currentPage}/${this.currentSize}`, {
        withCredentials: true
      }).then(res => {
        this.tableData = res.data;
        console.log(this.tableData);
      })
    },
    handleChange(row) {
      if (row.value === true) {
        axios.post(`/user/cancelFollowUser/${row.username}`,null, {
          withCredentials: true
        });
      } else {
        axios.post(`/user/followUser/${row.username}`, null,{
          withCredentials: true
        });
      }
      console.log(row.value === true);
      console.log(row.value);
    },
  },
  mounted() {
    this.fetchData(); // 在组件挂载后，获取第一页的数据
  },
}
</script>

<style scoped>

</style>