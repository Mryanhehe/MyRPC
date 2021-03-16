package yanyixuan.version.client;

import lombok.AllArgsConstructor;
import yanyixuan.version.common.RpcRequest;
import yanyixuan.version.common.RpcResponse;
import yanyixuan.version.service.RPCClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class ClientProxy implements InvocationHandler {
    private RPCClient client;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = RpcRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsTypes(method.getParameterTypes()).build();
        RpcResponse  response= client.sendRequest(request);
        return response.getData();
    }

    public <T>T getProxy(Class<T> tClass){
        Object o = Proxy.newProxyInstance(tClass.getClassLoader(),new Class[]{tClass},this);
        return (T) o;
    }
}
