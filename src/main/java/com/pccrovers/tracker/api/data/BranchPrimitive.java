package com.pccrovers.tracker.api.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;

import java.util.Map;

public class BranchPrimitive<T> extends BaseBranch
{
    private String name;
    private T value;

    public BranchPrimitive(String name, T value)
    {
        this.name = name;
        this.value = value;
    }

    @Override
    public BaseBranch traverse(String path)
    {
        return null;
    }

    @Override
    protected JsonElement get(Map<String, String[]> params)
    {
        if(value instanceof String)
            return new JsonPrimitive((String) value);
        else if(value instanceof Boolean)
            return new JsonPrimitive((Boolean) value);
        else if(value instanceof Character)
            return new JsonPrimitive((Character) value);
        else if(value instanceof Number)
            return new JsonPrimitive((Number) value);
        else if(value == null)
            return JsonNull.INSTANCE;
        else
        {
            status = 500;
            error = name + " is not a non-primitive value";
            return null;
        }
    }
}
