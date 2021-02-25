package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.vo.User;

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


            PrintWriter pw = response.getWriter();

            pw.println("<!DOCTYPE html>");
            pw.println("<html lang=\"en\">");
            pw.println("<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>用户列表</title>\n" +
                    "</head>");
            pw.println("<body>\n" +
                    "    <center>\n" +
                    "        <h1>用户列表</h1>\n" +
                    "        <table border=\"1\">\n" +
                    "            <tr>\n" +
                    "                <td>用户名</td>\n" +
                    "                <td>密码</td>\n" +
                    "                <td>昵称</td>\n" +
                    "                <td>年龄</td>\n" +
                    "            </tr>");
            for (User user:list){
                pw.println("<tr>\n" +
                        "                <td>"+user.getUsername()+"</td>\n" +
                        "                <td>"+user.getPassword()+"</td>\n" +
                        "                <td>"+user.getNickname()+"</td>\n" +
                        "                <td>"+user.getAge()+"</td>\n" +
                        "            </tr>");
            }

            pw.println(" </table>\n" +
                    "    </center>\n" +
                    "</body>\n" +
                    "</html>");
        System.out.println("页面生成完毕!");
        //
        response.setContentType("text/html");

        System.out.println("ShowAllUserSerlver:用户列表页面处理完毕!");
    }

}

