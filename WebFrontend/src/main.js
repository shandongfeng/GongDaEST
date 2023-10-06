import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store';

// import VueCookies from 'vue-cookies';
import utils from './utils'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';


Vue.config.productionTip = false; //设置生产环境的提示信息为 false，以隐藏 Vue 的一些开发提示。
Vue.use(ElementUI); //注册全局插件，使所有文件都能使用图形化界面
// Vue.use(VueCookies);


// router.beforeEach((to,from,next) =>{
//   const isLog = VueCookies.get('token').isLog;
//   console.log("登录信息验证码：",isLog," 源地址：",from.path," 目的地址",to.path);
//   if(!store.state.code && (to.path !== '/' && to.path !== '/login')){
//     next('/login');
//     console.log("没有权限,重新登录")
//   }else if(store.state.code && (to.path === '/' || to.path === '/login')){
//     next('/main');
//   }else{
//     next(); //随意跳转指定界面
//   }
// })




new Vue({
  utils,
  store,
  router,   //挂载路由
  render: h => h(App)
}).$mount('#app')

