<template>
    <el-container>

        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <HomeAside></HomeAside>
        </el-aside>
          <el-container>
              <el-header><HomeHeader></HomeHeader></el-header>
              <el-main><HomeMain></HomeMain></el-main>
          </el-container>
 
    </el-container>
</template>

<style>
  
  
  .el-aside {
    color: #333;
  }
</style>

<script>

import HomeAside from '../components/HomeAside.vue';
import HomeMain from '@/components/HomeMain.vue';
import HomeHeader from '@/components/HomeHeader.vue';

  export default {
    data() {
      
    
    },
    components: {
        HomeAside,
        HomeMain,
        HomeHeader
    }
  };
</script>