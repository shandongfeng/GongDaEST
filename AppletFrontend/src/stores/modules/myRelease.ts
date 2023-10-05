import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { getProductItemFromServerType } from '@/types/productType'
// 定义 Store
export const myReleaseStore = defineStore(
  'myReleaseStore',
  () => {
    // 我的发布信息
    const profile = ref<getProductItemFromServerType[]>()

    const setProfile = (val: getProductItemFromServerType) => {
      if (profile.value == null) {
        profile.value = []
      }
      profile.value.push(val)
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
