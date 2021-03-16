package yanyixuan.version.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;
import yanyixuan.version.common.ServiceProvider;
import yanyixuan.version.utilTools.JsonSerializer;
import yanyixuan.version.utilTools.MyDecode;
import yanyixuan.version.utilTools.MyEncode;


@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private ServiceProvider serviceProvider;
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyDecode());
//        pipeline.addLast(new MyEncode(new ObjectSerializer()));
        pipeline.addLast(new MyEncode(new JsonSerializer()));

        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));
    }
}