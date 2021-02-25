package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.regex.Pattern;

public class LoginServlet {
    public void service(HttpRequest request, HttpResponse response) {
        System.out.println("RegServlet:开始处理用户登录....");
/**
 * 1.通过request获取用户在注册页面上输入的注册信息(表单上的信息)
 * 2.将用户的注册信息写入文件user.dat中
 * 3.设置response给客户端响应注册结果页面
 */
//1 注意,这里getParameter方法传入的参数要与注册页面上对应输入框的name属性的值一致！
        String username = request.getParameter("toname");
        String password = request.getParameter("topassword");
        /**
         * 必要的验证工作,如果上述四项有空,或者年龄不是一个数字时,直接响应给客户端一个注册错误
         * 的提示页面：reg_info_error.html，里面居中显示一行字：注册信息输入有误，请重新注册。
         * 注：该页面也放在webapps/myweb这个网络应用中.|
         */
        if (username == null || password == null) {
            File file = new File("./webapps/myweb/login_fail.html");
            response.setEntity(file);
            return;
        }
        System.out.println(username + "," + password);
        /**
         * 2
         * 每条用户信息占用100字节,其中用户名,密码,昵称为字符串各占32字节,年龄为int值占4字节
         */
        try {
            try (
                    RandomAccessFile raf = new RandomAccessFile("user.dat", "rw");

            ) {
                /**
                 * 验证是否为重复用户
                 * 先读取user.dat文件中现有的所有用户的名字，并与本次注册的用户名比对，如果存在则
                 * 直接响应页面：have_user.htmL，居中显示一行字：该用户已存在，请重新注册
                 * 否则才进行注册操作。
                 */
                byte[] buff = new byte[32];
                for (int i = 0; raf.length() / 100 > i; i++) {
                    raf.seek(i * 100);
                    raf.read(buff);
                    String name = new String(buff, "UTF-8").trim();
                    if (username.equals(name)) {
                        raf.read(buff);
                        String topassword = new String(buff, "UTF-8").trim();
                        if (topassword.equals(password)){
                            File file = new File("./webapps/myweb/success.html");
                            response.setEntity(file);
                            System.out.println("RegServlet:用户登录处理完成....");
                            return;
                        }
                    }
                }
                File file = new File("./webapps/myweb/login_fail.html");
                response.setEntity(file);



            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("RegServlet:用户登录处理完成....");
    }
}
