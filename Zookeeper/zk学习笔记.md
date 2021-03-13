官网【http://zookeeper.apache.org/】

# docker安装

1. 创建挂载目录

   ~~~shell
   mkdir /opt/docker/zookeeper/data/ -p 
   ~~~

2. 拉取镜像

   ~~~shell
   docker pull zookeeper
   ~~~

3. 启动

   ~~~shell
   docker run -d -p 2181:2181 -v /opt/docker/zookeeper/data/:/data/ --name=zk --privileged zookeeper
   ~~~

4. 查看日志

   ~~~shell
   docker logs -f zk
   ~~~

5. …





# 前世今生

  Apache ZooKeeper 是一个【高可靠的分布式协调中间件】。它是 Google Chubby 的一个开源实现，那么它主要是解决什么问题的呢？那就得先了解 Google Chubby是谷歌的一个用来解决分布式一致性问题的组件，同时，也是粗粒度的分布式锁服务，由于 Chubby 没有开源，所以雅虎公司基于 chubby 的思想，开发了一个类似的分布式协调组件 Zookeeper，后来捐赠给了 Apache。Chubby不开源，所以雅虎自己开发了。

# 分布式一致性问题
  什么是分布式一致性问题呢？简单来说，就是在一个分布式系统中，有多个节点，每个节点都会提出一个请求，但是在所有节点中只能确定一个请求被通过。而这个通过是需要所有节点达成一致的结果，所以所谓的一致性就是在提出的所有请求中能够选出最终一个确定请求。并且这个请求选出来以后，所有的节点都要知道。这个就是典型的拜占庭将军问题

## 本质

拜占庭将军问题的本质,由于网络通信存在不可靠的问题，可能存在消息丢失或网络延迟。如何在这样的背景下对某一个请求达成一致。为了解决这个问题，很多人提出了各种协议，比如大名鼎鼎的 Paxos，在不可信的网络环境中，按照 paxos协议就能够针对某个提议达成一致。所以：分布式一致性的本质【在分布式系统中，多个节点就某一个提议如何达成一致】

# 设计思想
  1)、防止单点故障（集群master/slave，leader/follower/observer(不参与投票)）
  2)、数据同步问题
  3)、leader选举算法（paxos-》zab协议(zookeeper中的)）
  4)、2pc协议

# 数据模型
  zookeeper 的视图结构和标准的文件系统非常类似，每一个节点称之为 ZNode，是 zookeeper 的最小单元。每个 znode上都可以保存数据以及挂载子节点。构成一个层次化的树形结构

# 基本操作

1. 创建节点

   ~~~shell
   create -e -s /temp s_temp_tiger
   create /lock/ val
   create /lock/tiger haha
   ## 有序节点
   create -s /lock/ v
   ## 临时节点,临时节点owner属性有值，相当于会话sessionID
   create -e /temp temp_tiger
   ## 临时有序
   create -e -s /temp s_temp_tiger
   
   ~~~

2. 删除节点

   ~~~shell
   delete /node_name
   ## 递归删除
   deleteall /lock
   ~~~

3.  设置数据

   ~~~shell
   set /node_name
   ~~~

4. 获取数据

   ~~~shell
   get /node_name
   ~~~

5. …

# 节点特点

1. 是一种树形结构：key=value 形式存储。

2. 删除必须从最里层节点一层一层往外删(可以递归删除)。

3. 同一层节点不能重复。

4. 临时节点的生命周期和客户端的会话绑定在一起，当客户端会话失效该节点自动清理

5. 对节点进行监听，事件只监听一次，开启两个回话，只会触发一次
     在其中一个会话中【get -w /haha】，另一个会话中【set haha tiger】
     【WATCHER::
      WatchedEvent state:SyncConnected type:NodeDataChanged path:/haha】

6. watch机制、临时节点、持久节点，实现注册服务

7. 版本号

   ~~~
     1)、当前节点版本号：version，修改值一次，版本号加1。
     2)、子节点版本号：cversion，新建一个子节点，子节点版本号加1。
   ~~~

8. 顺序一致性

# leader 选举的原理



1、存在两个阶段：启动时的选举、运行时leader宕机时的选举

  1)、服务器启动时：leader 选举每个节点启动的时候状态都是 LOOKING，处于观望状态，接下来就开始进行选主流程 

2、投票流程：投票、统计投票信息、选举状态更新。

  1)、投票：先比较epoch、后比较zxid，最后比较myid
    (1)、epoch：逻辑时钟(logicalclock)又叫投票的次数，同一轮投票过程中的逻辑时钟值是相同的。每投完一次票这个数据就会增加，然后与接收到的其它服务器返回的投票信息中的数值相比，根据不同的值做出不同的判断。
    (2)、zxid：事务id，值越大说明数据越新，在选举算法中的权重也越大。
    (3)、服务器ID(myid)：比如有三台服务器，编号分别是 1,2,3。编号越大在选择算法中的权重越大。
	
  2)、统计投票信息：每次投票后，服务器都会统计投票信息，判断是否已经有过半机器接受到相同的投票信息，如果统计出集群中已经有两台机器接受了某台机器，此时便认为已经选出了Leader。

  3)、改变服务器状态。一旦确定了 Leader，每个服务器就会更新自己的状态，如果是 Follower，那么就变更为 FOLLOWING，如果是 Leader，就变更为 LEADING。

3、选举状态

  1)、LOOKING：竞选状态。

  2)、FOLLOWING：随从状态，同步 leader 状态，参与投票。

  3)、OBSERVING：观察状态,同步 leader 状态，不参与投票。

  4)、LEADING：领导者状态。

  

  

  

  

  

  

  

  

  

  

  



  













































