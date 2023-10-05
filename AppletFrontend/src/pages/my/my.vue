<script lang="ts">
import { defineComponent, ref } from 'vue'
//###持久化存储
import { memberStore } from '@/stores/modules/member'
import { myReleaseStore } from '@/stores/modules/myRelease'
//###导入自定义方法
import '@/utils/http'

export default defineComponent({
  data() {
    return {
      //本地用户信息缓存
      useMemberStore: memberStore(),
      //本地发布信息缓存
      useMyReleaseStore: myReleaseStore(),
      //表单信息
      formData1: [
        {
          title: '待付款',
          icon: 'wallet-filled',
          iconSize: 40,
        },
        {
          title: '待分享',
          icon: 'staff',
          iconSize: 40,
        },
        {
          title: '待发货',
          icon: 'person',
          iconSize: 40,
        },
        {
          title: '待收货',
          icon: 'auth',
          iconSize: 40,
        },
        {
          title: '评价',
          icon: 'chat',
          iconSize: 40,
        },
      ],
      formData2: [
        {
          title: '优惠卷',
          icon: 'gift',
          iconSize: 40,
        },
        {
          title: '我的发布',
          icon: 'paperplane-filled',
          iconSize: 40,
        },

        {
          title: '店铺关注',
          icon: 'shop',
          iconSize: 40,
        },
        {
          title: '售后退款',
          icon: 'chat',
          iconSize: 40,
        },
      ],
      //显示修改头像/昵称信息小窗口
      showSmallWindow: ref(false),
      //刷新总视图
      refreshWindow: ref(true),
    }
  },
  methods: {
    //点击注册/登录
    openLogin() {
      uni.navigateTo({ url: '../login/login' })
    },
    //表单点击处理
    clickOnForm(index: number) {
      let URL: string = ''
      switch (index) {
        case 0:
          URL = ''
          break
        case 1:
          break
        case 2:
          URL = ''
          break
        case 3:
          URL = ''
          break
        case 4:
          break
        case 5:
          break
        case 6:
          URL = 'myRelease'
          uni.navigateTo({ url: URL })
          break
        case 7:
          break
        case 8:
          break
      }
    },
    //修改头像昵称信息
    reviseUserInfo() {
      this.showSmallWindow = !this.showSmallWindow
    },
    //设置按钮
    clickOnSetting() {
      console.log('我的: 点击设置按钮')
    },
    //退出登录
    exit() {
      if (this.useMemberStore.profile) {
        //清除用户本地缓存
        console.log('我的: 清除本地缓存并跳转到登录界面')
        this.useMemberStore.clearProfile()
        //清除发布缓存
        this.useMyReleaseStore.clearProfile()
        this.openLogin()
      }
    },
    //刷新总视图
    RefreshWindow() {
      console.log('我的: 稍等一会,马上刷新总视图')
      this.refreshWindow = false
      setTimeout(() => {
        this.refreshWindow = true
      }, 500)
    },
  },
  created() {
    if (this.useMemberStore.profile?.statusCode == 404) {
      console.log('我的: 当前无登录状态')
      this.useMemberStore.clearProfile()
    }
  },
  onShow() {
    console.log('########## 我的——onShow ##########')
    this.RefreshWindow()
  },
})
</script>

