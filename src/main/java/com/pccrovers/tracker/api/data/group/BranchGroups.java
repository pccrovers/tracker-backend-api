package com.pccrovers.tracker.api.data.group;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.pccrovers.tracker.api.data.BaseBranch;
import com.pccrovers.tracker.api.data.ShardedCounter;

import java.util.Map;

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
    protected JsonElement get(Map<String, String[]> params)
    {
        Query q = new Query("Group");

        PreparedQuery pq = DATASTORE.prepare(q);

        FetchOptions fo = FetchOptions.Builder.withLimit(DEFAULT_COLLECTION_LIMIT);
        if(params.containsKey("limit"))
            fo.limit(Integer.parseInt(params.get("limit")[0]));
        if(params.containsKey("page"))
            fo.offset(fo.getLimit() * (Integer.parseInt(params.get("page")[0])-1));

        Iterable<Entity> entities = pq.asIterable(fo);

        JsonArray ja = new JsonArray();
        for(Entity entity : entities)
        {
            ModelGroup group = new ModelGroup();
            group.fromEntity(entity);
            ja.add(group.toJsonObject());
        }


        if(params.containsKey("count"))
        {
            addExtra("count", new ShardedCounter("groups").getCount());
        }

        return ja;
    }

    @Override
    protected JsonElement post(Map<String, String[]> params)
    {
        ModelGroup group = new ModelGroup();

        group.name = params.get("name")[0];

        group.insert();

        new ShardedCounter("groups").increment();

        status = 201;

        return new JsonPrimitive(group.getId());
    }
}
