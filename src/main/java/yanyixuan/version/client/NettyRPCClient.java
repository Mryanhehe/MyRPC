package yanyixuan.version.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.AttributeKey;
import yanyixuan.version.common.RpcRequest;
import yanyixuan.version.common.RpcResponse;
import yanyixuan.version.service.RPCClient;
import io.netty.channel.socket.nio.NioSocketChannel;
import yanyixuan.version.service.register.ServiceRegister;
import yanyixuan.version.serviceImpl.registerImpl.ZkServiceRegister;

import java.net.InetSocketAddress;


public class NettyRPCClient implements RPCClient {
    private String host;
    private int port;
    private static final Bootstrap bootstrap;
    private static final EventLoopGroup eventExecutors;
    private ServiceRegister serviceRegister;

    static {
        bootstrap = new Bootstrap();
        eventExecutors = new NioEventLoopGroup();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }
    public NettyRPCClient() {
        this.serviceRegister = new ZkServiceRegister();
    }


    @Override
    public RpcResponse sendRequest(RpcRequest request) {
        InetSocketAddress address = this.serviceRegister.serviceDiscovery(request.getInterfaceName());
        host = address.getHostName();
        port = address.getPort();
        try {
            ChannelFuture channelFuture  = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            // 发送数据
            channel.writeAndFlush(request);
            channel.closeFuture().sync();
            // 阻塞的获得结果，通过给channel设计别名，获取特定名字下的channel中的内容（这个在hanlder中设置）
            // AttributeKey是，线程隔离的，不会由线程安全问题。
            // 实际上不应通过阻塞，可通过回调函数
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("RpcResponse");
            RpcResponse response = channel.attr(key).get();

            System.out.println(response);
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
