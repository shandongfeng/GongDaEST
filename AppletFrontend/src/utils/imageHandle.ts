export type uploadImageType = {
  path: string
  data: string
}

const uploadImageMsg: uploadImageType = {
  path: '',
  data: '',
}
//选择图片并压缩转格式，返回string类型的图片路径和base64 string类型的图片数据
export const imageHandle = (): Promise<uploadImageType> => {
  return new Promise((resolve, reject) => {
    uni.chooseMessageFile({
      count: 1, //上传图片数量
      extension: ['.jpg'], //图片格式，仅jpg支持压缩
      type: 'image',
      success(res) {
        console.log('选择图片成功,路径 = ' + res.tempFiles[0].path)
        uploadImageMsg.path = res.tempFiles[0].path
        uni.getFileSystemManager().readFile({
          filePath: res.tempFiles[0].path, //选择图片返回的相对路径
          encoding: 'base64', //编码格式
          success(success) {
            console.log('图片转换成功')
            //const base64 = 'data:image/jpeg;base64,' + success.data //加上前面字符串，java后台解析base64字符串会报错
            const base64 = success.data as string
            uploadImageMsg.data = base64
            //console.log('图片转换成功 = ' + base64)
            resolve(uploadImageMsg)
          },
          fail(error) {
            console.log('图片转换失败 = ' + error)
            reject(error)
          },
        })
        //压缩图片
        // if (res.tempFiles[0].size > 200) {
        //   uni.compressImage({
        //     src: res.tempFiles[0].path,
        //     quality: 100, //压缩质量0-100
        //     compressedWidth: 100, //压缩后图片的宽
        //     compressedHeight: 100, //压缩后图片的高
        //     success: (success) => {
        //       const compressImagePath = success.tempFilePath
        //       uploadImageMsg.path = compressImagePath
        //       console.log('压缩图片成功：' + compressImagePath)
        //       //图片格式转换
        //       uni.getFileSystemManager().readFile({
        //         filePath: compressImagePath, //选择图片返回的相对路径
        //         encoding: 'base64', //编码格式
        //         success(success) {
        //           console.log('图片转换成功')
        //           //const base64 = 'data:image/jpeg;base64,' + success.data //加上前面字符串，java后台解析base64字符串会报错
        //           const base64 = success.data as string
        //           uploadImageMsg.data = base64
        //           resolve(uploadImageMsg)
        //         },
        //         fail(error) {
        //           console.log('图片转换失败 = ' + error)
        //           reject(error)
        //         },
        //       })
        //     },
        //     fail: (fail) => {
        //       console.log('压缩图片失败：' + fail)
        //       reject(fail)
        //     },
        //   })
        // }
      },
    })
  })
}
