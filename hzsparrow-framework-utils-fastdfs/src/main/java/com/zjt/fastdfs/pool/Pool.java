package com.zjt.fastdfs.pool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

public abstract class Pool
{
  @SuppressWarnings("rawtypes")
private final GenericObjectPool internalPool;

  @SuppressWarnings({ "unchecked", "rawtypes" })
public Pool(GenericObjectPool.Config poolConfig, PoolableObjectFactory factory)
  {
    this.internalPool = new GenericObjectPool(factory, poolConfig);
  }

  public StorageClient getResource() throws Exception
  {
    try
    {
      return (StorageClient)this.internalPool.borrowObject();
    }
    catch (Exception e)
    {
      throw new Exception("Could not get a resource from the pool", e);
    }
  }

  @SuppressWarnings("unchecked")
public void returnResource(StorageClient resource) throws Exception
  {
    try
    {
      this.internalPool.returnObject(resource);
    }
    catch (Exception e)
    {
      throw new Exception("Could not return the resource to the pool", e);
    }
  }

  @SuppressWarnings("unchecked")
public void returnBrokenResource(StorageClient resource) throws Exception
  {
    try
    {
      this.internalPool.invalidateObject(resource);
    }
    catch (Exception e)
    {
      throw new Exception("Could not return the resource to the pool", e);
    }
  }

  public void destroy() throws Exception
  {
    try
    {
      this.internalPool.close();
    }
    catch (Exception e)
    {
      throw new Exception("Could not destroy the pool", e);
    }
  }
}