import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { loginResultType } from '@/types/loginType'

// 定义 Store
export const memberStore = defineStore(
  'member',
  () => {
    // 会员信息
    const profile = ref<loginResultType>()

    // 保存会员信息，登录时使用
    const setProfile = (val: loginResultType) => {
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
