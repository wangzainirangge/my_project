package com.wangzai.project.controller;

import com.wangzai.project.common.BaseModel;
import com.wangzai.project.common.Constants;
import com.wangzai.project.entity.NewsComments;
import com.wangzai.project.service.NewsCommentsService;
import com.wangzai.project.utils.NowTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/newsComments")
public class NewsCommentsController {

    @Autowired
    private NewsCommentsService newsCommentsService;


    @RequestMapping(value = {"/{id}"},method = RequestMethod.GET)
    public BaseModel<NewsComments> findById(@PathVariable(value = "id") int id){
        BaseModel<NewsComments> baseModel = new BaseModel<>();
        try {
            NewsComments newsComments = newsCommentsService.findById(id);
            baseModel.setData(newsComments);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }



    @RequestMapping(value = {""},method = RequestMethod.GET)
    public BaseModel<List<NewsComments>> allNews(@RequestParam(value = "status") int status){
        BaseModel<List<NewsComments>> baseModel = new BaseModel<>();
        try {
            List<NewsComments> list = newsCommentsService.allNewsComments(status);
            baseModel.setData(list);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }


    @RequestMapping(value = "",method = RequestMethod.POST)
    public BaseModel<String> addNews(@RequestBody NewsComments newsComments){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            newsComments.setCreateTime(NowTimeUtil.nowTime());
            newsCommentsService.addNewsComments(newsComments);
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
            newsCommentsService.deleteNewsComments(id);
        } catch (Exception e) {
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }

}
