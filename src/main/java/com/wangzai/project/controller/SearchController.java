package com.wangzai.project.controller;

import com.wangzai.project.common.BaseModel;
import com.wangzai.project.common.Constants;
import com.wangzai.project.entity.FindUrls;
import com.wangzai.project.entity.Search;
import com.wangzai.project.service.SearchService;
import com.wangzai.project.utils.ESUtil;
import com.wangzai.project.utils.NowTimeUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/searches")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private ESUtil esUtil;

    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 通过索引id查询索引信息
     * @param id            索引id
     * @return              查询到的数据
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Search findByID(@PathVariable(value = "id") int id){
        Search search = searchService.findByID(id);
        return search;
    }

    /**
     * 通过关键词搜索符合的数据
     * @param keyword       关键词
     * @return              符合的数据
     */
    @RequestMapping(value = "/searchUrls",method = RequestMethod.GET)
    public BaseModel<List<FindUrls>> searchUrls(@RequestParam(value = "keyword",required = false,defaultValue = "") String keyword,
                                                @RequestParam(value = "type") String type){

        /*@RequestParam(value = "pageIndex") Integer pageIndex,
        @RequestParam(value = "pageSize") Integer pageSize*/
        BaseModel<List<FindUrls>> baseModel = new BaseModel<>();
        try {
            List<FindUrls> list = esUtil.searchKeyword(keyword,"news",1,50);
            Calendar calendar = Calendar.getInstance();
            Date endTime = new Date();
            calendar.setTime(endTime);
            if (type.equals("1")){
                calendar.add(Calendar.DATE, - 1);
            }else if (type.equals("2")){
                calendar.add(Calendar.DATE, - 7);
            }else if (type.equals("3")){
                calendar.add(Calendar.MONTH, -1);
            }else if (type.equals("4")){
                calendar.add(Calendar.YEAR, -1);
            }else {
                List<FindUrls> publicList = new ArrayList<>();
                for (FindUrls findUrls:list){
                    if (findUrls.getState()==1){
                        publicList.add(findUrls);
                    }
                }
                baseModel.setData(publicList);
                return baseModel;
            }
            Date startTime = calendar.getTime();
            baseModel.setData(NowTimeUtil.SearchByTime(list,startTime,endTime));
        } catch (Exception e){
            baseModel.setStatus(Constants.FAIL_SERVER_ERROR);
            baseModel.setMessage(Constants.ERROR_MSG);
            e.printStackTrace();
        }
        return baseModel;


    }

}
