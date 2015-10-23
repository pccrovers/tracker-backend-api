package com.pccrovers.tracker.api.data.user;

import com.google.appengine.api.datastore.Key;
import com.pccrovers.tracker.api.data.BaseModel;

/**
 * Used for associating users with groups
 */
public class ModelUserGroup extends BaseModel
{
    /**
     * The user in question
     */
    public Key userId;

    /**
     * The id of the group that the user is in
     */
    public Key groupId;
}
