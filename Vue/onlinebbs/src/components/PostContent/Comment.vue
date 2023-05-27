<template>
  <div class="comment">
    <div>
      <div v-clickoutside="hideReplyBtn" @click="inputFocus" class="my-reply">
        <el-avatar class="header-img" :size="40" :src="avatar"></el-avatar>
        <div class="reply-info">
          <div
              tabindex="0"
              contenteditable="true"
              id="replyInput"
              spellcheck="false"
              placeholder="输入评论..."
              class="reply-input"
              @focus="showReplyBtn"
              @input="onDivInput($event)"
          ></div>
        </div>
        <div class="reply-btn-box" v-show="btnShow">
          <span style="margin-left: 20px;">
            <el-checkbox v-model="anonymous">是否匿名</el-checkbox>
            <el-button
                          class="reply-btn"
                          size="medium"
                          @click="sendComment"
                          type="primary"
                      >发表评论
          </el-button
          >
          </span>
        </div>
      </div>
      <div
          v-for="(item, i) in comments"
          :key="i"
          class="author-title reply-father"
      >
        <el-avatar class="header-img" :size="40" :src="item.avatar"></el-avatar>
        <div class="author-info">
          <span class="author-name" v-if="item.anonymous===false">{{ item.username }}</span>
          <span class="author-name" v-else>匿名</span>
          <span class="author-time">{{ item.replying_time | formatDate}}</span>
        </div>
        <div class="icon-btn">
          <span @click="showReplyInput(i, -1 ,item.username, item.id)">
            <i class="iconfont el-icon-s-comment"></i>{{ item.commentNum }}
          </span>
        </div>
        <div class="talk-box">
          <p>
            <span class="reply"> {{ item.content }}</span>
          </p>
        </div>
        <div class="reply-box">
          <div v-for="(reply, j) in item.son" :key="j" class="author-title">
            <el-avatar
                class="header-img"
                :size="40"
                :src="reply.avatar"
            ></el-avatar>
            <div class="author-info">
              <span class="author-name" v-if="reply.anonymous===false">{{ reply.username }}</span>
              <span class="author-name" v-else>匿名</span>
              <span class="author-time">{{ reply.replying_time | formatDate}}</span>
            </div>
            <div class="icon-btn">
              <span @click="showReplyInput(i, j, reply.username, reply.id)"
              ><i class="iconfont el-icon-s-comment"></i
              ></span
              >
            </div>
            <div class="talk-box">
              <p >
                回复<span> @{{ reply.toReply }}: </span>
                <span class="reply"> {{ reply.content }}</span>
              </p>
            </div>
            <div class="reply-box"></div>
          </div>
        </div>
        <div v-show="_inputShow(i)" class="my-reply my-comment-reply">
          <el-avatar class="header-img" :size="40" :src="avatar"></el-avatar>
          <div class="reply-info">
            <div
                tabindex="0"
                contenteditable="true"
                spellcheck="false"
                :placeholder="placeholder"
                @input="onDivInput($event)"
                class="reply-input reply-comment-input"
            ></div>
          </div>
          <div class="reply-btn-box">
            <el-checkbox v-model="anonymous">是否匿名</el-checkbox>
            <el-button
                class="reply-btn"
                size="medium"
                @click="sendCommentReply(i,item)"
                type="primary"
            >发表评论
            </el-button
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const clickoutside = {
  // 初始化指令
  bind(el, binding, vnode) {
    function documentHandler(e) {
      // 这里判断点击的元素是否是本身，是本身，则返回
      if (el.contains(e.target)) {
        return false;
      }
      // 判断指令中是否绑定了函数
      if (binding.expression) {
        // 如果绑定了函数 则调用那个函数，此处binding.value就是handleClose方法
        binding.value(e);
      }
    }

    // 给当前元素绑定个私有变量，方便在unbind中可以解除事件监听
    el.vueClickOutside = documentHandler;
    document.addEventListener("click", documentHandler);
  },
  update() {
  },
  unbind(el, binding) {
    // 解除事件监听
    document.removeEventListener("click", el.vueClickOutside);
    delete el.vueClickOutside;
  },
};
export default {
  name: "Comment", // 组件名称
  props: ['postid'],
  data() {
    return {
      anonymous: false,
      placeholder: "", // 回复者名称
      btnShow: false,
      sonIndex: -1,
      index: "0",
      replyComment: "", // 评论输入内容
      username: "jaychou", // 登录的用户名
      avatar: "https://s1.ax1x.com/2022/06/10/Xc9lUf.png", // 登录用户头像
      userId: "", // 登录用户id
      parentName: "", // 回复的对象（父评论）用户名
      parentId: 0, // 父id
      itemId: "6666", // 文章等等id
      comments: [
      ],
    };
  },
  directives: {clickoutside},
  created() {
    // this.myrefresh();
    // 注意this
    // 获取用户登录信息
    this.getLoginUser();
  },
  filters: {
    formatDate(value) {
      let date = new Date(value);

      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();

      return year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
    }
  },
  methods: {
    myrefresh() {
      axios.get(
          "/reply/findTopReplyByPost/" + this.postid,{
            withCredentials: true
          }).then(
          (resp) => {
            let list = resp.data;
            console.log(list);
            this.comments = list;
          });
    },
    getLoginUser() {
      this.avatar = "https://s1.ax1x.com/2022/06/10/Xc9lUf.png";
      this.myrefresh() // 刷新
    },
    inputFocus() {
      var replyInput = document.getElementById("replyInput");
      replyInput.style.padding = "8px 8px";
      replyInput.style.border = "2px solid #409EFF";
      replyInput.focus();
    },
    showReplyBtn() {
      this.btnShow = true;
    },
    hideReplyBtn() {
      this.btnShow = false;
      replyInput.style.padding = "10px";
      replyInput.style.border = "none";
    },
    showReplyInput(i, j, name, id) {
      console.log(i, j, name, id);
      this.sonIndex = j;
      this.comments[this.index].inputShow = false;
      this.index = i;
      this.comments[i].inputShow = true;
      this.parentName = name;
      this.parentId = id;
      this.placeholder = "回复 @";
      //alert(i)
    },
    _inputShow(i) {
      return this.comments[i].inputShow;
    },
    sendComment() {
      // 父评论
      if (!this.replyComment) {
        this.$message({
          showClose: true,
          type: "warning",
          message: "评论不能为空",
        });
      } else {
        axios.post(`/reply/replyToPost/${this.postid}/${this.replyComment}/${this.anonymous}`, null,
            {
              withCredentials: true
            })
        .then((response) => {
          console.log(response.data);
            this.$message.success("评论成功!");
        })
        .finally(() => {
          this.$router.go(0);
          // this.myrefresh();
        });
        //
        document.getElementById("replyInput").innerHTML = "";
        this.replyComment = "";
      }
    },
    sendCommentReply(i,item) {
      // 子回复提交
      if (!this.replyComment) {
        this.$message({
          showClose: true,
          type: "warning",
          message: "评论不能为空",
        });
      } else {
        // 组装请求数据
        //
        let toReply = item.reply_id;
        if(this.sonIndex !== -1){
          toReply = item.son[this.sonIndex].reply_id;
        }
        console.log(toReply);
        axios
        .post(`reply/replyToReply/${toReply}/${this.replyComment}/${this.anonymous}`, null,{
          withCredentials:true
        })
        .then((response) => {
            console.log(this.anonymous);
            this.$message.success("回复成功！");
        })
        .finally(() => {
          this.$router.go(0);
          // this.myrefresh();
        });

        this.replyComment = "";
        document.getElementsByClassName("reply-comment-input")[i].innerHTML =
            "";
      }
    },
    onDivInput: function (e) {
      this.replyComment = e.target.innerHTML;
    },

  },
};
</script>

