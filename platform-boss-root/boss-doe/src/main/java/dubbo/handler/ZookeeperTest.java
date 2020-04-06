package dubbo.handler;



import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperTest implements Watcher {
    private static Logger LOGGER = LoggerFactory.getLogger(ZookeeperTest.class);
    private static ZooKeeper zoo;
    private static final CountDownLatch CONNECTEDSIGNAL = new CountDownLatch(1);

    public ZookeeperTest() {
        try {
            if (zoo == null) {
                zoo = new ZooKeeper("192.168.1.37:2181", 2000, this);
            }
        } catch (IOException e) {
            LOGGER.error("zk连接出错:", e);
        }
    }

    /**
     * 关闭连接
     *
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        zoo.close();
    }

    /**
     * OPEN_ACL_UNSAFE 获取所有权限，
     * PERSISTENT 永久节点
     * PERSISTENT_SEQUENTIAL 永久顺序节点
     * EPHEMERAL  临时节点
     * EPHEMERAL_SEQUENTIAL 临时顺序节点
     *
     * @param path
     * @param date
     * @throws KeeperException
     * @throws InterruptedException
     * @throws IOException
     */
    public void createNone(String path, String date) throws Exception {
        if (exitNode(path, false)) {
            LOGGER.error("当前节点已经存在");
            return;
        }
        zoo = new ZooKeeper("192.168.10.138:2181", 10000, null);
        zoo.create(path, date.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 节点是否存在
     *
     * @param path
     * @param watcher
     * @return
     * @throws Exception
     */
    public boolean exitNode(String path, boolean watcher) throws Exception {
        Stat stat = zoo.exists(path, watcher);
        if (stat == null) {
            return false;
        }
        return true;
    }

    /**
     * 获取节点数据
     *
     * @param path
     * @param watcher 是否设置观察点， 设置观察点在节点改变时可获取一次通知
     * @return
     * @throws Exception
     */
    public String getData(String path, boolean watcher) throws Exception {
        if (!exitNode(path, false)) {
            LOGGER.warn("当前节点不存在");
            return "";
        }
        //stat<数据版本号>
        byte[] stat = zoo.getData(path, watcher, null);
        return new String(stat, "UTF-8");
    }

    /**
     * Watcher Server,处理收到的变更
     *
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        LOGGER.warn("收到事件通知：" + watchedEvent.getState() + watchedEvent.getPath());
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            CONNECTEDSIGNAL.countDown();
        }
    }

    /**
     * 为节点设置数据
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public void setData(String path, String data) throws Exception {
        Stat stat = zoo.setData(path, data.getBytes(), zoo.exists(path, true).getVersion());
        LOGGER.warn("数据设置成功");
    }

    /**
     * 获取子节点
     *
     * @param path
     * @return
     * @throws Exception
     */
    public List<String> getChildren(String path) throws Exception {
        List<String> children = zoo.getChildren(path, null);
        return children;
    }

    /**
     * 删除节点
     *
     * @param path
     * @throws Exception
     */
    public void deleteNode(String path) throws Exception {
        zoo.delete(path, zoo.exists(path, true).getVersion());
    }
}

