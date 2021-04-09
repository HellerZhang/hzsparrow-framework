package com.zjt.fastdfs.pool;

import java.io.File;
import java.io.IOException;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.util.ResourceUtils;

public class FastdfsPool extends Pool {

    @SuppressWarnings("rawtypes")
    public FastdfsPool(GenericObjectPool.Config poolConfig, PoolableObjectFactory factory) {
        super(poolConfig, factory);
    }

    public FastdfsPool(GenericObjectPool.Config poolConfig) {
        super(poolConfig, new FastdfsClientFactory());
    }

    @SuppressWarnings("rawtypes")
    private static class FastdfsClientFactory extends BasePoolableObjectFactory {

        public Object makeObject() throws Exception {
            File configFile = new File(ResourceUtils.getFile("file:fastdfs_client.conf").getPath());
            if (!configFile.exists()) {
                configFile = new File(ResourceUtils.getFile("classpath:fastdfs_client.conf").getPath());
            }
            String configFilePath = configFile.getAbsolutePath();
            ClientGlobal.init(configFilePath);
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient client = new StorageClient(trackerServer, storageServer);
            return client;
        }

        public void destroyObject(Object obj) throws Exception {
            if ((obj == null) || (!(obj instanceof StorageClient))) return;
            StorageClient storageClient = (StorageClient) obj;
            TrackerServer trackerServer = storageClient.getTrackerServer();
            StorageServer storageServer = storageClient.getStorageServer();
            if (!(trackerServer == null)) {
                trackerServer.close();
            }
            if (!(storageServer == null)) {
                storageServer.close();
            }
        }

        public boolean validateObject(Object obj) {
            StorageClient storageClient = (StorageClient) obj;
            try {
                return ProtoCommon.activeTest(storageClient.trackerServer.getSocket());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
