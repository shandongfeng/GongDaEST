import { http } from '@/utils/http'
import type { resultType, getProductItemFromServerType } from '@/types/productType'
//获取指定数量的商品信息,返回类型
export const getProductItemFromServer = (
  startIndex: number,
  productItemCount: number,
): Promise<resultType<getProductItemFromServerType[]>> => {
  return http<resultType<getProductItemFromServerType[]>>({
    url: 'product/getProductInfo?startIndex=' + startIndex + '&requestCount=' + productItemCount,
    method: 'GET',
  })
}

/**
 * 根据商品信息得到用户信息然后再显示到商品介绍里
 * @param userId
 * @returns userInfoResult
 */
interface userInfoResult {
  name: string
  avatarData: string //直接读取图片数据
}
export const getUserItemFromServer = <userInfoResult>(userId: number) => {
  return http<userInfoResult>({
    url: 'user/getInfo?userId=' + userId,
    method: 'GET',
  })
}
/**
 * 根据第一次请求服务器获取图片的服务器本地路径,第二次请求服务器获取图片完整信息
 * @param imagePath
 * @returns string[]
 */
export const getImageDataFromServer = (imagePath: string): Promise<resultType<string[]>> => {
  return http<resultType<string[]>>({
    url: 'product/getImageInfo?imagePath=' + imagePath,
    method: 'GET',
  })
}
