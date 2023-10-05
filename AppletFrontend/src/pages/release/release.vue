<script setup lang="ts">
const { safeAreaInsets } = uni.getSystemInfoSync() //获取导航边界到屏幕的距离
import { memberStore } from '@/stores/modules/member'
const useMemberStore = memberStore()
//点击拍照进入信息填写页
const openDrawer = () => {
  //console.log('跳转到商品发布页')
  //如果当前用户已登录，可以发布商品
  if (useMemberStore.profile) {
    uni.navigateTo({
      url: '../login/productLogin',
    })
  }
  //如果当前用户未登录，请先获取用户信息
  else {
    uni.navigateTo({
      url: '../login/login',
      success: (success) => {
        uni.navigateTo({
          url: '../login/productLogin',
        })
      },
    })
  }
}
</script>

<template>
  <view class="release-page">
    <view>
      <uni-notice-bar text="感谢长春工业大学" showIcon showClose scrollable />
    </view>
    <view class="cartoon-container">
      <view class="cartoon-view">
        <!--背景图-->
        <!-- <image class="image" src="../../static/background/cartoon_image.png" mode="scaleToFill" /> -->
        <view class="camera" @click="openDrawer">
          <!--拍摄图片的图标-->
          <image src="../../static/background/camera.png" mode="scaleToFill" />
        </view>
        <view class="text">
          <text style="font-size: 50rpx; color: aqua">点击图标，上传图片</text>
        </view>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
.release-page {
  display: flex;
  flex-direction: column;
  height: 100vh;
}
.top-navbar {
  height: 200rpx;
  background-color: brown;
}
.data-container {
  height: 300rpx;
  //对齐
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;

  //background-color: chocolate;
}
.cartoon-container {
  flex-grow: 1;
  //background-color: blue;
}
.cartoon-view {
  //固定
  //position: relative;
  //对齐
  display: flex;
  flex-direction: column;
  align-items: center;

  margin-top: 100rpx;
  height: 700rpx;
  //background-color: chartreuse;
}
.image {
  position: absolute; //固定图片
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
.camera {
  margin-top: 200rpx;
  height: 150rpx;
  width: 150rpx;
  z-index: 66;
  //background-color: aqua;
}
.text {
  padding-top: 40rpx;
  height: 100rpx;
  width: 100vw;

  text-align: center;

  color: coral;
  z-index: 66;
}
</style>
