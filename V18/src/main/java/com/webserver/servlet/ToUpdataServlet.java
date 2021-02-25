package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class ToUpdataServlet {
    public void service(HttpRequest request, HttpResponse response){
        String username =request.getParameter("username");
        try(
                RandomAccessFile raf = new RandomAccessFile("user.dat", "r");
                ) {
            byte[] data =new byte[32];
           for (int i=0;i<raf.length()/100;i++){
               raf.seek(i*100);
               raf.read();
               String name=new String(data,"UTF-8").trim();
               if (name.equals(username)){
                   //找到该用户，读取该用户其他信息
                   raf.read(data);
                   String pwd = new String(data,"UTF-8").trim();
                   raf.read(data);
                   String nick = new String(data,"UTF-8").trim();
                   int age = raf.readInt();
                   response.setContentType("text/html");

               }

           }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
