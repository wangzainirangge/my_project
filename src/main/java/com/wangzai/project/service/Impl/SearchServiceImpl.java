package com.wangzai.project.service.Impl;

import com.wangzai.project.dao.SearchDao;
import com.wangzai.project.entity.Search;
import com.wangzai.project.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    /**
     * 添加检索
     * @param search            添加的数据
     * @return                  添加结果
     */
    @Override
    public void addSearch(Search search) {
        searchDao.addSearch(search);
    }

    /**
     * 通过id查找检索
     * @param id                检索id
     * @return                  查找到的数据
     */
    @Override
    public Search findByID(int id) {
        Search search = searchDao.findByID(id);
        return search;
    }

    /**
     * 通过实体id和类型查询id
     * @param map               实体id和类型
     * @return                  id
     */
    @Override
    public Object selectID(HashMap<String, Object> map) {
        Object id = searchDao.selectID(map);
        return id;
    }

}
