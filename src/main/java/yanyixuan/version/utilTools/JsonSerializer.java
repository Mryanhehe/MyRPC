package yanyixuan.version.utilTools;

import lombok.AllArgsConstructor;
import yanyixuan.version.common.RpcRequest;
import yanyixuan.version.common.RpcResponse;
import yanyixuan.version.service.Serializer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


@AllArgsConstructor
public class JsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes = JSONObject.toJSONBytes(obj);
        return bytes;
    }

//    msaage表示request和response
    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        switch (messageType){
            case 0:
                RpcRequest request = JSON.parseObject(bytes,RpcRequest.class);
                if(request.getParams() == null) return request;
                Object[] objects = new Object[request.getParams().length];
                for(int i = 0 ; i < objects.length ;i++){
                    Class<?> paramsType = request.getParamsTypes()[i];
                    if(!paramsType.isAssignableFrom(request.getParams()[i].getClass())){
                        objects[i] = JSONObject.toJavaObject((JSONObject) request.getParams()[i],
                                request.getParamsTypes()[i]);
                    }
                    else{
                        objects[i] = request.getParams()[i];
                    }
                }
                request.setParams(objects);
                obj = request;
                break;
            case 1:
                RpcResponse response = JSON.parseObject(bytes,RpcResponse.class);
                Class<?> dataType = response.getDataType();
                if(!dataType.isAssignableFrom(response.getData().getClass())) {
                    response.setData(JSONObject.toJavaObject((JSONObject) response.getData(), dataType));
                }
                obj = response;
                break;
            default:
                System.out.println("不支持这种消息");
                throw new RuntimeException();

        }
        return obj;
    }

    @Override
    public int getType() {
        return 1;
    }
}
