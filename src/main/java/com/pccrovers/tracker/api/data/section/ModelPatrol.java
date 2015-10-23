package com.pccrovers.tracker.api.data.section;

import com.google.appengine.api.datastore.Key;
import com.pccrovers.tracker.api.data.BaseModel;

/**
 * The patrols within each section
 */
public class ModelPatrol extends BaseModel
{
    /**
     * The id of the section that owns this patrol
     */
    public Key sectionId;

    /**
     * The name of the patrol (i.e. Orca, Polar Bear)
     */
    public String name;
}
