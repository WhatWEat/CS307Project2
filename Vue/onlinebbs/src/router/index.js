import Vue from 'vue'
import VueRouter from 'vue-router'
import MainBBS from "@/components/MainBBS.vue";
import PersonCenter from "@/components/PersonCenter.vue";
import LoginReg from "@/components/LoginReg.vue";
import PostContent from "@/components/PostContent/PostContent.vue";
import Test from "@/components/Test/test.vue";
import Marked from "@/components/Person/Marked.vue";
import Like from "@/components/Person/Like.vue";
import Release from "@/components/Person/Release.vue";
import Share from "@/components/Person/Share.vue";
Vue.use(VueRouter)
export const router = new VueRouter({
      routes: [
        {path: '/', redirect: '/login'},
        {path: '/person', redirect: '/person/release'},
        {path: '/login', component: LoginReg},
        {path: '/main', component: MainBBS},
        {
          path: '/person',
          component: PersonCenter,
          children: [
            {
              path: 'mark',
              component: Marked
            },
            {
              path: 'like',
              component: Like
            },{
              path: 'release',
              component: Release
            },{
              path: 'share',
              component: Share
            }]
        },
        {path: '/comment', component: Comment},
        {path: '/test', component: Marked},
        {
          path: '/post-list/:id',
          name: 'post-list',
          component: PostContent
        }
      ],
      mode: 'history'
    }
)
export default router