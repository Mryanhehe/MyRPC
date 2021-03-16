# MyRPC

为了学习RPC，于是从0开始造了一个轮子

## 技术栈

1. java，会用到反射和动态代理（其实用静态代理也可以，但是会麻烦很多）
2. netty（jdk有NIO，但是太麻烦，直接用了netty）
3. 阿里巴巴的fastjson，主要是序列化会用到（java自带的序列化效率太低）
4. zookeeper，用来做注册中心

