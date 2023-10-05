import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { getProductItemFromServerType, productItemStoreType } from '@/types/productType'

// 定义 Store
export const productInfoStore = defineStore(
  'productInfoStore',
  () => {
    // 本地商品随机缓存
    const profile = ref<productItemStoreType>()

    const setProfile = (val: productItemStoreType) => {
      profile.value = val
    }

    const clearProfile = () => {
      profile.value = undefined
    }

    // 记得 return
    return {
      profile,
      setProfile,
      clearProfile,
    }
  },
  // TODO: 持久化
  {
    persist: {
      storage: {
        getItem(key) {
          return uni.getStorageSync(key)
        },
        setItem(key, value) {
          uni.setStorageSync(key, value)
        },
      },
    },
  },
)
