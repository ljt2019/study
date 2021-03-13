# 为什么要用dubbo

* 服务治理框架
* 服务的监控
* 服务的注册发现
* 服务的通信
* 服务的容错
* 服务的负载均衡

# Dubbo怎么去使用

## 发布一个dubbo服务

发布服务之后，提供一个协议url地址

~~~
dubbo://192.168.1.104:20880/com.gupaoedu.dubbo.server.ILoginService
~~~

# dubbo支持的注册中心

* consul
* zookeeper
* eureka
* redis
* etcd
* nacos
* ....