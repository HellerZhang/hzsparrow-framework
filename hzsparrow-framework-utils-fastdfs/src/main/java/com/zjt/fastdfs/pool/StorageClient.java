package com.zjt.fastdfs.pool;

import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;

public class StorageClient extends StorageClient1
{
  public TrackerServer trackerServer;
  public StorageServer storageServer;

  public StorageClient()
  {
  }

  public StorageClient(TrackerServer trackerServer, StorageServer storageServer)
  {
    super(trackerServer, storageServer);
  }

  public TrackerServer getTrackerServer() {
    return this.trackerServer;
  }

  public void setTrackerServer(TrackerServer trackerServer) {
    this.trackerServer = trackerServer;
  }

  public StorageServer getStorageServer() {
    return this.storageServer;
  }

  public void setStorageServer(StorageServer storageServer) {
    this.storageServer = storageServer;
  }
}