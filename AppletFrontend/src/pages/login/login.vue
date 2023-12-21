<script lang="ts">
/**
 * 引用官方库及自定义库
 */
import { ref, defineComponent } from 'vue'
//###持久化导入
import { memberStore } from '@/stores/modules/member' //验证此时用户登录状态
//###自定义函数导入
import { register_and_getToken } from '@/services/login'
import { imageHandle } from '@/utils/imageHandle'
//###类型导入
import type { loginType, loginResultType } from '@/types/loginType'

export default defineComponent({
  data() {
    return {
      /**
       * 响应式引用，主要用于渲染界面
       */
      //打开注册小窗口
      openSmallWindow_register: ref(false),
      //打开登录小窗口
      openSmallWindow_login: ref(false),
      //存储上传照片的底层模板图以及后续添加的图片
      imagePath: ref('../../static/background/huisetouxiang.png'),
      /**
       * 持久化引用
       */
      //登录用户信息
      useMemberStore: memberStore(),
      /**
       * 容器
       */
      //用户请求info
      requestInfo: {} as loginType,
    }
  },
  methods: {
    //打开注册小窗体
    async openSmallWindowRegister() {
      console.log('注册/登录页面: 打开注册小窗口')
      this.openSmallWindow_register = false
      setTimeout(() => {
        this.openSmallWindow_register = true //开启小窗体
      }, 200)
      //如果当前不是登录状态，注册前需要先向微信后台发送请求，获取openid等数据
      const response = await uni.login()
      console.log(
        '注册/登录页面: login: 打开此界面自动调用uni.login,返回值 = ' + JSON.stringify(response),
      )
      this.requestInfo.requestCode = 0
      this.requestInfo.code = response.code
      this.requestInfo.errMsg = response.errMsg
      this.requestInfo.token = ''
    },
    //上传头像
    async uploadAvatar() {
      /**
       * 调用自定义图片处理函数
       * @returns
       *    1.path:本地图片缓存路径
       *    2.data:base64类型的图片数据
       */
      let result = await imageHandle()
      this.imagePath = result.path //渲染头像
      this.requestInfo.avatar = result.data //将头像数据发送的请求体
    },
    //点击注册窗口中的确定按钮
    async clickOnRegisterConfirm() {
      //用户信息是否填写完整,姓名。头像、手机号都填写完整
      if (!this.requestInfo.name || !this.requestInfo.avatar || !this.requestInfo.phoneNumber) {
        uni.showToast({ icon: 'error', title: '数据不能为空' })
        return
      }
      //用户信息上传并返回token
      let response = await register_and_getToken<loginResultType>(this.requestInfo)
      if (response.statusCode == 404) {
        uni.showToast({ title: '注册失败', icon: 'error' })
        return
      }
      //解析用户注册返回的信息
      console.log('注册/登录页面: register: 服务器响应成功：' + JSON.stringify(response))
      /**
       * 图片存储
       * 标注：客户端存储图片在服务端的路径
       * 操作：
       *      1.如果客户不使用服务端图片，可直接将之前上传压缩过的图片在微信后台的地址放在持久化中存储使用，如下所示
       *      2.如果使用服务端图片，需要自己单独在客户端实现一个请求（向服务端获取图片信息）
       *        因为服务端的应答包含的是图片在服务端的路径，在客户端无法预览，需要再次请求获取图片信息
       */
      //将注册信息保存到storage
      this.useMemberStore.setProfile(response)
      uni.showToast({ title: '注册成功', icon: 'success' })
      //跳转到上一级界面
      uni.navigateBack()
    },
    /**
     * 登录
     */
    //打开登录小窗口
    openSmallWindowLogin() {
      console.log('注册/登录页面: 打开登录小窗口')
      this.openSmallWindow_login = false
      setTimeout(() => {
        this.openSmallWindow_login = true
      }, 500)
    },
    //点击登录确认按钮
    async clickOnLoginConfirm() {
      if (!this.requestInfo.phoneNumber) {
        uni.showToast({ title: '请输入手机号', icon: 'error' })
        return
      }
      this.requestInfo.requestCode = 1
      //等待服务器成功返回信息
      let response = await register_and_getToken<loginResultType>(this.requestInfo)
      //如果查询到老用户信息则储存在本地
      if (response.statusCode == 404) {
        console.log('注册/登录页面: 服务器响应失败')
        return
      }
      //console.log('login: 服务器响应成功 = ' + JSON.stringify(response))
      this.useMemberStore.setProfile(response)
      uni.showToast({ title: '登录成功', icon: 'success' })
      //关闭登录小窗口
      this.openSmallWindow_login = false
      //跳转到上一级界面
      uni.navigateBack()
    },
    //注册/登录窗口取消按钮
    cancelClicked() {
      console.log('注册/登录页面: 点击取消按钮')
      this.openSmallWindow_register = false
      this.openSmallWindow_login = false
    },
  },

  /**
   * 验证当前登录状态
   */
  async onShow() {
    console.log('########## login-onShow ##########')
    //如果当前是登录状态，返回
    if (this.useMemberStore.profile !== undefined) {
      console.log('注册/登录页面: 当前是登录状态')
    }
  },
})
</script>

