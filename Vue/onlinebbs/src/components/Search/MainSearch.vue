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
            </el-select>
            <el-input v-model="input.value" class="input-with-select">
            </el-input>
          </div>
        </el-col>
        <el-col :span="4" style="width: 200px">
          <el-button type="danger" icon="el-icon-minus" v-if="inputs.length > 1" circle @click="removeInput(index) "></el-button>
        </el-col>
      </el-row>
    </el-main>
    <el-footer>
      <el-row type="flex" justify="center">
        <el-col :span="8">
          <el-button type="primary" icon="el-icon-plus" @click="addInput"></el-button>
        </el-col>
        <el-col :span="15">
          <el-button type="success" icon="el-icon-search" @click="search">搜搜你的</el-button>
        </el-col>
      </el-row>
    </el-footer>
  </el-container>
</template>


<script>
export default {
  data() {
    return {
      inputs: [{ select: '', value: '' }]
    };
  },
  methods: {
    addInput() {
      this.inputs.push({ select: '', value: '' });
    },
    removeInput(index) {
      this.inputs.splice(index, 1);
    },
    search() {
      console.log('Search:');
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