package com.pccrovers.tracker.api.data.group;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pccrovers.tracker.api.data.BaseBranch;
import com.pccrovers.tracker.api.data.ShardedCounter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class BranchGroup extends BaseBranch
{
    protected long groupId;

    private ModelGroup group;

    public BranchGroup(long groupId)
    {
        this.groupId = groupId;
    }

    @Override
    public BaseBranch traverse(String path)
    {
        return null;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        group = new ModelGroup();
        super.process(req, resp);
    }

    @Override
    protected JsonElement get(HashMap<String,String> params)
    {

        try
        {
            group.get(groupId);

            JsonObject jo = new JsonObject();

            jo.add("data", group.toJsonObject());
            jo.addProperty("success", true);
            jo.addProperty("status", 200);

            return jo;
        }
        catch (EntityNotFoundException e)
        {
            status = 404;
            error = e.getMessage();
            return null;
        }
    }

    @Override
    protected JsonElement post(HashMap<String, String> params)
    {
        if(group.exists(groupId))
        {
            status = 409;
        }
        else
        {
            status = 404;
        }

        return null;
    }

    @Override
    protected JsonElement patch(HashMap<String, String> params)
    {
        group.name = params.get("name");

        try
        {
            group.patch(groupId);
        }
        catch (EntityNotFoundException e)
        {
            status = 404;
            error = e.getMessage();
        }

        return null;
    }

    @Override
    protected JsonElement put(HashMap<String, String> params)
    {
        group.name = params.get("name");

        try
        {
            group.put(groupId);
        }
        catch (EntityNotFoundException e)
        {
            status = 404;
            error = e.getMessage();
        }

        return null;
    }

    @Override
    protected JsonElement delete(HashMap<String, String> params)
    {
        group.delete(groupId);
        new ShardedCounter("groups").decrement();

        status = 200;

        return null;
    }
}
