20210117

更加详细查询redis官方网站【https://www.redis.net.cn/order/3625.html】

flushall

flushdb 

# Redis 5种数据类型

## 字符串（String）Map<key,value>

二进制的形式存储，value最大容纳数据长度512M，(string/int/float)

   ### 基本操作命令

1. 赋值更新

   ~~~shell
   set key value
   setnx key value  #如果key存在就不设置，返回0，不存在才设置，返回
   ~~~

2. 赋值多个值

   ~~~shell
   mset key1 value1 key2 value2
   ~~~

3. 设置过期时间

   ~~~shell
   setex key second value  #second表示second后秒过期
   ~~~

4. 按索引替换

   ~~~shell
   setrange key index value  #index表示从index处开始替换
   ~~~

5. 取值

   ~~~shell
   get key
   ~~~

6. 删除

   ~~~shell
   del key
   ~~~

7. 扩展

   ~~~shell
   getset key value  #表示先将值取出再重新覆盖赋值
   ~~~

8. 数值增减

   ~~~shell
   incr/decr key  #key自增/减1，如果key不存在，则先给key自动赋值0，再加/减1	
   incrby/decrby key 2  #key的加/减加2	
   ~~~

9. …

### 应用场景

* 分布式锁
* 字符串分布式键
* 分布式计时器



## 哈希（Hash）Map<String,HashMap<String,value>>

由多个无序键值对(key-value)散列组组成，其中key是字符串，value是元素，按key进行增删，每个key可以存储2^32-1（40亿）

### 基本操作命令

1. 赋值

   ~~~shell
   hset key field value  
   hmset key filed1 value1 filed2 value2  #一次设置多个值
   ~~~

2. 取值

   ~~~shell
   hget key field  #取指定key中field的value
   hmget key field1 field2  #取指定key中多个field的值
   hgetall key  #获取指定key中所有(field,value)
   hkeys key  #获取指定key中所有field
   hvals key  ##获取指定key中所有value
   ~~~

3. 删除

   ~~~shell
   hdel key field1 field2  #删除指定key中(field,value)
   del key  #删除指定key中的所有元素
   ~~~

4. 自学命令

   ~~~shell
   hexists key field  #判断某属性是否存在
   ~~~

5. 获取长度

   ~~~shell
   hlen key  #获取指定key的里面field的个数，长度
   ~~~

6. 对于field存储的是integer类型的可以进行加减

   ~~~shell
   hincrby key field
   ~~~

7. …



### 应用场景

* 

### 特点

* 节省内存空间
* 减少key冲突
* 取值减少性能消耗
* field不能单独设置过期时间
* 需要考虑大数据量分布的问题

### 存储结构

1. ziplist(OBJECT_ENCODING_ZIPLIST)：压缩列表

   > 经过特殊编码，由连续的内存块组成的双向链表，它不存储指向上和下链表的指针，而是存储上和当前节点的长度
   >
   > > 1. 一个hash对象保存的field数量<512
   > > 2. 一个hash对象所有的field和value字符长度都<64byte

2. hashtable(OBJECT_ENCODING_HT)：哈希表

## 字符串列表（List）Map<key,LinkedList<value>>

有序可重复字符串(左head->右tail)，可以存储2^32-1（40亿）

   ### 基本操作命令

1. 赋值

   ~~~shell
   lpush/rpush key value[value...]  #往key列表键中左/右边放入元素，key不存在则新建
   lpushx/rpushx key value[value...]  #往key列表键中左/右边放入元素，key存在才赋值
   ~~~

2. 取值

   ~~~shell
   lrange key start stop  #获取指定key中从start下标到stop下标元素
   lrange key 0 -1  #获取指定key中所有的元素
   lindex key index  #根据指定索引index获取元素
   ~~~

3. 删除

   ~~~shell
   lpop/rpop key #弹出左/右边的元素
   blpop key [key...] timeout  #阻塞从key列表键最左端弹出元素，若列表键中不存在元素，阻塞等待{timeout}秒，若{timeout}=0，则一直等待
   brpop key [key...] timeout  #改性质可以用来做消息队列的实现
   ~~~

4. 获取长度

   ~~~
   llen key  #取元素的长度
   ~~~

5. …

### 应用场景

1. 消息列表
2. 文章列表
3. 公告列表
4. 评论列表
5. 活动列表

### 存储结构

quicklist

1. list-max-ziplist-szie(fill)

   1. 正数表示单个ziplist最多存储的entry个数

   2. 负数表示单个ziplist的大小

      > * -1:4KB
      > * -2:8KB
      > * -3:16KB
      > * -4:32KB
      > * -5:64KB

