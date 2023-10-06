<template>
    <div class="header">
        <!--面包屑-->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="/">活动管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>活动列表</el-breadcrumb-item>
            <el-breadcrumb-item>活动详情</el-breadcrumb-item>
        </el-breadcrumb>
        
        <el-dropdown @command="exit" :hide-on-click="true">
            <!--头像-->
            <el-row>
                <el-col>
                    <div class="demo-basic--circle">
                        <div class="block">
                            <el-avatar :size="50"></el-avatar>
                        </div>
                    </div>
                </el-col>
            </el-row>
            <!--下拉菜单-->
            <el-dropdown-menu slot="dropdown" :hide-on-click="true">
                <el-dropdown-item command="退出">退出</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>


    </div>
   
</template>

<style lang="less" scoped>
    .header{
        background-color: rgb(94, 145, 239);       
        height: 60px;
        width: 85vw;

        display: flex; 
        justify-content: space-between;
    }
    .el-breadcrumb {
        font-size: 17px;
        line-height: 5;
    }
    .el-row {
        margin-top: 5px;
        margin-right: 5px;
    }
    .el-dropdown-menu {
        color: cornflowerblue;
    }
</style>

<script>
export default {
    data() {
        return {}
    },
    methods: {
        exit(command) {
            console.log('刚才点击的菜单选项：'+command);
            // this.$store.commit('setCode', 0);
            this.$cookies.remove('token');
            this.$router.push('/login');
            window.location.reload();   //刷新界面
        }
    },
    components: {
        
    }
}
</script>