<template>
  <div>
    <!--路由出口,如果不添加则无法使用路由跳转界面-->
    <router-view>
      
    </router-view>
  </div>
  
</template>

<script>
  export default{
    data() {
        return {
            message: "奥力给"
        };
    },
}
</script>

<style lang="less" scoped>
  html,body {
    margin: 0;
    padding: 0;
  }
</style>
