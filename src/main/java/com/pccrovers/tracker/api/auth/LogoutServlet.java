package com.pccrovers.tracker.api.auth;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LogoutServlet extends HttpServlet
{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        PrintWriter writer = resp.getWriter();
        UserService userService = UserServiceFactory.getUserService();

        JsonObject jo = new JsonObject();

        if(userService.isUserLoggedIn())
        {
            jo.addProperty("data", "You're not logged in");
            jo.addProperty("success", false);
            jo.addProperty("status", 304);

            resp.setStatus(304);
        }
        else
        {
            jo.addProperty("data", userService.createLogoutURL("/auth/callback?login"));
            jo.addProperty("success", true);
            jo.addProperty("status", 200);

            resp.setStatus(200);
        }

        writer.write(jo.toString());
    }
}