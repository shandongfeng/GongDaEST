package com.fengxiangkai.controller;

import com.alibaba.fastjson2.JSON;
import com.fengxiangkai.pojo.*;
import com.fengxiangkai.service.UserInfoService;
import com.fengxiangkai.utils.JWTVerify;
import com.fengxiangkai.utils.ImageHandleUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;

@Slf4j
@RestController
public class UserLoginControler {
//    @CrossOrigin(origins = "http://localhost:64943,http://localhost:5500") // 设置允许跨域请求的来源,否则本电脑其他端口发送请求无效
//    @GetMapping("/login/user")
//    public Result login(@RequestParam String msg){
//        log.info("管理员登录:{}",msg);
//        return Result.success();
//    }
    @Autowired
    private UserInfoService userInfoService;
    //微信小程序注册接口
    @PostMapping ("/register")
    @ResponseBody
    //接收小程序发送的post请求体,并返回token
    public ResultType<UserInfo> userLoginHandle(@RequestBody UserLoginParam userRequestInfo){
        /**
         * 根据手机号判断用户状态(新、老用户)，根据发送的userInfo有无code判断执行（注册还是登录）
         * 问题：因为无法通过手机号向用户发送验证，所以无法保证用户信息的安全，但没有办法
         * 过程：通过手机号访问数据库查看当前登录的用户是否注册过；
         *      1.如果注册过，直接返回之前的注册信息到客户端；
         *      2.未注册，则调用userRegister(userInfo);注册并返回data到客户端
         * 返回类型：loginResult
         */
        //如果code为空，说明是老用户要登录
        if (userRequestInfo.getRequestCode() == 1)
        {
            System.out.println("##########老用户登录##########");
            //根据手机号查询该用户是否注册过
            UserInfo userinfo = userInfoService.selectUser(userRequestInfo.getPhoneNumber());
            try {
                userinfo.setAvatar(ImageHandleUtil.imageToByteArray(userinfo.getAvatar()));
            } catch(IOException e) {
                System.out.println("login: 图片转换成base64字符串失败");
            }
            //不为空，说明是老用户
            if (userinfo != null)
            {
                System.out.println("用户：" +userRequestInfo.getName() + "," + userRequestInfo.getPhoneNumber() +  "查询成功");
                System.out.println("用户登录响应 = " + userinfo);
                return ResultType.success(userinfo);
            }
            //为空，说明该手机号未注册过
            else
            {
                System.out.println("查询失败");
                //发送404错误码
                return ResultType.error(null);
            }
        }
        //code=0，用户注册
        else {
            System.out.println("##########新用户注册##########");
            System.out.println("用户注册响应 = " + userRequestInfo);
            return userRegister(userRequestInfo);
        }
    }

    //用户注册,成功就保存用户到数据库并返回data到客户端
    public ResultType<UserInfo> userRegister(UserLoginParam param){
        String name = param.getName();
        String phoneNumber = param.getPhoneNumber();
        //保存图片信息到本地并返回存储地址
        String imagePath = ImageHandleUtil.uploadImageHandle("image",phoneNumber, param.getAvatar());
        //获取微信后台url
        String appid = "wxa93d455f60f36c3e";
        String appSecret = "3823325348d556e62e255d7f164a4698";
        String code = param.getCode();
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", appid, appSecret, code);
        System.out.println("微信后台url = " + url);
        //向微信后台发送get请求
        try{
            WeixinRequestResult weixin_response = request_user_msg(url);
            //成功获取微信后台返回的小程序登录信息
            System.out.println("发送code到微信后台,返回 = " + weixin_response.toString());
            //返回值errCode等于0，说明成功
            if(weixin_response.getErrcode() == 0) {
                String session_key = weixin_response.getSession_key();
                String openid = weixin_response.getOpenid();
                //添加信息并获取jwt令牌
                HashMap<String,Object> map = new HashMap<>();
                map.put("session_key",session_key);
                map.put("openid",openid);
                String token = JWTVerify.generateJwt(map);
                System.out.println("token = " + token);
                //保存到数据库
                int userId = userInfoService.addUser(session_key,openid,name,imagePath, phoneNumber, token);
                //图片处理
                String avatar = ImageHandleUtil.imageToByteArray(imagePath);
                return ResultType.success(new UserInfo(userId,name,avatar,phoneNumber,token));
            }
        }catch(IOException e){
            System.out.println("register: 图片转换成base64字符串失败");
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return ResultType.error(null);
    }
    //接收到code然后向微信后台发送请求获取appid等用户信息
    public WeixinRequestResult request_user_msg(String url) throws IOException, InterruptedException {
        // 创建 HttpClient 实例
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        // 创建 HttpRequest 实例
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // 发送请求并获取响应
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        //处理响应，字符串转成自定义类型
        return JSON.parseObject(response.body(),WeixinRequestResult.class);
    }


    //@PostMapping("/upload/user/avatar")
    //@GetMapping("/upload/user/image")
//    public int uploadImageHandle(MultipartFile  receiveImage) {
//        if (!receiveImage.isEmpty()) {
//            try {
//                //获取当前路径
//                Path currentDirectory = Paths.get(System.getProperty("user.dir"));
//                //指定保存路径
//                String filePath = currentDirectory.getParent() + "/static/user/image/touxiang.jpg"; // 设置文件保存路径
//                //上传图片信息
//                File file = new File(filePath);
//                receiveImage.transferTo(file);
//                System.out.println("图片成功上传到服务器");
//                return 200;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return 0;
//    }
}
