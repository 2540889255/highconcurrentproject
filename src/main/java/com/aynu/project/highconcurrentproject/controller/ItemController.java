package com.aynu.project.highconcurrentproject.controller;

import com.aynu.project.highconcurrentproject.bean.Item;
import com.aynu.project.highconcurrentproject.bean.ItemHtml;
import com.aynu.project.highconcurrentproject.service.ItemService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: LC
 * @Date : 2021 04 02 23:17
 * @Description : com.aynu.project.highconcurrentproject.controller
 * @Version 1.0
 */
@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    /**
     * 添加新的item界面
     * @param title
     * @param content
     * @param model
     * @return
     */
    @RequestMapping("/additem")
    public String addItem(@PathParam("title")String title,@PathParam("content") String content, Model model){
        System.out.println("desc"+content);
        int id=1;
        String version =String.valueOf(id);
        Item item=new Item();
        item.setId(id);
        item.setTitle(title);
        item.setContent(content);
        item.setLastGenerate(new Date(System.currentTimeMillis()));
        item.setVersion(version);
        String s = itemService.addItem(item);
        model.addAttribute("message",s);
        return "Itemoperation";
    }

    /**
     * 根据id获取到对应的item界面
     *
     *
     * 请求参数可以为@RequestParam("id")注解，请求uri为http://localhost:8086/items?id=1
     * 请求参数可以为PathParam(id)           请求uri为http://localhost:8086/items/1   @RequestMapping("/items/{id}")
     * @return
     */
    @RequestMapping("/items")
    public String itemView(@RequestParam("id")int id, Model model){
        System.out.println(id);
        Item item = itemService.selectItemById(id);
        System.out.println(item.toString());
        model.addAttribute("item",item);

        return "Itemview";
    }

    /**
     * 获取到静态item的列表
     * @param model
     * @return
     */
    @RequestMapping("/itemlist")
    public String itemlist(Model model){

        List<Item> items = itemService.selectitemlist();
        model.addAttribute("items",items);
        return "Itemlist";
    }

    /**
     * 生成item的静态网页
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/generate")
    public String generateitem(@RequestParam("id")int id,Model model){

        itemService.generateitem(id);
        return "success";
    }

    /**
     * 批量生成Item静态文件
     * @param model
     * @return
     */
    @RequestMapping("/generateall")
    public String generateItemAll(Model model){

        List<ItemHtml> itemHtmls = itemService.generateItemAll();

        model.addAttribute("itemhtmls",itemHtmls);
        return "itemresulthtmls";
    }

    /**
     * 生成静态主页页面
     * @param model
     * @return
     */
    @RequestMapping("/generatemainitems")
    public String generateMainItems(Model model){

        List<Item> items = itemService.generateMainItem();

        model.addAttribute("items",items);

        return "mainitems";
    }
    /**
     * 实现网页资源加载到富文本编辑器里
     * @param model
     * @return
     */
    @RequestMapping("/template")
    public String template(Model model){

        String fileHtmlByTemplate = itemService.getFileHtmlByTemplate();
        System.out.println(fileHtmlByTemplate);
        model.addAttribute("item",fileHtmlByTemplate);
        return "template";
    }

    /**
     * 返回实体类页面模板，展示在前端富文本编辑器中，进行动态页面的修改
     * @param model
     * @param content
     * @return
     */
    @RequestMapping("/savetemplate")
    public String saveTemplateByFileName(@RequestParam("content") String content,Model model){
        System.out.println(content);
        itemService.saveTemplateByFileName(content);
        return "success";

    }

    /**
     * 生成item主页
     * @param model
     * @return
     */
    @RequestMapping("/mainitems")
    public String itemsMain(Model model){

        List<Item> items = itemService.selectitemlist();

        model.addAttribute("items",items);

        return  "mainitems";
    }

    /**
     * 健康检查
     * @param model
     * @return
     */
    @RequestMapping("/heath")
    public String heath(Model model){

        HashMap<String,Boolean> map = itemService.heath();

        model.addAttribute("map",map);

        return  "heath";
    }

    @RequestMapping("/editor")
    public String editor(Integer id,Model model){

        Item itemrecord=itemService.findById(id);

        Boolean canWrite=itemService.getLock(id);

        model.addAttribute("item",itemrecord);

        model.addAttribute("canWrite",canWrite);

        return  "editor";
    }


    /**
     * 对于未缓存的页面进行检查
     * @param model
     * @return
     */
    @RequestMapping("/check")
    public String check(Model model){

        List<Item> loseitems = itemService.check();

        model.addAttribute("loseitems",loseitems);

        return  "check";
    }

    /**
     *
     **/
    @Test
    public void test() {

    }
}
