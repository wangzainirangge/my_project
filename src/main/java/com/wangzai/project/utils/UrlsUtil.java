package com.wangzai.project.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UrlsUtil {

    //把文本存储并返回文件名
    public static String setContent(String content,String id){
        //String time = NowTimeUtil.getFileName();
        String htmlName = id +".html";
        String txtName = id +".txt";
        //创建一个Configuration对象，直接new一个对象，构造方法的参数就是对应的版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        //创建模版所在路径
        ClassPathResource classPathResource = new ClassPathResource("/templates");
        //设置编码格式
        configuration.setDefaultEncoding("utf-8");
        Writer outToHtml = null;
        Writer outTotxt = null;
        try {
            configuration.setDirectoryForTemplateLoading(new File(classPathResource.getURI()));
            //获取模版
            Template template =configuration.getTemplate("test.ftl");
            //创建一个模版所用的数据集
            Map<String, Object> root =new HashMap<>();
            //向数据集中添加数据
            root.put("content",content);
            //创建一个Write对象，生成一个指定文件
            String path = System.getProperty("user.dir");//本地获取项目根目录
            outToHtml = new FileWriter(new File("D:/IDEA/毕设/HTML/"+htmlName));
            outTotxt = new FileWriter(new File("D:/IDEA/毕设/TXT/"+txtName));
            //调用模块对象的process输出
            template.process(root,outToHtml);
            template.process(root,outTotxt);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            if (null!=outTotxt){
                try {
                    outTotxt.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null!=outToHtml){
                try {
                    outToHtml.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return txtName;
        }
    }

    //获取文本内容
    public static String getContent(String htmlName){
        //String path = System.getProperty("user.dir");
        String filePath ="D:/IDEA/毕设/TXT/"+ htmlName;
        String s = null;
        String txt = null;
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((s = bufferedReader.readLine())!=null){
                stringBuffer.append(s.trim());
            }
            txt = stringBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return txt;
        }
    }

    //根据传入的urls名称删除指定txt和html
    public static void deleteUrls(String urls, String type){
        String txt = urls;
        String html = urls.substring(0,urls.indexOf(".")+1)+"html";
        String path = System.getProperty("user.dir");//获得程序当前路径
        //设置存放文本的路径
        //String txtPath = "D:/IDEA/img/"+type+"/txt/"+ txt;
        //String htmlPath = "D:/IDEA/img/"+type+"/html/"+ html;
        String txtPath = path +"/../ce_innovation_data/iiat/"+type+"/TXT/"+ txt;
        String htmlPath = path +"/../ce_innovation_data/iiat/"+type+"/HTML/"+ html;
        File txtFile = new File(txtPath);
        File htmlFile = new File(htmlPath);
        txtFile.delete();
        htmlFile.delete();
    }
}
