package com.pccrovers.tracker.api.data.user;

import com.pccrovers.tracker.api.data.BaseModel;

/**
 * Used for associating users with sections
 */
public class ModelUserSection extends BaseModel
{
    /**
     * The user in question
     */
    public Long userId;

    /**
     * The section that the user is in
     */
    public Long sectionId;
}
