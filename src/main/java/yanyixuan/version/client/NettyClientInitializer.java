package yanyixuan.version.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;
import yanyixuan.version.utilTools.JsonSerializer;
import yanyixuan.version.utilTools.MyDecode;
import yanyixuan.version.utilTools.MyEncode;

@AllArgsConstructor
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyDecode());
//        pipeline.addLast(new MyEncode(new ObjectSerializer()));
        pipeline.addLast(new MyEncode(new JsonSerializer()));
        pipeline.addLast(new NettyClientHandler());
    }
}
