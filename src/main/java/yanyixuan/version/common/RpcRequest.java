package yanyixuan.version.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class RpcRequest  implements Serializable {
    public String interfaceName;
    public String methodName;
    public Object[] params;
    public Class<?>[] paramsTypes;
}
