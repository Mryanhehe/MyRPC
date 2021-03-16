package yanyixuan.version.service;

import yanyixuan.version.utilTools.JsonSerializer;
import yanyixuan.version.utilTools.ObjectSerializer;

public interface Serializer {
    byte[] serialize(Object obj);
    Object deserialize(byte[] bytes,int messageType);
    int getType();
    // 根据序号取出序列化器，暂时有两种实现方式，需要其它方式，实现这个接口即可
    static Serializer getSerializerByCode(int code){
        switch (code){
            case 0:
                return new ObjectSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
