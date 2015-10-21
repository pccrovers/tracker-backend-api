package com.pccrovers.tracker.api.data.inventory;

import com.pccrovers.tracker.api.data.BaseModel;

/**
 * Items in inventory
 */
public class ModelItem extends BaseModel
{
    /**
     * The class of item (i.e. lantern, stove, knife)
     */
    public String genus;

    /**
     * The brand or manufacturer of the item (i.e. primus, sunbeam, stovetec)
     */
    public String brand;

    /**
     * The item name (i.e. firefly stove, propane lantern)
     */
    public String product;

    /**
     * The model of the item
     */
    public String model;

    /**
     * The serial number of the item
     */
    public String serialNumber;

    /**
     * The nth item of the set, could be used for identifying otherwise identical items
     */
    public Integer number;

    /**
     * The id of the user whom this item is lent to
     */
    public Long borrowerUserId;
}
