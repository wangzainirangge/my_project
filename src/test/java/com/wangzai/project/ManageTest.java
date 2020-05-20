package com.wangzai.project;

import com.wangzai.project.entity.LikesViews;
import com.wangzai.project.entity.LoginInformation;
import com.wangzai.project.entity.Manage;
import com.wangzai.project.entity.NewsComments;
import com.wangzai.project.service.LikesViewsService;
import com.wangzai.project.service.LoginInformationService;
import com.wangzai.project.service.ManageService;
import com.wangzai.project.service.NewsCommentsService;
import com.wangzai.project.utils.NowTimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManageTest {

    @Autowired
    private ManageService manageService;

    @Autowired
    private LoginInformationService loginInformationService;

    @Autowired
    private LikesViewsService likesViewsService;

    @Resource
    private NewsCommentsService newsCommentsService;

    @Test
    public void addManage() {
        Manage manage = new Manage();
        manage.setUsername("aaaaa");
        manage.setPassword("1111111111");
        manage.setRealName("张三");
        manage.setGender(1);
        manage.setType(1);
        manage.setIdentityLevel(2);
        manage.setEmail("gsfgskhgskhgsgd");
        manage.setCreateTime(NowTimeUtil.nowTime());
        manage.setRegisteredIP("sdhgksghjdfdg");
        manageService.addManage(manage);
    }

    @Test
    public void addLoginInformation() {
        LoginInformation loginInformation = new LoginInformation();
        loginInformation.setPersonID(1);
        loginInformation.setLoginIP("aaaaa");
        loginInformation.setLoginTime(NowTimeUtil.nowTime());
        loginInformation.setType(1);
        loginInformationService.addLoginInformation(loginInformation);
    }

    @Test
    public void addLikesViews() {
        LikesViews likesViews = new LikesViews();
        likesViews.setNewsId(1);
        likesViews.setUserId(1);
        likesViews.setType(1);
        likesViews.setOperationTime(NowTimeUtil.nowTime());
        likesViews.setUserIP("aaaaaa");
        likesViewsService.addLikesViews(likesViews);
    }

    @Test
    public void addNewsComments(){
        NewsComments newsComments = new NewsComments();
        newsComments.setNewsID(1);
        newsComments.setUseID(1);
        newsComments.setContent("aaaaaaaaaaaaaaaaaaaaaaa");
        newsComments.setCreateTime(NowTimeUtil.nowTime());
        newsComments.setCreateIP("232332");
        newsCommentsService.addNewsComments(newsComments);
    }


}