2. list-compress-depth(compress),压缩深度魔人是0

   1. 首尾ziplist不压缩
   2. 首尾第一第二个ziplist不压缩，以此类推

## 字符串集合（Set）Map<key,Set<value>>

无序不可重复，可以存储2^32-1（40亿）

### 基本操作命令

1. 赋值

   ~~~shell
   sadd key member [member ...]  #往集合键key中存放元素，若key不存在则新建
   ~~~

2. 取值

   ~~~shell
   smembers key  #获取集合键key中所有元素
   srandmember key  #从集合键中随机获取1个元素
   srandmember key [count]  #从集合键中随机获取{count}个元素
   sismember key member  #判断{member}是否存在于集合键key中
   scard key  #获取集合键key元素个数
   ~~~

3. 删除

   ~~~shell
   srem key member [member ...]  #从集合键key中删除指定元素
   spop key  #从集合键中随机选1个元素，并删除
   ~~~

4. 差集运算，存储

   ~~~shell
   sdiff set1 set2  #set1中元素减去set2中元素之差
   sdiffstore set set1 set2  #
   ~~~

5. 交集运算，存储

   ~~~shell
   sinter set1 set2  #求出两集合中都有的元素
   sinterstore set set1 set2  #
   ~~~

6. 并集运算，存储

   ~~~shell
   sdunion set1 set2  #合并两个集合，重复的元素set会去掉
   sunionstore set set1 set2  #
   ~~~

7. …

### 应用场景

* 跟踪数据唯一性

* 维护数据对象之间的关联关系

* 抽奖

* 点赞

* 签到

* 打卡

* 商品标签

* 用户画像（兴趣爱好，信用属性，人口属性，社交）

* 用户关注，推荐模型

* 案例1

  > * 用like:t1001来维护t1001这条微博所有的点赞用户
  > * 用户utiger点赞：sadd like:t1001 utiger
  > * 用户utiger取消点赞：srem like:t1001 utiger
  > * 用户utiger是否点赞：sismember like:t1001 utiger
  > * 点赞的所有用户：smembers like:t1001
  > * 点赞总数：scard like:t1001

* 相互关注，我关注的人也关注了她？可能认识的人？

### 存储结构

1. inset
2. hashtable

## 有序字符串集合（Sorted Set）  Map<key,TreeMap<value,score>>

带分数(value,score)有序集合，value不可重复，score可重复

### 基本操作命令

1. 赋值， https://yq.aliyun.com/articles/504008

   ~~~shell
   zadd key score member score1 member1  #往有序集合键key中存放元素，若key不存在则新建
   zadd key score member  #会替换掉之前存在的member分数scored的值
   ~~~

2. 取值

   ~~~shell
   zscore key member  #获取指定member的分数
   zrange key 0 -1  #取某范围内的值，默认是升序
   zrevrange key 0 -1  #取某范围内的值，降序
   zrange key 0 -1 withscores  #取某范围内的值，并将分数也取出
   zrangebyscore key 80 100 withscores limit 0 2  #根据分数的范围进行取值
   zcard key  #取长度
   zcount key 80 90  #获取某分数之间的个数
   ~~~

3. 删除

   ~~~shell
   zrem key zs ls
   zremrangebyrank key 0 4  #根据排名的范围进行删除
   zremrangebyscore key 80 100  #根据分数的范围进行删除
   
   ~~~

4. 给某个分数加值

   ~~~shell
   zincrby key 3 ls  #表示给ls的分数加3
   ~~~

5. …

### 应用场景

* 排名、排行榜

* 热点话

  > * id为n1001的新闻的点阅数加1
  >
  >   ~~~shell
  >   zincrby hotnews:20201111 1 n1001
  >   ~~~
  >
  > * 获取今天点击量最多的15条：
  >
  >   ~~~shell
  >   zrevrange hotnews:202011 0 15 withscores
  >   ~~~
  >
  > * 
  >
  > 

* 构建索引数据

  

### 存储结构

1. ziplist（元素个数小于128，所有元素长度小于64bytes）
2. skiplist+dick

## Redis Key 的通用命令

1. 获得所有key

   ~~~shell
   keys *
   ~~~

2. 删除指定key

   ~~~shell
   del key1 key2 key3
   ~~~

3. 查看指定key是否存在

   ~~~shell
   exists key
   ~~~

4. 对key重命名

   ~~~shell
   rename key newKey
   ~~~

5. 设置key的过期时间

   ~~~shell
   expire key 1000  #单位是秒
   ~~~

