package com.pccrovers.tracker.api.data;

import com.google.appengine.api.datastore.*;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class BaseModel
{
    protected Long id;

    public long getId()
    {
        return id;
    }

    public boolean exists(long id) {
        Key key = createKey(id);

        Query q = new Query(key.getKind());
        q.setFilter(new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.EQUAL, key));
        q.setKeysOnly();

        PreparedQuery pq = BaseBranch.DATASTORE.prepare(q);
        Entity entity = pq.asSingleEntity();

        return entity != null;
    }

    public void get(long id) throws EntityNotFoundException
    {
        Entity entity = BaseBranch.DATASTORE.get(createKey(id));

        fromEntity(entity);
    }

    public void insert()
    {
        Key key = BaseBranch.DATASTORE.put(toEntity());
        id = key.getId();
    }

    public void patch(long id) throws EntityNotFoundException
    {
        // Copy the changes we want to make
        Entity entity = toEntity();

        // Pull from database
        get(id);

        // Replace changes from copy
        fromEntity(entity);

        // Update database
        put(id);
    }

    public void put(long id) throws EntityNotFoundException
    {
        if(!exists(id))
            throw new EntityNotFoundException(createKey(id));

        BaseBranch.DATASTORE.put(toEntity(id));
    }

    public void delete(long id)
    {
        BaseBranch.DATASTORE.delete(createKey(id));
    }

    protected String getEntityName()
    {
        return this.getClass().getSimpleName();
    }

    public JsonObject toJsonObject()
    {
        JsonObject retObj = new JsonObject();

        retObj.addProperty("id", id);

        for(Field field : this.getClass().getDeclaredFields())
        {
            if(Modifier.isStatic(field.getModifiers())) continue;
            if(Modifier.isFinal(field.getModifiers())) continue;
            if(!Modifier.isPublic(field.getModifiers())) continue;

            try
            {
                // Convert Camel-Cased field name to snake-case for json
                String name = field.getName().replaceAll("([^_A-Z])([A-Z])", "$1_$2").toLowerCase();
                Object value = field.get(this);

                if(value == null)
                    retObj.add(name, JsonNull.INSTANCE);
                else if(value instanceof String)
                    retObj.addProperty(name, (String) value);
                else if(value instanceof Boolean)
                    retObj.addProperty(name, (Boolean) value);
                else if(value instanceof Character)
                    retObj.addProperty(name, (Character) value);
                else if(value instanceof Number)
                    retObj.addProperty(name, (Number) value);
                else
                    retObj.addProperty(name, value.toString());
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

        return retObj;
    }

    public BaseModel fromEntity(Entity entity)
    {
        try
        {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields)
            {
                Object val = entity.getProperty(field.getName());
                if(val == null) continue;

                if (field.getType() == Short.class && val.getClass() == Long.class)
                    field.set(this, ((Long) val).shortValue());
                else if (field.getType() == Integer.class && val.getClass() == Long.class)
                    field.set(this, ((Long) val).intValue());
                else
                    field.set(this, val);
            }

            long id = entity.getKey().getId();
            if(id > 0) this.id = id;
        }
        catch (IllegalAccessException e1)
        {
            e1.printStackTrace();
        }

        return this;
    }

    protected Entity toEntity()
    {
        return toEntity(new Entity(getEntityName()));
    }

    protected Entity toEntity(Long id)
    {
        return toEntity(new Entity(getEntityName(), id));
    }

    protected Entity toEntity(Entity entity)
    {
        try
        {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields)
            {
                if(Modifier.isStatic(field.getModifiers())) continue;
                if(Modifier.isFinal(field.getModifiers())) continue;

                entity.setProperty(field.getName(), field.get(this));
            }
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return entity;
    }

    protected Key createKey(long id)
    {
        return KeyFactory.createKey(getEntityName(), id);
    }
}
