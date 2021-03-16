import yanyixuan.version.serviceImpl.registerImpl.ZkServiceRegister;

import java.net.InetSocketAddress;

public class Test1 {
    public static void main(String[] args) {
        for(int i = 0 ; i <5 ;i++){
            Thread t =  new Thread(()->{
                ZkServiceRegister zkServiceRegister = new ZkServiceRegister();
                zkServiceRegister.serviceDiscovery("yanyixuan.version.service.BlogService");
            });
            t.start();
        }
    }
}
