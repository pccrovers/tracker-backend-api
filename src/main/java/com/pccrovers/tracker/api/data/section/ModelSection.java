package com.pccrovers.tracker.api.data.section;

import com.google.appengine.api.datastore.Key;
import com.pccrovers.tracker.api.data.ApiAccessible;
import com.pccrovers.tracker.api.data.BaseModel;

/**
 * The sections within each group ranging from beavers to rovers
 */
public class ModelSection extends BaseModel
{
    /**
     * The id of the group that owns this section
     */
    @ApiAccessible
    public Key groupId;

    /**
     * The name of the section (i.e. beavers, cubs, scouts, venturers, rovers)
     */
    @ApiAccessible
    public String name;
}
