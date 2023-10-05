package com.fengxiangkai.controller;

import com.fengxiangkai.pojo.Admin;
import com.fengxiangkai.pojo.Result;

import com.fengxiangkai.service.AdminService;
import com.fengxiangkai.utils.JWTVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j  //日志
@RestController
public class LoginController {
    @Autowired
    private AdminService adminService;

    @CrossOrigin(origins = "http://localhost:7000") // 设置允许跨域请求的来源,否则本电脑其他端口发送请求无效
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        //输出登录信息到日志
        log.info("管理员登录:{}",admin);
        //校验身份
        Admin e = adminService.login(admin);
        //如果验证成功，生成并下发令牌
        if(e != null){
            Map<String ,Object> map = new HashMap<>();
            map.put("username",e.getUsername());
            map.put("password",e.getPassword());
            return Result.success(JWTVerify.generateJwt(map));
        }else {
            return Result.error("账号或密码错误");
        }
        //return e!=null?Result.success():Result.error("aa");
    }
}

//import com.fengxiangkai.pojo.applet.ProductMessage;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.time.Duration;
//
//@Slf4j
//@RestController
//public class LoginController {
//    //@CrossOrigin(origins = "http://localhost:64943,http://localhost:5500") // 设置允许跨域请求的来源,否则本电脑其他端口发送请求无效
//    @PostMapping("/messageStore")
//    public Result login(@RequestBody ProductMessage msg){
//        log.info("管理员登录:{}",msg);
//        return Result.success();
//    }
//    //微信小程序注册接口
//    @GetMapping("/register")
//    @ResponseBody
//    //接收小程序发送的post请求体
//    public void register(@RequestParam String verifyCode){
//        String appid = "wxa93d455f60f36c3e";
//        String appSecret = "3823325348d556e62e255d7f164a4698";
//        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", appid, appSecret, verifyCode);
//        //向微信后台发送get请求
//        try{
//            String weixin_response = request_user_msg(url);
//            if(weixin_response != null) {
//
//            }
//        }catch(IOException e){
//            e.printStackTrace();
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//
//    }
//    //接收到code然后向微信后台发送请求获取appid等用户信息
//    public String request_user_msg(String url) throws IOException, InterruptedException {
//        // 创建 HttpClient 实例
//        HttpClient httpClient = HttpClient.newBuilder()
//                .connectTimeout(Duration.ofSeconds(10))
//                .build();
//
//        // 创建 HttpRequest 实例
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .GET()
//                .build();
//
//        // 发送请求并获取响应
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//        //处理响应
//        if(response.statusCode() == 200){
//            return response.body();
//        }
//        return null;
//    }
//}