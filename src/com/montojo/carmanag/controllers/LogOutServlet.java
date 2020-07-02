package com.montojo.carmanag.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean loggedIn;
        HttpSession session = req.getSession();
        if(session.getAttribute("login") == null){
            loggedIn = false;
            session.setAttribute("login", loggedIn);
            req.setAttribute("error","Please, introduce correct username and password");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req,resp);
        }
        loggedIn = (boolean) session.getAttribute("login");
        System.out.println("login session variable in /logout doGet method: " + loggedIn);

        loggedIn = false;
        session.removeAttribute("login");
        session.setAttribute("login",loggedIn);
        System.out.println("You're logged out!! Session finished: " + req.getSession().getId());
        String newSession = req.changeSessionId();
        System.out.println("New session after loggin out: " + newSession);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req,resp);
    }
}
