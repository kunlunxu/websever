package com.webserver.http;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
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
        initMimeMapping();

        /**
         * 根据给定的资源后缀名获取到对应的Content-Type的值
         */

    }
    private static void initMimeMapping(){
        try {
            SAXReader reader =new SAXReader();
            Document doc =reader.read("./" +
                    "config/web.xml");
            //获取根标签名字
            Element root =doc.getRootElement();
            //获取所有<emp>标签
            List<Element> list =root.elements("mime-mapping");
            System.out.println("共有"+list.size()+"个<emp>标签");
            for (Element empEle:list){
                String key=empEle.elementText("extension");
                String value=empEle.elementText("mime-type");
                mimeMapping.put(key, value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        mimeMapping.put("html","text/html");
//        mimeMapping.put("css","text/css");
//        mimeMapping.put("js","application/javascript");
//        mimeMapping.put("png","image/png");
//        mimeMapping.put("gif","image/gif");
//        mimeMapping.put("jpg","image/jpeg");
    }


    public static String getMimeType(String ext) {
        return mimeMapping.get(ext);
    }
}
