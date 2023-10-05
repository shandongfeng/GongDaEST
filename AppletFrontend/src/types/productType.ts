//#############################首页################################
//productInfo在数据库中的所有字段
export type productFieldInDB = {
  productId: number
  userId: number //user_id外键,以后可以根据它查询商品主人的信息
  description: string
  type: string
  fineness: string
  originalPrice: number
  currentPrice: number
  address: string
  service: string
  time: Date
  imageInfo: string[]
}

//#############################商品发布页################################
//请求服务器的type，存储数据
export type productLoginType = {
  phoneNumber: string
  productInfoList: productFieldInDB
}

//向服务器请求productItem的返回类型
export type getProductItemFromServerType = {
  name: string
  avatar: string
  productInfo: productFieldInDB
}
//productItem在本地的缓存类型
export type productItemStoreType = {
  itemInfo: {
    page: number //当前页码
    pageCount: number //每页的数量
    pages: number //总页数
  }
  itemData: {
    name: string
    avatar: string
    productInfo: productFieldInDB
  }[]
}

//服务器返回响应总处理
export type resultType<T> = {
  statusCode: number
  errMsg: string
  data: T
}
