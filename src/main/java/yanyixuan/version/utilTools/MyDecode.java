package yanyixuan.version.utilTools;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import yanyixuan.version.service.Serializer;

import java.util.List;

@AllArgsConstructor
public class MyDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List list) throws Exception {
        short messageType = byteBuf.readShort();
        if(messageType != MessageType.REQUEST.getCode() && messageType != MessageType.RESPONSE.getCode()){
            System.out.println("暂不支持此种数据");
            return;
        }
//        读取序列化的类型
        short serializerType = byteBuf.readShort();
        Serializer serializer = Serializer.getSerializerByCode(serializerType);
        if(serializer == null){
            throw new RuntimeException("没有这种序列化器");
        }
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Object deserialize = serializer.deserialize(bytes,messageType);
        list.add(deserialize);
    }
}
