<template>
  <!--  我的收藏-->
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
          width="180"
          align="center"
          :formatter="row => row.title.length > 20 ? row.title.substr(0,20) + '...' : row.title"
      ></el-table-column>
      <el-table-column
          label="取消收藏"
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
  name: "Marked",
  data() {
    return {
      tableData: [],
      currentPage: 1,
      currentSize: 50,
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
      // 在这里实现获取数据的逻辑，例如从你的后端API获取数据
      // 然后将获取的数据赋值给 this.tableData
      axios.get(`/post/findPostByFavorite/${this.currentPage}/${this.currentSize}`,{
        withCredentials: true
      }).then(res => {
        this.tableData = res.data;
        console.log(this.tableData);
      })
    },
    handleChange(row) {
      if(row.value === true){
        axios.post(`/post/userCancelFavoritePost/${row.id}`,null,{
          withCredentials: true
        })
      } else {
        axios.post(`/post/userFavoritePost/${row.id}`,null,{
          withCredentials: true
        })
      }
      console.log(row.value);
    },
    goPost(row,column,cell,event){
      if(column.label !== '取消收藏'){
        this.$router.push(`/post-list/${row.id}`);
      }

    },
  },

  mounted() {
    this.fetchData(); // 在组件挂载后，获取第一页的数据
  }
}
</script>

<style scoped>

</style>