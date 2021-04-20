package com.aynu.project.highconcurrentproject.service;


import com.aynu.project.highconcurrentproject.bean.Item;
import com.aynu.project.highconcurrentproject.bean.ItemExample;
import com.aynu.project.highconcurrentproject.bean.ItemHtml;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: LC
 * @Date : 2021 04 02 23:21
 * @Description : com.aynu.project.highconcurrentproject.service
 * @Version 1.0
 */

public interface ItemService {

    /**
     * 添加item实体的方法
     * @param item
     * @return
     */
    public String addItem(Item item);

    /**
     * 通过id查询item实体类
     * @param id
     * @return
     */
    public Item selectItemById(int id);

    /**
     * 查询item实体类集合
     * @return
     */
    public List<Item> selectitemlist();

    /**
     * 生成指定实体类的静态界面
     * @param id
     */
    public void generateitem(int id);

    /**
     * 返回实体类页面模板，展示在前端富文本编辑器中，进行动态页面的修改
     * @return
     */
    public String getFileHtmlByTemplate();

    public void saveTemplateByFileName(String content);

    public List<ItemHtml> generateItemAll();

    public List<Item> generateMainItem();

    public HashMap<String, Boolean> heath();

    public List<Item>  check();

    public Item findById(Integer id);

    public Boolean getLock(Integer id);
}
