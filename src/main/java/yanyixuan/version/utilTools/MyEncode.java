package yanyixuan.version.utilTools;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import yanyixuan.version.common.RpcRequest;
import yanyixuan.version.common.RpcResponse;
import yanyixuan.version.service.Serializer;

@AllArgsConstructor
public class MyEncode extends MessageToByteEncoder {
    private Serializer serializer;
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        System.out.println(o.getClass());
        if(o instanceof RpcRequest){
            byteBuf.writeShort(MessageType.REQUEST.getCode());
        }
        else if(o instanceof RpcResponse) {
            byteBuf.writeShort(MessageType.RESPONSE.getCode());
        }
        byteBuf.writeShort(serializer.getType());
        byte[] serialize = serializer.serialize(o);
        byteBuf.writeInt(serialize.length);
        byteBuf.writeBytes(serialize);
    }
}
