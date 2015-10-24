package com.pccrovers.tracker.api.data.user;

import com.google.appengine.api.datastore.*;
import com.pccrovers.tracker.api.data.BaseBranch;
import com.pccrovers.tracker.api.data.BaseModel;

public class UserInvitation extends BaseModel
{
    /**
     * The id of the user in question
     */
    public Key userId;

    /**
     * Whether or not the invitation has been used
     */
    public Boolean used = false;

    public static UserInvitation getByUserId(long userId)
    {
        Query q = new Query(UserInvitation.class.getSimpleName());

        q.setFilter(new Query.FilterPredicate("userId", Query.FilterOperator.EQUAL, KeyFactory.createKey(User.class.getSimpleName(), userId)));

        UserInvitation invitation = new UserInvitation();

        invitation.fromEntity(BaseBranch.DATASTORE.prepare(q).asSingleEntity());

        return invitation;
    }

    public void use()
    {
        used = true;
        try
        {
            put(this.getId().getId());
        }
        catch (EntityNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public String getCode()
    {
        return Long.toString(userId.getId(), Character.MAX_RADIX) + Long.toString(getId().getId(), Character.MAX_RADIX);
    }
}
