package com.yunzhidong.utils;

/**
 * Created by Dell on 2017/3/7.
 * 单例模式实现全局变量
 */
public class Global {
    private  String testUrl="https://test.yunzhidong.com";
    private  String url="https://wwww.yunzhidong.com";

    public String getTestUrl() {
        return testUrl;
    }

    public void setTestUrl(String testUrl) {
        this.testUrl = testUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private static class GlobalHolder{
       private static Global global=new Global();
   }
    private Global(){

    }
    public static Global global(){
        return GlobalHolder.global;
    }

}
