package com.pccrovers.tracker.api.data.user;

import com.pccrovers.tracker.api.data.BaseModel;

/**
 * Each person, volunteer or participant, is a user
 */
public class ModelUser extends BaseModel
{
    /**
     * The account id received from google when the user accepts the invite
     */
    public Long googleId;


}
