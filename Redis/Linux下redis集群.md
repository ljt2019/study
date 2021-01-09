# Redis 集群

## 前置依赖

1. 集群用到ruby命令，因此安装ruby，方式一

   ~~~
   yum install ruby
   yum install rubygems
   gem install redis  --安装redis和ruby接口,redis版本要2.2.x以上
   ~~~

## 说明

   至少6台，3主3从，本例中【7001[master1]、7002[master2]、7003[master3]、7004[slave1]、7005[slave2]、7006[slave3]】，在同台机器下的集群

   ## 搭建步骤

   1. 分别在6台机器上递归创建文件夹：【mkdir -p /usr/local/redis-cluster/700*】

   2. 将redis.conf配置文件分别copy到700*下，进行修改

      > 1. 后台启动【daemonize yes】
      >
      > 2. 端口号【prot 700*】
      >
      > 3. 绑定当前机器IP【bind 192.168.56.101】
      >
      > 4. 指定数据文件存放路径：【dir /usr/local/redis-cluster/700*/】,不修改担心丢失数据*
      > 5. 持久方式开启快照：【appendonly yes】，生产上用【appendfsync always】
      > 6. 启动集群模式：【cluster-enabled yes】
      > 7. 节点配置文件与端口号对应：【cluster-config-file nodes700*.conf】，让每个节点相互知道对方的存在*
      > 8. 设置超时时间：【cluster-node-timeout 15000】
      > 9. 

   3. 逐个启动6个redis实例

      ~~~shell
      /usr/local/redis/bin/redis-server /usr/local/redis-cluster/700*/redis.conf
      ~~~

   4. 检查是否启动成功：【ps -ef | grep redis】,查看是否已存在对应进程

   5. 在redis的安装目录下，执行集群脚本：【/usr/local/redis-3.0.7/src/redis-trib.rb create --replicas 1 192.168.56.101:7001 192.168.56.102:7002 192.168.56.103:7003 192.168.56.104:7004 192.168.56.105:7005 192.168.56.106:7006】，下次重启机器不需要此操作
        注：其中的 1 为主节点与从节点的比值(1 = 主节点数量/从节点数量),主节点排完，接着排从节点，一一对应，例如，7001为master1，则7004为slave1

   6. 连接任意客户端：【/usr/local/redis/bin/redis-cli -c -h 192.168.56.101 -p 7001】(./redis-cli -c -h -p),[-c 表示集群模式，-h指定ip,-p指定端口号]

   7. 验证集群是否搭建成功：查看集群信息【cluster info】，查看集群节点【cluster nodes】

   8. 关闭集群：【/usr/local/redis/bin/redis-cli -c -h 192.168.56.101 -p 700* shutdown】，需要逐个关闭

   9. 注意：出现集群无法启动时，删除临时的数据文件，再次重新启动每个redis服务，然后重新构建集群环境


================== 192.168.56.107 单机集群 ==================
1、安装在同台机器时启动：【/usr/local/redis/bin/redis-server /usr/local/redis-cluster/7001/redis.conf && /usr/local/redis/bin/redis-server /usr/local/redis-cluster/7002/redis.conf && /usr/local/redis/bin/redis-server /usr/local/redis-cluster/7003/redis.conf && /usr/local/redis/bin/redis-server /usr/local/redis-cluster/7004/redis.conf && /usr/local/redis/bin/redis-server /usr/local/redis-cluster/7005/redis.conf && /usr/local/redis/bin/redis-server /usr/local/redis-cluster/7006/redis.conf】

2、安装在同台机器时关闭：【/usr/local/redis/bin/redis-cli -c -h 192.168.56.107 -p 7001 shutdown && /usr/local/redis/bin/redis-cli -c -h 192.168.56.107 -p 7002 shutdown && /usr/local/redis/bin/redis-cli -c -h 192.168.56.107 -p 7003 shutdown && /usr/local/redis/bin/redis-cli -c -h 192.168.56.107 -p 7004 shutdown && /usr/local/redis/bin/redis-cli -c -h 192.168.56.107 -p 7005 shutdown && /usr/local/redis/bin/redis-cli -c -h 192.168.56.107 -p 7006 shutdown】

3、执行集群脚本【/usr/local/redis-3.0.7/src/redis-trib.rb create --replicas 1 192.168.56.107:7001 192.168.56.107:7002 192.168.56.107:7003 192.168.56.107:7004 192.168.56.107:7005 192.168.56.107:7006】

4、连接任意一台客户端【/usr/local/redis/bin/redis-cli -c -h 192.168.56.107 -p 7001】



























