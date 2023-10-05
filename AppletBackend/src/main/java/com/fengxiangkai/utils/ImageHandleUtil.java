package com.fengxiangkai.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;

public class ImageHandleUtil {
    //上传用户图片请求处理
    public static String uploadImageHandle(String type,String phoneNumber,String  base64Image) {
        if (!base64Image.isEmpty()) {
            try {
                //1.base64解码
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                //2.获取当前路径
                Path currentDirectory = Paths.get(System.getProperty("user.dir"));
                //3.指定保存路径
                Random random = new Random();
                int number = random.nextInt(200)+1;
                //保证图片名字不重复
                while(Files.exists(Paths.get(currentDirectory.getParent() + "\\static\\" + type + "\\" + phoneNumber + "\\" + number + ".jpg"))){
                    number = random.nextInt(200)+1;
                }
                String filePath = currentDirectory.getParent() + "\\static\\" + type + "\\" + phoneNumber + "\\" + number + ".jpg"; // 设置文件保存路径
                //4.获取图片路径的父文件夹，如果不存在则创建
                Path directoryPath = Paths.get(filePath).getParent();
                boolean directoryExists = Files.exists(directoryPath);
                if (!directoryExists) {
                    Files.createDirectories(directoryPath);
                }
                //5.将解码后的字节流写入文件
                OutputStream outputStream = new FileOutputStream(filePath);
                outputStream.write(imageBytes);
                outputStream.close();
                return filePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    //根据路径解析图片
    public static String imageToByteArray(String path) throws IOException
    {
        BufferedImage image = ImageIO.read(new File(path));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        //不加它data:image/jpg;base64,小程序无法识别返回字符串为base64类型
        return "data:image/jpg;base64," + Base64.getEncoder().encodeToString(imageBytes);



        //正则匹配路径中的手机号
//        Pattern pattern = Pattern.compile("\\b(1[0-9]\\d{9})\\b");
//        Matcher matcher = pattern.matcher(path);
//        if (matcher.find()) {
//            String phoneNumber = matcher.group();
//
//        } else {
//            System.out.println("图片路径错误");
//            return "";
//        }
    }
}
