<script lang="ts">
//###官方库引入
import { defineComponent } from 'vue'
//###持久化存储
import { productInfoStore } from '@/stores/modules/productInfo'
import { productItemIndexStore } from '@/stores/modules/productItemIndex'
import { cartProductIndex } from '@/stores/modules/cartProductIndex'
import { myReleaseStore } from '@/stores/modules/myRelease'
//###类型引入
import type { getProductItemFromServerType } from '@/types/productType'
//###自定义函数引入
import { getImageDataFromServer } from '@/services'

export default defineComponent({
  data() {
    return {
      /**
       * 获取本地缓存
       */
      //获取本地商品缓存
      useProductInfoStore: productInfoStore(),
      //获取存储首页点击的item的索引列表
      useProductItemIndexStore: productItemIndexStore(),
      //当前商品详情页所在缓存中的索引
      currentIndex: -1,
      //声明购物车存储数组
      useCartProductIndex: cartProductIndex(),
      //获取我的发布缓存
      useMyReleaseStore: myReleaseStore(),
      /**
       * 临时存储
       */
      //根据索引查找对应商品的本地缓存
      productItemInfo: null as getProductItemFromServerType | null,
      //底部栏
      options: [
        {
          icon: 'chat',
          text: '客服',
          info: 0,
          infoBackgroundColor: '#007aff',
          infoColor: '#f5f5f5',
        },
        {
          icon: 'shop',
          text: '店铺',
          info: 0,
          infoBackgroundColor: '#007aff',
          infoColor: '#f5f5f5',
        },
        {
          icon: 'cart',
          text: '购物车',
          info: 0,
          infoBackgroundColor: '#007aff',
          infoColor: '#f5f5f5',
        },
      ],
      buttonGroup: [
        {
          text: '加入购物车',
          backgroundColor: 'linear-gradient(90deg, #FFCD1E, #FF8A18)',
          color: '#fff',
        },
        {
          text: '立即购买',
          backgroundColor: 'linear-gradient(90deg, #FE6035, #EF1224)',
          color: '#fff',
        },
      ],
    }
  },
  methods: {
    //客服，店铺，购物车
    joinShoppingCart(e: any) {
      switch (e.index) {
        case 0:
          console.log('客服')
          break
        case 1:
          console.log('店铺')
          break
        case 2:
          console.log('即将进入购物车')
          uni.switchTab({ url: '../cart/cart' })
          break
      }
    },
    //加入购物车、立即购买
    clickOnBuy(e: any) {
      switch (e.index) {
        case 0:
          console.log('商品详情页: 索引为 ' + this.currentIndex + ' 加入购物车')
          this.useCartProductIndex.setProfile(this.currentIndex)
          uni.showToast({ title: '加入购物车', icon: 'success' })
          break
        case 1:
          console.log('商品详情页: 立即购买')
          break
      }
    },
  },
  created() {
    console.log('########## 商品详情页——created ##########')
  },
  /**
   *
   * @param options 1.store类型（本地的随机缓存还是我的发布缓存）
   *                2.点击item的索引
   */
  async onLoad(options: any) {
    console.log('########## 商品详情页——onLoad ##########')
    console.log('商品详情页: 上一个页面传递的信息 = ' + JSON.stringify(options))
    /**
     * options是调用navigateTo跳转到此界面的url里面的参数
     */
    let storeType = options.storeType as string
    this.currentIndex = options.index as number
    console.log('商品详情页: 当前store = ' + storeType + ',当前索引 = ' + this.currentIndex)
    //如果选择本地缓存
    if (storeType == 'random') {
      /**
       * 如果该商品有一张以上的照片，就需要再次请求服务器获取剩余图片信息
       * 原因：第一次请求服务器时只返回一张图片数据，
       * 目的：减少内存消耗，无需加载过多图片信息
       */
      //获取当前当前详情页商品信息
      let productData = this.useProductInfoStore.profile?.itemData[this.currentIndex]
      //获取商品图片信息
      const imageData = productData?.productInfo.imageInfo
      //获取当前详情页商品的存储图片信息的数组的长度
      let length = imageData?.length as number
      //如果图片长度大于1，并且储存点击商品item的索引列表没有当前点击索引，再请求服务器
      if (length > 1 && !this.useProductItemIndexStore.profile?.includes(this.currentIndex)) {
        this.useProductItemIndexStore.setProfile(this.currentIndex)
        //获取productItem数组从1到尾的数据组合成字符串并用','分开
        let requestImagePath = imageData!.slice(1).join(',')
        console.log('商品详情页: 拼接string = ' + requestImagePath)
        const res = await getImageDataFromServer(requestImagePath)
        //console.log('服务器响应数据 = ' + JSON.stringify(imageData))
        if (res.statusCode === 404) {
          uni.showToast({ title: '无法进入', icon: 'error' })
          return
        }
        //将服务器响应的图片数据保存到缓存中
        for (let j = 0; j < res.data.length; ++j) {
          this.useProductInfoStore.profile!.itemData[this.currentIndex].productInfo.imageInfo[
            j + 1
          ] = res.data[j]
        }
        console.log('商品详情页: 成功获取该商品全部信息')
      } else {
        console.log('商品详情页: 此商品已缓存完毕，无需再次缓存')
      }

      //从本地商品缓存中获取当前商品的信息并临时存储到productItemInfo
      if (this.useProductInfoStore.profile !== null) {
        this.productItemInfo = this.useProductInfoStore.profile!.itemData[this.currentIndex]
      }
    } else if (storeType == 'myRelease') {
      this.productItemInfo = this.useMyReleaseStore.profile![this.currentIndex]
    }
  },
})
</script>
<template>
  <view class="productReferences-container">
    <view>
      <uni-notice-bar text="感谢长春工业大学" showIcon showClose scrollable />
    </view>
    <!--头像、分享-->
    <view
      style="
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
      "
    >
      <view>
        <image
          :src="productItemInfo?.avatar"
          mode="scaleToFill"
          style="height: 120rpx; width: 120rpx; border-radius: 100%"
        />
        <text style="margin: 0 0 0 20rpx; font-size: 50rpx">{{ productItemInfo?.name }}</text>
      </view>
      <view style="display: flex; flex-direction: column">
        <uni-icons type="redo" :size="23" color="purple" />
        <text>分享 </text>
      </view>
    </view>
    <!--价格栏-->
    <view
      style="
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
      "
    >
      <view>
        <text style="font-size: 60rpx; color: brown">{{
          '￥' + productItemInfo?.productInfo.currentPrice
        }}</text>
        <text style="margin-left: 10rpx; text-decoration: line-through">{{
          '原价：￥' + productItemInfo?.productInfo.originalPrice
        }}</text>
      </view>
      <view style="display: flex">
        <uni-icons type="info" :size="23" color="purple" />
        <text>举报</text>
        <uni-icons type="fire" :size="23" color="purple" style="margin-left: 10rpx" />
        <text>游览</text>
      </view>
    </view>
    <!--商品描述-->
    <view style="">
      <view class="tag-and-desc-view">
        <uni-tag
          :text="productItemInfo?.productInfo.fineness"
          size="small"
          type="primary"
          :circle="true"
        />
        <text style="margin-left: 10rpx; margin-top: 5rpx;">{{
          productItemInfo?.productInfo.description.substring(0, 24)
        }}</text>
      </view>
      <view>
        <text>{{ productItemInfo?.productInfo.description.substring(24) }}</text>
      </view>
    </view>
    <!--地址、时间-->
    <view style="margin-top: 10rpx; display: flex; flex-direction: column; font-size: 25rpx">
      <text>{{ '发布地点：' + productItemInfo?.productInfo.address }}</text>
      <text>{{ '发布时间: ' + productItemInfo?.productInfo.time }}</text>
    </view>
    <!--分割线-->
    <text
      style="margin: 10rpx; height: 5rpx; width: 100vw; background-color: rgb(244, 63, 13)"
    ></text>
    <!--商品图片-->
    <view>
      <uni-section class="mb-10" title="商品详情" sub-title="" type="line"></uni-section>
      <view v-for="(item, index) in productItemInfo?.productInfo.imageInfo" :key="index">
        <image
          :src="item"
          mode="scaleToFill"
          style="margin: 0 0 10rpx 5%; height: 800rpx; width: 90%; border-radius: 10rpx;"
        />
      </view>
    </view>
    <!--分割线-->
    <text
      style="margin: 10rpx; height: 5rpx; width: 100vw; background-color: rgb(31, 239, 194)"
    ></text>
    <view class="commentArea">
      <uni-section class="mb-10" title="评论区" type="line"></uni-section>
    </view>
    <!--底部商品栏-->
    <view class="bottomArea">
      <uni-goods-nav
        :options="options"
        :fill="true"
        :button-group="buttonGroup"
        @click="joinShoppingCart"
        @buttonClick="clickOnBuy"
        button-class
      />
    </view>
  </view>
</template>
<style lang="scss">
.productReferences-container {
  margin-top: 0;
  height: auto;
  width: 100vw;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
//标签and商品描述
.tag-and-desc-view {
  display: flex;
  align-items: center;
  white-space: nowrap;
  padding: 5rpx 0 0 10rpx;
  font-size: 35rpx;
}
//#评论区
.commentArea {
  height: 400rpx;
  background-color: aquamarine;
}
//#底部栏
.bottomArea {
  width: 100%;
  position: fixed;
  bottom: 0;
  left: 0;
  background-color: red;
}
</style>
