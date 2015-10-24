package com.pccrovers.tracker.api.data.user;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.pccrovers.tracker.api.data.BaseBranch;
import com.pccrovers.tracker.api.data.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Used for associating users with groups
 */
public class UserGroup extends BaseModel
{
    /**
     * FLAG PERMISSIONS_READ = 1;
     */
    public static final int PERMISSION_READ = 0b1;

    /**
     * FLAG PERMISSIONS_READ = 2;
     */
    public static final int PERMISSION_WRITE = 0b10;

    /**
     * The user in question
     */
    public Key userId;

    /**
     * The id of the group that the user is in
     */
    public Key groupId;

    /**
     * Access permissions (i.e. read and write)
     * Group permissions should override section permissions if the section is in that group
     */
    public Integer permissions;

    public static UserGroup[] getByUserId(long userId)
    {
        Query q = new Query(UserGroup.class.getSimpleName());
        q.setFilter(new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.EQUAL, KeyFactory.createKey(User.class.getSimpleName(), userId)));

        List<UserGroup> userGroupList = new ArrayList<>();
        for(Entity e : BaseBranch.DATASTORE.prepare(q).asIterable())
        {
            UserGroup userGroup = new UserGroup();
            userGroup.fromEntity(e);
            userGroupList.add(userGroup);
        }

        return userGroupList.toArray(new UserGroup[userGroupList.size()]);
    }
}
