package com.pccrovers.tracker.api.data.group;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.pccrovers.tracker.api.data.BaseBranch;
import com.pccrovers.tracker.api.data.ShardedCounter;

import java.util.HashMap;

public class BranchGroups extends BaseBranch
{

    @Override
    public BaseBranch traverse(String path)
    {
        try
        {
            return new BranchGroup(Long.parseLong(path));
        }
        catch(NumberFormatException e)
        {
            return null;
        }
    }

    @Override
    protected JsonElement get(HashMap<String,String> params)
    {
        Query q = new Query("Group");

        PreparedQuery pq = DATASTORE.prepare(q);

        FetchOptions fo = FetchOptions.Builder.withLimit(DEFAULT_COLLECTION_LIMIT);
        if(params.containsKey("limit"))
            fo.limit(Integer.parseInt(params.get("limit")));
        if(params.containsKey("page"))
            fo.offset(fo.getLimit() * (Integer.parseInt(params.get("page"))-1));

        Iterable<Entity> entities = pq.asIterable(fo);

        JsonArray ja = new JsonArray();
        for(Entity entity : entities)
        {
            ModelGroup group = new ModelGroup();
            group.fromEntity(entity);
            ja.add(group.toJsonObject());
        }

        JsonObject jo = new JsonObject();

        jo.add("data", ja);

        if(params.containsKey("count"))
        {
            jo.addProperty("count", new ShardedCounter("groups").getCount());
        }

        jo.addProperty("success", true);
        jo.addProperty("status", 200);

        return jo;
    }

    @Override
    protected JsonElement post(HashMap<String, String> params)
    {
        ModelGroup group = new ModelGroup();

        group.name = params.get("name");
        group.groupNumber = Integer.valueOf(params.get("group_number"));

        group.insert();

        new ShardedCounter("groups").increment();

        status = 201;

        return new JsonPrimitive(group.getId());
    }
}
