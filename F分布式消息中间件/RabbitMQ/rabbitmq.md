# 什么是MQ

MQ（Message Queue）[消息队列](https://baike.baidu.com/item/消息队列/4751675)，是基础数据结构中“先进先出”的一种数据结构。一般用来解决应用解耦，异步消息，流量削峰等问题，实现高性能，高可用，可伸缩和最终一致性架构。

# MQ的优点

1. 解耦：上下游逻辑+物理解耦，除了与MQ有物理连接，模块之间都不相互依赖；将消息写入消息队列，新增一个下游消息关注方，上游不需要修改任何代码。

2. 异步：上游执行时间短，将消息写入消息队列，非必要的业务逻辑以异步的方式运行，加快响应速度。

3. 削峰：系统A慢慢的按照数据库能处理的并发量，从消息队列中慢慢拉取消息。在生产中，这个短暂的高峰期积压是允许的。

# MQ的缺点

1. 系统可用性降低。依赖服务也多，服务越容易挂掉。需要考虑MQ瘫痪的情况

2. 系统复杂性提高。需要考虑消息丢失、消息重复消费、消息传递的顺序性

3. 业务一致性。主业务和从属业务一致性的处理

4. 上游无法知道下游的执行结果，这一点是很致命的

# 应用场景

1. 数据驱动的任务依赖
2. 上游不关心多下游执行结果
3. 异步返回执行时间长

# 不适用场景

举个**栗子**：用户登录场景，登录页面调用passport服务，passport服务的执行结果直接影响登录结果，此处的“登录页面”与“passport服务”就必须使用调用关系，而不能使用MQ通信。

**结论**：**调用方实时依赖执行结果的业务场景，请使用调用，而不是MQ**。

# AMQP协议

1. Server：又称Broker，接收客户端连接，实现AMQP实体服务

2. Connection：连接，应用程序与Broker的网络连接

3. Channel：网络信道，几乎所有的操作都是在这上面进行，是消息读写的通道，客户端可以建立多个，每个代表一个会话

4. Message：消息，服务器与应用程序之间传送数据的载体，由Properties和Body组成。Properties可以对消息进行修饰，比如优先级、延迟等高级特性，Body是消息体内容。

5. Virtual host：虚拟主机，用于逻辑隔离，最上层的消息路由。可以有多个，一般由项目模块划分

6. Exchange：交换机，接收消息，根据路由键转发消息到绑定的队列

7. Binding：Exchange与Queue之间的虚拟连接，binding中可以包含routing key

8. RoutingKey：一个路由规则，虚拟机可以用它来确定如何路由一个特定的消息

9. Queue：即Message Queue，消息队列，保存消息并将它们转发给消费者

10. 是消费者主动获取消息还是Broker推送给消费者？

1. Pull模式

   basicGet,消息存放咋啊服务端，只有消费者主动获取才能拿到消息，好处是可以根据自己的消费能力设定获取消息的频率

2. Push模式

   basicConsume,只要生产者发送消息到服务器，就立马推送到消费者，消息保存在客户端，实时性高，如果消费不过来可能会造成积压。spring AMQP是Push方式。通过事件机制对队列进行监听，只要消息到达队列就立刻触发消费消息的方法。

3. RabbitMQ中Pull和Push都实现，kafka和RocketMQ只有Pull

# 安装

## window

1. 安装 Erlang for Windows 程序

   ~~~
   http://www.erlang.org/downloads
   ~~~

2. Erlang环境变量配置：

   1. 新增变量

      ~~~tex
      ERLANG_HOME=C:\Program Files\erl9.2
      ~~~

   2. 在Path中加入

      ~~~tex
      %ERLANG_HOME%\bin;
      ~~~

   3. 测试erl配置是否正确，开始-运行-cmd，输入erl，显示 Eshell V9.2  (abort with ^G)，说明成功。

3. 下载安装RabbitMQ

   ~~~tex
   http://www.erlang.org/downloads
   ~~~

4. RabbitMQ环境变量配置

   1. 新增变量

      ~~~tex
      RABBITMQ_SERVER=C:\Program Files\RabbitMQ Server\rabbitmq_server-3.7.3
      ~~~

   2. 在Path中加入

      ~~~tex
      %RABBITMQ_SERVER%\sbin;
      ~~~

5. 激活管控台rabbitmq_management，在CMD中键入如下命令：

   ~~~tex
   C:\Program Files\RabbitMQ Server\rabbitmq_server-3.7.3\sbin\rabbitmq-plugins.bat" enable rabbitmq_management
   ~~~

6. 启动RabbitMQ和启动服务

   ~~~shell
   net start RabbitMQ
   net stop RabbitMQ
   ~~~

7. RabbitMQ测试：测试地址 【 http://localhost:15672/ 】，默认账户密码：guest/guest

8. Java客户端测试：

## linux

1. 安装 Erlang

   ~~~shell
   # 方式1
   wget https://packages.erlang-solutions.com/erlang-solutions-1.0-1.noarch.rpm
   rpm -Uvh erlang-solutions-1.0-1.noarch.rpm
   # 方式2
   sudo yum install epel-release
   sudo yum install erlang
   ~~~

2. 安装

   ~~~sh
   wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.6/rabbitmq-server-3.6.6-1.el7.noarch.rpm
   yum install rabbitmq-server-3.6.6-1.el7.noarch.rpm
   ~~~

3. 开放端口

   ~~~shell
   /sbin/iptables -I INPUT -p tcp --dport 5672 -j ACCEPT
   /sbin/iptables -I INPUT -p tcp --dport 15672 -j ACCEPT
   ~~~

4. 复制配置文件：

   ~~~shell
   cp /usr/share/doc/rabbitmq-server-3.6.6/rabbitmq.config.example /etc/rabbitmq/rabbitmq.config
   ~~~

5. 编辑rabbitmq.config：将%% 和后面的，删除

6. 安装管控台插件

   ~~~shell
   /sbin/rabbitmq-plugins enable rabbitmq_management
   ~~~

7. 重启服务

   ~~~shell
   service rabbitmq-server restart
   ~~~

8. 关闭防火墙：

   ~~~shell
   systemctl stop firewalld.service
   # 端口是15672，账号和密码默认都是guest
   ~~~

9. 进入管控台

   ~~~tex
   http://192.168.56.101:15672
   ~~~

10. 赋予用户默认vhost的全部操作权限

    ~~~shell
    rabbitmqctl set_permissions -p / username ".*" ".*" ".*"
    ~~~

11. 添加开机启动RabbitMQ服务

    ~~~shell
    sudo chkconfig rabbitmq-server on
    ~~~

12. 启动服务

    ~~~shell
    /sbin/service rabbitmq-server start &
    /sbin/service rabbitmq-server restart &
    ~~~

13. 查看服务状态

    ~~~shell
    /sbin/service rabbitmq-server status
    ~~~

14. 停止服务

    ~~~shell
    sudo /sbin/service rabbitmq-server stop
    ~~~

15. 查看所有用户

    ~~~shell
    rabbitmqctl list_users
    ~~~

16. 查看用户权限

    ~~~shell
    rabbitmqctl list_user_permissions username
    ~~~

17. 删除用户

    ~~~shell
    rabbitmqctl delete_user guest
    ~~~

18. 添加用户

    ~~~shell
    sudo rabbitmqctl add_user username password
    ~~~

19. 设置用户tag

    ~~~shell
    rabbitmqctl set_user_tags username administrator
    ~~~

20. …

## 单机版

CentOS7安装RabbitMQ单机版(3.8.4)

~~~
https://gper.club/articles/7e7e7f7ff4g56gceg6e
~~~



# 路由方式

## Direct 直连

一个队列与直连类型的交换机绑定，需要指定一个明确的绑定键（binding key）,生成者发送消息会携带一个路由键（routing key）。当消息的路由键与某个队列的绑定键完全匹配时，这条消息才会从交换机路由到这个队列上。多个队列也可以使用相同的绑定键。

适用于一些业务用途明确的消息

## Topic 主题

一个队列与主题类型的交换机绑定时，可以在绑定键中使用通配符。支持两个通配符

~~~
# 代表0个或者多个单词
* 代表不多不少一个单词
注意：单词（world）指的是用英文的点‘.’隔开。例如 a.db.def是3个单词
~~~

主题类型的交换机适用于一些根据业务主题或者消息等级过滤消息的场景。例如一条消息可能跟资金有关，又跟风控有关，那就可以让这个消息指定一个多级的路由键。

## Fanout 广播

广播类型的交换机与队列绑定时，不需要指定绑定键。因此生产者发送消息到广播类型的交换机上，也不需要带路由键。消息到达交换机时，所有与之绑定的队列都会收到相同的消息副本。

# Java API

```properties
# amqp:用户名:密码@IP:端口/虚拟主机 
rabbitmq.uri=amqp://guest:guest@127.0.0.1:5672
# 默认使用 / 的vhost，如果修改vhost，加在端口后即可，如 /tigerhost
rabbitmq.uri=amqp://guest:guest@127.0.0.1:5672/tigerhost
```

## 死信队列







# springboot使用rabbitmq

## 引入依赖包

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.gupaoedu</groupId>
    <artifactId>springboot-rabbit-consumer</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>springboot-rabbit-consumer</name>
    <description>Demo project for RabbitMQ Consumer</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.6.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.5</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.21</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
~~~

## RabbitConfig配置类

创建队列、交换机、队列与交换机的绑定关系

~~~java
package com.gupaoedu.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: qingshan
 * @Description: 咕泡学院，只为更好的你
 */
@Configuration
@PropertySource("classpath:gupaomq.properties")
public class RabbitConfig {
    @Value("${com.gupaoedu.firstqueue}")
    private String firstQueue;

    @Value("${com.gupaoedu.secondqueue}")
    private String secondQueue;

    @Value("${com.gupaoedu.thirdqueue}")
    private String thirdQueue;

    @Value("${com.gupaoedu.fourthqueue}")
    private String fourthQueue;

    @Value("${com.gupaoedu.directexchange}")
    private String directExchange;

    @Value("${com.gupaoedu.topicexchange}")
    private String topicExchange;

    @Value("${com.gupaoedu.fanoutexchange}")
    private String fanoutExchange;

    // 创建四个队列
    @Bean("vipFirstQueue")
    public Queue getFirstQueue(){
        return new Queue(firstQueue);
    }

    @Bean("vipSecondQueue")
    public Queue getSecondQueue(){
        return new Queue(secondQueue);
    }

    @Bean("vipThirdQueue")
    public Queue getThirdQueue(){
        return  new Queue(thirdQueue);
    }

    @Bean("vipFourthQueue")
    public Queue getFourthQueue(){
        return  new Queue(fourthQueue);
    }

    // 创建三个交换机
    @Bean("vipDirectExchange")
    public DirectExchange getDirectExchange(){
        return new DirectExchange(directExchange);
    }

    @Bean("vipTopicExchange")
    public TopicExchange getTopicExchange(){
        return new TopicExchange(topicExchange);
    }

    @Bean("vipFanoutExchange")
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange(fanoutExchange);
    }

    // 定义四个绑定关系

    // 直连型交换机(directExchange) 与 队列1绑定
    @Bean
    public Binding bindFirst(@Qualifier("vipFirstQueue") Queue queue, @Qualifier("vipDirectExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("gupao.best");
    }

    // 主题型交换机(topicExchange) 与 队列2绑定
    @Bean
    public Binding bindSecond(@Qualifier("vipSecondQueue") Queue queue, @Qualifier("vipTopicExchange") TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("*.gupao.*");
    }

    // 广播型交换机(fanoutExchange) 与 队列3绑定
    @Bean
    public Binding bindThird(@Qualifier("vipThirdQueue") Queue queue, @Qualifier("vipFanoutExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }

    // 广播型交换机(fanoutExchange) 与 队列4绑定
    @Bean
    public Binding bindFourth(@Qualifier("vipFourthQueue") Queue queue, @Qualifier("vipFanoutExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }

    /**
     * 在消费端转换JSON消息
     * 监听类都要加上containerFactory属性
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setAutoStartup(true);
        return factory;
    }
}
~~~

## 创建消费者

~~~java
package com.gupaoedu.consumer;

import com.gupaoedu.entity.Merchant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Author: qingshan
 * @Description: 咕泡学院，只为更好的你
 */
@Component
@PropertySource("classpath:gupaomq.properties")
// 监听的队列名称，可以同时监听多个
@RabbitListener(queues = "${com.gupaoedu.firstqueue}", containerFactory="rabbitListenerContainerFactory")
public class FirstConsumer {

    @RabbitHandler
    public void process(@Payload Merchant merchant){
        System.out.println("First Queue received msg : " + merchant.getName());
    }
}
~~~

