<script lang="ts">
/**
 * bug: popup不能放在data return{}里
 * 写法：只能放在方法并声明let popup = this.$refs.popup as UniPopup
 *       然后才能调用popup.open()、popup.close()函数
 */

/**
 * 引用官方库及自定义库
 */
import { defineComponent, ref } from 'vue' //响应式编程
//###service方法导入
import { sendProductInfo } from '@/services/product' //自定义的http包装类
//###类型导入
import type {
  getProductItemFromServerType,
  productLoginType,
  resultType,
} from '@/types/productType'
import type { UniPopup } from '@uni-helper/uni-ui-types'
//###自定义方法导入
import { imageHandle } from '@/utils/imageHandle' //图片处理
//###持久化存储导入
import { memberStore } from '@/stores/modules/member' //持久化存储userInfo
import { myReleaseStore } from '@/stores/modules/myRelease'

/**
 * 功能
 * 1.发布信息到服务器并保存在数据库
 * 2.保存在本地缓存一份，便于（我的发布）页面实现
 */
const { safeAreaInsets } = uni.getSystemInfoSync()
export default defineComponent({
  data() {
    return {
      /**
       * 系统信息
       */
      safeAreaInsets,
      /**
       * 响应式引用类型ref，主要用于页面组件渲染刷新
       */
      //图片上传后不会立刻显示，需要修改此变量的value会显示上传图片
      uploadPhotoRefresh: ref(false),
      //动态获取点击的form-row的view（form-row分成5份)索引
      formDataIndex: ref(0),
      //动态获取rightlabel，判断并刷新
      rightLabelRefresh: ref('请选择'),
      /**
       * 持久化存储
       */
      //获取当前登录状态，用于判断是否可以发布
      useMemberStore: memberStore(),
      //获取我的发布——本地缓存
      useMyReleaseStore: myReleaseStore(),
      /**
       * 自定义容器
       */
      //用来实现form表单，通过for循环划分5个view并添加下方属性
      formData: [
        {
          leftLabel: '商品类型',
          rightLabel: '请选择',
          leftIcon: 'cart-filled',
          rightIcon: 'right',
          textLength: '400rpx',
          iconSize: 23,
          color: 'black',
        },
        {
          leftLabel: '成色',
          rightLabel: '请选择',
          leftIcon: 'help-filled',
          rightIcon: 'right',
          textLength: '470rpx',
          iconSize: 23,
          color: 'black',
        },
        {
          leftLabel: '价格',
          rightLabel: '请选择',
          leftIcon: 'star',
          rightIcon: 'right',
          textLength: '470rpx',
          iconSize: 23,
          color: 'black',
        },
        {
          leftLabel: '地址',
          rightLabel: '请选择',
          leftIcon: 'home',
          rightIcon: 'right',
          textLength: '470rpx',
          iconSize: 23,
          color: 'black',
        },
        {
          leftLabel: '售后服务',
          rightLabel: '请选择',
          leftIcon: 'phone-filled',
          rightIcon: 'right',
          textLength: '470rpx',
          iconSize: 23,
          color: 'black',
        },
      ],
      //弹窗popup的标题、标签等
      popupData: [
        {
          title: '选择商品类型',
          label: ['生活用品', '学习用品', '其他'],
        },
        {
          title: '选择成色',
          label: ['9.9新', '5成新', '超级破'],
        },
        {
          title: '选择价格',
          label: [],
        },
        {
          title: '选择地址',
          label: ['南湖校区', '北湖西区', '北湖东区'],
        },
        {
          title: '选择售后服务',
          label: ['支持退换', '只退不换', '不退换'],
        },
      ],
      //临时存储价格
      originPrice: '',
      currentPrice: '',
      //存储将要上传的图片路径,自带默认图片数据一张
      imagePath: ['../../static/background/uploadPhoto.png'],
      //存储将要发布的数据类型
      productData: {
        phoneNumber: '',
        productInfoList: {
          productId: -1,
          userId: 0,
          description: '',
          type: '',
          fineness: '',
          originalPrice: 0,
          currentPrice: 0,
          address: '',
          service: '',
          time: new Date(),
          imageInfo: [] as string[],
        },
      } as productLoginType,
    }
  },
  methods: {
    //上方导航栏自定义返回
    back() {
      //console.log('send emit')
      uni.switchTab({ url: '/pages/release/release' })
    },
    //上传图片
    async uploadPhoto(path: string, index: number) {
      // 判断:
      // 1.点击上传图片的view才能上传图片
      // 2.图片最多保存两个，除了原始图片，因为view放不下，会溢出
      if (this.imagePath.length === 3 && path === '../../static/background/uploadPhoto.png') {
        return
      }
      //单击图片（除图片上传底片），即可删除
      if (path !== '../../static/background/uploadPhoto.png') {
        console.log('点击图片index = ' + index)
        this.productData.productInfoList.imageInfo.splice(index, 1)
        this.imagePath.splice(index, 1)
        this.uploadPhotoRefresh = false
        setTimeout(() => {
          this.uploadPhotoRefresh = true
        }, 100)
        return
      }
      //点击图片上传
      const result = await imageHandle()
      //console.log('数组初始长度' + imagePath.length)
      this.productData.productInfoList.imageInfo.unshift(result.data) //存储压缩后的图片data
      this.imagePath.unshift(result.path) //渲染刷新界面
      this.uploadPhotoRefresh = false
      setTimeout(() => {
        this.uploadPhotoRefresh = true
      }, 100)
    },
    //点击form，触发popup，为了渲染popup---button
    openPopup(index: number) {
      let popup = this.$refs.popup as UniPopup
      popup.open()
      //view右标签清空
      this.rightLabelRefresh = '请选择'
      this.formDataIndex = index
      //#this.formData[index].rightLabel = '请选择'
      console.log('商品发布页: formrow被点击 = ' + this.formDataIndex)
    },
    //获取弹窗中按钮的点击事件
    popupIndexSelect(msg: string, index: number) {
      let popup = this.$refs.popup as UniPopup
      console.log('商品发布页: 弹窗按钮被点击 = ' + msg)
      this.formData[this.formDataIndex].rightLabel = msg //及时更新formData
      this.rightLabelRefresh = msg
      popup.close()
    },
    //获取弹窗中---选择价格---确定按钮
    popupOk() {
      if (!this.originPrice || !this.currentPrice) {
        uni.showToast({ title: '请填写完整', icon: 'error' })
        return
      }
      let popup = this.$refs.popup as UniPopup
      this.formData[2].rightLabel = this.currentPrice
      //将现价存储到formData借此触发form-box，重新渲染form
      this.rightLabelRefresh = this.currentPrice
      popup.close()
    },
    //发布信息
    async releaseMsg() {
      let productList = this.productData.productInfoList
      //1.更新即将发送的数据
      productList.type = this.formData[0].rightLabel
      productList.fineness = this.formData[1].rightLabel
      productList.originalPrice = parseFloat(this.originPrice)
      productList.currentPrice = parseFloat(this.currentPrice)
      productList.address = this.formData[3].rightLabel
      productList.service = this.formData[4].rightLabel
      //console.log(JSON.stringify(requestBody))
      //将productInfoList列表转数组
      const values = Object.values(productList)
      console.log(values.length)
      //2.遍历数组，如果存在空元素，禁止发布
      if (
        this.productData.phoneNumber == '' ||
        values.some((item) => item == '' || item == '请选择' || item == 0)
      ) {
        console.log(JSON.stringify(this.productData))
        uni.showToast({ icon: 'error', title: '发送数据有空值' })
        return
      }
      /**
       * 调用http.ts封装好的request函数
       * @return res.data 返回的是主键id(user_id)
       */
      const res: resultType<number> = await sendProductInfo(this.productData)
      console.log('商品发布页: 获取响应' + JSON.stringify(res))
      if (res.statusCode !== 200) {
        uni.showToast({ title: '发送失败', icon: 'error' })
        return
      }
      console.log('商品发布页: 发布商品成功' + JSON.stringify(res))
      //将发布成功的请求信息复制一遍到本地(我的发布)缓存
      let productInfo = {
        name: this.useMemberStore.profile?.data.name,
        avatar: this.useMemberStore.profile?.data.avatar,
        productInfo: {
          productId: 0,
          userId: this.useMemberStore.profile?.data.userId,
          description: productList.description,
          type: productList.type,
          fineness: productList.fineness,
          originalPrice: productList.originalPrice,
          currentPrice: productList.currentPrice,
          address: productList.address,
          service: productList.service,
          time: productList.time,
          imageInfo: this.imagePath.slice(0, this.imagePath.length - 1),
        },
      } as getProductItemFromServerType
      this.useMyReleaseStore.setProfile(productInfo)
      uni.showToast({ title: '发布信息成功', icon: 'success' })
      setTimeout(() => {
        uni.switchTab({ url: '/pages/index/index' })
      }, 400)
    },
  },
  created() {
    console.log('########## 商品发布页——created ##########')
    if (this.useMemberStore.profile == undefined) {
      console.log('商品发布页: 当前无用户登录状态')
      return
    }
    //初始化即将作为请求发送的容器的字段
    this.productData.phoneNumber = this.useMemberStore.profile.data.phoneNumber
    this.productData.productInfoList.userId = this.useMemberStore.profile.data.userId
    console.log(
      '商品发布页: 用户手机号 = ' +
        this.productData.phoneNumber +
        ',用户数据库id = ' +
        this.productData.productInfoList.userId,
    )
  },
  components: {
    //
  },
})
</script>

