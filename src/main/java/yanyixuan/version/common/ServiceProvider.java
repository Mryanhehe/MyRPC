package yanyixuan.version.common;

import yanyixuan.version.service.register.ServiceRegister;
import yanyixuan.version.serviceImpl.registerImpl.ZkServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;

public class ServiceProvider {
    private HashMap<String,Object> interfaceProvider;
    private ServiceRegister serviceRegister;
    private String host;
    private int port;
    public ServiceProvider(String host, int port) {
        interfaceProvider = new HashMap<>();
        this.host = host;
        this.port = port;
        this.serviceRegister = new ZkServiceRegister();
    }

    public  void provideServiceInterface(Object service){
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for(Class c : interfaces) {
            this.interfaceProvider.put(c.getName(), service);
            serviceRegister.register(c.getName(),new InetSocketAddress(host,port));
        }
    }

    public Object getService(String interfaceName){
        return this.interfaceProvider.get(interfaceName);
    }
}
