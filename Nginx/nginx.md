# HTTP协议

HTTP 协议是基于应用层的协议，并且在传输层使用的 TCP 的可靠性通信协议

## IP:端口

ip确定具体的地址，端口确定地址内的进程，组合在一起确定了某个进程的确定位置

## 域名

ip不易记住，用域名来方便人脑记忆，通过dns域名解析器来转换

~~~tex
协议://子域名.顶级域名.域名类型/资源路径?参数
http://nginx.org
https://www.baidu.com:443
https://www.qq.com
https://user.qzone.qq.com/1165069099/infocenter?via=toolbar
~~~

1. 协议：http、https
2. 子域名：www、user.qzone
3. 顶级域名：baidu、qq
4. 域名类型：com、org、cn
5. 资源路径：/1165069099/infocenter
6. 参数：?via=toolbar

## 特点

1. 简单快速 
2. 灵活
3. 无连接 
4. 无状态

## MIME Type

描述消息内容类型的因特网标准，常见的几种类型

1. 文本文件：text/html,text/plain,text/css,application/xhtml+xml,application/xml,application/json
2. 图片文件：image/jpeg,image/gif,image/png
3. 视频文件：video/mpeg,video/quicktime

## 文件的渲染类型

1. Accept：表示客户端希望接受的数据类型，即告诉服务器我需要什么媒体类型的数据，此时服务器应该根据 Accept 请求头生产指定媒体类型的数据。
2. Content-Type：表示发送端发送的实体数据类型，比如代码中设置：resposne.setContentType("application/json;charset=utf-8")，表示服务端返回的数据格式是json。

## 请求报文格式

1. 请求行【请求方法 uri 版本】空格分开，例如：POST /v1/sd HTTP/1.1
2. 请求报头
3. 空行
4. 正文主体，POST请求才有

### 请求方法 Request Method

1. GET(查询操作)：获取资源，不会改变资源信息，不支持大数据的传输，参数明文显示，敏感信息请求不要使用。
2. POST(创建操作)：一般用户客户端传输一个实体给到服务端，让服务端去保存，信息在请求体中，加密处理。
3. PUT(更新操作)：从客户端向服务器传送的数据取代指定的文档的内容。
4. DELETE(删除操作)：客户端发起一个 Delete 请求要求服务端把某个数据删除。
5. HEAD:获得报文首部。
6. OPTIONS：询问支持的方法，允许客户端查看服务器的性能。
7. TRACE：追踪路径，回显服务器收到的请求，主要用于测试或诊断。
8. CONNECT：HTTP/1.1 协议中预留给能够将连接改为管道方式的代理服务器。

## 响应报文格式

1. 状态行【HTTP/1.1 200 OK】
2. 响应报头
3. 空行
4. 响应正文

### 响应状态码

状态码：描述服务端向客户端返回的请求处理结果状态，浏览器可以知道服务器是正常处理请求还是出现了错误。

1. 1XX(Informational,信息状态码)：接收的请求正在处理。
2. 2XX(Success,成功状态码)：请求正常处理完毕。
3. 3XX(Redirection,重定向状态码)：需要进行附加操作以完成请求。
4. 4XX(Client Error,客户端请求错误码)：服务器无法处理请求。
5. 5XX(Sever Error,服务端错误代码)：服务器处理请求出错。

常见状态码

> 1. 200：一切正常
> 2. 301：永久重定向
> 3. 404：请求资源不存在
> 4. 500：服务端内部错误



## 通用头字段 Common Header

| 字段              | 含义                                       |
| ----------------- | ------------------------------------------ |
| Cache-Control     | 控制缓存的行为                             |
| Connection        | 控制不再转发给代理的首部字段、管理持久连接 |
| Date              | 创建报文的日期时间                         |
| Pragma            | 报文指令                                   |
| Trailer           | 报文末端的首部一览                         |
| Transfer-Encoding | 指定报文主体的传输编码方式                 |
| Upgrade           | 升级为其他协议                             |
| Via               | 代理服务器的相关信息                       |
| Warning           | 错误通知                                   |

## 请求头字段

| 字段                | 含义                                          |
| ------------------- | --------------------------------------------- |
| Accept              | 用户代理可处理的媒，  本类型                  |
| Accept-Charset      | 优先的字符集                                  |
| Accept-Encoding     | 优先的内容编码                                |
| Accept-Language     | 优先的语言（自然语言）                        |
| Authorization       | Web认证信息                                   |
| Expect              | 期待服务器的特定行为                          |
| From                | 用户的电子邮箱地址                            |
| Host                | 请求资源所在服务器                            |
| If-Match            | 比较实体标记（ETag）                          |
| If-Modified-Since   | 比较资源的更新时间                            |
| If-None-Match       | 比较实体标记（与If-Match相反）                |
| If-Range            | 资源未更新时发送实体Byte的范围请求            |
| If-Unmodified-Since | 比较资源的更新时间（与If-Modified-Since相反） |
| Max-Forwards        | 最大传输逐跳数                                |
| Proxy-Authorization | 代理服务器要求客户端的认证信息                |
| Range               | 实体的字节范围请求                            |
| Referer             |                                               |
| TE                  | 传输编码的优先级                              |
| User-Agent          | HTTP客户端程序的信息                          |

## 响应头字段

| 字段               | 含义                         |
| ------------------ | ---------------------------- |
| Accept-Ranges      | 是否接受字节范围请求         |
| Age                | 推算资源创建经过时间         |
| ETag               | 资源的匹配信息               |
| Location           | 令客户端重定向至指定URI      |
| Proxy-Authenticate | 代理服务器对客户端的认证信息 |
| Retry-After        | 对再次发起请求的时机要求     |
| Server             | HTTP服务器的安装信息         |
| Vary               | 代理服务器缓存的管理信息     |
| WWW-Authenticate   | 服务器对客户端的认证信息     |

# docker安装nginx

## 创建挂载目录

~~~shell
mkdir -p /usr/local/soft/nginx/{conf,conf.d,html,log}
~~~

## 配置文件nginx.conf ，上传到nginx的conf目录下

1. 全局快
2. events块
3. http块

~~~shell
user  nginx;
worker_processes  1;

error_log  /var/log/nginx/error.log warn;
pid        /var/run/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    keepalive_timeout  65;

    #gzip  on;

    #include /etc/nginx/conf.d/*.conf;

    server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            root   /usr/share/nginx/html;
            #root这里填了容器中的绝对路径
            #我填相对路径如html时
            #容器会去/etc/nginx/html找
            index  index.html index.htm;
        }
        
        location /api {
        	#这里是后端的设置，根据你的实际情况写
            rewrite ^/api/(.*)$ /$1 break;
            proxy_pass http://49.234.55.50:80;
        }
    }
}


~~~

## 启动

~~~shell
docker run --name nginx01 -d -p 80:80  -v /usr/local/soft/nginx/conf/nginx.conf:/etc/nginx/nginx.conf  -v /usr/local/soft/nginx/log:/var/log/nginx  -v /usr/local/soft/nginx/html:/usr/share/nginx/html nginx
~~~



