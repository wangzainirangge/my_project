package com.wangzai.project.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Base64Util {
    //通过传入的图片数据和图片名称把图片放到指定位置
    public static void saveImg(String base64Data, String photoName){
        String data = "";
        String[] d = base64Data.split("base64,");
        if(d != null && d.length == 2){
            data = d[1];//获取到的图片内容
        }
        byte[] bs = Base64Utils.decodeFromString(data);//对Base64进行解码
        for(int i=0;i<bs.length;++i) {
            if(bs[i]<0) {
                bs[i]+=256;//调整异常数据
            }
        }
        String nowPath = System.getProperty("user.dir");//获得程序当前路径
        System.out.println(nowPath);
        //设置存放图片的路径
        String imgFilePath01="D:/IDEA/毕设/img/"+photoName;
        //String imgFilePath02="D:/IDEA/img/"+from+photoName;
        File file = new File(imgFilePath01);
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(bs,0,bs.length);//通过字节流把图片存储在服务器指定路径
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //根据指定文件名把图片剪切成指定大小
    public static void cutImg(String photoName, String from, int width, int height, int check){
        String type = photoName.substring(photoName.indexOf(".")+1);
        String nowPath = System.getProperty("user.dir");//获得程序当前路径
        //String imgFilePath01="D:/IDEA/img/"+from+photoName;
        String imgFilePath01 = nowPath + "/../webapps/assets/iiat/"+from+photoName;
        String imgFilePath02 = "";
        if(check==1){
            //imgFilePath02 ="D:/图库/"+from+"check01/"+photoName;
            imgFilePath02 = nowPath + "/../webapps/assets/cutIiat/"+from+"check01/"+photoName;
        }else if (check==2){
            //imgFilePath02 ="D:/图库/"+from+"check02/"+photoName;
            imgFilePath02 = nowPath + "/../webapps/assets/cutIiat/"+from+"check02/"+photoName;
        }else {
            //imgFilePath02 ="D:/图库/"+from+photoName;
            imgFilePath02 = nowPath + "/../webapps/assets/cutIiat/"+from+photoName;
        }
        File file01 = new File(imgFilePath01);
        File file02 = new File(imgFilePath02);
        BufferedImage image = null;
        try {
            image = ImageIO.read(file01);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thumbnails.Builder<BufferedImage> builder = null;
        int imageWidth = image.getWidth();
        int imageHeitht = image.getHeight();
        if ((float)height / width != (float)imageWidth / imageHeitht) {
            //把图片从中心开始裁剪成指定大小
            builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, width, height).size(width, height);
        } else {
            builder = Thumbnails.of(image).size(width, height);
        }
        try {
            builder.outputFormat(type).toFile(file02);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //根据传入的图片名称删除指定图片
    public static void deleteImg(String photoName){
        String imgFilePath = System.getProperty("user.dir");//获得程序当前路径
        //设置存放图片的路径;
        //imgFilePath="D:/IDEA/img/"+photoName;
        imgFilePath = imgFilePath + "/../webapps/assets/iiat/" + photoName;
        System.out.println(imgFilePath);
        File file = new File(imgFilePath);
        file.delete();
    }

}
