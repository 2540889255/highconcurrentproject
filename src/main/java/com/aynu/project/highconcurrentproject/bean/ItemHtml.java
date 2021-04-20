package com.aynu.project.highconcurrentproject.bean;


/**
 * @Auther: LC
 * @Date : 2021 04 07 22:46
 * @Description : com.aynu.project.highconcurrentproject.bean
 * @Version 1.0
 */
public class ItemHtml extends Item{

    private static final long serialVersionUID = 1L;

    private String htmlState;

    private String location;


    public String getHtmlState() {
        return htmlState;
    }

    public void setHtmlState(String htmlState) {
        this.htmlState = htmlState;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static void main(String[] args) {

    }


}
