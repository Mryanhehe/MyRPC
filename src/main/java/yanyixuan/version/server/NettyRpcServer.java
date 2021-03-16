package yanyixuan.version.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;
import yanyixuan.version.common.ServiceProvider;
import yanyixuan.version.service.RPCServer;

@AllArgsConstructor
public class NettyRpcServer  implements RPCServer {
    private ServiceProvider provider;
    @Override
    public void start(int port) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        System.out.println("netty服务端启动");
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer(provider));
            ChannelFuture cf = bootstrap.bind(port).sync();
            cf.channel().closeFuture().sync();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {

    }
}
