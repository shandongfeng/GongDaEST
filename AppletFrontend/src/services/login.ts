import { http } from '@/utils/http'
import type { loginType, loginResultType } from '@/types/loginType'
//export let sendCompressImagePath = ''
//向微信后台发送请求，获取登录token
export const register_and_getToken = <T>(requestBody: loginType) => {
  return http<T>({
    url: 'register',
    method: 'POST',
    data: requestBody,
  })
}

// //选择图片并压缩，返回文本格式的图片信息
// export const compress_and_transfer = (res: UniApp.ChooseMessageFileSuccessCallbackResult): Promise<string> => {
//   return new Promise((resolve, reject) => {
//     if (res.tempFiles[0].size > 200) {
//       uni.compressImage({
//         src: res.tempFiles[0].path,
//         quality: 100, //压缩质量0-100
//         compressedWidth: 100, //压缩后图片的宽
//         compressedHeight: 100, //压缩后图片的高
//         success: (success) => {
//           const compressImagePath = success.tempFilePath
//           sendCompressImagePath = compressImagePath
//           console.log('压缩图片成功：' + compressImagePath)
//           //图片格式转换
//           uni.getFileSystemManager().readFile({
//             filePath: compressImagePath, //选择图片返回的相对路径
//             encoding: 'base64', //编码格式
//             success(success) {
//               console.log('图片转换成功')
//               //const base64 = 'data:image/jpeg;base64,' + success.data //加上前面字符串，java后台解析base64字符串会报错
//               const base64 = success.data as string
//               resolve(base64)
//             },
//             fail(error) {
//               console.log('图片转换失败 = ' + error)
//               reject(error)
//             },
//           })
//         },
//         fail: (fail) => {
//           console.log('压缩图片失败：' + fail)
//           reject(fail)
//         },
//       })
//     }
//   })
// }
