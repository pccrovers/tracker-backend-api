package com.pccrovers.tracker.api.auth;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.com.google.api.client.http.GenericUrl;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet
{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        PrintWriter writer = resp.getWriter();
        UserService userService = UserServiceFactory.getUserService();

        JsonObject jo = new JsonObject();

        if(userService.isUserLoggedIn())
        {
            jo.addProperty("data", "You're already logged in!");
            jo.addProperty("success", false);
            jo.addProperty("status", 304);

            resp.setStatus(304);
        }
        else
        {
            GenericUrl url = new GenericUrl(req.getRequestURI());
            url.setRawPath("/auth/callback?login");

            jo.addProperty("data", userService.createLoginURL(url.build()));
            jo.addProperty("success", true);
            jo.addProperty("status", 200);

            resp.setStatus(200);
        }

        writer.write(jo.toString());
    }
}
