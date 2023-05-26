<template>
  <!--  我的屏蔽-->
  <div>
    <el-table
        :data="tableData"
        style="width: 100%"
    >
      <el-table-column
          prop="username"
          label="用户"
          width="180"
          align="center"
      ></el-table-column>
      <el-table-column
          label="取消屏蔽"
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
  name: "Block",
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
      axios.get(`user/findBlockList/${this.currentPage}/${this.currentSize}`,{
        withCredentials: true
      }).then(res => {
        this.tableData = res.data;
        console.log(this.tableData);
      })
    },
    handleChange(row) {
      if(row.value===true){
        axios.post(`/user/cancelBlockUser/${row.username}`, null, {
          withCredentials: true
        })
      } else {
        axios.post(`/user/blockUser/${row.username}`, null, {
          withCredentials: true
        })
      }
      console.log(row);
    },

  },
  mounted() {
    this.fetchData(); // 在组件挂载后，获取第一页的数据
  },
}
</script>

<style scoped>

</style>
}