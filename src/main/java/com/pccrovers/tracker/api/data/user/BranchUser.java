package com.pccrovers.tracker.api.data.user;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.JsonElement;
import com.pccrovers.tracker.api.data.BaseBranch;

import java.util.Map;

public class BranchUser extends BaseBranch
{
    private long userId;

    public BranchUser(long userId)
    {
        this.userId = userId;
    }

    @Override
    public BaseBranch traverse(String path)
    {
        if(path.equals("invitation"))
        {
            return new BranchUserInvitation(userId);
        }
        return null;
    }

    @Override
    protected JsonElement get(Map<String, String[]> parameters)
    {
        ModelUser user = new ModelUser();
        try
        {
            user.get(userId);

            return user.toJsonObject();
        }
        catch (EntityNotFoundException e)
        {
            return super.get(parameters);
        }
    }
}
