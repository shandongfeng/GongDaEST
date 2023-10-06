<template>
    <div class="login">
        <div class="login-form">
            <el-form
                :model="loginForm" 
                ref="loginForm" 
                :rules="rules"
                label-width="100px"
                >
                <!--账号-->
                    <el-form-item label="账号" prop="username">
                        <!--自动填充-->
                        <el-input 
                            v-model="loginForm.username" 
                            autocomplete="off" 
                            placeholder="请输入账号">
                        </el-input>
                    </el-form-item>
                <!--密码-->   
                    <el-form-item label="密码" prop="password">
                        <el-input 
                            v-model="loginForm.password" 
                            autocomplete="off" 
                            placeholder="请输入密码"
                            >
                        </el-input>
                    </el-form-item>
                <!--登录和重置-->
                    <el-form-item>
                        <!--@click="submitForm('loginForm')"-->
                        <el-button type="primary" @click="submitForm()">登录</el-button>
                        <el-button @click="resetForm()">重置</el-button>
                    </el-form-item>
            </el-form>
        </div>
 
    </div>
</template>

<script>
//import axios from 'axios';


    export default{
        data(){
            return {
                //账号密码必须填写
                rules: {
                    //四项信息必须填写，否则报错
                    username:[{required: true, message: "请输入账号"}],
                    password:[{required: true, message: "请选择密码"}],
                },
                loginForm: {
                    username: '丰向凯',
                    password: '王书鑫',
                },
                
            }
        },
        methods:{
            submitForm() {
                // console.log("账号：" + this.loginForm.username);
                // console.log("密码：" + this.loginForm.password);

                ////this.$store.commit('setCode', 14);
                this.$router.push('/main');
                
                
                
                // axios.post(
                //  'http://localhost:8080/login',     //要访问的服务器的ip
                //  {
                //     username: this.loginForm.username, 
                //     password: this.loginForm.password
                //  })
                // .then(response => {
                //     //在这里处理服务器响应状态码,发送到vuex的store中
                //     if(response.code !== 0){     
                //         if(this.$cookies.isKey('token') === false){                
                //             this.$cookies.set('token',response.data.data);
                //             console.log("login———— 创建token成功");
                //         }   
                //         //使用store储存并验证登录状态，第一个参数是store的mutations中的方法名
                //         // this.$store.commit('setCode', response.data.code);
                //         //驱动路由
                //         this.$router.push('/main');
                //         console.log("login———— 登录校验成功");
                //     }
                //     else{
                //         console.log("login———— 登录校验失败");
                //     }
                // })
                // .catch(error => {
                //     // 在这里处理请求错误
                //     console.error(error);
                // });

                // fetch('http://localhost:8080/login', {
                //     method: 'POST',
                //     headers: {
                //         'Content-Type': 'application/json'
                //     },
                //     body: JSON.stringify({ username: this.loginForm.username, password: this.loginForm.password })
                //     })
                //     .then(response => response.json())
                //     .then(data => {
                //         console.log(data.code); // 在控制台打印响应数据
                //     })
                //     .catch(error => {
                //         console.error(error); // 在控制台打印请求错误信息
                // });

                },
                
        },
        created() {
            
        },
        mounted(){
        }
    }
    
</script>

<style lang="less" scoped>
html,body {
    margin: 0;
}
.login {
    /* 将form表单置于login容器中间 */
    display: flex;
    justify-content: center;
    align-items: center;
    /*背景图片居中*/
    background-image: url(../assets/img/dog.jpg);
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;

    height: 100vh;

};
.login-form {
    background-color: transparent; 
    .el-input {
        width: 300px;
    }
}



</style>