<script lang="ts">
//###导入事件处理官方函数，用于界面初次渲染并执行回调函数
import { defineComponent, ref } from 'vue'
//###组件导入
import CustomNavbar from './components/CustomNavbar.vue' //导入上方导航栏组件
import CustomSwiper from './components/CustomSwiper.vue' //导入轮播图组件
import CategoryPanel from './components/CategoryPanel.vue' //导入前台面板组件

//###外部函数调用
import { getProductItemFromServer, getImageDataFromServer } from '@/services/index'
//###类型导入
import type {
  resultType,
  getProductItemFromServerType,
  productItemStoreType,
} from '@/types/productType' //导入productItemType，规定商品页的内容form
//###持久化注入
import { productInfoStore } from '@/stores/modules/productInfo'
import { productItemIndexStore } from '@/stores/modules/productItemIndex'

export default defineComponent({
  data() {
    return {
      /**
       * 持久化
       */
      //商品信息持久化
      useProductInfoStore: productInfoStore(),
      //productItem索引
      useProductItemIndex: productItemIndexStore(),
      /**
       * 响应式ref
       */
      //productItem渲染
      renderProductItem: ref(true),
      //下拉刷新渲染
      isPullToRefresh: ref(false),
      //刷新底部文字栏，如果数据请求完毕了，要及时更新文字状态（还能加载数据 || 已加载完毕）
      bottomText: ref('正在加载请稍后...'),
      /**
       * 临时存储，容器
       */
      //地域滚动
      areaData: ['全部', '南湖校区', '北湖西区', '北湖东区'],
      //当前地域
      currentAreaName: ref('全部'),
      //获取服务器响应的商品信息
      productItemResult: null as resultType<getProductItemFromServerType[]> | null,
      //本地缓存
      productItemStore: {
        itemInfo: {
          page: 0,
          pageCount: 4,
          pages: 4,
        },
        itemData: [],
      } as productItemStoreType,
    }
  },
  methods: {
    //区域点击，筛选
    clickOnArea(name: string) {
      this.bottomText = ''
      this.currentAreaName = ''
      setTimeout(() => {
        this.currentAreaName = name
      }, 100)
      console.log('主页: 当前地域 = ' + name)
    },
    //点击任一productItem进入商品详情页
    async openProductReferences(index: number) {
      //其实这里不会为null,因为不渲染productItem也不会触发当前的点击信号
      if (this.useProductInfoStore.profile == null) {
        console.log('主页: productItem信息为空')
        return
      }
      console.log('主页: 当前点击的productIndex = ' + index)
      setTimeout(() => {
        uni.navigateTo({
          url: '../index/productReferences?storeType=' + 'random&' + 'index=' + index,
        })
      }, 200)
    },
    //下拉刷新处理
    pullToRefresh() {
      this.isPullToRefresh = true
      this.renderProductItem = false
      setTimeout((e) => {
        this.isPullToRefresh = false
        this.renderProductItem = true
      }, 200)
    },
    //##########触底事件处理##########
    //无法继续触底处理
    alreadyToEnd() {
      this.bottomText = ''
      setTimeout(() => {
        this.bottomText = '已经滑到最底部了...'
      }, 100)
      console.log('主页: 已经到底,无法继续上滑')
    },
    //触底
    async triggerToEnd() {
      console.log('主页: 一次触底')
      //如果当前缓存无数据，无法使用触底行为
      if (this.useProductInfoStore.profile === null) {
        console.log('主页: 加载商品信息失败')
        return
      }
      const itemInfo = this.useProductInfoStore.profile!.itemInfo
      //如果已经到最底部了，就不监视该行为了
      if (itemInfo.page == itemInfo.pages) {
        this.alreadyToEnd()
        return
      }
      //判断当前页面是否还能填充数据
      if (itemInfo.page < itemInfo.pages) {
        console.log('准备加载新的商品信息')
        //向服务器请求数据
        console.log(
          '主页: 即将下拉第' +
            itemInfo.page +
            '页,' +
            '本次请求商品数量 = ' +
            itemInfo.pageCount +
            ',总页数 = ' +
            itemInfo.pages,
        )
        const nextData = await getProductItemFromServer(itemInfo.page, itemInfo.pageCount)
        if (nextData.statusCode !== 200) {
          this.alreadyToEnd()
          return
        }
        //console.log(JSON.stringify(nextData))
        //持久化存储
        ++itemInfo.page
        nextData.data.forEach((item, index) => {
          this.useProductInfoStore.profile!.itemData.push(item)
        })
      }
      setTimeout(() => {
        '刷新速度太快了,延迟一下便于观察'
      }, 2000)
    },
  },
  created() {
    console.log('########## 主页——onShow ##########')
  },
  /**
   * 界面初次加载
   * @call1 getProductItemFromServer()
   * @Param number,请求productItem数量
   * @Retrun productItemData[]，其中productItemData.productInfo.imageInfo[0]是base64图片数据，其他是图片在服务器的本地路径
   *
   * @call2 openProductReferences()
   * @Param productItem被点击的索引
   *
   *
   * @key 用户头像和商品信息第一张图片会在第一次请求时返回
   *      当调用call2后进入商品详情页时，发出第二个请求获取商品信息剩余图片数据
   */
  async onLoad(options: any) {
    console.log('########## 主页——onLoad ##########')
    //如果本地没有productItem缓存，就请求服务器获取数据
    if (!this.useProductInfoStore.profile) {
      //商品信息获取,每次获取10组productItem
      this.productItemResult = await getProductItemFromServer(0, 4)
      //console.log('#######' + JSON.stringify(productItemResult))
      if (this.productItemResult.statusCode === 404) {
        console.log('主页: 响应失败,无法渲染商品滚动显示')
        return
      }
      ++this.productItemStore.itemInfo.page
      this.productItemStore.itemData = this.productItemResult.data
      this.useProductInfoStore.setProfile(this.productItemStore)
    }
    //productItemData = useProductInfoStore.profile?.itemData
    this.renderProductItem = false
    setTimeout(() => {
      this.renderProductItem = true
    }, 100)
  },
  //页面初次渲染完成时触发的生命周期函数，表示页面已经准备好了
  onShow() {
    console.log('########## 主页——onShow ##########')
    this.bottomText = ''
    this.renderProductItem = false
    setTimeout(() => {
      this.renderProductItem = true
    }, 100)
  },
  //组件
  components: {
    CustomNavbar,
    CustomSwiper,
    CategoryPanel,
  },
  computed: {
    //过滤器
    filteredItemData() {
      const itemData = this.useProductInfoStore.profile?.itemData || []
      const filteredItems = itemData.filter((item) => {
        return this.currentAreaName == '全部' || item.productInfo.address == this.currentAreaName
      })
      return filteredItems
    },
  },
})
</script>