6. 查看key过期所剩的时间

   ~~~shell
   ttl key
   ~~~

7. 获取key的类型

   ~~~shell
   type key
   ~~~

8. …

# 事务操作

演示

~~~shell
 set tiger 100  #充值100元
set sp 100  #充值100元
multi  #开启事务
decrby tiger 100  #向sp转账100元
incrby sp 100  #收到tiger转来的100元
exec  #执行事务

discard  #取消事务，执行到一半时发现有吴，则可以这样取消
~~~

# Lua脚本

为什么要使用Lua脚本？

1. 批量执行命令
2. 原子性
3. 操作集合的复用
4. 排他，在执行lua脚本时，会拒绝其他客户端的任何请求

## 基本命令

~~~shell
EVAL script numkeys key [key ...] arg [arg ...]
~~~

> - **script**： 参数是一段 Lua 5.1 脚本程序。脚本不必(也不应该)定义为一个 Lua 函数。
> - **numkeys**： 用于指定键名参数的个数。
> - **key [key ...]**： 从 EVAL 的第三个参数开始算起，表示在脚本中所用到的那些 Redis 键(key)，这些键名参数可以在 Lua 中通过全局变量 KEYS 数组，用 1 为基址的形式访问( KEYS[1] ， KEYS[2] ，以此类推)。
> - **arg [arg ...]**： 附加参数，在 Lua 中通过全局变量 ARGV 数组访问，访问的形式和 KEYS 变量类似( ARGV[1] 、 ARGV[2] ，诸如此类)。

## Lua执行redis命令

~~~shell
redis.call(command,key[param1,param2…])
~~~

* 案例

  ~~~shell
  eval "return redis.call('set','foo','bar')" 0  #写死
  eval "return redis.call('set',KEYS[1],ARGV[1])" 1 tiger 10000  #带参数
  ~~~

* 调用Lua脚本，对ip进行限流

  ~~~shell
  ./redis-cli --eval "ip_limit.lua" app:ip:limit:192.168.3.10 , 6 10
  ~~~

  > ip_limit.lua
  >
  > ~~~shell
  > local num=redis.call('incr',KEYS[1])  #计数1，key不存在则创建
  > if tonumber(num)==1 then 
  >    redis.call('expire',KEYS[1],ARGV[1])  #第一次访问，设置过期时间
  >    return 1
  > elseif tonumber(num)>tonumber(ARGV[2]) then  #第二个参数表示否超过限制的次数
  >   return 0  #超限
  > else 
  >   return 1  #可以正常访问
  > end
  > ~~~
  >
  > 

* 自乘

  ~~~
  ./redis-cli --eval "self_multiply.lua" mutliply , 3
  ~~~

  > 缓存脚本，得到一个摘要 ‘8b25e457d2e7c839ca9d3ed00f69d13cffbb7253’
  >
  > ~~~shell
  > script load "local curVal = redis.call('get',KEYS[1]);if curVal == false then curVal = 0 else curVal = tonumber(curVal) end;curVal = curVal*tonumber(ARGV[1]);redis.call('set',KEYS[1],curVal); return curVal;"
  > ~~~
  >
  > 执行缓存脚本
  >
  > ~~~shell
  > evalsha '8b25e457d2e7c839ca9d3ed00f69d13cffbb7253' 1 num 5
  > ~~~
  >
  > 脚本执行太长时间时，可以对其进行杀死
  >
  > ~~~
  > script kill
  > ~~~
  >
  > 

  

* 测试redis到底有多快

  ~~~shell
  /usr/local/redis/bin/redis-benchmark -t set,lpush -n 100000 -q
  ## 在redis客户端启动目录下
  ./redis-benchmark -n 100000 -q script load "redis.call('set','foo','bar')"
  ~~~

* 

# redis持久化设置

修改redis.conf 文件：【vim /usr/local/redis/etc/redis.conf】

## 两种持久化方式

### snapshotting

(快照，rdb)默认方式：在指定的时间间隔内将内存中的数据集快照写入磁盘，rdb文件：dump.rdb

> 1. save 900 1 ###900秒内如果超过1个key被修改则发起快照保存
> 2. save 300 10 ###300秒内如果超过10个key被修改则发起快照保存
> 3. save 60 10000 ###60秒内如果超过10000个key被修改则发起快照保存

### append-only file

(aof)方式：类似于Oracle日志，体现数据的高可靠，必须开启，工作中常用，但一定层度上会导致变慢 ，aof设置：【appendonly yes】，开启后，rdb方式自动失效，文件名：【appendfilename "appendonly.aof"】，可以修改

