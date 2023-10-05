<script lang="ts">
import { defineComponent, ref } from 'vue'
//###引入持久化存储
import { productInfoStore } from '@/stores/modules/productInfo'
import { cartProductIndex } from '@/stores/modules/cartProductIndex'
//###类型导入
import type { getProductItemFromServerType } from '@/types/productType'
/**
 * 购物车
 * create: 刚初次进入该界面时，根据本地的购物车索引列表的成员向商品缓存提取数据并赋值到临时数组
 */
export default defineComponent({
  data() {
    return {
      /**
       * 本地缓存
       */
      //获取加入购物车的商品在本地缓存中的索引列表
      useCartProductIndex: cartProductIndex(),
      //获取本地缓存中的商品信息
      useProductInfoStore: productInfoStore().profile,
      /**
       * 临时存储
       */
      //临时存储——根据索引获取的商品数据
      temporaryStoreProductItem: [] as getProductItemFromServerType[],
      //距离选中的商品在useCartProductIndex的索引
      selectItemIndexList: [] as number[],
      /**
       * 响应式ref
       */
      //总金额
      totalMoney: ref(0.0),
      //全选
      isSelectedAll: ref(false),
      //重新渲染界面
      renderWindow: ref(true),
    }
  },
  methods: {
    //清空页面可视数据（总金额、总数量、全选取消）
    clearData() {
      this.isSelectedAll = false
      this.totalMoney = 0
      this.selectItemIndexList = []
    },

    //刷新界面
    RefreshWindow() {
      this.renderWindow = false
      console.log('购物车: 等待一会，刷新购物车')
      setTimeout(() => {
        //重新渲染窗口
        this.renderWindow = true
      }, 100)
    },

    //记录选中item
    recordItemIndex(index: number) {
      //去重
      if (!this.selectItemIndexList.includes(index)) {
        //记录选中item
        this.selectItemIndexList.push(index)
      }
    },

    //点击按钮选中/取消
    clickOnSwitch(index: number, money: number, value: any) {
      let selectStatus = value
      let cartIndexList = this.useCartProductIndex.profile
      console.log('购物车: ' + '商品' + index + ',选中状态 = ' + selectStatus)
      //判断switch状态，如果是true，总和++; 如果false，总和--。同时也关联总数量
      if (selectStatus) {
        this.totalMoney += money
        this.recordItemIndex(cartIndexList![index])
      } else {
        //###关键###
        //如果不查找当前索引在cartIndexList列表的位置，会导致误删除，从而使结算数量出错
        let findIndex = this.selectItemIndexList.indexOf(cartIndexList![index])
        if (findIndex !== -1) {
          this.selectItemIndexList.splice(findIndex, 1)
        }
        this.totalMoney -= money
      }
      console.log('购物车: 当前选中列表 = ' + JSON.stringify(this.selectItemIndexList))
      console.log('购物车: 当前总金额 = ' + this.totalMoney)
    },

    //底部全选
    clickOnTotal(e: any) {
      //获取商品长度
      let len = this.temporaryStoreProductItem.length as number
      console.log('购物车: 当前购物车商品数量 = ' + len)
      //全部item索引添加到列表
      let allMoney = 0
      for (let i = 0; i < len; i++) {
        this.recordItemIndex(this.useCartProductIndex.profile![i])
        allMoney += this.temporaryStoreProductItem[i].productInfo.currentPrice
      }
      //判断是否全选。
      let selectStatus = e.detail.value
      if (selectStatus == 'cb') {
        this.isSelectedAll = true
        this.totalMoney = allMoney
        console.log('购物车: 全选')
      } else {
        //清空数据
        this.clearData()
        console.log('购物车: 取消全选')
      }
    },

    //结算
    clickOnBuy() {
      if (this.selectItemIndexList.length == 0) {
        uni.showToast({ title: '请选择商品', icon: 'error' })
        return
      }
      uni.showToast({ title: '购买成功', icon: 'success' })
      let cartIndexList = this.useCartProductIndex.profile
      //根据选中列表来对临时数组、总金额、总数量、购物车数量缓存，进行相应的删除或扣除
      console.log('购物车: 选中索引列表 = ' + JSON.stringify(this.selectItemIndexList))
      this.selectItemIndexList.forEach((value, index) => {
        //清理购物车索引列表
        let findIndex2 = cartIndexList!.findIndex((item) => item == value)
        if (findIndex2 !== -1) {
          cartIndexList!.splice(findIndex2, 1)
        }
      })
      //因为[]每删除一个元素，后面的元素会前进一位，会导致指定元素的删除会失效，所以采用重新赋值的方法
      this.temporaryStoreProductItem = []
      let len = cartIndexList!.length
      for (let i = 0; i < len; ++i) {
        let productInfo = this.useProductInfoStore!.itemData[cartIndexList![i]]
        this.temporaryStoreProductItem.push(productInfo)
      }
      //清空选中金额、选中数量、取消全选
      this.clearData()
      //刷新
      this.RefreshWindow()
      console.log(
        '购物车: ' +
          '临时数组长度 = ' +
          this.temporaryStoreProductItem.length +
          ',当前购物车数量' +
          this.useCartProductIndex.profile?.length,
      )
    },
  },
  created() {
    console.log('########## 购物车——created ######')
    //根据索引，从本地缓存中提取购物车的商品数据
    if (this.useProductInfoStore && this.useCartProductIndex.profile) {
      this.useCartProductIndex.profile!.forEach((item, index) => {
        let productInfo = this.useProductInfoStore!.itemData[item]
        this.temporaryStoreProductItem.push(productInfo)
      })
      console.log('购物车: created界面初始化完毕')
    }
  },
  onLoad(options: any) {
    console.log('########## 购物车——onLoad ######')
  },
  onReady() {
    console.log('########## 购物车——onReady ######')
  },
  onShow() {
    console.log('########## 购物车——onShow ######')
    if (this.useCartProductIndex.profile == null || this.useProductInfoStore == null) {
      console.log('购物车: 当前购物车无数据')
      return
    }
    //获取购物车索引列表
    let cartIndexList = this.useCartProductIndex.profile
    //获取临时数组长度
    let tempListLength = this.temporaryStoreProductItem.length
    console.log(
      '购物车: 当前索引列表数量 = ' +
        cartIndexList?.length +
        ',当前购物车商品数量总和 = ' +
        tempListLength,
    )

    //如果购物车有新商品添加，需要立刻更新tempList
    for (let i = tempListLength; i < cartIndexList.length; ++i) {
      let productInfo = this.useProductInfoStore!.itemData[cartIndexList![i]]
      this.temporaryStoreProductItem.push(productInfo)
    }
    console.log('购物车: 更新购物车成功')

    //清理数据
    this.clearData()
    //重新渲染界面
    this.RefreshWindow()
    console.log('购物车: 更新后购物车数量 = ' + tempListLength)
  },
})
</script>

