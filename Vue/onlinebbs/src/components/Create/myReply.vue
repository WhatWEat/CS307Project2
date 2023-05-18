<template>
  <div>
    <el-table
        :data="tableData"
        style="width: 100%"
    >
      <el-table-column
          prop="date"
          label="回复时间"
          width="180"
      ></el-table-column>
      <el-table-column
          prop="title"
          label="帖子标题"
          width="220"
          align="center"
          :formatter="row => row.title.length > 11 ? row.title.substr(0,11) + '...' : row.title"
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
      currentPage: 1
    };
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.fetchData(); // 当页数改变时，获取新的数据
    },
    fetchData() {
      // 在这里实现获取数据的逻辑，例如从你的后端API获取数据
      // 然后将获取的数据赋值给 this.tableData
    }
  },
  mounted() {
    this.fetchData(); // 在组件挂载后，获取第一页的数据
  }
}
</script>

<style scoped>

</style>