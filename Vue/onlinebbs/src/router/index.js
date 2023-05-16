import Vue from 'vue'
import VueRouter from 'vue-router'
import MainBBS from "@/components/MainBBS.vue";
import PersonCenter from "@/components/PersonCenter.vue";
import LoginReg from "@/components/LoginReg.vue";
import PostContent from "@/components/PostContent/PostContent.vue";
Vue.use(VueRouter)
export const router = new VueRouter({
      routes: [
        {path: '/', redirect: "/login"},
        {path: '/login', component: LoginReg},
        {path: '/main', component: MainBBS},
        {path: '/person', component: PersonCenter},
        {path: '/comment', component: Comment},
        {path: '/post-list/:id',
          name: 'post-list',
          component: PostContent}
      ],
      mode: 'history'
    }
)
export default router