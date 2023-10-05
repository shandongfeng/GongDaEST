//user在数据库的字段
export type loginParam = {
  userId: number
  session_key: string
  openid: string
  name: string
  avatar: string //base64字符串
  phoneNumber: string
  token: string
}
//请求（注册）类型
export type loginType = {
  requestCode: number //0是注册，1是登录
  code: string //微信uni.login获取的code
  errMsg: string
  name: string
  avatar: string //base64字符串
  phoneNumber: string
  token: string
}
//返回类型
export type loginResultType = {
  statusCode: number
  errMsg: string
  data: {
    userId: number
    name: string
    avatar: string //头像
    phoneNumber: string
    token: string
  }
}
