import {createRouter, createWebHistory} from 'vue-router';
import Vue from 'vue';
import MainBBS from "@/components/MainBBS.vue";
import PersonCenter from "@/components/PersonCenter.vue";
import LoginReg from "@/components/LoginReg.vue";

const routes = [
  {path: '/', redirect: "/login"},
  {path: '/login', component: LoginReg},
  {path: '/main', component: MainBBS},
  {path: '/person', component: PersonCenter}
]
const router = createRouter({
  history: createWebHistory(),
  routes
})
// router.beforeEach((to, from, next) => {
//     if (LoginReg.isLogin) {
//       next();
//     } else {
//       next('/login');
//     }
// });
export default router;