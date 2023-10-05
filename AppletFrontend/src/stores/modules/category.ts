import { defineStore } from 'pinia'
import { ref } from 'vue'

// 定义 Store
export const categoryLabelIndexStore = defineStore(
  'categoryLabelIndexStore',
  () => {
    // 存储主页的分类标签索引
    const profile = ref<number>()

    // 保存会员信息，登录时使用
    const setProfile = (val: number) => {
      profile.value = val
    }

    // 清理会员信息，退出时使用
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
