package com.webserver.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 当前类用于保存所有与HTTP协议相关的规定内容以便重用
 */
public class HttpContext {
    /**
     * 资源后缀名与响应头Content-Type值的对应关系
     * key:资源后缀名
     * value:Content-Type对应的值
     */
    private static Map<String, String> mimeMapping =new HashMap<>();
    static {
        mimeMapping.put("html","text/html");

        /**
         * 根据给定的资源后缀名获取到对应的Content-Type的值
         */
        private static void initMimeMapping(String ext){
        return mimeMapping.get(ext);
    }
}
