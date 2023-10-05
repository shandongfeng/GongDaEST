<script setup lang="ts">
import { ref } from 'vue'
const activeIndex = ref(0)

const getSwiperIndex: UniHelper.SwiperOnChange = (ev) => {
  activeIndex.value = ev.detail!.current
}
</script>

<template>
  <view style="height: 280rpx; background-color: blue">
    <view class="carousel">
      <swiper :circular="true" :autoplay="true" :interval="3000" @change="getSwiperIndex">
        <swiper-item>
          <navigator url="/pages/index/index" hover-class="none" class="navigator">
            <image
              mode="aspectFill"
              class="image"
              src="https://ts1.cn.mm.bing.net/th/id/R-C.1920b13bf4cf3e2d63be74d586f9b545?rik=IkPbITbT1%2fToAg&riu=http%3a%2f%2fwww.gxphd.com%2fuploads%2f2018%2f12%2f251844144926.jpg&ehk=7nEr86Ka2kFM6PyS71LZHLe5oIoalIQGrkmryitwODg%3d&risl=&pid=ImgRaw&r=0"
            ></image>
          </navigator>
        </swiper-item>
        <swiper-item>
          <navigator url="/pages/index/index" hover-class="none" class="navigator">
            <image
              mode="aspectFill"
              class="image"
              src="https://lib.ccut.edu.cn/images/20221123093409.jpg"
            ></image>
          </navigator>
        </swiper-item>
        <swiper-item>
          <navigator url="/pages/index/index" hover-class="none" class="navigator">
            <image
              mode="aspectFill"
              class="image"
              src="https://mmbiz.qpic.cn/mmbiz_jpg/sX9SvRtbQSibV9zk9aaEpdZOKvcAIicyOPW1GNExsicGtyNzW26CeBND8y5keB5rwHOK5ibxjq8eU0poVFustecgmw/640?wx_fmt=jpeg&wxfrom=13&tp=wxpic"
            ></image>
          </navigator>
        </swiper-item>
      </swiper>
      <!-- 指示点 -->
      <view class="indicator">
        <text
          v-for="(item, index) in 3"
          :key="item"
          class="dot"
          :class="{ active: index === activeIndex }"
        ></text>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
:host {
  display: block;
  height: 280rpx;
}
/* 轮播图 */
.carousel {
  height: 280rpx;
  position: relative;
  overflow: hidden;
  transform: translateY(0);
  background-color: #efefef;
  .indicator {
    position: absolute;
    left: 0;
    right: 0;
    bottom: 16rpx;
    display: flex;
    justify-content: center;
    .dot {
      width: 30rpx;
      height: 6rpx;
      margin: 0 8rpx;
      border-radius: 6rpx;
      background-color: rgba(255, 255, 255, 0.4);
    }
    .active {
      background-color: #fff;
    }
  }
  .navigator,
  .image {
    width: 100%;
    height: 100%;
  }
}
</style>
