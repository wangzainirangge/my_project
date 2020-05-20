package com.wangzai.project.controller;

import com.github.pagehelper.PageInfo;
import com.wangzai.project.common.BaseModel;
import com.wangzai.project.common.Constants;
import com.wangzai.project.entity.LoginInformation;
import com.wangzai.project.entity.Manage;
import com.wangzai.project.service.LoginInformationService;
import com.wangzai.project.service.ManageService;
import com.wangzai.project.utils.NowTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/manage")
public class ManageController {

    @Autowired
    private ManageService manageService;
    @Autowired
    private LoginInformationService loginInformationService;


    @RequestMapping(value = {"/{id}"},method = RequestMethod.GET)
    public BaseModel<Manage> findById(@PathVariable(value = "id") int id){
        BaseModel<Manage> baseModel = new BaseModel<>();
        try {
            Manage manage = manageService.findByID(id);
            baseModel.setData(manage);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }

    /**
     *
     * @param identityLevel     1 运营者 2 管理员
     * @param status            1 可用 2 不可用
     * @param pageSize
     * @param pageNo
     * @return
     */
    @RequestMapping(value = {""},method = RequestMethod.GET)
    public BaseModel<List<Manage>> allManage(@RequestParam(value = "identityLevel") int identityLevel,
                                             @RequestParam(value = "status") int status,
                                             @RequestParam(value = "pageSize") int pageSize,
                                             @RequestParam(value = "pageNo") int pageNo){
        BaseModel<List<Manage>> baseModel = new BaseModel<>();
        try {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("identityLevel",identityLevel);
            hashMap.put("status",status);
            hashMap.put("pageSize",pageSize);
            hashMap.put("pageNo",pageNo);
            List<Manage> list = manageService.allManage(hashMap);
            baseModel.setPage(new PageInfo<>(list));
            baseModel.setData(list);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public BaseModel<Manage> manageLogin(@RequestBody Manage manage,
                                         HttpSession session){
        BaseModel<Manage> baseModel = new BaseModel<>();
        try {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("username",manage.getUsername());
            hashMap.put("password",manage.getPassword());
            hashMap.put("identityLevel",manage.getIdentityLevel());
            Manage result = manageService.manageLogin(hashMap);
            if (result==null){
                baseModel.setStatus(Constants.FAIL_INVALID_DATA);
                baseModel.setMessage("登录失败！");
            }else {
                result.setLastLoginTime(NowTimeUtil.nowTime());
                result.setLastLoginIP(manage.getLastLoginIP());
                manageService.updateManage(result);
                baseModel.setData(result);
                LoginInformation loginInformation = new LoginInformation();
                loginInformation.setPersonID(result.getManageID());
                loginInformation.setLoginIP(result.getLastLoginIP());
                loginInformation.setLoginTime(result.getLastLoginTime());
                loginInformation.setType(result.getIdentityLevel());
                loginInformationService.addLoginInformation(loginInformation);
            }
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public BaseModel<String> addManage(@RequestBody Manage manage){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            manage.setCreateTime(NowTimeUtil.nowTime());
            manageService.addManage(manage);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage("添加失败！");
            Throwable cause = e.getCause();
            if(cause instanceof java.sql.SQLIntegrityConstraintViolationException){
                baseModel.setMessage("用户名已被注册，请选择其它用户名！");
            }
            e.printStackTrace();
        }
        return baseModel;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public BaseModel<String> updateManage(@PathVariable("id") int id,
                                          @RequestBody Manage manage){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            manage.setModifyTime(NowTimeUtil.nowTime());
            manageService.updateManage(manage);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public BaseModel<String> deleteManage(@PathVariable(value = "id") int id){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            manageService.deleteManage(id);
        } catch (Exception e) {
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PATCH)
    public BaseModel<String> updateManageState(@PathVariable(value = "id") int id,
                                               @RequestBody Manage manage){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("manageID",id);
            hashMap.put("status",manage.getStatus());
            hashMap.put("modifyTime",NowTimeUtil.nowTime());
            manageService.updateManageState(hashMap);
        } catch (Exception e) {
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }


    @RequestMapping(value = "/property",method = RequestMethod.GET)
    public BaseModel<List<Manage>> selectByProperty(@RequestParam(value = "username") String username,
                                                    @RequestParam(value = "realName") String realName,
                                                    @RequestParam(value = "pageSize") int pageSize,
                                                    @RequestParam(value = "pageNo") int pageNo){
        BaseModel<List<Manage>> baseModel = new BaseModel<>();
        try {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("username",username);
            hashMap.put("realName",realName);
            hashMap.put("pageSize",pageSize);
            hashMap.put("pageNo",pageNo);
            List<Manage> list = manageService.selectByProperty(hashMap);
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
