package com.pccrovers.tracker.api.data.user;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.pccrovers.tracker.api.data.ApiAccessible;
import com.pccrovers.tracker.api.data.BaseBranch;
import com.pccrovers.tracker.api.data.BaseModel;
import com.pccrovers.tracker.api.data.group.ModelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Each person, volunteer or participant, is a user
 */
public class ModelUser extends BaseModel
{
    public static final String GDS_KIND = "User";

    /**
     * The account id received from google when the user accepts the invite
     */
    public Long googleId;

    /**
     * The given name of the user (i.e. first name)
     */
    @ApiAccessible
    public String givenName;

    /**
     * The family name of the user (i.e. last name)
     */
    @ApiAccessible
    public String familyName;

    public static ModelUser[] getByGroupId(long groupId)
    {
        Query q = new Query(GDS_KIND);
        q.setFilter(new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.EQUAL, KeyFactory.createKey(ModelGroup.GDS_KIND, groupId)));

        List<ModelUser> userList = new ArrayList<>();
        for(Entity e : BaseBranch.DATASTORE.prepare(q).asIterable())
        {
            ModelUser user = new ModelUser();
            user.fromEntity(e);
            userList.add(user);
        }

        return userList.toArray(new ModelUser[userList.size()]);
    }

    @Override
    protected String getKindName()
    {
        return GDS_KIND;
    }
}
