package com.wangzai.project.controller;

import com.github.pagehelper.PageInfo;
import com.wangzai.project.common.BaseModel;
import com.wangzai.project.common.Constants;
import com.wangzai.project.entity.NewsInformation;
import com.wangzai.project.service.NewsService;
import com.wangzai.project.utils.Base64Util;
import com.wangzai.project.utils.NowTimeUtil;
import com.wangzai.project.utils.UrlsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(value = "/news")
public class NewsController {

    @Autowired
    private NewsService newsService;


    @RequestMapping(value = {"/{id}"},method = RequestMethod.GET)
    public BaseModel<NewsInformation> findById(@PathVariable(value = "id") int id){
        BaseModel<NewsInformation> baseModel = new BaseModel<>();
        try {
            NewsInformation newsInformation = newsService.findById(id);
            newsInformation.setMsgTXT(UrlsUtil.getContent(newsInformation.getHtmlUrl()));
            //System.out.println(newsInformation.getMsgTXT());
            baseModel.setData(newsInformation);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }



    @RequestMapping(value = {""},method = RequestMethod.GET)
    public BaseModel<List<NewsInformation>> allNews(@RequestParam(value = "status") int status,
                                                    @RequestParam(value = "type") int type,
                                                    @RequestParam(value = "pageSize") int pageSize,
                                                    @RequestParam(value = "pageNo") int pageNo){
        BaseModel<List<NewsInformation>> baseModel = new BaseModel<>();
        try {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("status",status);
            hashMap.put("type",type);
            hashMap.put("pageSize",pageSize);
            hashMap.put("pageNo",pageNo);
            List<NewsInformation> list = newsService.allNews(hashMap);
            baseModel.setPage(new PageInfo<>(list));
            //输出数据总数
            //System.out.println(baseModel.getPage().getTotalNum());
            baseModel.setData(list);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }


    @RequestMapping(value = "",method = RequestMethod.POST)
    public BaseModel<String> addNews(@RequestBody NewsInformation newsInformation){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            newsInformation.setCreateTime(NowTimeUtil.nowTime());
            newsInformation.setPushTime(NowTimeUtil.dealDateFormat(newsInformation.getPushTime()));
            String imgBase = newsInformation.getImgBase();
            //获取图片类型
            String photoType = imgBase.substring(11,imgBase.indexOf(";"));
            //随机获取uuid
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            String photoName = uuid + "." + photoType;
            newsInformation.setPhotoUrl(photoName);
            newsInformation.setHtmlUrl(uuid + ".txt");
            Base64Util.saveImg(imgBase,photoName);
            UrlsUtil.setContent(newsInformation.getMsgTXT(),uuid);
            newsService.addNews(newsInformation);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }



    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public BaseModel<String> updateNews(@PathVariable("id") int id,
                                        @RequestBody NewsInformation newsInformation){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            //随机获取uuid
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            if (newsInformation.getImgBase()!=null){
                String imgBase = newsInformation.getImgBase();
                //获取图片类型
                String photoType = imgBase.substring(11,imgBase.indexOf(";"));
                String photoName = uuid + "." + photoType;
                newsInformation.setPhotoUrl(photoName);
                Base64Util.saveImg(imgBase,photoName);
            }
            newsInformation.setHtmlUrl(uuid + ".txt");
            UrlsUtil.setContent(newsInformation.getMsgTXT(),uuid);
            newsInformation.setModifyTime(NowTimeUtil.nowTime());
            newsService.updateNews(newsInformation);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }



    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public BaseModel<String> deleteNews(@PathVariable(value = "id") int id){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            newsService.deleteNews(id);
        } catch (Exception e) {
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.PATCH)
    public BaseModel updateNewsStatus(@PathVariable(value = "id") int id,
                                      @RequestBody NewsInformation newsInformation){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("newsID",id);
            hashMap.put("status",newsInformation.getStatus());
            if (newsInformation.getStatus() == 1){
                hashMap.put("pushTime",NowTimeUtil.nowTime());
            }
            newsService.updateNewsStatus(hashMap);
        } catch (Exception e) {
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }


    @RequestMapping(value = "/property",method = RequestMethod.GET)
    public BaseModel<List<NewsInformation>> selectByProperty(@RequestParam(value = "title") String title,
                                                             @RequestParam(value = "startTime") String startTime,
                                                             @RequestParam(value = "endTime") String endTime,
                                                             @RequestParam(value = "author") String author,
                                                             @RequestParam(value = "status") int status,
                                                             @RequestParam(value = "type") int type,
                                                             @RequestParam(value = "pageSize") int pageSize,
                                                             @RequestParam(value = "pageNo") int pageNo){
        BaseModel<List<NewsInformation>> baseModel = new BaseModel<>();
        try {
            if (startTime.equals("null")){
                startTime = "2000-01-01";
                endTime = "2111-12-31";
            }
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("title",title);
            hashMap.put("startTime",startTime);
            hashMap.put("endTime",endTime);
            hashMap.put("author",author);
            hashMap.put("status",status);
            hashMap.put("type",type);
            hashMap.put("pageSize",pageSize);
            hashMap.put("pageNo",pageNo);
            List<NewsInformation> list = newsService.selectByProperty(hashMap);
            System.out.println(list.size());
            baseModel.setPage(new PageInfo<>(list));
            baseModel.setData(list);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }

        return baseModel;
    }
}