<template>
  <!--总容器-->
  <view class="login-container">
    <view v-if="!openSmallWindow_register && !openSmallWindow_login" class="new_and_old_btn">
      <button @tap="openSmallWindowRegister" style="width: 400rpx; font-size: 50px">新用户</button>
      <button @tap="openSmallWindowLogin" style="width: 400rpx; font-size: 50px">老用户</button>
    </view>
    <view v-else style="flex: 1">
      <!--注册小窗口-->
      <view v-if="openSmallWindow_register" class="smallWindow_register">
        <text>更新个人信息</text>
        <image :src="imagePath" mode="scaleToFill" class="circle_image" />
        <button @click="uploadAvatar" type="primary" hover-class="button-hover">
          点击获取微信头像
        </button>
        <input
          v-model="requestInfo.name"
          placeholder="点击输入昵称"
          placeholder-class="input-placeholder"
        />
        <input
          v-model="requestInfo.phoneNumber"
          placeholder="点击输入手机号"
          placeholder-class="input-placeholder"
        />
        <view class="verifyCode-view">
          <input placeholder="请输入验证码" placeholder-class="input-placeholder" />
          <button style="width: 250rpx; font-size: 30rpx" hover-class="button-hover">
            发送验证码
          </button>
        </view>
        <view class="below_two_btn">
          <button @tap="cancelClicked" hover-class="button-hover">取消</button>
          <button @tap="clickOnRegisterConfirm" type="warn" hover-class="button-hover">确定</button>
        </view>
      </view>
      <!--登录小窗口-->
      <view v-if="openSmallWindow_login" class="smallWindow_login">
        <input
          v-model="requestInfo.phoneNumber"
          placeholder="请输入手机号"
          placeholder-class="input-placeholder"
        />
        <view class="verifyCode-view">
          <input placeholder="请输入验证码" placeholder-class="input-placeholder" />
          <button style="width: 250rpx; font-size: 30rpx" hover-class="button-hover">
            发送验证码
          </button>
        </view>
        <view class="login_ok_and_cancel">
          <button @tap="cancelClicked" hover-class="button-hover">取消</button>
          <button @tap="clickOnLoginConfirm" type="warn" hover-class="button-hover">确定</button>
        </view>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
//登录注册总容器
.login-container {
  display: flex;
  width: 100vw;
}
//新老用户按钮
.new_and_old_btn {
  flex: 1;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  //background-color: aqua;
}
//##########注册小窗体##########
.smallWindow_register {
  position: absolute;
  top: 20%;
  left: 10%;
  height: 800rpx;
  width: 600rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  justify-content: space-around; //使每个水平子组件间隔分布
  border-radius: 10px; /* 设置圆角半径 */
  background-color: aquamarine;
}
//圆形框
.circle_image {
  width: 300rpx;
  height: 300rpx;
  border-radius: 50%;
  background-color: red;
}
//取消和确认按钮
.below_two_btn {
  display: flex;
  width: 600rpx;
  //background-color: rgb(60, 231, 26);
}

//##########登录小窗口##########
.smallWindow_login {
  position: absolute;
  top: 25%;
  left: 10%;
  height: 30%;
  width: 85%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around; //使每个水平子组件间隔分布
  text-align: center;
  border-radius: 10%;
  background-color: coral;
}
//验证码
.verifyCode-view {
  height: 100rpx;
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  //background-color: aquamarine;
  text-align: center;
}
//确认/取消按钮
.login_ok_and_cancel {
  display: flex;
  flex-direction: row;
  bottom: 0;
  width: 600rpx;
}
</style>
