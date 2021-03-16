package yanyixuan.version.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import yanyixuan.version.common.RpcRequest;
import yanyixuan.version.common.RpcResponse;
import yanyixuan.version.common.ServiceProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
public class NettyRPCServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private ServiceProvider provider;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest request) throws Exception {
        RpcResponse response =getResponse(request);
        channelHandlerContext.writeAndFlush(response);
        channelHandlerContext.close();
    }


    public RpcResponse getResponse(RpcRequest request){
        String interfaceName= request.getInterfaceName();
        Object service = provider.getService(interfaceName);
        Method method = null;
        try{
            method = service.getClass().getMethod(request.getMethodName(),request.getParamsTypes());
            Object invoke = method.invoke(service , request.getParams());
            return RpcResponse.success(invoke);
        } catch (NoSuchMethodException  | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return RpcResponse.fail();
        }

    }
}
