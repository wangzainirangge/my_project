package com.wangzai.project.controller;

import com.github.pagehelper.PageInfo;
import com.wangzai.project.common.BaseModel;
import com.wangzai.project.common.Constants;
import com.wangzai.project.entity.LoginInformation;
import com.wangzai.project.entity.UserInformation;
import com.wangzai.project.service.LoginInformationService;
import com.wangzai.project.service.UserService;
import com.wangzai.project.utils.NowTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginInformationService loginInformationService;

    @RequestMapping(value = {"/{id}"},method = RequestMethod.GET)
    public BaseModel<UserInformation> findById(@PathVariable(value = "id") int id){
        BaseModel<UserInformation> baseModel = new BaseModel<>();
        try {
            UserInformation userInformation = userService.findByID(id);
            baseModel.setData(userInformation);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }

    @RequestMapping(value = {""},method = RequestMethod.GET)
    public BaseModel<List<UserInformation>> allUser(@RequestParam(value = "status") int status,
                                                    @RequestParam(value = "pageSize") int pageSize,
                                                    @RequestParam(value = "pageNo") int pageNo){
        BaseModel<List<UserInformation>> baseModel = new BaseModel<>();
        try {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("status",status);
            hashMap.put("pageSize",pageSize);
            hashMap.put("pageNo",pageNo);
            List<UserInformation> list = userService.allUser(hashMap);
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
    public BaseModel<UserInformation> userLogin(@RequestBody UserInformation userInformation,
                                                HttpSession session){
        BaseModel<UserInformation> baseModel = new BaseModel<>();
        try {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("username",userInformation.getUsername());
            hashMap.put("password",userInformation.getPassword());
            UserInformation result = userService.userLogin(hashMap);
            if (result==null){
                baseModel.setStatus(Constants.FAIL_INVALID_DATA);
                baseModel.setMessage("登录失败！");
            }else {
                String nowTime = NowTimeUtil.nowTime();
                HashMap<String, Object> hash = new HashMap<>();
                hash.put("userID",result.getUserID());
                hash.put("lastLoginTime",nowTime);
                hash.put("lastLoginIP",userInformation.getRegisteredIP());
                userService.updateLoginData(hash);
                LoginInformation loginInformation = new LoginInformation();
                loginInformation.setPersonID(result.getUserID());
                loginInformation.setLoginIP(result.getLastLoginIP());
                loginInformation.setLoginTime(nowTime);
                loginInformation.setType(3);
                loginInformationService.addLoginInformation(loginInformation);
            }
            baseModel.setData(result);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public BaseModel<String> addUser(@RequestBody UserInformation userInformation){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            userInformation.setCreateTime(NowTimeUtil.nowTime());
            userService.addUser(userInformation);
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
    public BaseModel<String> updateUser(@PathVariable("id") int id,
                                        @RequestBody UserInformation userInformation){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            userInformation.setModifyTime(NowTimeUtil.nowTime());
            userService.updateUser(userInformation);
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public BaseModel<String> deleteUser(@PathVariable(value = "id") int id){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PATCH)
    public BaseModel<String> updateUserState(@PathVariable(value = "id") int id,
                                     @RequestBody UserInformation userInformation){
        BaseModel<String> baseModel = new BaseModel<>();
        try {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("userID",id);
            hashMap.put("status",userInformation.getStatus());
            hashMap.put("modifyTime", NowTimeUtil.nowTime());
            userService.updateUserState(hashMap);
        } catch (Exception e) {
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;
    }

    @RequestMapping(value = "/property",method = RequestMethod.GET)
    public BaseModel<List<UserInformation>> selectByProperty(@RequestParam(value = "username") String username,
                                                            @RequestParam(value = "email") String email,
                                                            @RequestParam(value = "pageSize") int pageSize,
                                                            @RequestParam(value = "pageNo") int pageNo){
        BaseModel<List<UserInformation>> baseModel = new BaseModel<>();
        try {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("username",username);
            hashMap.put("email",email);
            hashMap.put("pageSize",pageSize);
            hashMap.put("pageNo",pageNo);
            List<UserInformation> list = userService.selectByProperty(hashMap);
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
