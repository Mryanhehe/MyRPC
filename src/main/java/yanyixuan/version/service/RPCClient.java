package yanyixuan.version.service;

import yanyixuan.version.common.RpcRequest;
import yanyixuan.version.common.RpcResponse;

public interface RPCClient {
    RpcResponse sendRequest(RpcRequest request);

}
