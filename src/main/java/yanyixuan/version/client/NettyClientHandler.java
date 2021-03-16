package yanyixuan.version.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import yanyixuan.version.common.RpcResponse;

public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        // 接收到response, 给channel设计别名，让sendRequest里读取response
        AttributeKey<RpcResponse> key = AttributeKey.valueOf("RpcResponse");
        ctx.channel().attr(key).set(msg);
        ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}