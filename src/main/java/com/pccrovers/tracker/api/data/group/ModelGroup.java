package com.pccrovers.tracker.api.data.group;

import com.pccrovers.tracker.api.data.BaseModel;

/**
 * Each home-group gets one 'group' record in the database
 */
public class ModelGroup extends BaseModel
{
    /**
     * The name of the group (i.e. 180th Pacific Coast Scout Group)
     */
    public String name;

    @Override
    protected String getKindName()
    {
        return "Group";
    }
}
