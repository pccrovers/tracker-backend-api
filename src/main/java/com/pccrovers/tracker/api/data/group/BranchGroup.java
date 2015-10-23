package com.pccrovers.tracker.api.data.group;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.JsonElement;
import com.pccrovers.tracker.api.data.BaseBranch;
import com.pccrovers.tracker.api.data.ShardedCounter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
    protected JsonElement get(Map<String, String[]> params)
    {

        try
        {
            group.get(groupId);

            return group.toJsonObject();
        }
        catch (EntityNotFoundException e)
        {
            status = 404;
            error = e.getMessage();
            return null;
        }
    }

    @Override
    protected JsonElement post(Map<String, String[]> params)
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
    protected JsonElement patch(Map<String, String[]> params)
    {
        group.name = params.get("name")[0];

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
    protected JsonElement put(Map<String, String[]> params)
    {
        group.name = params.get("name")[0];

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
    protected JsonElement delete(Map<String, String[]> params)
    {
        group.delete(groupId);
        new ShardedCounter("groups").decrement();

        status = 200;

        return null;
    }
}
