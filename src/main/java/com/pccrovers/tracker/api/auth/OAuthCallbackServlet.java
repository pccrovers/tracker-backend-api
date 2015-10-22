package com.pccrovers.tracker.api.auth;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

public class OAuthCallbackServlet extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        UserService userService = UserServiceFactory.getUserService();
        Writer writer = resp.getWriter();

        if(userService.isUserLoggedIn())
        {
            Object scope = req.getSession().getAttribute("scope");
            if(!(scope instanceof Collection)) throw new RuntimeException("scope was not found");

            OAuth2Manager oAuth2Manager = new OAuth2Manager(userService.getCurrentUser().getUserId(), (Collection) req.getSession().getAttribute("scope"));

            oAuth2Manager.createAndStoreCredential(req, req.getRequestURL().toString());

            if(oAuth2Manager.isAuthorized())
            {
                writer.write("Authorization successful.  You may close this window.");
            }
            else
            {
                writer.write("Authorization failed, please <a href=\"" + oAuth2Manager.createAuthorizationURL(req.getRequestURI()) + "\">try again</a>");
            }
        }
        else
        {
            resp.setStatus(401);
            writer.write("You must be logged in");
        }
    }
}