<template>
  <view class="index-container">
    <!--导航栏-->
    <CustomNavbar />
    <!--滚动区域-->
    <scroll-view
      lower-threshold="5px"
      refresher-enabled
      @refresherrefresh="pullToRefresh"
      :refresher-triggered="isPullToRefresh"
      @scrolltolower="triggerToEnd"
      scroll-y
      class="scroll-view"
    >
      <!--轮播图-->
      <CustomSwiper />
      <!--导航分类-->
      <CategoryPanel />

      <!--地域滚动-->
      <scroll-view scroll-x class="scroll-areaSelect">
        <button
          v-for="(item, index) in areaData"
          :key="index"
          type="primary"
          :plain="true"
          @tap="clickOnArea(item)"
          class="areaBtn"
        >
          {{ item }}
        </button>
      </scroll-view>

      <!--#####内容展示-->
      <view v-if="renderProductItem" class="grip-view">
        <view
          v-for="(item, index) in filteredItemData"
          :key="index"
          @tap="openProductReferences(index)"
          class="grid-item"
        >
          <!--####每个grid-item的view容器-->
          <view class="data-grid-item">
            <!--###图片-->
            <view style="position: relative; height: 420rpx">
              <image
                :src="item.productInfo.imageInfo[0]"
                mode="scaleToFill"
                style="border-radius: 20rpx"
              />
              <view style="position: absolute; bottom: 10rpx; left: 10rpx; display: flex">
                <uni-icons type="location-filled" color="green" size="24" />
                <text style="margin-top: 5rpx; margin-left: 5rpx">{{
                  item.productInfo.address
                }}</text>
              </view>
            </view>
            <!--###成色标签和描述-->
            <view class="tag-and-desc-view">
              <uni-tag
                :text="item.productInfo.fineness"
                size="small"
                type="primary"
                :circle="true"
              />
              <text style="margin-left: 10rpx; padding-top: 10rpx">{{
                item.productInfo.description
              }}</text>
            </view>
            <!--价格-->
            <view style="margin-top: 10rpx">
              <text style="color: brown">{{ '￥' + item.productInfo.currentPrice }}</text>
              <text style="margin-left: 10rpx; text-decoration: line-through">{{
                '￥' + item.productInfo.originalPrice
              }}</text>
            </view>
            <!--头像、姓名和发布日期-->
            <view
              style="
                margin-top: 10rpx;
                display: flex;
                flex-direction: row;
                justify-content: space-between;
              "
            >
              <view>
                <image
                  :src="item.avatar"
                  mode="scaleToFill"
                  style="height: 80rpx; width: 80rpx; border-radius: 100%"
                />
                <text style="margin-left: 10rpx">{{ item.name }}</text>
              </view>
              <uni-tag
                :text="item.productInfo.time.toLocaleString()"
                size="normal"
                type="primary"
                :circle="true"
                style="line-height: 70rpx"
              />
            </view>
          </view>
        </view>
      </view>
      <!--底部加载文字栏-->
      <view class="bottom-whiteSpace-view">
        <text>{{ bottomText }}</text>
      </view>
    </scroll-view>
  </view>
