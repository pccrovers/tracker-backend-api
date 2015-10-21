package com.pccrovers.tracker.api;

import com.pccrovers.tracker.api.data.BaseBranch;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseApiServlet extends HttpServlet
{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String pathinfo = req.getPathInfo();
        String[] parts = pathinfo != null ? pathinfo.split("/") : new String[] {};

        BaseBranch branch = getStartingBranch();
        for (String part : parts)
        {
            if (part.length() <= 0)
                continue;

            branch = branch.traverse(part.toLowerCase());

            if(branch == null)
            {
                resp.sendError(404, req.getPathInfo() + " was not found.");
                return;
            }
        }

        branch.process(req, resp);
    }

    protected abstract BaseBranch getStartingBranch();
}
