package com.wangzai.project.service;

import com.wangzai.project.entity.Search;

import java.util.HashMap;

public interface SearchService {
    /**
     * 添加检索
     * @param search            添加的数据
     * @return                  添加结果
     */
    public void addSearch(Search search);

    /**
     * 通过id查找检索
     * @param id                检索id
     * @return                  查找到的数据
     */
    public Search findByID(int id);

    /**
     * 通过实体id和类型查询id
     * @param map               实体id和类型
     * @return                  id
     */
    public Object selectID(HashMap<String, Object> map);

}
