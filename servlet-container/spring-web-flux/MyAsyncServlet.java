package com.async.servlet.web;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/hello" ,asyncSupported = true)
public class MyAsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("=======嘻嘻========");

        //异步非阻塞请求
        AsyncContext asyncContext = req.startAsync();

        asyncContext.start(()->{
            try {
                System.out.println("=======1、触发开始========");
                resp.getWriter().print("Hello World!");
                //触发完成
                asyncContext.complete();
                System.out.println("=======2、触发完成========");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println("=======3、非异步========");
    }
}
