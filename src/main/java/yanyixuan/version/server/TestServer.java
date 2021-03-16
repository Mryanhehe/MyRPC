package yanyixuan.version.server;

import yanyixuan.version.common.ServiceProvider;
import yanyixuan.version.service.BlogService;
import yanyixuan.version.service.RPCServer;
import yanyixuan.version.service.UserService;
import yanyixuan.version.serviceImpl.BlogServiceImpl;
import yanyixuan.version.serviceImpl.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();
        ServiceProvider provider = new ServiceProvider("127.0.0.1",8899);
        provider.provideServiceInterface(userService);
        provider.provideServiceInterface(blogService);

        RPCServer rpcServer = new NettyRpcServer(provider);
        rpcServer.start(8899);
    }
}
