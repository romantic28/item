import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import store from './store';
import { Post } from "@/api/http"
import comm from './utils/comm';
//挂载Post请求到我们vue的原型链上
Vue.prototype.$Post = Post;
Vue.prototype.$comm = comm; 


Vue.config.productionTip = false
Vue.use(ElementUI)

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
