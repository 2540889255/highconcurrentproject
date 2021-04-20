package com.aynu.project.highconcurrentproject.service.impl;

import com.aynu.project.highconcurrentproject.bean.Item;
import com.aynu.project.highconcurrentproject.bean.ItemExample;
import com.aynu.project.highconcurrentproject.bean.ItemHtml;
import com.aynu.project.highconcurrentproject.mapper.ItemDAO;
import com.aynu.project.highconcurrentproject.service.ItemService;
import com.jfinal.kit.Kv;
import com.jfinal.template.Engine;
import com.jfinal.template.Template;
import com.jfinal.template.ext.spring.JFinalViewResolver;
import com.jfinal.template.io.WriterBuffer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @Auther: LC
 * @Date : 2021 04 02 23:23
 * @Description : com.aynu.project.highconcurrentproject.service
 * @Version 1.0
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private static List<Integer>  locks =new ArrayList<>();

    @Autowired
    ItemDAO itemDAO;

    @Value(value = "${nginx.html.root}")
    String htmlRoot;

    @Value(value = "${jfinal.templates.location}")
    String templateslocation;
    @Override
    public String addItem(Item item) {

        int insert = itemDAO.insert(item);

        String result="";
        if (insert==1){
            result="success";
        }else {
            result="faild";
        }
        return result;
    }

    @Override
    public Item selectItemById(int id) {
        Item item = itemDAO.selectByPrimaryKey(id);
        if (item==null){
            System.out.println("无该item类");
        }
        return item;
    }


    @Override
    public List<Item> selectitemlist(){
        ItemExample itemExample=new ItemExample();

        return itemDAO.selectByExample(itemExample);
    }

    @Override
    public void generateitem(int id) {

        Engine engine = JFinalViewResolver.engine;


        System.out.println("模板引擎中的engine"+ToStringBuilder.reflectionToString(engine));
        Item item = itemDAO.selectByPrimaryKey(id);

        //初始化模板引擎
        /*Engine engine=Engine.use();

        engine.setDevMode(true);

        engine.setToClassPathSourceFactory();

        System.out.println("自创建的engine"+ToStringBuilder.reflectionToString(engine));*/
        //获取到对应的模板
        Template template = engine.getTemplate("Itemview.html");

        //设置kv值,前端模板使用的item类的键值对
        Kv kv=Kv.by("item",item);

        String fileName="item"+id+".html";
        //路径，能直接被用户访问到的路径
        String filePath=htmlRoot;

        File  file=new File(filePath+fileName);
        //设定文件的位置

        template.render(kv,file);

    }

    @Override
    public String getFileHtmlByTemplate() {

        //tomcat下部署项目可以使用以下的代码
        //String file = ClassLoader.getSystemResource("templates/Itemview.html").getFile();

        //linux系统下使用如下可变路径
        String file = templateslocation+"Itemview.html";
        BufferedReader reader = null;
        //获取静态文件的输入流
        try {
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //读写缓冲区
        StringBuffer stringBuffer=new StringBuffer();
        String result="";
        try {
            result = reader.readLine();
        } catch (IOException e) {

        }
        while (result!=null){
            stringBuffer.append(result).append("\r\n");
            try {
                result= reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuffer.toString();
    }

    @Override
    public void saveTemplateByFileName(String content) {
        //jfinal中的解析方式
        //String file = ClassUtils.getDefaultClassLoader().getResource("templates/Itemview.html").getFile();
        //读取本地文件地址的方法，获取文件流
        //String file="D:\\upload\\Itemview.html";

        //linux系统下使用如下可变路径
        String file = templateslocation+"Itemview.html";
        try {
            BufferedWriter writer=new BufferedWriter(new FileWriter(file));
            writer.write("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"utf-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n" +
                    "    <title>流量测试管理系统</title>\n" +
                    "    <link rel=\"stylesheet\" href=\"/layui/css/layui.css\">\n" +
                    "    <script type=\"text/javascript\"  src=\"/layui/layui.js\" ></script>\n" +
                    "</head>\n" +
                    "<body>");
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ItemHtml> generateItemAll() {

        List<ItemHtml>  itemHtmls= itemDAO.selectItemAll();
        Engine engine = JFinalViewResolver.engine;

        System.out.println("模板引擎中的engine"+ToStringBuilder.reflectionToString(engine));
        Template template = engine.getTemplate("Itemview.html");


        for (ItemHtml item:itemHtmls
             ) {

            Kv kv=Kv.by("item",item);

            String fileName="item"+item.getId()+".html";
            //路径，能直接被用户访问到的路径
            String filePath=htmlRoot;

            File  files=new File(filePath+fileName);
            //设定文件的位置

            template.render(kv,files);

            item.setHtmlState("OK");
            item.setLocation("E:\\javaproject\\highconcurrentproject\\src\\main\\resources\\templates\\");
        }


        return itemHtmls;
    }

    @Override
    public List<Item> generateMainItem() {

        Engine engine = JFinalViewResolver.engine;


        //Template template = use.getTemplate(templateslocation+"mainitems.html");
        Template template = engine.getTemplate("mainitems.html");

        ItemExample example=new ItemExample();

        List<Item> items = itemDAO.selectByExample(example);

        Kv kv= Kv.by("items",items);
        String localtion=htmlRoot;

        String fileName="mainitems.html";
        File file = new File(localtion+fileName);
        template.render(kv,file);
        return items;
    }

    /**
     * 健康检查，使用InetAddress类对于地址ip进行联通检查
     * @return
     */
    @Override
    public HashMap<String, Boolean> heath() {

        HashMap hashMap=new HashMap();

        hashMap.put("182.92.86.253",null);
        hashMap.put("182.92.86.252",null);
        hashMap.put("182.92.86.251",null);
        hashMap.put("182.92.86.250",null);

        Set<String> set = hashMap.keySet();

        for (String key:set
             ) {
            try {
                InetAddress inetAddress=InetAddress.getByName(key);
                hashMap.put(key,inetAddress.isReachable(3000));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return hashMap;
    }


    @Override
    public List<Item> check(){
        ItemExample example=new ItemExample();

        List<Item> items = itemDAO.selectByExample(example);

        List<Item> error = new ArrayList<>();

        for (Item item :items) {

            File file=new File(htmlRoot+"item"+item.getId()+".html");

            if (!file.exists()){
                error.add(item);
            }
        }
        return error;
    }

    @Override
    public Item findById(Integer id) {
        Item item = itemDAO.selectByPrimaryKey(id);

        return item;
    }


    @Override
    public synchronized Boolean getLock(Integer id) {
        //去Lock中去取id,有的话说明不可写，没有的话，添加一个id进去
        int index = locks.indexOf(id);

        if (index==-1){
            locks.add(id);
            return true;
        }else {
            //需要处理死锁问题
            return false;
        }
    }


    public static void main(String[] args) {

    }

    /**
     *
     **/
    @Test
    public void test() {

    }
}
