package com.pccrovers.tracker.api.data.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.pccrovers.tracker.api.data.BaseBranch;

import java.util.Map;

public class BranchUserInvitation extends BaseBranch
{
    private long userId;

    public BranchUserInvitation(long userId)
    {
        this.userId = userId;
    }

    @Override
    public BaseBranch traverse(String path)
    {
        return null;
    }

    @Override
    protected JsonElement get(Map<String, String[]> parameters)
    {
        return new JsonPrimitive(ModelUserInvitation.getByUserId(userId).getCode());
    }
}