</template>

<style lang="scss">
//##########滚动区域##########
.scroll-view {
  padding-top: 250rpx;
  height: 100vh;
  //height: auto;
  display: flex;
  flex-direction: column;
  //background-color: rgb(90, 188, 20);
}
//##########地域选择##########
//地域选择view
.scroll-areaSelect {
  margin-top: 60rpx;
  white-space: nowrap; //不允许换行
  height: 50rpx;
  width: 100vw;
}
//地域选择按钮
.areaBtn {
  display: inline-flex; /* 使用 inline-flex 替代 inline-block */
  align-items: center; /* 垂直居中对齐 */
  justify-content: center; /* 水平居中对齐 */
  margin-right: 30rpx;
  height: 50rpx;
}
//##########内容展示区域##########
.grip-view {
  margin-top: 10rpx;
  height: auto;
  width: 100vw;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  //background-color: chartreuse;
}
//单个展示item结构
.grid-item {
  height: 670rpx;
  width: 100%;
  display: flex;
  //background-color: rgb(222, 18, 90);
}
//item内部样式
.data-grid-item {
  display: flex;
  flex-direction: column;
  padding: 20rpx 10rpx 20rpx 10rpx;
}
//标签和描述样式
.tag-and-desc-view {
  height: auto;
  margin-top: 10rpx;
  display: -webkit-box;
  -webkit-line-clamp: 2; /* 显示两行文本 */
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis; /* 使用省略号表示超出部分 */
}
//###底部预留
.bottom-whiteSpace-view {
  height: 200rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 35rpx 0 0 0;
  font-size: 50rpx;
  //background-color: blueviolet;
}
</style>
