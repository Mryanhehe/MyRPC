package yanyixuan.version.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class RpcResponse implements Serializable {
    private int code;
    private String message;
    private Class<?> dataType;
    private Object data;

    public static RpcResponse success(Object data) {
        return RpcResponse.builder().code(200).data(data).dataType(data.getClass()).build();
    }
    public static RpcResponse fail() {
        return RpcResponse.builder().code(500).message("服务器发生错误").build();
    }
}