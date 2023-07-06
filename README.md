# CS307Project2
## 使用方法
使用springboot和vue
首先mvn install来安装springboot相关依赖
然后再Vue文件夹内npm install来安装vue相关依赖
## 具体实现
### 架构
主要是用vue在前端绘制页面，把restful请求发送给后端springboot
springboot通过mybatis plus对数据库进行处理
在springboot的**Controller**层接受请求，**Service**层处理请求，在**Mapper**处进行数据持久化
### 实现了登录
