package com.pccrovers.tracker.api.data.user;

import com.pccrovers.tracker.api.data.BaseModel;

/**
 * Used for associating users with sections
 */
public class ModelUserSection extends BaseModel
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
    public Long userId;

    /**
     * The section that the user is in
     */
    public Long sectionId;

    /**
     * Access permissions (i.e. read and write)
     * Group permissions should override section permissions if the section is in that group
     */
    public Integer permissions;
}
