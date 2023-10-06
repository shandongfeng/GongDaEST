<template>
    <el-menu default-active="1-4-1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse">
        <h3>鑫晨科技n</h3>
        <!--没有子菜单的循环遍历展示组件-->    
        <el-menu-item @click="menuClick(item)" v-for="item in noChildren" :key="item.name" :index="item.name">
            <i :class="['el-icon-' + item.icon]"></i>
            <span slot="title">{{ item.label }}</span>
        </el-menu-item>
        <!--有子菜单的循环遍历展示组件-->        
        <el-submenu v-for="item in hasChildren" :key="item.label" :index="item.label">
            <template slot="title">
                <i :class="['el-icon-' + item.icon]"></i>
                <span slot="title">{{ item.label }}</span>
            </template>

            <el-menu-item-group v-for="subItem in item.children" :key="subItem.name">
                <!-- <span slot="title">分组一</span> -->
                <el-menu-item @click="menuClick(subItem)" :index="subItem.name">{{ subItem.label }}</el-menu-item>                  
            </el-menu-item-group>           
            
        </el-submenu>      

    </el-menu>
</template>

<style lang="less" scoped>
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
  .el-menu {
    height: 100vh;
    h3 {
        text-indent: 25px;
        color: red,
    }
  }
</style>

<script>

  export default {
    data() {
      return {
        isCollapse: false,  //左侧栏收缩
        menuData: [
            {
                path: '/home',
                name: 'home',
                label: '首页',
                icon: 's-home',
                //url: 
            },
            {
                path: '/goods',
                name: 'goods',
                label: '商品管理',
                icon: 'video-play',
                //url:
            },
            {
                path: '/user',
                name: 'user',
                label: '用户管理',
                icon: 'user',
                //url:
            },
            {
                label:'其他',
                icon: 'other',
                children:[
                    {
                        path: '/business',
                        name: 'business',
                        label: '商家',
                        icon: 'sell',
                        //url:
                    },
                    {
                        path: '/buyer',
                        name: 'buyer',
                        label: '买家',
                        icon: 's-custom',
                        //url:
                    }
                ]
            }
        ],
      }
         
    },

    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },

      menuClick(item) {
        //console.log(item);
        //路径重复跳转会报错，也要防止重定向————route是获取，router是跳转
        if(this.$route.path !== item.path && !(this.$route.path === '/home' && (item.path === '/'))){
            this.$router.push(item.path);
        }
      }
    },
    computed: {
        noChildren(){
            return this.menuData.filter(item => !item.children)
        },
        hasChildren(){
            return this.menuData.filter(item => item.children)
        }
    }
  }
</script>