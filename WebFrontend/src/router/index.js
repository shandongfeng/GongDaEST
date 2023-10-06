import Vue from 'vue'
import VueRouter from 'vue-router'
//子界面
import LoginView from '../views/LoginView.vue'
import MainView from '../views/MainView.vue'
import HomeView from '../views/HomeView.vue'
import GoodsManage from '../views/GoodsManage.vue'
import UserManage from '../views/UserManage.vue'
import BusinessView from '../views/BusinessView.vue'
import BuyerView from '../views/BuyerView.vue'

Vue.use(VueRouter)

//每个路由对象描述了一个路径和相应的组件，并进行映射
const routes = [
  {
    path: '/',
    redirect: '/login', //只要输入'/'，就跳转到home页面
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    // meta:{
    //   keepalive: false,
    // }
  },
  {
    path: '/main',
    name: 'main',
    component: MainView,
    redirect: '/user', //只要输入'/'，就跳转到指定页面
    children:[
      {path: '/home', component: HomeView},
      {path: '/goods', component: GoodsManage},
      {path: '/user', component: UserManage},
      {path: '/business',component: BusinessView},
      {path: '/buyer',component: BuyerView}
    ]
  },
  
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  
]


const router = new VueRouter({
  routes  //传入的数组参数
})

//必须把导航守卫放在new VueRouter下面
//import store from '@/store/index.js'
import Cookies from '@/utils/index.js'
router.beforeEach((to,from,next) =>{
  const jwt = Cookies.$cookies.get('token');
  console.log("router————","源地址：",from.path," 目的地址:",to.path);
  if(!jwt && (to.path !== '/' && to.path !== '/login')){
    if(from.path === '/login'){
      console.log(`————router————令牌错误，无法跳转到${to.path}`);
    }else{
      console.log("————router————令牌错误，返回登录界面")
      next('/login');  
    }    
  }else if(jwt && (to.path === '/' || to.path === '/login')){
    console.log("————router————令牌正确，无法回到登录界面，导入main界面");
    next('/main');
  }else{
    console.log(`————router————成功登录${to.path}`);
    next(); //随意跳转指定界面
  }
})

//暴露router实例，便于其他文件引入该实例
export default router
