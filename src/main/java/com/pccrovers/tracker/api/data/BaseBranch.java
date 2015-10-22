package com.pccrovers.tracker.api.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseBranch
{
    public static final DatastoreService DATASTORE = DatastoreServiceFactory.getDatastoreService();

    public static final int DEFAULT_COLLECTION_LIMIT = 20;

    protected String error = null;
    protected int status = 200;

    private Map<String, JsonElement> extras;

    public abstract BaseBranch traverse(String path);

    public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        PrintWriter writer = resp.getWriter();
        JsonElement retData = null;

        @SuppressWarnings("unchecked")
        Map<String, String[]> map = req.getParameterMap();

        try
        {
            switch (req.getMethod().toLowerCase())
            {
                case "get":
                    retData = get(map);
                    break;
                case "post":
                    retData = post(map);
                    break;
                case "patch":
                    retData = patch(map);
                    break;
                case "put":
                    retData = put(map);
                    break;
                case "delete":
                    retData = delete(map);
                    break;
                default:
                    status = 404;
                    error = "Resource not found";
            }
        }
        catch(RuntimeException e)
        {
            status = 500;
            error = e.getMessage();
        }

        resp.setStatus(status);

        JsonElement response;
        if(error != null)
        {
            JsonObject jo = new JsonObject();

            JsonObject data = new JsonObject();
            data.addProperty("error", error);
            data.addProperty("request", req.getServletPath());
            data.addProperty("method", req.getMethod());

            jo.add("data", data);
            jo.addProperty("success", false);
            jo.addProperty("status", status);

            response = jo;
        }
        else if(retData instanceof JsonPrimitive)
        {
            JsonObject jo = new JsonObject();

            jo.add("data", retData);
            jo.addProperty("success", true);
            jo.addProperty("status", status);

            response = jo;
        }
        else if(retData == null)
        {
            JsonObject jo = new JsonObject();

            jo.addProperty("success", true);
            jo.addProperty("status", status);

            response = jo;
        }
        else
        {
            response = retData;
        }

        resp.getWriter().write(response.toString());
    }

    protected JsonElement get(Map<String, String[]> parameters)
    {
        status = 404;
        error = "Resource not found";
        return null;
    }
    protected JsonElement post(Map<String, String[]> parameters)
    {
        status = 404;
        error = "Resource not found";
        return null;
    }
    protected JsonElement patch(Map<String, String[]> parameters)
    {
        status = 404;
        error = "Resource not found";
        return null;
    }
    protected JsonElement put(Map<String, String[]> parameters)
    {
        status = 404;
        error = "Resource not found";
        return null;
    }
    protected JsonElement delete(Map<String, String[]> parameters)
    {
        status = 404;
        error = "Resource not found";
        return null;
    }
}
