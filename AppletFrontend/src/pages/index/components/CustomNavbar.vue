<script setup lang="ts">
//获取导航边界到屏幕的距离
const { safeAreaInsets } = uni.getSystemInfoSync()
</script>

<template>
  <!--动态修改导航到屏幕顶部的距离-->
  <view class="CustomNavbar" :style="{ paddingTop: safeAreaInsets?.top + 'px' }">
    <!-- logo文字 -->
    <text class="logo">工大二手堂</text>
    <!--空行-->
    <text style="height: 10rpx" />
    <!-- 搜索条 -->
    <view class="search">
      <text class="icon-search">搜索商品</text>
      <text class="icon-scan"></text>
    </view>
  </view>
</template>

<style lang="scss">
/* 自定义导航条 */
.CustomNavbar {
  top: 0;
  left: 0;
  right: 0;
  height: 250rpx;
  display: flex;
  flex-direction: column;
  position: fixed;
  z-index: 999;
  background-image: url(@/static/background/navigator_bg.png);
  background-size: cover;
  .logo {
    padding-top: 10rpx;
    text-align: center;
  }
  .search {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 10rpx 0 26rpx;
    height: 64rpx;
    margin: 20rpx 20rpx;
    color: #fff;
    font-size: 28rpx;
    border-radius: 32rpx;
    background-color: rgba(255, 255, 255, 0.5);
  }
  .icon-search {
    &::before {
      margin-right: 10rpx;
    }
  }
  .icon-scan {
    font-size: 30rpx;
    padding: 15rpx;
  }
}
</style>
