package com.pccrovers.tracker.api.data;

import com.google.appengine.api.datastore.*;

import java.util.ConcurrentModificationException;
import java.util.Random;

public class ShardedCounter
{
    private static final int NUM_SHARDS = 4;
    private final Random generator = new Random();

    private String shardName;

    public ShardedCounter(String shardName)
    {
        this.shardName = shardName;
    }

    public void increment()
    {
        update(1L);
    }

    public void decrement()
    {
        update(-1L);
    }

    public long getCount()
    {
        long sum = 0;

        for (int i = 0; i < NUM_SHARDS; i++)
        {
            Key shardKey = KeyFactory.createKey("ShardedCounter", shardName + "_" + i);

            try
            {
                sum += (Long) BaseBranch.DATASTORE.get(shardKey).getProperty("count");
            }
            catch (EntityNotFoundException e)
            {
                // this means that this shard was not initialized
            }
        }

        return sum;
    }

    private void update(Long change)
    {
        Key shardKey = KeyFactory.createKey("ShardedCounter", shardName + "_" + generator.nextInt(NUM_SHARDS));

        Transaction tx = BaseBranch.DATASTORE.beginTransaction();
        try
        {
            Entity shard;
            try
            {
                shard = BaseBranch.DATASTORE.get(tx, shardKey);
                long count = (Long) shard.getProperty("count");
                shard.setUnindexedProperty("count", count + change);
            }
            catch (EntityNotFoundException e)
            {
                shard = new Entity(shardKey);
                shard.setUnindexedProperty("count", change);
            }
            BaseBranch.DATASTORE.put(tx, shard);
            tx.commit();
        }
        catch (ConcurrentModificationException e)
        {
            // may need to add more shards
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
        }
    }
}