<template>
  <!--
    界面组成：两个总界面+底部预留空白
    renderWindow：用于动态渲染第一个view
    temporaryStoreProductItem：渲染数据列表
   -->
  <view>
    <view v-if="renderWindow && temporaryStoreProductItem.length !== 0" class="cart-container">
      <view v-for="(item, index) in temporaryStoreProductItem" :key="index">
        <view class="card-view">
          <view class="image-view">
            <image :src="item.productInfo.imageInfo[0]" mode="scaleToFill" />
          </view>
          <view class="desc-price-view">
            <view>
              <text style="font-size: 28rpx">{{ item.productInfo.description }}</text>
              <text class="price-item">{{ '￥' + item.productInfo.currentPrice }}</text>
            </view>
            <switch
              :checked="isSelectedAll"
              @change="((e: any) => {clickOnSwitch(index,item.productInfo.currentPrice, e.detail.value)})"
              color="#FFCC33"
              class="switch-item"
            />
          </view>
        </view>
      </view>
      <!--底部结算-->
      <view class="bottom-view">
        <checkbox-group @change="clickOnTotal">
          <label style="display: flex; align-items: center; color: red">
            <checkbox value="cb" :checked="isSelectedAll" />全选
          </label>
        </checkbox-group>
        <text style="margin-left: 50rpx; color: red">{{ '合计：￥' + totalMoney }}</text>
        <button @click="clickOnBuy" hover-class="button-hover" style="margin-right: 0">
          去结算({{ selectItemIndexList.length }})
        </button>
      </view>
    </view>
    <view v-else class="cart-container2">
      <text style="position: absolute; font-size: 50rpx">购物车还是空的，快去选购商品吧</text>
    </view>
    <!--底部预留空白区域-->
    <view class="bottom-whiteSpace-view" />
  </view>
</template>

<style lang="scss">
//###########购物车整体容器###########
.cart-container {
  width: 100vw;
  display: flex;
  flex-direction: column;
  flex-direction: space-around;
  //background-color: aqua;
}
//###########卡片item样式###########
.card-view {
  margin-bottom: 20rpx;
  height: 250rpx;
  width: 100vw;
  display: flex;
  flex-direction: row;
  //background-color: chartreuse;
}
//图片
.image-view {
  //position: absolute;
  margin: 25rpx 0 0 25rpx;
  height: 200rpx;
  width: 25%;
  border-radius: 20%;
  //background-color: aquamarine;
}
//描述和价格
.desc-price-view {
  position: relative;
  height: 200rpx;
  width: 70%;
  display: flex;
  flex-direction: column;
  margin: 25rpx 0 25rpx 25rpx;
  //background-color: brown;
}
//价格
.price-item {
  position: absolute;
  left: 0;
  bottom: 0;
  color: red;
}
//滑动
.switch-item {
  position: absolute;
  bottom: 0;
  right: 0;
  transform: scale(0.7);
}
//###########底部结算栏###########
.bottom-view {
  bottom: 0;
  position: fixed;
  height: 100rpx;
  width: 100vw;
  display: flex;
  align-items: center;
  background-color: yellow;
}
//###########第二个界面容器——即购物车没有商品时显示的view ###########
.cart-container2 {
  height: 100vh;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  //background-color: aqua;
}
//###########底部预留空白区域###########
.bottom-whiteSpace-view {
  height: 100rpx;
  //background-color: rgb(140, 8, 160)
}
</style>
