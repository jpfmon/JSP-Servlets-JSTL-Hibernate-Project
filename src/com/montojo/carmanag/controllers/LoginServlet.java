package com.montojo.carmanag.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        System.out.println("**************************************************************");
        System.out.println("Init of LoginServlet");
        System.out.println("**************************************************************");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean loggedIn;
//        System.out.println("Aqui andamos, en login servlet, GET method");
        HttpSession session = req.getSession();
        if(session.getAttribute("login") == null){
            loggedIn = false;
            session.setAttribute("login", loggedIn);
            req.setAttribute("error","Please, introduce correct username and password");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req,resp);
        }
//        loggedIn = (boolean) session.getAttribute("login");
//        System.out.println("Vuelves, pero estas loggedin: " + loggedIn);
//        resp.setContentType("text/html");
//        PrintWriter out = resp.getWriter();
//        out.println("<html><body>");
//        out.println("Hola desde LoginServlet, GET method");

        req.setAttribute("error","Please, introduce correct username and password");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean loggedIn;
        HttpSession session = req.getSession();
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("Hola desde LoginServlet, POST method");

        String validuser = "1";
        String validpass = "1";

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        username = username.trim().toLowerCase();
        password = password.trim().toLowerCase();

        if(username.equals(validuser) && password.equals(validpass)){
            loggedIn = true;
            String fakedUsername = "LOGGED_USER";
            session.setAttribute("login",loggedIn);
            session.setAttribute("userName",fakedUsername);

            out.println("Username is: " + username +  " <br> and Password is: " + password);
            resp.sendRedirect("/dashboard");


//             As we're using sendRedirect, no more dispatcher is needed
//            RequestDispatcher dispatcher = req.getRequestDispatcher("/dashboard");
//            dispatcher.forward(req,resp);
        }else{
            loggedIn = false;
            session.setAttribute("login", loggedIn);
            req.setAttribute("error","Please, introduce correct username and password");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req,resp);
        }
    }
}
