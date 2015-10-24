package com.pccrovers.tracker.api.data.user;

import com.google.gson.JsonElement;
import com.pccrovers.tracker.api.data.BaseBranch;

import java.util.Map;

public class BranchUsers extends BaseBranch
{
    @Override
    public BaseBranch traverse(String path)
    {
        try
        {
            return new BranchUser(Long.valueOf(path));
        }
        catch(NumberFormatException e)
        {
            return null;
        }
    }

    @Override
    protected JsonElement get(Map<String, String[]> parameters)
    {
        return super.get(parameters);
    }

    @Override
    protected JsonElement post(Map<String, String[]> parameters)
    {
        ModelUser user = new ModelUser();

        user.givenName = parameters.get("given_name")[0];
        user.familyName = parameters.get("family_name")[0];

        user.insert();

        ModelUserInvitation registrationCode = new ModelUserInvitation();

        registrationCode.userId = user.getId();
        registrationCode.insert();

        return user.toJsonObject();
    }
}
