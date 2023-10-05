<script lang="ts">
//###官方库导入
import { defineComponent, ref } from 'vue'
//###持久化存储
import { myReleaseStore } from '@/stores/modules/myRelease'

export default defineComponent({
  data() {
    return {
      /**
       * 持久化引用
       */
      //我的发布——本地缓存
      useMyReleaseStore: myReleaseStore(),
      //记录点击的商品——并查询其在发布缓存中的索引
      clickItemIndex: -1,
      /**
       * 响应式引用ref
       */
      //刷新窗口
      refreshWindow: ref(true),
      currentTime: new Date(),
    }
  },
  methods: {
    //点击卡片
    onClickCard(index: number) {
      console.log('我的发布: 点击卡片' + index)
      this.clickItemIndex = index
      console.log('我的发布: 即将跳转到商品详情页')
      setTimeout(() => {
        uni.navigateTo({
          url: '../index/productReferences?storeType=' + 'myRelease&' + 'index=' + index,
        })
      }, 200)
    },
    //刷新界面
    RefreshWindow() {
      console.log('我的发布: 刷新界面')
      this.refreshWindow = false
      setTimeout(() => {
        this.refreshWindow = true
      }, 100)
    },
    //解析时间
    formatTime(date: any) {
      const year = date.getFullYear()
      const month = date.getMonth() + 1
      const day = date.getDate()
      return `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`
    },
  },
  onShow() {
    console.log('########## 我的发布——onShow ##########')
    this.RefreshWindow()
  },
})
</script>

<template>
  <view>
    <view v-if="refreshWindow && useMyReleaseStore.profile">
      <view v-for="(item, index) in useMyReleaseStore.profile" :key="index" class="card-view">
        <uni-card
          :title="item.productInfo.type"
          :sub-title="item.productInfo.address"
          :extra="formatTime(currentTime)"
          :thumbnail="item.productInfo.imageInfo[0]"
          @click="onClickCard(index)"
        >
          <text>{{ item.productInfo.description }}</text>
        </uni-card>
      </view>
    </view>
    <view v-else class="no-release-view">
      <text style="position: absolute; font-size: 50rpx">还没有发布商品呢,快去发布吧</text>
    </view>
  </view>
</template>

<style lang="scss">
//###有发布商品样式
//卡片样式
.card-view {
  display: -webkit-box;
  -webkit-line-clamp: 2; /* 显示两行文本 */
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis; /* 使用省略号表示超出部分 */
}
//###无发布商品的视图样式
.no-release-view {
  height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 50rpx;
}
</style>
