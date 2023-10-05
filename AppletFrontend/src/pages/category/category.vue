<script lang="ts">
//###导入官方库
import { defineComponent, ref } from 'vue'
//###导入自定义组件
import CustomSwiper from '../index/components/CustomSwiper.vue'
//###持久化导入
import { categoryLabelIndexStore } from '@/stores/modules/category'

/**
 * 分类页
 */
export default defineComponent({
  data() {
    return {
      //主页标签点击index
      useCategoryLabelIndexStore: categoryLabelIndexStore(),
      //分类标签
      categoryLabel: [
        '居家',
        '美食',
        '服饰',
        '母婴',
        '个护',
        '严选',
        '数码',
        '运动',
        '办公',
        '其他',
      ],
      //当前标签index
      labelIndex: ref(0),
      //商品信息图片
      productIamge: [
        '../../static/category/居家.png',
        '../../static/category/美食.png',
        '../../static/category/服饰.png',
        '../../static/category/母婴.png',
        '../../static/category/个护.png',
        '../../static/category/严选.png',
        '../../static/category/数码.png',
        '../../static/category/运动.png',
        '../../static/category/办公.png',
        '../../static/category/其他.png',
      ],
    }
  },
  methods: {
    //点击左侧标签
    clickOnLabel(index: number) {
      console.log('分类页: 点击标签' + index)
      this.labelIndex = index
    },
  },
  /**
   * @param options storeType: string, index:number
   * 1.storeType选择读取的商品缓存列表
   * 2.index决定点击的标签索引
   */
  onLoad(options: any) {
    console.log('########## onLoad ##########')
    console.log('分类页: onLoad接收参数 = ' + JSON.stringify(options))
  },
  onShow() {
    console.log('########## onShow ##########')
    this.labelIndex = (this.useCategoryLabelIndexStore.profile as number) || 0
  },
  components: {
    CustomSwiper,
  },
})
</script>

<template>
  <view>
    <!--搜索栏-->
    <view class="search">
      <uni-icons type="search" color="" size="24" style="padding-left: 10rpx" />
      <text style="position: absolute; left: 80rpx">搜索商品</text>
      <text></text>
    </view>
    <!--下半部分-->
    <view class="bottom-view">
      <!--左侧标签-->
      <view class="label-view">
        <!--子标签-->
        <view
          v-for="(item, index) in categoryLabel"
          :key="index"
          @tap="clickOnLabel(index)"
          class="label-item-view"
          :style="{ 'background-color': index == labelIndex ? 'white' : 'WhiteSmoke' }"
        >
          <text>{{ item }}</text>
        </view>
      </view>
      <!--右侧商品展示-->
      <view class="right-view">
        <!--轮播图-->
        <CustomSwiper />
        <view class="productInfo-view">
          <image :src="productIamge[labelIndex]" mode="scaleToFill" />
        </view>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
//###搜索框
.search {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64rpx;
  margin: 20rpx 20rpx;
  color: #fff;
  font-size: 28rpx;
  border-radius: 32rpx;
  background-color: rgba(235, 11, 11, 0.5);
}
//###商品区域
//底部总视图
.bottom-view {
  display: flex;
  flex-direction: row;
}
//标签区域
.label-view {
  width: 150rpx;
  background-color: WhiteSmoke;
}
//单个标签样式
.label-item-view {
  height: 100rpx;
  display: flex;
  align-items: center;
  justify-content: center;
}
//###右侧视图
.right-view {
  width: 100vw;
  margin: 0 20rpx 0 20rpx;
  display: flex;
  flex-direction: column;
  //background-color: aqua;
}
//商品信息view
.productInfo-view {
  height: 1200rpx;
  //background-color: brown;
}
</style>