<template>
  <view>
    <view>
      <!--自定义顶部导航栏-->
      <view :style="{ paddingTop: safeAreaInsets?.top + 'px' }">
        <uni-nav-bar shadow left-icon="left" title="信息页" :fixed="true" @clickLeft="back" />
      </view>
      <!--储存text的容器-->
      <view class="text-box">
        <uni-easyinput
          type="textarea"
          v-model="productData.productInfoList.description"
          placeholder="请您为即将发布的商品添加描述"
        />
        <!--图片上传-->
        <view
          class="uploadPhoto"
          v-for="(item, index) in imagePath"
          :key="index"
          @click="uploadPhoto(item, index)"
        >
          <image :src="item" mode="scaleToFill" />
        </view>
      </view>
      <!-- ############################form容器begin################################### -->
      <!--存储form的容器-->
      <view class="form-box">
        <!--存放form每行的数据容器 @click="openPopup('商品类型')"-->
        <view
          class="form-row"
          v-for="(item, index) in formData"
          :key="index"
          @click="openPopup(index)"
        >
          <view style="display: flex; flex-direction: row">
            <uni-icons :type="item.leftIcon" :size="item.iconSize" :color="item.color" />
            <text>{{ item.leftLabel }}</text>
          </view>
          <view style="display: flex; flex-direction: row">
            <!--很关键-->
            <text v-if="index === formDataIndex">{{
              rightLabelRefresh || formData[index].rightLabel
            }}</text>
            <text v-else>{{
              formData[index].rightLabel ? formData[index].rightLabel : '请选择'
            }}</text>
            <uni-icons :type="item.rightIcon" :size="item.iconSize" :color="item.color" />
          </view>
        </view>
      </view>
      <!--发布按钮-->
      <view class="release-btn">
        <button @click="releaseMsg" type="default" :plain="true">发布</button>
      </view>

      <!--############################弹窗begin###################################
        设计：
              1.一开始并不渲染弹出层，只有当触发form-row的view视图的openPopup，并初始化popupindex时才渲染
              2.价格弹窗要单独设计
      -->
      <uni-popup ref="popup" type="bottom" background-color="white">
        <!--弹窗标题-->
        <view style="text-align: center; font-size: 120rpx">{{
          popupData[formDataIndex].title
        }}</view>
        <!--当点击的视图是---商品管理、成色、地址、售后时，显示的popup的view-->
        <view v-if="formDataIndex !== 2">
          <view
            v-for="(item, index) in popupData[formDataIndex].label"
            :key="index"
            class="popup-btn"
          >
            <button @click="popupIndexSelect(item, index)" type="default">{{ item }}</button>
          </view>
        </view>
        <!--当点击的视图是---价格，显示的popup的view-->
        <view v-else class="popup-input">
          <input
            v-model="originPrice"
            placeholder="请输入商品原价(不能为空)"
            placeholder-class="input-placeholder"
          />
          <input
            v-model="currentPrice"
            placeholder="请输入商品现价(不能为空)"
            placeholder-class="input-placeholder"
          />
          <!--确定按钮,必须填完原价、现价才能点击确定-->
          <button
            @click="popupOk"
            style="margin-top: 20rpx"
            type="default"
            hover-class="button-hover"
          >
            确定
          </button>
        </view>
      </uni-popup>
      <!--############################弹窗end###################################-->
    </view>
  </view>
</template>

<style lang="scss">
//文本view
.text-box {
  height: 500rpx;
  //background-color: chartreuse;
}
//textarea多行文本框
.uni-easyinput__content {
  height: 300rpx;
  max-height: 500rpx;
}
//上传图片
.uploadPhoto {
  margin-top: 0rpx;
  margin-left: 30rpx;
  width: 200rpx;
  height: 200rpx;
  display: inline-flex;
  background-color: rgb(81, 79, 78);
}
//form表格视图
.form-box {
  margin-top: 50rpx;
  height: 750rpx;
  display: flex;
  flex-direction: column;
  //background-color: rgb(249, 10, 66);
}
//form每行的样式
.form-row {
  margin-bottom: 50rpx;
  height: 20%; //把父view分成五等份
  display: flex;
  align-items: center; //垂直居中
  justify-content: space-between; //水平间距
  //background-color: aqua;
}
//弹出层---按钮
.popup-btn {
  margin-top: 100rpx;
  display: inline-flex;
  margin-right: 50rpx;
}
//弹出层---输入框
.popup-input {
  padding-top: 100rpx;
}
//发布按钮
.release-btn {
  margin-top: 0rpx;
  background-color: yellow;
}
</style>
