import { createSSRApp } from 'vue'
import pinia from './stores'
import App from './App.vue'
//自定义组件
import MyTabbar from './components/myTabbar/custom-tab-bar.vue'
//导出设置
export function createApp() {
  //初始化app
  const app = createSSRApp(App)
  //设置自定义组件作为全局变量
  app.component('myTabbar', MyTabbar)
  //使用持久化存储
  app.use(pinia)
  //返回app实例
  return {
    app,
  }
}
