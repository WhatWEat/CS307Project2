import Vue from 'vue'
import VueRouter from 'vue-router'
import {Message} from "element-ui";
import MainBBS from "@/components/MainBBS.vue";
import PersonCenter from "@/components/PersonCenter.vue";
import LoginReg from "@/components/LoginReg.vue";
import PostContent from "@/components/PostContent/PostContent.vue";
import Marked from "@/components/Person/Marked.vue";
import Like from "@/components/Person/Like.vue";
import Release from "@/components/Create/Release.vue";
import Share from "@/components/Person/Share.vue";
import myReply from "@/components/Create/myReply.vue";
import PersonCreate from "@/components/PersonCreate.vue";
import Subscribe from "@/components/Person/Subscribe.vue";

Vue.use(VueRouter)
export const router = new VueRouter({
      routes: [
        {path: '/', redirect: '/login'},
        {path: '/person', redirect: '/person/share'},
        {path: '/create', redirect: '/create/my-reply'},
        {path: '/login', component: LoginReg},
        {
          path: '/main', component: MainBBS, meta: {requiresAuth: true},
        },
        {
          path: '/person',
          component: PersonCenter,
          meta: {requiresAuth: true},
          children: [
            {
              path: 'mark',
              component: Marked
            },
            {
              path: 'like',
              component: Like
            }, {
              path: 'share',
              component: Share
            }, {
              path: 'subscribe',
              component: Subscribe
            }]
        },
        {
          path: '/create',
          component: PersonCreate,
          meta: {requiresAuth: true},
          children: [
            {
              path: 'my-post',
              component: Release
            },
            {
              path: 'my-reply',
              component: myReply
            },
          ]
        },
        {path: '/comment', component: Comment},
        {
          meta: {requiresAuth: true},
          path: '/post-list/:id',
          name: 'post-list',
          component: PostContent
        },
        {path: '/test', component: Marked},
      ],
      mode: 'history'
    }
)
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // 这个路由需要认证，检查是否已经登录
    // 你需要实现 `isUserLoggedIn` 函数来检查登录状态
    if (!isUserLoggedIn()) {
      Message({
        message: '请先登录',
        type: 'waring',
        duration: 5 * 1000,
        offset: 250
      })
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
    } else {
      next()
    }
  } else if (to.path === '/login' && isUserLoggedIn()) {
    next({
      path: '/person',
    })
  } else {
    next()
  }
})

// 你需要实现这个函数，检查 cookie 中是否存在 session_id
function isUserLoggedIn() {
  let cookieArray = document.cookie.split(';');
  for(let cookie of cookieArray) {
    if(cookie.includes("session_id")) {
      return true;
    }
  }
  return false;
}

export default router