> 1. #appendfsnyc always ###收到命令立即写入磁盘，效率最慢，但保证完全持久化，工作中用
> 2. #appendfsnyc everysec ###每秒写入一次磁盘，在性能和持久化方面做了折中
> 3. #appendfsnyc no ###完全依赖os，持久化没保证，一般不用

### 持久化文件放到改目录下便于管理

~~~
dir /usr/local/redis/etc/
~~~





# 分布式锁

使用zk会更专业，但是如果需要高并发处理，则redis会更合适

## redisson

基于redis实现的java驻内存数据网格（In-Memory Data-Grid），提供了分布式和可扩展的java数据结构

~~~xml
    <dependencies>
        <dependency>
            <groupId>org.redisson</groupId>
            <artifactId>redisson</artifactId>
            <version>3.5.4</version>
        </dependency>
    </dependencies>
~~~



## Luttuce

## Jedis

## zookeeper

## 分布式锁的基本要求

### 互斥性：

只有一个客户端能够持有锁【jedis.setnx(key, value)】

~~~java
package distlock;

import redis.clients.jedis.Jedis;
import java.util.Collections;

public class DistLock {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    
    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        // set支持多个参数 NX（not exist） XX（exist） EX（seconds） PX（million seconds）
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
}
~~~

### 不会产生死锁：

即使持有锁的客户端崩溃，也能保证其他客户端后续获得锁【jedis.expire(key, seconds)】

### 只有持有锁的客户端才能释放锁

## Pipeline

~~~java
package pipeline;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * tiger
 */
public class PipelineSet {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.3.81", 6379);
        Pipeline pipelined = jedis.pipelined();
        long t1 = System.currentTimeMillis();
        for (int i=0; i < 1000000; i++) {
            pipelined.set("batch"+i,""+i);
        }
        pipelined.syncAndReturnAll();
        long t2 = System.currentTimeMillis();
        System.out.println("耗时："+(t2-t1)+"ms");
    }
}

~~~

~~~java
package pipeline;

import redis.clients.jedis.*;
import java.util.*;

public class PipelineGet {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.3.81", 6379);
        //jedis.auth("tiger@139.com");
        Set<String> keys = jedis.keys("batch*");
        List<Object> result = new ArrayList();
        Pipeline pipelined = jedis.pipelined();
        long t1 = System.currentTimeMillis();
        for (String key : keys) {
            pipelined.get(key);
        }
        result = pipelined.syncAndReturnAll();
        for (Object src : result) {
            //System.out.println(src);
        }
        System.out.println("Pipeline get耗时："+(System.currentTimeMillis() - t1));
    }
}

~~~

## 缓存雪崩

大量热点数据同时过期

1. 加锁
2. 预更新
3. ttl加随机数
4. 永不过期

## 缓存击穿

一个热点key过期

1.永不过期

## 缓存穿透

请求数据库不存在的值，redis失去作用



## BitMap（位图）

存储0/1（默认全部是0），类似签到表，在打勾1，不在打叉0

1. 把不定长度转成相同长度输出
2. 均匀（hash函数：MD5、SHA-1）,hash碰撞一定存在，只能尽可能减少概率，

## Bloom Filter（布隆过滤器），Guava

快速判断某个元素是否在容器里边，而且又节省内存，案例：海量元素中（100亿无序，不定长，不重复）快速判断元素是否存在？

一个位数组和若干个hash函数

> 1. 位数组容量基于元素个数和误判率计算得出
> 2. 根据为数组容量大小进一步计算出哈希函数个数

1. 如果布隆过滤器判断某个元素在容器中存在，不一定存在，因为可能是其他元素标记的
2. 如果布隆过滤器判断某个元素在容器中不存在，那么一定不存在

~~~xml
         <!-- https://mvnrepository.com/artifact/com.google.guava/guava -->
		<!-- Guava布隆过滤器-->
		<dependency>
			<groupId>com.google.guava</groupId>
			<artifactId>guava</artifactId>
			<version>21.0</version>
		</dependency>

		<!-- CountingBloomFilter 带计数器的布隆过滤器-->
		<dependency>
			<groupId>com.baqend</groupId>
			<artifactId>bloom-filter</artifactId>
			<version>1.0.7</version>
		</dependency>
~~~



## 保留小数点位数,显示百分比

~~~java
    public static void main(String[] args) {
        int wrong = 1100;
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(2); //最大小数位数
        float percent = (float) wrong / 9900;
        float bingo = (float) (9900 - wrong) / 9900;
        System.out.println(percentFormat.format(bingo));
        System.out.println(percentFormat.format(percent));
    }
~~~

