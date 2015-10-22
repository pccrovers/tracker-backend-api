package com.pccrovers.tracker.api.auth;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class LoginCallbackServlet extends HttpServlet
{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Writer writer = resp.getWriter();
        UserService userService = UserServiceFactory.getUserService();

        String mode = req.getQueryString();
        if(mode.equals("login"))
        {
            if(userService.isUserLoggedIn())
            {
                writer.write("Login successful");
            }
            else
            {
                writer.write("Login failed");
            }
        }
        else if(mode.equals("logout"))
        {
            if(userService.isUserLoggedIn())
            {
                writer.write("Logout failed");
            }
            else
            {
                writer.write("Logout successful");
            }
        }
        else
        {
            resp.setStatus(404);
        }
    }
}
