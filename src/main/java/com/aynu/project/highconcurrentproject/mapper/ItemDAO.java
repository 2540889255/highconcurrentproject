package com.aynu.project.highconcurrentproject.mapper;

import com.aynu.project.highconcurrentproject.bean.Item;
import com.aynu.project.highconcurrentproject.bean.ItemExample;
import com.aynu.project.highconcurrentproject.bean.ItemHtml;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ItemDAO继承基类
 */
@Mapper
public interface ItemDAO extends MyBatisBaseDao<Item, Integer, ItemExample> {


    @Select("select * from item")
    public List<ItemHtml>  selectItemAll();
}