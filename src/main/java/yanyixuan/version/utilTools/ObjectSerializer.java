package yanyixuan.version.utilTools;

import yanyixuan.version.service.Serializer;

import java.io.*;

public class ObjectSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes = null;
//        以IO流的方式对字节数组进行读写
//        获取缓冲区的数据，转换成字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try{
//            创建对象输出六
            ObjectOutputStream oos = new ObjectOutputStream(bos);
//            将对象写入ObjectOutputStream，并读入ByteArrayOutputStream
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try{
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public int getType() {
        return 0;
    }
}
