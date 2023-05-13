import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from 'axios'
import router from './router/index.js'
import * as ElementPlusIcons from '@element-plus/icons-vue'

const app = createApp(App)
// initial axios
axios.defaults.baseURL = "http://localhost:8088"
app.config.globalProperties.$http = axios
// import icon
for (const [key, component] of Object.entries(ElementPlusIcons)) {
  app.component(key, component)
}
// register ui
app.use(ElementPlus)
// register router
app.use(router)
app.mount('#app')