<template>
  <view v-if="refreshWindow">
    <!--注册/登录-->
    <view v-if="!useMemberStore.profile" class="no-login-view" @click="openLogin">
      <text>注册/登录</text>
    </view>
    <!--已登录-->
    <view v-else class="yes-login-view">
      <image :src="useMemberStore.profile.data.avatar" mode="scaleToFill" class="avatar-view" />
      <view style="display: flex; flex-direction: column">
        <text style="margin: 10rpx 0 0 45rpx; font-size: 40rpx">{{
          useMemberStore.profile.data.name
        }}</text>
        <button
          @click="reviseUserInfo"
          size="mini"
          plain
          hover-class="button-hover"
          class="revise-btn"
        >
          修改头像昵称
        </button>
      </view>
      <!--右下角设置-->
      <button
        plain
        size="mini"
        hover-class="button-hover"
        @click="clickOnSetting"
        class="setting-btn"
      >
        设置
      </button>
    </view>
    <!--form表单1-->
    <view class="form1-view">
      <view
        v-for="(item, index) in formData1"
        :key="index"
        @tap="clickOnForm(index)"
        class="form1-item"
      >
        <uni-icons :type="item.icon" color="default" :size="item.iconSize" />
        <text>{{ item.title }}</text>
      </view>
    </view>
    <!--钱包-->
    <view class="wallet-view">
      <text>我的钱包</text>
      <view>
        <text style="">查看余额</text>
        <uni-icons type="right" size="17" />
      </view>
    </view>
    <!--form表单2-->
    <view class="form2-view">
      <view
        v-for="(item, index) in formData2"
        :key="index"
        @tap="clickOnForm(formData1.length + index)"
        class="form2-item"
      >
        <uni-icons :type="item.icon" color="default" :size="item.iconSize" />
        <text>{{ item.title }}</text>
      </view>
    </view>
    <!--修改信息和退出按钮-->
    <view class="revise-and-exit-btn-view">
      <button type="primary" hover-class="button-hover">切换账号</button>
      <button @tap="exit" type="warn" hover-class="button-hover">退出登录</button>
    </view>
    <!--切换账号小窗口-->
    <view v-if="showSmallWindow" class="switchWindow-view">
      <text style="font-size: 130rpx">待开发</text>
    </view>
  </view>
</template>

<style lang="scss">
//##########注册/登录##########
//未登录样式
.no-login-view {
  height: 150rpx;
  width: 100vw;
  display: flex;
  font-size: 50rpx;
  align-items: center;
  justify-content: center;
  //background-color: aqua;
}
//已登录样式
.yes-login-view {
  position: relative;
  height: 150rpx;
  width: 100vw;
  display: flex;
  //background-color: aqua;
}
//头像样式
.avatar-view {
  border-radius: 50%;
  margin-left: 30rpx;
  margin-right: 20rpx;
  height: 150rpx;
  width: 150rpx;
  //background-color: brown;
}
//修改头像昵称
.revise-btn {
  margin-top: 10rpx;
  border-radius: 10%;
}
//设置按钮样式
.setting-btn {
  position: absolute;
  bottom: 0;
  right: 0;
}

//##########表单1##########
.form1-view {
  margin-top: 50rpx;
  height: 150rpx;
  width: 100vw;
  display: flex;
  flex-direction: wrap;
  font-size: 30rpx;
  //background-color: rgb(75, 240, 9);
}
//表单中的item
.form1-item {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  flex-grow: 1;
  margin-bottom: 20rpx;
}
//##########表单2##########
.form2-view {
  margin-top: 50rpx;
  height: 150rpx;
  width: 100vw;
  display: flex;
  flex-direction: wrap;
  font-size: 30rpx;
  //background-color: rgb(75, 240, 9);
}
//表单中的item
.form2-item {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  flex-grow: 1;
  margin-bottom: 20rpx;
}
//##########余额##########
.wallet-view {
  margin-top: 50rpx;
  height: 100rpx;
  width: 100vw;
  display: flex;
  justify-content: space-between;
  align-items: center;
  //background-color: aquamarine;
}
//##########切换和退出登录按钮视图样式
.revise-and-exit-btn-view {
  margin-top: 150rpx;
  height: 300rpx;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  //background-color: blanchedalmond;
}
//##########切换窗口样式
.switchWindow-view {
  position: fixed;
  top: 50%;
  left: 10%;
  right: 10%;
  transform: translate(0, -50%);
  height: 700rpx;
  display: flex;
  //background-color: red;
}
</style>
