package com.aynu.project.highconcurrentproject.controller;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: LC
 * @Date : 2021 04 01 23:41
 * @Description : com.aynu.project.highconcurrentproject.controller
 * @Version 1.0
 */
@Controller
public class MainController {

    /**
     * 展示主界面
     * @return
     */
    @RequestMapping("/main")
    public String index(){

        return "Main";
    }

    /**
     * 展示发布界面
     * @return
     */
    @RequestMapping("item")
    public String item(){

        return "Itemoperation";
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
