package com.pccrovers.tracker.api.data.section;

import com.pccrovers.tracker.api.data.BaseModel;

/**
 * The sections within each group ranging from beavers to rovers
 */
public class ModelSection extends BaseModel
{
    /**
     * The id of the group that owns this section
     */
    public Long groupId;

    /**
     * The name of the section (i.e. beavers, cubs, scouts, venturers, rovers)
     */
    public String name;
}
