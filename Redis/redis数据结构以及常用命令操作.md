
更加详细查询redis官方网站【https://www.redis.net.cn/order/3625.html】

flushall

# Redis 5种数据类型

## 字符串（String）Map<key,value>

二进制的形式存储，value最大容纳数据长度512M，key-value(string/int/float)，雷同：Map<key,value>

   ### 基本操作命令

1. 赋值更新

   ~~~
   set key value
   setnx key value --如果key存在就不设置，返回0，不存在才设置，返回
   ~~~

2. 赋值多个值

   ~~~
   mset key1 value1 key2 value2
   ~~~

3. 设置过期时间

   ~~~
   setex key second value --second表示second后秒过期
   ~~~

4. 按索引替换

   ~~~
   setrange key index value --index表示从index处开始替换
   ~~~

5. 取值

   ~~~
   get key
   ~~~

6. 删除

   ~~~
   del key
   ~~~

7. 扩展

   ~~~
   getset key value --表示先将值取出再重新覆盖赋值
   ~~~

8. 数值增减

   ~~~
   incr/decr key --key自增/减1，如果key不存在，则先给key自动赋值0，再加/减1	
   incrby/decrby key 2 --key的加/减加2	
   ~~~

9. …

### 应用场景

* 分布式锁
* 字符串分布式键
* 分布式计时器



## 哈希（Hash）Map<String,HashMap<String,value>>

由多个无序键值对(key-value)散列组组成，其中key是字符串，value是元素，按key进行增删，每个key可以存储2^32-1,4294967295个键值对（40亿）；雷同：Map<String,HashMap<String,value>>

### 基本操作命令

1. 赋值

   ~~~
   hset key field value  
   hmset key filed1 value1 filed2 value2  --一次设置多个值
   ~~~

2. 取值

   ~~~
   hget key field  --取指定key中field的value
   hmget key field1 field2 --取指定key中多个field的值
   hgetall key  --获取指定key中所有(field,value)
   hkeys key  --获取指定key中所有field
   hvals key  ----获取指定key中所有value
   ~~~

3. 删除

   ~~~
   hdel key field1 field2  --删除指定key中(field,value)
   del key  --删除指定key中的所有元素
   ~~~

4. 自学命令

   ~~~
   hexists key field  --判断某属性是否存在
   ~~~

5. 获取长度

   ~~~
   hlen key  --获取指定key的里面field的个数，长度
   ~~~

6. …



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
2. hashtable(OBJECT_ENCODING_HT)：哈希表

## 字符串列表（List）Map<key,List<value>>

允许重复元素，雷同：Map<key,List<value>>

   ### 基本操作命令

1. 赋值

   ~~~
   lpush/rpush key value[value...]  --往key列表键中左/右边放入一个元素，key不存在则新建
   lpushx/rpushx key value[value...]  --往key列表键中左/右边放入一个元素，key存在才赋值
   ~~~

2. 取值

   ~~~
   lrange key start stop --获取列表键从start下标到stop下标元素
   lrange key 0 -1  --获取所有
   ~~~

3. 删除

   ~~~
   lpop/rpop key --弹出左/右边的元素
   blpop key [key...] timeout  --阻塞从key列表键最左端弹出一个元素，若列表键中不存在元素，阻塞等待{timeout}秒，若{timeout}=0，则一直等待
   brpop key [key...] timeout  --改性质可以用来做消息队列的实现
   ~~~

   

4. 获取长度

   ~~~
   llen key  --取元素的长度
   ~~~

5. …

### 应用场景



## 字符串集合（Set）Map<key,Set<value>>

无序不重复，跟踪数据唯一性，维护数据对象之间的关联关系，雷同：Map<key,Set<value>>

### 基本操作命令

1. 赋值

   ~~~
   sadd key member [member ...]  --往集合键key中存放元素，若key不存在则新建
   
   ~~~

2. 取值

   ~~~
   smembers key  --获取集合键key中所有元素
   srandmember key [count]  --从集合键中随机获取{count}个元素
   sismember key member  --判断{member}是否存在于集合键key中
   scard key  --获取集合键key元素个数
   ~~~

3. 删除

   ~~~
   srem key member [member ...]  --从集合键key中删除元素
   spop key [count]  --从集合键中随机选{count}个元素，并删除
   ~~~

4. 差集运算，存储

   ~~~
   sdiff set1 set2  --求出两集合中相差的元素
   sdiffstore set set1 set2  --
   ~~~

5. 交集运算，存储

   ~~~
   sinter set1 set2  --求出两集合中都有的元素
   sinterstore set set1 set2  --
   ~~~

6. 并集运算，存储

   ~~~
   sdunion set1 set2  --合并两个集合，重复的元素set会去掉
   sunionstore set set1 set2  --
   ~~~

7. …

## 有序字符串集合（Sorted Set）   Map<key,TreeMap<key,value>>

带分数score-value有序集合,value不可重复，score可重复，雷同：   Map<key,TreeMap<key,value>>

### 基本操作命令

1. 赋值， https://yq.aliyun.com/articles/504008

   ~~~
   zadd key [NX|XX] [CH] [INCR] score member [score member ...]  --往有序集合键key中存放元素，若key不存在则新建
   zadd key 100 zs  --会替换掉之前存在的分数的值
   ~~~

2. 取值

   ~~~
   zscore key zs
   zrange key 0 -1  --取某范围内的值，默认是升序
   zrevrange key 0 -1  --取某范围内的值，从大到小
   zrange key 0 -1 withscores  --取某范围内的值，并将分数也取出
   zrangebyscore key 80 100 withscores limit 0 2  --根据分数的范围进行取值
   zcard key  --取长度
   zcount key 80 90  --获取某分数之间的个数
   ~~~

3. 删除

   ~~~
   zrem key zs ls
   zremrangebyrank key 0 4  --根据排名的范围进行删除
   zremrangebyscore key 80 100  --根据分数的范围进行删除
   
   ~~~

   

4. 给某个分数加值

   ~~~
   zincrby key 3 ls  --表示给ls的分数加3
   ~~~

5. …

### 应用场景

* 排名

* 热点话

* 构建索引数据

  

## Redis Key 的通用命令

1. 获得所有key

   ~~~
   keys *
   ~~~

2. 删除指定key

   ~~~
   del key1 key2 key3
   ~~~

3. 查看指定key是否存在

   ~~~
   exists key
   ~~~

4. 对key重命名

   ~~~
   rename key newKey
   ~~~

5. 设置key的过期时间

   ~~~
   expire key 1000  --单位是秒
   ~~~

6. 查看key过期所剩的时间

   ~~~
   ttl key
   ~~~

7. 获取key的类型

   ~~~
   type key
   ~~~

8. …

