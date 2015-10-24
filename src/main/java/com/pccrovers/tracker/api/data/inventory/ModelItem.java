package com.pccrovers.tracker.api.data.inventory;

import com.google.appengine.api.datastore.Key;
import com.pccrovers.tracker.api.data.ApiAccessible;
import com.pccrovers.tracker.api.data.BaseModel;

/**
 * Items in inventory
 */
public class ModelItem extends BaseModel
{
    /**
     * The class of item (i.e. lantern, stove, knife)
     */
    @ApiAccessible
    public String genus;

    /**
     * The brand or manufacturer of the item (i.e. primus, sunbeam, stovetec)
     */
    @ApiAccessible
    public String brand;

    /**
     * The item name (i.e. firefly stove, propane lantern)
     */
    @ApiAccessible
    public String product;

    /**
     * The model of the item
     */
    @ApiAccessible
    public String model;

    /**
     * The serial number of the item
     */
    @ApiAccessible
    public String serialNumber;

    /**
     * The nth item of the set, could be used for identifying otherwise identical items
     */
    @ApiAccessible
    public Integer number;

    /**
     * The id of the user whom this item is lent to
     */
    @ApiAccessible
    public Key borrowerUserId;
}
