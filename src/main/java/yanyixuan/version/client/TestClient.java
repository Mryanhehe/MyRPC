package yanyixuan.version.client;


import yanyixuan.version.common.Blog;
import yanyixuan.version.common.User;
import yanyixuan.version.service.BlogService;
import yanyixuan.version.service.RPCClient;
import yanyixuan.version.service.UserService;

public class TestClient {
    public static void main(String[] args) {
        RPCClient client = new NettyRPCClient();
        ClientProxy proxy = new ClientProxy(client);
        // 调用方法
        UserService userService = proxy.getProxy(UserService.class);
        User userByUserId = userService.getUserByUserId(10);
        System.out.println("从服务端得到的user为：" + userByUserId);

        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer integer = userService.insertUserId(user);
        System.out.println("向服务端插入数据："+integer);

        BlogService blogService = proxy.getProxy(BlogService.class);

        Blog blogById = blogService.getBlogById(10000);
        System.out.println("从服务端得到的blog为：" + blogById);

    }
}
