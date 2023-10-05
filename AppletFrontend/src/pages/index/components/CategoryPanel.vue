<script setup lang="ts">
import { categoryLabelIndexStore } from '@/stores/modules/category'
//分类标签
const useCategoryLabelIndexStore = categoryLabelIndexStore()
const nameList = ['居家', '美食', '服饰', '母婴', '个护', '严选', '数码', '运动', '办公', '其他']
const clickOnLabel = (index: number) => {
  useCategoryLabelIndexStore.setProfile(index)
  uni.switchTab({ url: '../category/category' })
}
</script>

<template>
  <!--可以使用阿里图标库https://www.iconfont.cn/-->
  <!-- <uni-section> 宫格上面还有一个text-line标题-->
  <view style="height: 250rpx">
    <uni-grid :column="5" :highlight="true">
      <uni-grid-item v-for="(item, index) in nameList" :key="index">
        <view @tap="clickOnLabel(index)" class="grid-item-box">
          <!-- <uni-icons type="heart-filled" color="red" size="40" /> -->
          <text class="text">{{ item }}</text>
        </view>
      </uni-grid-item>
    </uni-grid>
  </view>
</template>

<style lang="scss">
.uni-grid {
  color: aqua;
}
.grid-item-box {
  display: flex;
  flex: 1;
  align-items: center;
  justify-content: center;
  font-size: 50rpx;
  color: rgb(252, 15, 15);
}
</style>
