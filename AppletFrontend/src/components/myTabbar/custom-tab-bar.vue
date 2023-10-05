<script setup lang="ts">
const selectedIndex = uni.getStorageSync('selectedIndex') || 0

const array = [
  {
    pagePath: 'pages/index/index',
    iconPath: '/static/tabs/home_default.png',
    selectedIconPath: '/static/tabs/home_selected.png',
    text: '首页',
  },
  {
    pagePath: 'pages/category/category',
    iconPath: '/static/tabs/category_default.png',
    selectedIconPath: '/static/tabs/category_selected.png',
    text: '分类',
  },
  {
    pagePath: 'pages/release/release',
    iconPath: '/static/tabs/release_default.png',
    selectedIconPath: '/static/tabs/release_selected.png',
    text: '',
  },
  {
    pagePath: 'pages/cart/cart',
    iconPath: '/static/tabs/cart_default.png',
    selectedIconPath: '/static/tabs/cart_selected.png',
    text: '购物车',
  },
  {
    pagePath: 'pages/my/my',
    iconPath: '/static/tabs/user_default.png',
    selectedIconPath: '/static/tabs/user_selected.png',
    text: '我的',
  },
]
function switchTab(item: any, index: number) {
  let url = '/' + item.pagePath
  uni.switchTab({ url })
  array.forEach((v, i) => {
    if (item.pagePath === v.pagePath) {
      uni.setStorageSync('selectedIndex', index)
    }
  })
}
</script>
<template>
  <view>
    <!--插槽，为下面的组件占位-->
    <view class="tab-content">
      <slot />
    </view>
    <!--tabbar容器-->
    <view class="tabbar">
      <view class="navigator">
        <view
          class="navigator-item"
          v-for="(item, index) in array"
          :key="item.pagePath"
          @click="switchTab(item, index)"
        >
          <!--###发布的图标样式仍需要自定义，需要在这里修改-->
          <img :src="item.iconPath" class="icon" v-if="selectedIndex !== index" />
          <img :src="item.selectedIconPath" class="icon" v-else />
          <text :class="['item-text', { 'text-active': selectedIndex === index }]">
            {{ item.text }}
          </text>
        </view>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
.tabbar {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 130rpx;
  z-index: 999;
  background: yellow;
  border-top: 2rpx solid white;
}
.navigator {
  display: flex;
  justify-content: space-between;
  width: 85%;
  margin: 0 auto;
  padding: 20rpx;
}

.navigator-item {
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: space-between;
  width: 50rpx;
  height: 100%;
}
.item-text {
  white-space: nowrap;
  margin-top: 6rpx;
  color: #777e86;
  font-size: 30rpx;
}

.text-active {
  color: #2e92fd !important;
}

.icon {
  width: 60rpx;
  height: 60rpx;
}
.icon-two {
  transform: translateY(-40rpx); /* 或者你想要的上移偏移量 */
  width: 75rpx;
  height: 75rpx;
}
</style>