<style>
.comment {
  width: 1000px;
  margin: 0 auto;
  font-family: PingFang SC, HarmonyOS_Regular, Helvetica Neue, Microsoft YaHei,
  sans-serif;
}

.my-reply {
  padding: 10px;
  background-color: #fafbfc;
}

.my-reply .header-img {
  display: inline-block;
  vertical-align: top;
}

.my-reply .reply-info {
  display: inline-block;
  margin-left: 5px;
  width: 90%;
}

@media screen and (max-width: 1200px) {
  .my-reply .reply-info {
    width: 80%;
  }
}

.my-reply .reply-info .reply-input {
  min-height: 20px;
  line-height: 22px;
  padding: 10px 10px;
  color: #ccc;
  background-color: #fff;
  border-radius: 5px;
}

.my-reply .reply-info .reply-input:empty:before {
  content: attr(placeholder);
}

.my-reply .reply-info .reply-input:focus:before {
  content: none;
}

.my-reply .reply-info .reply-input:focus {
  padding: 8px 8px;
  border: 2px solid #409eff;
  box-shadow: none;
  outline: none;
}

/* .reply-info>div .reply-input:focus{
    border: 2px solid #409EFF;
} */
.my-reply .reply-btn-box {
  height: 25px;
  margin: 10px 0;
}

.my-reply .reply-btn-box .reply-btn {
  position: relative;
  float: right;
  margin-right: 15px;
}

.my-comment-reply {
  margin-left: 50px;
}

.my-comment-reply .reply-input {
  /*width: flex;*/
  display: flex;
}

.author-title:not(:last-child) {
  border-bottom: 1px solid rgba(178, 186, 194, 0.3);
}

.author-title {
  padding: 10px;
}

.author-title .header-img {
  display: inline-block;
  vertical-align: top;
}

.author-title .author-info {
  display: inline-block;
  margin-left: 5px;
  width: 60%;
  height: 40px;
  line-height: 20px;
}

.author-title .author-info > span {
  display: block;
  cursor: pointer;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.author-title .author-info .author-name {
  color: #303133;
  font-size: 18px;
  font-weight: 500;
}

.reply-box .talk-box {
  color: #606266;
}

.reply-box .talk-box span {
  color: #6298ce;
}

.author-title .author-info .author-time {
  font-size: 14px;
}

.author-time {
  color: #606266;
}

.author-title .icon-btn {
  width: 30%;
  padding: 0 !important;
  float: right;
}

@media screen and (max-width: 1200px) {
  .author-title .icon-btn {
    width: 20%;
    padding: 7px;
  }
}

.author-title .icon-btn > span {
  cursor: pointer;
}

.author-title .icon-btn .iconfont {
  margin: 0 5px;
}

.author-title .talk-box {
  margin: 0 50px;
}

.author-title .talk-box > p {
  margin: 0;
}

.author-title .talk-box .reply {
  font-size: 16px;
  color: #606266;
}

.author-title .reply-box {
  margin: 10px 0 0 50px;
  background-color: #efefef;
}

/* 点赞图标颜色 */
.likeIcon {
  color: #40a0ff;
}
</style>