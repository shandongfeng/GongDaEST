import { http } from '@/utils/http'
import type { resultType, productLoginType } from '@/types/productType'

export const sendProductInfo = (productData: productLoginType): Promise<resultType<number>> => {
  return http<resultType<number>>({
    url: 'register/product',
    method: 'POST',
    data: productData, //请求体
  })
}
