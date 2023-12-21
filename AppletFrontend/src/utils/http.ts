/**
 * 拦截器
 *
 */

//基础url
const baseURL = 'http://localhost:8181/'
//拦截器
const httpInterceptor = {
  //拦截前触发
  invoke(options: UniApp.RequestOptions) {
    //检测请求头是否有中文字符
    if (options.method === 'GET') {
      //以'?'分割，返回数组
      const requestPath = options.url.split('?')
      console.log('以？分割后的字符串 = ' + requestPath)
      const chinesePattern = /[\u4e00-\u9fa5]/
      const isHasChinese = chinesePattern.test(requestPath[1])
      if (isHasChinese) {
        console.log('###请求头有中文')
        console.log('处理前url = ' + options.url)
        options.url = requestPath[0] + '?' + encodeURIComponent(requestPath[1]).replace(/%3D/g, '=')
        console.log('处理后url = ' + options.url)
        console.log('###处理结束')
      }
    }
    //拼接请求路径
    options.url = baseURL + options.url
    console.log('请求url = ' + options.url)
    //options.method = 'GET'
    //补充header
    // options.header = {
    //   ...options.header,
    //   'source-client': '工大二手堂',
    // }
    // //补充token
    // const useMemberStore = memberStore()
    // const token = useMemberStore.profile?.token
    // if (token) {
    //   options.header.Authorization = token
    // }
    // //设置超时事件
    // options.timeout = 10000
    // console.log('111'+options.url)
  },
  //处理服务器响应
  // returnValue(options: UniApp.RequestSuccessCallbackResult){
  //   if (options.statusCode === 404) {
  //     console.log('响应失败' + 404)
  //     return
  //   }

  // }
}

uni.addInterceptor('request', httpInterceptor)
uni.addInterceptor('uploadFile', httpInterceptor)

/**
 * 请求包装
 *
 */
//对uni.request的包装
export const http = <T>(options: UniApp.RequestOptions) => {
  //返回promise对象
  return new Promise<T>((resolve, reject) => {
    uni.request({
      ...options,
      //请求成功
      success(res) {
        console.log('请求成功')
        //获取响应数据并转换成自定义data格式存储
        resolve(res.data as T)
      },
      fail(error) {
        console.log('请求失败 = ' + JSON.stringify(error))
      },
    })
  })
}
