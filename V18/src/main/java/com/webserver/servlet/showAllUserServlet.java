package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.vo.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class showAllUserServlet {
    public void service(HttpRequest request, HttpResponse response) {
        System.out.println("ShowAllUserSerlver:开始处理用户列表页面......");
        //1：先将user.dat文件中所有用户信息读取出来
        List<User> list = new ArrayList<>();
        try (
                RandomAccessFile raf = new RandomAccessFile("user.dat", "r");
        ) {
            byte[] data = new byte[32];
            for (int i = 0; i < raf.length() / 100; i++) {
                //用户名
                raf.read(data);
                String username = new String(data, "UTF-8").trim();
                //密码
                raf.read(data);
                String password = new String(data, "UTF-8").trim();
                //昵称
                raf.read(data);
                String nickname = new String(data, "UTF-8").trim();
                //读取年龄
                int age = raf.readInt();
                User user = new User(username, password, nickname, age);
                System.out.println(user);
                list.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2根据读取到的用户信息来生成页面


        //使用thymeLeaf将数据与静态页面userList.html结合生成动态页面
        //创建Context实例，thymeleaf提供的，用于保存所有在页面上要显示的数据
        Context context=new Context();//使用类似app
        //将存放所有用户信息的List集合存入Context
        context.setVariable("list",list);

        //初始化thymeleaf模板引擎
        //模板解释器，用来告知模板引擎模板的相关情况（模板就是要结合的静态页面）
        FileTemplateResolver resolver=new FileTemplateResolver();
        resolver.setTemplateMode("html");
        resolver.setCharacterEncoding("UTF-8");
        //实例化模板引擎
        TemplateEngine te =new TemplateEngine();
        ///将模板解释器设置给引擎，这样它就能了解模板的相关信息了
        te.setTemplateResolver(resolver);

        //2.3利用模板引擎将数据与静态页面结合，生成动态页面
        /**
         * process方法用于生成动态页面
         * 参数1：模板位置（静态页面的位置）
         * 参数2：要在页面上显示的动态数据
         * 返回值：生成好的动态页面源代码
         */
        String html=te.process("./webapps/myweb/userList.html",context);
        System.out.println(html);
        System.out.println("页面生成完毕!");
        //
        PrintWriter pw =response.getWriter();
        pw.println(html);
        response.setContentType("text/html");

        System.out.println("ShowAllUserSerlver:用户列表页面处理完毕!");
    }

}

