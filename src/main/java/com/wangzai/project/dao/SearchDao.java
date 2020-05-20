package com.wangzai.project.dao;

import com.wangzai.project.entity.Search;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

;

@Repository
public interface SearchDao {

    /**
     * 添加检索
     * @param search            添加的数据
     * @return                  添加结果
     */
    public boolean addSearch(@Param("search") Search search);

    /**
     * 通过id查找检索
     * @param id                检索id
     * @return                  查找到的数据
     */
    public Search findByID(@Param("id") int id);

    /**
     * 通过实体id和类型查询id
     * @param map               实体id和类型
     * @return                  id
     */
    public Object selectID(HashMap<String, Object> map);

}
