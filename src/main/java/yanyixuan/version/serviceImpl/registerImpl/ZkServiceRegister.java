package yanyixuan.version.serviceImpl.registerImpl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import yanyixuan.version.service.register.ServiceRegister;
import yanyixuan.version.serviceImpl.loadBalanceImpl.RandomLoadBalance;
import yanyixuan.version.serviceImpl.loadBalanceImpl.RoundLoadBalance;

import java.net.InetSocketAddress;
import java.util.List;

public class ZkServiceRegister implements ServiceRegister {
//    curator题懂的zookeeper客户端
    private CuratorFramework client;
//    zookeeper根路径节点
    private static final String ROOT_PATH = "MyRPC";
//    初始化负载均衡器，使用随机
    private RoundLoadBalance loadBalance = new RoundLoadBalance();

    public ZkServiceRegister() {
        this.client = CuratorFrameworkFactory.builder()
                .connectString("148.70.15.23:2181")
                .sessionTimeoutMs(6000)
                .retryPolicy(new RetryNTimes(3,10))
                .namespace(ROOT_PATH).build();
        this.client.start();
        System.out.println("zookeeper连接成功");
    }


    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        try {
            if(client.checkExists().forPath("/"+serviceName) == null){
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                        .forPath("/"+serviceName);
            }

            String path = "/" + serviceName + "/" + getServiceAddress(serverAddress);
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                    .forPath(path);
            System.out.println("方法:"+ serviceName + "----"+serverAddress +"====注册成功");
        } catch (Exception e) {
            System.out.println("此服务已存在");
        }
    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            System.out.println("查找的名字是"+ serviceName);

            List<String> strings = client.getChildren().forPath("/"+serviceName);
            String str = loadBalance.balance(strings);
//            String str = strings.get(0);
            System.out.println("返回的ip是"+str);
            return parseAddress(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getServiceAddress(InetSocketAddress serverAddress){
        return serverAddress.getHostName() + ":"+serverAddress.getPort();
    }

    private InetSocketAddress parseAddress(String address){
        String[] result = address.split(":");
        return new InetSocketAddress(result[0],Integer.parseInt(result[1]));
    }
}
