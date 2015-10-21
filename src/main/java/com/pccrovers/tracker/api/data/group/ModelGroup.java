package com.pccrovers.tracker.api.data.group;

import com.pccrovers.tracker.api.data.BaseModel;

/**
 * Each home-group gets one 'group' record in the database
 */
public class ModelGroup extends BaseModel
{
    public String name;

    @Override
    protected String getEntityName()
    {
        return "Group";
    }
}
