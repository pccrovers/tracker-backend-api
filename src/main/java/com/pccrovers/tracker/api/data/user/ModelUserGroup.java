package com.pccrovers.tracker.api.data.user;

import com.pccrovers.tracker.api.data.BaseModel;

public class ModelUserGroup extends BaseModel
{
    /**
     * The user in question
     */
    public Long userId;

    /**
     * The id of the group that the user is in
     */
    public Long groupId;
}
