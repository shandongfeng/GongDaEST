package com.fengxiangkai.controller;

import com.fengxiangkai.pojo.ProductItemResult;
import com.fengxiangkai.pojo.ResultType;
import com.fengxiangkai.service.GetProductItemInfo;
import com.fengxiangkai.utils.ImageHandleUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 处理小程序主页请求，并返回相应商品数据
 */
@Slf4j
@RestController
public class IndexPageController {
    @Autowired
    private GetProductItemInfo getProductItemInfo;

    /**
     * 客户端主页请求滚动区域的productItem展示内容
     * @param requestCount,客户端请求productItem的数量
     * @returnType ProductItemResult[]
     * @key 关于图片信息需要特殊处理
     */
    @GetMapping("/product/getProductInfo")
    @ResponseBody
    private ResultType<ProductItemResult[]> productItemHandle(@RequestParam(value = "startIndex") int startIndex,@RequestParam(value = "requestCount") int requestCount)
    {
        ProductItemResult[] productItemResults; //将要返回的响应数据
        //访问数据库获取信息
        try {
            /**
             * 获取数据库信息
             */
            //指定获取数据库信息的起点和终点(左开右闭)
            int start = startIndex*requestCount, end = startIndex*requestCount+requestCount;
            System.out.println("数据库查询商品信息索引 = " + "[" + start + "," + end + "]");
            //2.判断起点是否超过了数据库长度，len+1才是总长度
            int len = getProductItemInfo.selectDBLength();
            if(len+1 <= start) {
                System.out.println("查询信息，超出数据库范围");
                return ResultType.error(null);
            }
            productItemResults = getProductItemInfo.getProductItemInfo(start, end);
        } catch(Exception e) {
            System.out.println("SQL异常");
            return ResultType.error(null);
        }

        System.out.println("将有" + productItemResults.length + "条数据处理并发送到小程序端");
        for(int i=0;i<productItemResults.length;++i) {
            /**
             * 图片格式处理
             */
            //头像转格式
            String avatarPath = productItemResults[i].getAvatar();
            try {
                System.out.println("头像路径 = " + avatarPath);
                productItemResults[i].setAvatar(ImageHandleUtil.imageToByteArray(avatarPath));
            } catch (IOException e) {
                System.out.println("用户头像转byteArray失败: " + e.toString());
                return ResultType.error(null);
            }
            //商品信息的imageInfo第一个索引成员转格式
            String imageInfoOne = productItemResults[i].getProductInfo().getImageInfo()[0];
            try {
                System.out.println("商品图片路径 = " + imageInfoOne);
                productItemResults[i].getProductInfo().getImageInfo()[0] = ImageHandleUtil.imageToByteArray(imageInfoOne);
            } catch (IOException e) {
                System.out.println("图片转byteArray失败: " + e.toString());
                return ResultType.error(null);
            }
            System.out.println("完成" + (i+1) + "次图片转换");
            //System.out.println(productItemResults[0]);
        }
        return ResultType.success(productItemResults);
    }
    /**
     * 当点击productItem进入详情页时，请求服务器获取剩余图片信息并返回客户端
     */
    @GetMapping("/product/getImageInfo")
    @ResponseBody
    private ResultType<String[]> productItemImageHandle(@RequestParam(value = "imagePath") String imagePath)
    {
        System.out.println(imagePath);
        //去除imagePath中的子字符串
        String[] results = imagePath.split(",");
        for(int i=0;i< results.length;++i)
        {
            try {
                if(results[i] == null)
                    break;
                results[i] = ImageHandleUtil.imageToByteArray(results[i]);
            } catch (IOException e) {
                System.out.println("根据路径获取图片信息失败 = " + e);
                ResultType.error(null);
            }
        }
        System.out.println("处理的图片数量 = " + results.length);
        return ResultType.success((results));
    }

}
