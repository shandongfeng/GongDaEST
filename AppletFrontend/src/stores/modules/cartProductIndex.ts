import { defineStore } from 'pinia'
import { ref } from 'vue'

// 定义 Store
export const cartProductIndex = defineStore(
  'cartProductIndex',
  () => {
    // 会员信息
    const profile = ref<number[]>()

    // 保存会员信息，登录时使用
    const setProfile = (val: number) => {
      //数组为空就创建
      if (profile.value == null) {
        profile.value = []
      }
      //防止数组有重复值
      if (!profile.value.includes(val)) {
        profile.value.push(val)
      }
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
