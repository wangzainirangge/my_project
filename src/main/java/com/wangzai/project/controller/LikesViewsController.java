package com.wangzai.project.controller;

import com.wangzai.project.common.BaseModel;
import com.wangzai.project.common.Constants;
import com.wangzai.project.entity.LikesViews;
import com.wangzai.project.service.LikesViewsService;
import com.wangzai.project.utils.NowTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping(value = "/likesViews")
public class LikesViewsController {

    @Autowired
    private LikesViewsService likesViewsService;


    @RequestMapping(value = {"/{id}"},method = RequestMethod.GET)
    public BaseModel<LikesViews> findById(@PathVariable(value = "id") int id){
        BaseModel<LikesViews> baseModel = new BaseModel<>();
        try {
            LikesViews likesViews = likesViewsService.findById(id);
            baseModel.setData(likesViews);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public BaseModel<String> addUser(@RequestBody LikesViews likesViews){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            likesViews.setOperationTime(NowTimeUtil.nowTime());
            likesViewsService.addLikesViews(likesViews);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }

    @RequestMapping(value = "/getStartNum",method = RequestMethod.GET)
    public BaseModel<Integer> getStartNum(@PathParam(value = "userId") int userId,
                                          @PathParam(value = "newsID") int newsID){
        BaseModel<Integer> baseModel = new BaseModel<>();
        try {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("userId",userId);
            hashMap.put("newsID",newsID);
            int countNum = likesViewsService.getStartNum(hashMap);
            baseModel.setData(countNum%2);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }

}
