package com.aynu.project.highconcurrentproject.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Auther: LC
 * @Date : 2021 04 09 16:14
 * @Description : com.aynu.project.highconcurrentproject.controller
 * @Version 1.0
 */

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    public String defaultException(Exception exception, Model model){

        model.addAttribute("exception",exception);
        System.out.println("-----");
        return "error";
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
