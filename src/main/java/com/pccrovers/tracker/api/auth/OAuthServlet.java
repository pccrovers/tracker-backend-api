package com.pccrovers.tracker.api.auth;

import com.google.api.client.http.GenericUrl;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;

public class OAuthServlet extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        UserService userService = UserServiceFactory.getUserService();
        Writer writer = resp.getWriter();

        if(userService.isUserLoggedIn())
        {
            User user = userService.getCurrentUser();
            String queryString = req.getQueryString();

            HttpSession session = req.getSession();

            OAuth2Manager oAuth2Manager;
            if(queryString != null)
                oAuth2Manager = new OAuth2Manager(user.getUserId(), queryString.split("&"));
            else
                oAuth2Manager = new OAuth2Manager(user.getUserId());

            session.setAttribute("scope", oAuth2Manager.getScopes());

            GenericUrl url = new GenericUrl(req.getRequestURL().toString());
            url.setRawPath("/oauth2/callback");

            resp.sendRedirect(oAuth2Manager.createAuthorizationURL(url.build()));
        }
        else
        {
            resp.setStatus(401);
            writer.write("You must be logged in");
        }
    }
}
