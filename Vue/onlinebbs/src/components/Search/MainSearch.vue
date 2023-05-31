<template>
  <el-container>
    <el-main style="margin-top: 5vh">
      <el-row v-for="(input, index) in inputs" :key="index" :gutter=10 align="middle" type="flex"
              style="margin-top: 20px;margin-left: 10px">
        <el-col :span="20" >
          <div style="display: flex; width: 600px">
            <el-select v-model="input.select" placeholder="请选择" style="width: 200px">
              <el-option label="标题" value="1"></el-option>
              <el-option label="标签" value="2"></el-option>
              <el-option label="作者" value="3"></el-option>
              <el-option label="时间" value="4"></el-option>
            </el-select>
            <el-input v-model="input.value" class="input-with-select" v-if="input.select!=='4'"></el-input>
            <div class="input-with-select" v-else>
              <el-date-picker
                  v-model="input.timeValue"
                  type="daterange"
                  align="right"
                  unlink-panels
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :picker-options="pickerOptions">
              </el-date-picker>
            </div>
          </div>
        </el-col>
        <el-col :span="4" style="width: 200px">
          <el-button type="danger" icon="el-icon-minus" v-if="inputs.length > 1" circle @click="removeInput(index) "></el-button>
        </el-col>
      </el-row>
    </el-main>
    <el-footer >
      <el-row type="flex" justify="center" style="margin-bottom: 30px">
        <el-col :span="8">
          <el-button type="primary" icon="el-icon-plus" @click="addInput"></el-button>
        </el-col>
        <el-col :span="15">
          <el-button type="success" icon="el-icon-search" @click="search">搜搜你的</el-button>
        </el-col>
      </el-row>
      <el-row style="display: flex; flex-direction: column; align-items: center;">
        <SearchResult :result="searchResult" v-if="loading===false">
        </SearchResult>
        <div class="loading" v-if="loading">
          <el-skeleton :rows="15" animated class="skeleton-with"/>
        </div>
      </el-row>
    </el-footer>
  </el-container>
</template>


<script>
import axios from "axios";
import SearchResult from "@/components/Search/SearchResult.vue";

export default {
  components: {SearchResult},
  data() {
    return {
      loading: false,
      searchResult: [],
      inputs: [{
        timeValue: [],
        select: '',
        value: '' }],
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
    };
  },
  methods: {
    addInput() {
      this.inputs.push({ select: '', value: '' ,
        timeValue1: 0, timeValue2: 0,});
    },
    removeInput(index) {
      this.inputs.splice(index, 1);
    },
    search() {
      if(this.inputs.length === 1 && this.inputs[0].select === ''){
        this.$message({
          offset: 100,
          message: '请输入搜索条件',
          type: 'warning'
        });
        return;
      }
      this.loading = true;
      console.log('Search:');
      console.log(this.inputs);
      axios.post('/post/searchPost', this.inputs,{
        withCredentials: true,
      }).then(res => {
        this.loading = false;
        this.searchResult = res.data;
        console.log(res.data);
      }).catch(err => {
        console.log(err);
      });
      // Implement your search logic here
    },
  },
};
</script>


<style>
.input-with-select{
  background-color: #fff;
  flex-grow: 1;
}
</style>