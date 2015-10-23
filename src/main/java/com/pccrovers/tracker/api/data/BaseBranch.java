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
            e.printStackTrace();
        }

        JsonObject response = new JsonObject();
        if(error != null)
        {
            JsonObject data = new JsonObject();
            data.addProperty("error", error);
            data.addProperty("request", req.getServletPath());
            data.addProperty("method", req.getMethod());

            response.add("data", data);
        }
        else if(retData != null)
        {
            response.add("data", retData);
        }

        if(extras != null)
        {
            for (Map.Entry<String, JsonElement> entry : extras.entrySet())
            {
                response.add(entry.getKey(), entry.getValue());
            }
        }

        response.addProperty("success", error == null);
        response.addProperty("status", status);

        resp.setStatus(status);
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

    protected void addExtra(String key, Object obj)
    {
        JsonPrimitive jp;
        if(obj instanceof Boolean)
            jp = new JsonPrimitive((Boolean) obj);
        else if(obj instanceof Number)
            jp = new JsonPrimitive((Number) obj);
        else if(obj instanceof String)
            jp = new JsonPrimitive((String) obj);
        else if(obj instanceof Character)
            jp = new JsonPrimitive((Character) obj);
        else
            jp = new JsonPrimitive(obj.toString());

        addExtra(key, jp);
    }

    protected void addExtra(String key, JsonElement value)
    {
        if(extras == null) extras = new HashMap<>();

        extras.put(key, value);
    }
}
