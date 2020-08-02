# [Docker Engine](https://docs.docker.com/get-docker/)

## [Install Docker for Linux](https://docs.docker.com/engine/install/centos/)

### 使用docker命令安装

1. 移除旧版

   ```
     sudo yum remove docker \
                     docker-client \
                     docker-client-latest \
                     docker-common \
                     docker-latest \
                     docker-latest-logrotate \
                     docker-logrotate \
                     docker-engine
   ```

2. 安装一些必要的系统工具

   ```
   sudo yum install -y yum-utils device-mapper-persistent-data lvm2
   ```

3. 添加软件源信息（阿里云的）

   ```
   sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
   ```

4. 更新 yum 缓存

   ```
   sudo yum makecache fast
   ```

5. Install the *latest version* of Docker Engine and containerd

   ```
   sudo yum install docker-ce docker-ce-cli containerd.io
   ```

6. 启动 Docker 后台服务

   ```
   sudo systemctl start docker
   ```

7. 测试运行 hello-world

   ```
   docker run hello-world
   ```

8. 设置开机启动

   ```
   systemctl start docker && systemctl enable docker
   ```

### 使用脚本安装

1. 使用 sudo 或 root 权限登录 Centos

2. 确保 yum 包更新到最新

   ```
   sudo yum update
   ```

3. 执行 Docker 安装脚本

   ```
   curl -fsSL https://get.docker.com -o get-docker.sh && sudo sh get-docker.sh
   ```

4. 启动Docker进程 && 后台启动

   ```
   sudo systemctl start docker && sudo systemctl enable docker
   ```

5. 测试运行 hello-world：

   ```
   docker run hello-world
   ```

## Docker Engine卸载

1. Uninstall the Docker Engine, CLI, and Containerd packages:

   ```
   sudo yum remove docker-ce docker-ce-cli containerd.io
   ```

2. Images, containers, volumes, or customized configuration files on your host are not automatically removed. To delete all images, containers, and volumes:

   ```
   sudo rm -rf /var/lib/docker
   ```

# 镜像加速

1. 创建文件夹

   ```
   sudo mkdir -p /etc/docker
   ```

2. 执行如下命令：网址，阿里云上加速地址【https://ea3wi4om.mirror.aliyuncs.com】

   ```
   sudo tee /etc/docker/daemon.json <<-'EOF'
   { 
     "exec-opts": ["native.cgroupdriver=systemd"],
     "registry-mirrors": ["https://ea3wi4om.mirror.aliyuncs.com"]
   }
   EOF
   sudo systemctl daemon-reload
   sudo systemctl restart docker
   ```

# [**images**](https://docs.docker.com/engine/reference/commandline/image/)

## images基本操作命令

1. 罗列所有镜像

   ```
   docker images
   ```

2. 拉取images

   ```
   docker pull 镜像名:[镜像标签]
   ```

3. 给镜像打标签

   ```
   docker tag 原镜像名 新镜像名
   ```

4. 删除images（删除前需要stop）：

   ```
   dcoker rmi -f 镜像名
   或
   docker rmi 镜像id
   ```

5. 删除所有images

   ```
   docker rmi -f $(docker images)
   ```

6. 由容器生成镜像

   ```
   docker commit 容器名称 新镜像名称
   ```

7. 由*Dockerfile*文件生成images[^Dockerfile]

   ```
   docker build -t dockerfile-demo .
   ```

# [**container**](https://docs.docker.com/engine/reference/commandline/container/)

## 何为容器？

### Images and containers

Fundamentally, a container is nothing but a running process, with some added encapsulation features applied to it in order to keep it isolated from the host and from other containers. One of the most important aspects of container isolation is that each container interacts with its own private filesystem; this filesystem is provided by a Docker **image**. An image includes everything needed to run an application - the code or binary, runtimes, dependencies, and any other filesystem objects required.

### Containers and virtual machines

A container runs *natively* on Linux and shares the kernel of the host machine with other containers. It runs a discrete process, taking no more memory than any other executable, making it lightweight.

By contrast, a **virtual machine** (VM) runs a full-blown “guest” operating system with *virtual* access to host resources through a hypervisor. In general, VMs incur a lot of overhead beyond what is being consumed by your application logic.

|                            docker                            |                             vm                             |
| :----------------------------------------------------------: | :--------------------------------------------------------: |
| ![alt Docker](https://docs.docker.com/images/Container%402x.png "Docker") | ![alt VM](https://docs.docker.com/images/VM%402x.png "VM") |

## container基本操作命令

### [创建容器](https://docs.docker.com/engine/reference/run/)

#### 基本格式

```
docker run [OPTIONS] IMAGE[:TAG|@DIGEST] [COMMAND] [ARG...]
```

1. 运行nginx容器

   ```
   【docker run -d --name 容器名 -p 容器端口:映射端口 镜像名】
   【docker run -d --name nginx -p 88:9090 nginx】
   ```

   - -d：作为守护进程来执行
   - --name：指定要创建容器的名称
   - -p：**-p 88:9090**：映射容器服务的 88端口到宿主机的 9090端口。外部可以直接通过宿主机ip:9090访问到 nginx的服务

2. 交互式运行

   ```
   docker run -d -it --name my-tomcat -p 9090:8080 tomcat
   ```

3. 内存限制以及cpu权重

   ```
   docker run -d --name tomcat1 -p 6666:8080 --memory 100M --cpu-shares 10 tomcat
   ```

   

4. 指定网络和该网络下的ip  ，单独的网络需要自己创建

   ```
   docker network create --subnet=172.19.0.0/24 tomcat-net
   ```

   ```
   docker run -d --name tomcat1 -p 6666:8080 --memory 100M --cpu-shares 10 --net=tomcat-net --ip 172.19.0.2 tomcat
   ```

5. [创建mysql容器](https://hub.docker.com/_/mysql)

   ```
   docker pull mysql数据持久挂载
   ```

   ```
   docker run -d --name tiger-mysql -p 3366:3306 -v mysql_volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 --privileged mysql
   ```

   ```
   docker run -d --name tiger-mysql -p 3366:3306 -e MYSQL_ROOT_PASSWORD=123456 --privileged mysql
   ```

### 查看容器

```
docker ps [-a]
```

- -a：查看所有，不带-a则查看运行中的

### 进入到容器中

```
docker exec -it my-tomcat /bin/bash
```

### 停止容器进程

```
docker stop 容器id
或
docker stop 容器名称
```

## 启动容器进程

```
docker start 容器id
或
docker start 容器名称
```

## 删除容器

1. 删除指定

   ```
   docker rm 容器名称
   ```

2. 删除所有

   ```
   docker rm -f $(docker ps -aq)
   ```

## 查看容器日志

```
docker logs 容器名称
或
docker logs 容器id
```

## 在host与container之间拷贝文件

```
docker cp indexdocker.html 容器id://usr/share/nginx/html
```

## 查看容器详情

```
docker inspect eb1dfeec7fbb
```

# docker推送到镜像仓库

## [官方仓库Docker Hub](https://hub.docker.com/)

1. docker官方

   ```
   43df2c27-d5ef-4d56-ac66-3c9b581692e1
   ```

2. 在docker机器上登录

   ```
   docker login
   ```

3. 输入用户名和密码，可以配置认证文件

4. **注意push到用户名下的空间，不然push不成功**，因此需要打个标签,再push打过标签的那个images

   ```
   docker tag hello-world linjt2019/hello-world
   docker push linjt2019/hello-world
   docker rim hello-world
   ```

5. 拉取，并且运行

   ```
   docker pull linjt2019/hello-world
   docker run linjt2019/hello-world
   ```

## [阿里云 regs](https://cr.console.aliyun.com/repository/)

1. 登陆

   ```
   sudo docker login --username=1165069099@qq.com registry.cn-hangzhou.aliyuncs.com
   ```

2. 在阿里云页面创建命名空间，比如：[**tiger1029**](https://cr.console.aliyun.com/cn-hangzhou/instances/namespaces)

3. 给images打tag

   ```
   sudo docker tag tiger-dockerfile registry.cn-hangzhou.aliyuncs.com/tiger2019/tiger-docker-image:v1.0
   ```

4. 推送到阿里云仓库

   ```
   sudo docker push registry.cn-hangzhou.aliyuncs.com/tiger2019/tiger-docker-image:v1.0
   ```

5. 拉取镜像 pull 

   ```
   sudo docker pull registry.cn-hangzhou.aliyuncs.com/tiger2019/tiger-docker-image:v1.0
   ```

6. 运行镜像 run

   ```
   sudo docker run -d --name user01 -p 6661:8080 registry.cn-hangzhou.aliyuncs.com/tiger2019/tiger-docker-image:v1.0
   
   ```

# Dockerfile

## Dockerfile语法

- FROM：基础镜像
- RUN：执行命令
- ADD：添加文件,会自动解压
- COPY：拷贝文件
- CMD：执行命令
- EXPOSE：暴露端口
- WORKDIR：指定路径
- MAINTAINER：维护者
- ENV：设定环境变量
- ENTRYPOINT：容器入口
- USER：指定用户
- VOLUME：mountpoint，挂载点，数据保存的地方

## 简单案例如下

```
FROM openjdk:8
MAINTAINER tiger
LABEL name="dockerfile-demo" version="1.0" author="tiger"
COPY dockerfile-demo-0.0.1-SNAPSHOT.jar dockerfile-image.jar
CMD ["java","-jar","dockerfile-image.jar"]
```

# Docker监控资源工具

## [weaveworks/scope](https://github.com/weaveworks/scope)

### 安装

```
sudo curl -L git.io/scope -o /usr/local/bin/scope
```

权限赋予

```
sudo chmod a+x /usr/local/bin/scope
```

### 启动

指定当前 centos ip

```
scope launch 192.168.3.51
```

同时监控两台机器，在两台机器中分别执行如下命令

```
scope launch ip1 ip2
```

### 停止

```
scope stop
```

启动信息：可以看到访问地址，注意**浏览器开启极速模式**

```
Scope probe started
Weave Scope is listening at the following URL(s):
  * http://172.19.0.1:4040/
  * http://10.0.2.15:4040/
  * http://192.168.3.10:4040/
```

# [数据持久化***volume***](https://docs.docker.com/engine/reference/commandline/volume_create/)

## [创建 volume](https://docs.docker.com/engine/reference/commandline/volume_create/)[^volume name]

```
docker volume create volume名称
```

## [查看volume详情](https://docs.docker.com/engine/reference/commandline/volume_inspect/)

默认生成目录，如下【/var/lib/docker/volumes/mysql_volume/_data】

```
$ docker volume inspect mysql_volume
[
    {
        "CreatedAt": "2020-07-26T14:31:54Z",
        "Driver": "local",
        "Labels": {},
        "Mountpoint": "/var/lib/docker/volumes/mysql_volume/_data",
        "Name": "mysql_volume",
        "Options": {},
        "Scope": "local"
    }
]

```

## 查看volume列表

~~~
docker volume ls
~~~

## 删除volume

~~~
docker volume rm -f volume名称
~~~

## [删除未使用的volume](https://docs.docker.com/engine/reference/commandline/volume_prune/)

~~~
docker volume prune
~~~

## 挂载案例

```
docker run -d --name tiger-mysql -p 3366:3306 -v mysql_volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 --privileged mysql
```

### 不需要创建volume挂载方式

如下，但文件夹必须存在

~~~
docker run -d --name tiger-mysql -p 3366:3306 -v /mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 --privileged mysql
~~~





## 验证数据持久是否生效思路

1. 创建一个volume

   ```
   docker volume create volume_mysql
   ```

2. 实例化出一个mysql容器**tiger-mysql**并将数据挂载到volume_mysql上

   ~~~
   docker run -d --name tiger-mysql -p 3366:3306 -v mysql_volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 --privileged mysql
   ~~~

3. 进入**tiger_mysql**容器

   ~~~
   docker exec -it tiger-mysql bash
   ~~~

4. 登陆mysql服务器，创建一个测试表

   ~~~
   mysql -uroot -123456
   create database db_test;
   ~~~

5. 退出mysql服务

6. 删除容器

   ~~~
   docker rm -f tiger-mysql
   ~~~

7. 重新新创建mysql容器**new-mysql**，并将数据挂载到原先的volume上

   ~~~
   docker run -d --name new-mysql -p 3366:3306 -v mysql_volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 --privileged mysql
   ~~~

8. 进入容器并登陆，查看表，发现存在表**db_test**，说明数据没有因为容器的丢失而丢失，实验成功。

## Bind Mounting

宿主机器任意目录 -- 容器的任意目录 一一对应起来 对于开发者非常有效的利器，实时更新宿主机中的源码，而无需动容器

- 创建一个tomcat容器

  ~~~
  docker run -d --name tomcat01 -p 9090:8080 -v /tmp/test:/usr/local/tomcat/webapps/test tomcat
  ~~~

- 查看两个目录
    centos：【cd /tmp/test】
    tomcat容器：先进入到容器：【docker exec -it tomcat01 bash】，再：【cd /usr/local/tomcat/webapps/test】
- 在centos的/tmp/test中新建1.html，并写一些内容：【<p style="color:blue; font-size:20pt;">This is p!</p>】
- 进入tomcat01的对应目录查看，发现也有一个1.html，并且也有内容
- 在centos7上访问该路径【curl localhost:9090/test/1.html】
- 在win浏览器中通过ip访问【http://192.168.3.13:9090/test/1.html】

也可以将cetnos中的目录映射到window上的机器进行修改，按自己的思维进行挂载，
我的虚拟机通过 Vagrantfile 来创建，在该文件中有如下信息，将这行取消注释 config.vm.synced_folder "../data", "/vagrant_data" ,配置对应的目录
  \# Share an additional folder to the guest VM. The first argument is
  \# the path on the host to the actual folder. The second argument is
  \# the path on the guest to mount the folder. And the optional third
  \# argument is a set of non-required options.
  config.vm.synced_folder "C://AAAAA//all-code//async-servlet-demo", "/tmp/test"

# mysql高可用集群***pxc***

## 集群创建

1. 拉取pxc镜像

   ~~~
   docker pull percona/percona-xtradb-cluster:5.7.21
   ~~~

2. 打标签/重命名

   ~~~
   docker tag percona/percona-xtradb-cluster:5.7.21 pxc
   docker rmi percona/percona-xtradb-cluster:5.7.21
   ~~~

3. 创建一个单独的网段，给mysql数据库集群使用

   ~~~shell
   docker network create --subnet=172.19.0.0/24 pxc-net
   ~~~

4. 创建3个数据挂载点**volume**

   ~~~shell
   docker volume create --name mysql_v1
   docker volume create --name mysql_v2
   docker volume create --name mysql_v3
   ~~~

5. 运行三个PXC容器

   ~~~shell
   docker run -d --name=mysql_node1 -p 3301:3306 -v mysql_v1:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=123456 --privileged  --net=pxc-net --ip 172.19.0.2 pxc
   ~~~

   注意：**下边两个节点(mysql_node2和mysql_node3)加入到mysql_node1节点中**

   ~~~shell
   docker run -d --name=mysql_node2 -p 3302:3306 -v v2:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -e CLUSTER_JOIN=mysql_node1 -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=123456 --privileged  --net=pxc-net --ip 172.19.0.3 pxc
   ~~~

   ~~~powershell
   docker run -d --name=mysql_node3 -p 3303:3306 -v v3:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -e CLUSTER_JOIN=mysql_node1 -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=123456 --privileged  --net=pxc-net --ip 172.19.0.4 pxc
   ~~~

## 负载均衡[**haproxy**](https://hub.docker.com/_/haproxy)

### 拉取镜像

~~~shell
docker pull haproxy
~~~

### haproxy 配置文件

这里使用 bind mounting 的方式，配置文件挂载在宿主机中

在宿主机中创建文件夹和***haproxy.cfg***文件

~~~shell
mkdir /tmp/haproxy
touch haproxy.cfg
~~~

***haproxy.cfg***内容如下

~~~yaml
global
	#工作目录，这边要和创建容器指定的目录对应
	chroot /usr/local/etc/haproxy
	#日志文件
	log 127.0.0.1 local5 info
	#守护进程运行
	daemon
defaults
	log	global
	mode	http
	#日志格式
	option	httplog
	#日志中不记录负载均衡的心跳检测记录
	option	dontlognull
 	#连接超时（毫秒）
	timeout connect 5000
 	#客户端超时（毫秒）
	timeout client  50000
	#服务器超时（毫秒）
 	timeout server  50000

    #监控界面	
    listen  admin_stats
	#监控界面的访问的IP和端口
	bind  0.0.0.0:8888
	#访问协议
 	mode        http
	#URI相对地址
 	stats uri   /dbs_monitor
	#统计报告格式
 	stats realm     Global\ statistics
	#登陆帐户信息
 	stats auth  admin:admin
	#数据库负载均衡
	listen  proxy-mysql
	#访问的IP和端口，haproxy开发的端口为3306
 	#假如有人访问haproxy的3306端口，则将请求转发给下面的数据库实例
	bind  0.0.0.0:3306  
 	#网络协议
	mode  tcp
	#负载均衡算法（轮询算法）
	#轮询算法：roundrobin
	#权重算法：static-rr
	#最少连接算法：leastconn
	#请求源IP算法：source 
 	balance  roundrobin
	#日志格式
 	option  tcplog
	#在MySQL中创建一个没有权限的haproxy用户，密码为空。
	#Haproxy使用这个账户对MySQL数据库心跳检测
 	option  mysql-check user haproxy
	server  MySQL_1 172.19.0.2:3306 check weight 1 maxconn 2000  
 	server  MySQL_2 172.19.0.3:3306 check weight 1 maxconn 2000  
	server  MySQL_3 172.19.0.4:3306 check weight 1 maxconn 2000 
	#使用keepalive检测死链
 	option  tcpka
~~~

### 运行容器

~~~shell
docker run -it -d -p 8888:8888 -p 3304:3306 -v /tmp/haproxy:/usr/local/etc/haproxy --name haproxy_mysql --privileged --net=pxc-net haproxy
~~~

### 根据haproxy.cfg文件启动 haproxy

~~~shell
docker exec -it haproxy_mysql bash
haproxy -f /usr/local/etc/haproxy/haproxy.cfg
~~~

### 连接 haproxy_mysql

~~~
  ip:192.168.3.10
  port:3304
  user:root
  password:123456
~~~

### 监控网页

【http://192.168.3.10:8888/dbs_monitor】，账户密码都是 admin

### 负载均衡链接授权

  CREATE USER 'haproxy'@'%' IDENTIFIED BY '';
[小技巧[如果创建失败，可以先输入一下命令]:
    drop user 'haproxy'@'%';
    flush privileges;
    CREATE USER 'haproxy'@'%' IDENTIFIED BY '';
]

# 实战-Nginx + Spring Boot集群+  MySQL

## [创建一个单独的网段](https://docs.docker.com/engine/reference/commandline/network_create/)

~~~shell
docker network create --subnet=172.18.0.0/24 nginx-net
~~~

## MySQL容器

- 创建volume数据卷挂载

  ~~~shell
  docker volume create my-mysql-v
  ~~~

- 创建mysql容器，并指定到网段

  ~~~shell
  docker run -d --name my-mysql -v my-mysql-v:/var/lib/mysql -p 3307:3306 -e MYSQL_ROOT_PASSWORD=123456 --net=nginx-net --ip 172.18.0.5 mysql
  ~~~

- datagrip连接，执行.mysql文件

  >
  >
  >  name:my-mysql
  >  ip:centos-ip
  >  端口:3307
  >  user:root
  >  password:123456
  >
  >>
  >>
  >>~~~sql
  >>create table t_user
  >>(
  >>	id int not null primary key,
  >>	username varchar(50) not null,
  >>	password varchar(50) not null,
  >>	number varchar(100) not null
  >>);
  >>~~~

## 构建镜像

- 将 springboot-mybatis 打成 jar 包：**springboot-mybatis.jar**

  在同一个网络中，bridge  nginx-net   容器之间不仅可以通过ip访问，而且可以通过名称 my-mysql

  ~~~
  url: jdbc:mysql://my-mysql:3306/db_gupao_springboot?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
  ~~~

- 创建目录并进入

  ~~~shell
  mkdir springboot-mybatis-nginx
  cd springboot-mybatis-nginx
  ~~~

- 将springboot-mybatis.jar传到该目录下

- 创**Dockerfile**文件，内容如下

  ~~~
  FROM openjdk:8
  MAINTAINER tiger2019
  LABEL name="springboot-mybatis-nginx" version="1.0" author="tiger2019"
  COPY springboot-mybatis.jar springboot-mybatis-nginx.jar
  CMD ["java","-jar","springboot-mybatis-nginx.jar"]
  ~~~

- 基于Dockerfile构建images

  ~~~shell
  docker build -t springboot-mybatis-nginx-image .
  ~~~

- 基于images创建容器，运行项目，并指定网段内ip

  ~~~shell
  docker run -d --name sbm1 -p 8091:8080 --net=nginx-net --ip 172.18.0.11 springboot-mybatis-nginx-image
  ~~~

- 查看启动日志

  ~~~
  docker logs sbm1
  ~~~

- 在win中访问项目：http://192.168.3.13:8091/user/listall

- 创建多个项目容器

  ~~~shell
  docker run -d --name sbm2 -p 8092:8080 --net=nginx-net --ip 172.18.0.12 springboot-mybatis-nginx-image
  docker run -d --name sbm3 -p 8093:8080 --net=nginx-net --ip 172.18.0.13 springboot-mybatis-nginx-image
  ~~~

## Nginx 配置

1. 创建目录文件

   >~~~shell
   >mkdir -r /tmp/nginx
   >touch nginx.conf
   >~~~

   ~~~
   user nginx;
   worker_processes  1;
   events {
       worker_connections  1024;
   }
   http {
       include       /etc/nginx/mime.types;
       default_type  application/octet-stream;
       sendfile        on;
       keepalive_timeout  65; 
   
       server {
           listen 80;
           location / {
            proxy_pass http://balance;
           }
       }
       
       upstream balance{  
   	    #均衡配置
   	    #这里可以通过名字来指定
           #server sbm1:8080;
           #server sbm2:8080;
           #server sbm3:8080;
           server 172.18.0.11:8080;  
           server 172.18.0.12:8080;
           server 172.18.0.13:8080;
       }
       include /etc/nginx/conf.d/*.conf;
   }
   ~~~

2. 创建nginx容器

   ~~~
   docker run -d --name my-nginx -p 80:80 -v /tmp/nginx/nginx.conf:/etc/nginx/nginx.conf --network=nginx-net --ip 172.18.0.10 nginx
   ~~~

3. win浏览器访问，省略端口号

   ~~~
   ip[centos]/user/listall
   ~~~

# [Docker网络通讯](https://docs.docker.com/network/)

## 网卡

### 查看网卡信息

~~~shell
#查看网卡
ip link show
#网卡文件
ls /sys/class/net
#查看详情
ip a
~~~

docker中不同的容器之间的网络通讯是通过docker0网络进行连接，每创建一个容器，都会分别在docker0和宿主机中

## docker网络笔记集合

~~~

====== 基本命令 ======
1、查看网卡[网络接口]
  1)、查看网卡【ip link show】
  2)、【ls /sys/class/net】
  3)、【ip a】
  ip a解读
  状态：UP/DOWN/UNKOWN等
  link/ether：MAC地址
  inet：绑定的IP地址

查看docker网络：【docker network ls】
                【docker inspect bridge】
				
2、配置文件
  1)、在Linux中网卡对应的其实就是文件，所以找到对应的网卡文件即可，比如：【cat /etc/sysconfig/network-scripts/ifcfg-eth0】
  2)、给网卡添加IP地址：当然，这块可以直接修改ifcfg-*文件，但是我们通过命令添加试试
  添加【ip addr add 192.168.0.100/24 dev eth0】
  删除【ip addr delete 192.168.0.100/24 dev eth0】
  
3、Network Namespace，对网络进行隔离，和java中的package有点类似
  查看【ip netns list】
  添加【ip netns add ns1】
  删除【ip netns delete ns1】
  
4、 namespace实战
  1)、创建一个network namespace【ip netns add ns1】
  2)、查看该namespace下网卡的情况【ip netns exec ns1 ip a】
  3)、启动ns1上的lo网卡【ip netns exec ns1 ifup lo】，【ip netns exec ns1 ip link set lo up】
  4)、可以发现state变成了UNKOWN
  5)、再次创建一个network namespace【ip netns add ns2】
  6)、再次创建一个network namespace【ip netns add ns2】
  7)、此时想让两个namespace网络连通起来，veth pair ：Virtual Ethernet Pair，是一个成对的端口，可以实现上述功能
  8)、创建一对link，也就是接下来要通过veth pair连接的link【ip link add veth-ns1 type veth peer name veth-ns2】
  9)、查看link情况【ip link】
  10)、将veth-ns1加入ns1中，将veth-ns2加入ns2中
  【ip link set veth-ns1 netns ns1】
  【ip link set veth-ns1 netns ns2】
  11)、查看宿主机和ns1，ns2的link情况
  【ip link】
  【ip netns exec ns1 ip link】
  【ip netns exec ns2 ip link】
  12)、此时veth-ns1和veth-ns2还没有ip地址，显然通信还缺少点条件
  【ip netns exec ns1 ip addr add 192.168.0.11/24 dev veth-ns1】
  【ip netns exec ns2 ip addr add 192.168.0.12/24 dev veth-ns2】
  13)、再次查看，发现state是DOWN，并且还是没有IP地址
  【ip netns exec ns1 ip link】
  【ip netns exec ns2 ip link】
  14)、启动veth-ns1和veth-ns2
  【ip netns exec ns1 ip link set veth-ns1 up】
  【ip netns exec ns2 ip link set veth-ns2 up】
  15)、再次查看，发现state是UP，同时有IP
  【ip netns exec ns1 ip a】
  【ip netns exec ns2 ip a】
  16)、此时两个network namespace互相ping一下，发现是可以ping通的
  【ip netns exec ns1 ping 192.168.0.12】
  【ip netns exec ns2 ping 192.168.0.11】

====== Container的NS ======
1、按照上面的描述，实际上每个container，都会有自己的network namespace，并且是独立的，我们可以进入到容器中进行验证
  1)、不妨创建两个container看看？
  【docker run -d --name tomcat01 -p 8081:8080 tomcat】
  【docker run -d --name tomcat02 -p 8082:8080 tomcat】
  2)、进入到两个容器中，并且查看ip
  【docker exec -it tomcat01 ip a】，例如查到ip是：172.17.0.4
  【docker exec -it tomcat02 ip a】，例如查到ip是：172.17.0.5
  3)、互相ping一下是可以ping通的
  【docker exec -it tomcat01 ping 172.17.0.5】
  【docker exec -it tomcat02 ping 172.17.0.4】
  值得我们思考的是，此时tomcat01和tomcat02属于两个network namespace，是如何能够ping通的？有些小伙伴可能会想，不就跟上面的namespace实战一样吗？注意这里并没有veth-pair技术
  
2、深入分析container网络-Bridge
  1)、查看centos的网络:ip a，可以发现
  2)、在centos中ping一下tomcat01的网络，发现可以ping通【ping 172.17.0.4】
  3)、既然可以ping通，而且centos和tomcat1又属于不同的network namespace，是怎么连接的？很显然，跟之前的实战是一样的，画个图，图脑补
  4)、也就是说，在tomcat01中有一个eth0和centos的docker0中有一个veth3是成对的，类似于之前实战中的veth-ns1和veth-ns2，不妨再通过一个命令确认下：brctl
  5)、安装一下：【yum install bridge-utils】，【brctl show】
  6)、那为什么tomcat01和tomcat02能ping通呢？不多说，直接上图，图脑补，docker作为中间桥梁，有两对link
  7)、这种网络连接方法我们称之为Bridge，其实也可以通过命令查看docker中的网络模式：docker network ls bridge 也是docker中默认的网络模式
  8)、不妨检查一下bridge：【docker network inspect bridge】
  9)、在tomcat01容器中是可以访问互联网的，顺便把这张图画一下咯，NAT是通过iptables实现的
  10)、创建自己的network
  【docker network create tomcat-net】
  【docker network create --subnet=172.17.0.4/24 tomcat-net】
  11)、查看已有的network：【docker network ls】
  12)、查看tomcat-net详情信息：【docker network inspect tomcat-net】
  13)、)创建tomcat的容器，并且指定使用tomcat-net
  【docker run -d --name custom-net-tomcat --network tomcat-net tomcat】
  14)、查看custom-net-tomcat的网络信息【docker exec -it custom-net-tomcat ip a】，172.18.0.2/16，与之前不是在同一个网段中
  15)、此时在custom-net-tomcat容器中ping一下tomcat01的ip会如何？发现无法ping通
  【docker exec -it custom-net-tomcat ping 172.17.0.4】，PING 172.17.0.4 (172.17.0.4) 56(84) bytes of data.
  16)、此时如果tomcat01容器能够连接到tomcat-net上应该就可以咯
  【docker network connect tomcat-net tomcat01】
  17)、查看tomcat-net网络，可以发现tomcat01这个容器也在其中【docker network inspect tomcat-net】
  18)、此时进入到tomcat01或者custom-net-tomcat中，不仅可以通过ip地址ping通，而且可以通过名字ping到，这时候因为都连接到了用户自定义的tomcat-net bridge上【docker exec -it custom-net-tomcat ping 172.18.0.4】
  【docker exec -it tomcat01 bash】，【ping 172.18.0.4】
  19)、但是ping tomcat02是不通的【ping 172.17.0.5】
~~~

# 06 Docker Compose

> `官网`：<https://docs.docker.com/compose/>

## 6.1 业务背景

## 6.2 Docker传统方式实现

### 6.2.1 写Python代码&build image

> (1)创建文件夹

```shell
mkdir -p /tmp/composetest
cd /tmp/composetest
```

> (2)创建app.py文件，写业务内容

```python
import time

import redis
from flask import Flask

app = Flask(__name__)
cache = redis.Redis(host='redis', port=6379)

def get_hit_count():
    retries = 5
    while True:
        try:
            return cache.incr('hits')
        except redis.exceptions.ConnectionError as exc:
            if retries == 0:
                raise exc
            retries -= 1
            time.sleep(0.5)

@app.route('/')
def hello():
    count = get_hit_count()
    return 'Hello World! I have been seen {} times.\n'.format(count)
```

> (3)新建requirements.txt文件

```
flask
redis
```

> (4)编写Dockerfile

```dockerfile
FROM python:3.7-alpine
WORKDIR /code
ENV FLASK_APP app.py
ENV FLASK_RUN_HOST 0.0.0.0
RUN apk add --no-cache gcc musl-dev linux-headers
COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt
COPY . .
CMD ["flask", "run"]
```

> (5)根据Dockerfile生成image

```
docker build -t python-app-image .
```

> (6)查看images：docker images

```
python-app-image latest 7e1d81f366b7 3 minutes ago  213MB
```

### 6.2.2 获取Redis的image

```
docker pull redis:alpine
```

### 6.2.3 创建两个container

> (1)创建网络

```
docker network ls
docker network create --subnet=172.20.0.0/24 app-net 
```

> (1)创建python程序的container，并指定网段和端口

```
docker run -d --name web -p 5000:5000 --network app-net python-app-image

```

> (2)创建redis的container，并指定网段

```
docker run -d --name redis --network app-net redis:alpine

```

### 6.2.4 访问测试

ip[centos]:5000

## 6.3 简介和安装

### 6.3.1 简介

> `官网`：https://docs.docker.com/compose/
>
> ```
> Compose is a tool for defining and running multi-container Docker applications. With Compose, you use a YAML file to configure your application’s services. Then, with a single command, you create and start all the services from your configuration.
> 
> ```

### 6.3.2 安装

> Linux环境中需要单独安装
>
> `官网`：https://docs.docker.com/compose/install/

```
sudo curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

```

```
sudo chmod +x /usr/local/bin/docker-compose

```

## 6.4 docker compose实现

> `reference`：<https://docs.docker.com/compose/gettingstarted/>

### 6.4.1 同样的前期准备

新建目录，比如composetest

进入目录，编写app.py代码

创建requirements.txt文件

编写Dockerfile

### 6.4.2 编写docker-compose.yaml文件

默认名称，当然也可以指定，`docker-compose.yaml`

```yaml
version: '3'
services:
  web:
    build: .
    ports:
      - "5000:5000"
    networks:
      - app-net

  redis:
    image: "redis:alpine"
    networks:
      - app-net

networks:
  app-net:
    driver: bridge

```

> (1)通过docker compose创建容器

```
docker-compose up -d
```

> (2)访问测试

![](D:/work/完成dockerV1.0/images/32.png)

## 6.5 详解docker-compose.yml文件

> (1)version: '3'

```
表示docker-compose的版本
```

> (2)services

```
一个service表示一个container
```

> (3)networks

```
相当于docker network create app-net
```

> (4)volumes

```
相当于-v v1:/var/lib/mysql

```

> (5)image

```
表示使用哪个镜像，本地build则用build，远端则用image

```

> (6)ports

```
相当于-p 8080:8080

```

> (7)environment

```
相当于-e 

```

## 6.6 docker-compose常见操作

(1)查看版本

​	docker-compose version

(2)根据yml创建service

​	docker-compose up

​	指定yaml：docker-compose  up -f xxx.yaml

​	后台运行：docker-compose up

(3)查看启动成功的service

​	docker-compose ps

​	也可以使用docker ps

(4)查看images

​	docker-compose images

(5)停止/启动service

​	docker-compose stop/start 

(6)删除service[同时会删除掉network和volume]

​	docker-compose down

(7)进入到某个service

​	docker-compose exec redis sh

## 6.7 scale扩缩容

> (1)修改docker-compose.yaml文件，主要是把web的ports去掉，不然会报错

```yaml
version: '3'
services:
  web:
    build: .
    networks:
      - app-net

  redis:
    image: "redis:alpine"
    networks:
      - app-net

networks:
  app-net:
    driver: bridge

```

> (2)创建service

```
docker-compose up -d

```

> (3)若要对python容器进行扩缩容

```
docker-compose up --scale web=5 -d
docker-compose ps
docker-compose logs web

```

# 07 [Docker Swarm](http://labs.play-with-docker.com)

> `官网`：<https://docs.docker.com/swarm/>

## 7.1 Install Swarm

### 7.1.1 环境准备

> (1)根据Vagrantfile创建3台centos机器
>
> [**大家可以根据自己实际的情况准备3台centos机器，不一定要使用vagrant+virtualbox**]
>
> 新建swarm-docker-centos7文件夹，创建Vagrantfile

```ruby
boxes = [
    {
        :name => "manager-node",
        :eth1 => "192.168.0.11",
        :mem => "1024",
        :cpu => "1"
    },
    {
        :name => "worker01-node",
        :eth1 => "192.168.0.12",
        :mem => "1024",
        :cpu => "1"
    },
    {
        :name => "worker02-node",
        :eth1 => "192.168.0.13",
        :mem => "1024",
        :cpu => "1"
    }
]

Vagrant.configure(2) do |config|

  config.vm.box = "centos/7"
  
   boxes.each do |opts|
      config.vm.define opts[:name] do |config|
        config.vm.hostname = opts[:name]
        config.vm.provider "vmware_fusion" do |v|
          v.vmx["memsize"] = opts[:mem]
          v.vmx["numvcpus"] = opts[:cpu]
        end

        config.vm.provider "virtualbox" do |v|
          v.customize ["modifyvm", :id, "--memory", opts[:mem]]
		  v.customize ["modifyvm", :id, "--cpus", opts[:cpu]]
		  v.customize ["modifyvm", :id, "--name", opts[:name]]
        end

        config.vm.network :public_network, ip: opts[:eth1]
      end
  end

end

```

> (2)进入到对应的centos里面，使得root账户能够登陆，从而使用XShell登陆

```
vagrant ssh manager-node/worker01-node/worker02-node
sudo -i
vi /etc/ssh/sshd_config
修改PasswordAuthentication yes
passwd    修改密码
systemctl restart sshd

```

> (3)在win上ping一下各个主机，看是否能ping通

```
ping 192.168.0.11/12/13

```

> (4)在每台机器上安装docker engine
>
> `小技巧`：要想让每个shell窗口一起执行同样的命令"查看-->撰写-->撰写窗口-->全部会话"

### 7.1.2 搭建Swarm集群

> (1)进入manager
>
> `提示`：manager node也可以作为worker node提供服务

```
docker swarm init --advertise-addr=192.168.0.11

```

`注意观察日志，拿到worker node加入manager node的信息`

```
docker swarm join --token SWMTKN-1-0a5ph4nehwdm9wzcmlbj2ckqqso38pkd238rprzwcoawabxtdq-arcpra6yzltedpafk3qyvv0y3 192.168.0.11:2377
```

> (2)进入两个worker

```
docker swarm join --token SWMTKN-1-0a5ph4nehwdm9wzcmlbj2ckqqso38pkd238rprzwcoawabxtdq-arcpra6yzltedpafk3qyvv0y3 192.168.0.11:2377
```

`日志打印`

```
This node joined a swarm as a worker.
```

> (3)进入到manager node查看集群状态

```
docker node ls
```

> (4)node类型的转换
>
> 可以将worker提升成manager，从而保证manager的高可用

```
docker node promote worker01-node
docker node promote worker02-node

#降级可以用demote
docker node demote worker01-node

```

### 7.1.3 在线的

> http://labs.play-with-docker.com

## 7.2 Swarm基本操作

### 7.2.1 Service

> (1)创建一个tomcat的service

```
docker service create --name my-tomcat tomcat

```

> (2)查看当前swarm的service

```
docker service ls

```

> (3)查看service的启动日志

```
docker service logs my-tomcat

```

> (4)查看service的详情

```
docker service inspect my-tomcat

```

> (5)查看my-tomcat运行在哪个node上

```
docker service ps my-tomcat

```

`日志`

```
ID                  NAME                IMAGE               NODE                DESIRED STATE       CURRENT STATE           ERROR               PORTS
u6o4mz4tj396        my-tomcat.1         tomcat:latest       worker01-node       Running             Running 3 minutes ago  

```

> (6)水平扩展service

```
docker service scale my-tomcat=3
docker service ls
docker service ps my-tomcat

```

`日志`：可以发现，其他node上都运行了一个my-tomcat的service

```
[root@manager-node ~]# docker service ps my-tomcat
ID                  NAME                IMAGE               NODE                DESIRED STATE       CURRENT STATE            ERROR               PORTS
u6o4mz4tj396        my-tomcat.1         tomcat:latest       worker01-node       Running             Running 8 minutes ago                        
v505wdu3fxqo        my-tomcat.2         tomcat:latest       manager-node        Running             Running 46 seconds ago                       
wpbsilp62sc0        my-tomcat.3         tomcat:latest       worker02-node       Running             Running 49 seconds ago  

```

此时到worker01-node上：docker ps，可以发现container的name和service名称不一样，这点要知道

```
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
bc4b9bb097b8        tomcat:latest       "catalina.sh run"   10 minutes ago      Up 10 minutes       8080/tcp            my-tomcat.1.u6o4mz4tj3969a1p3mquagxok

```

> (7)如果某个node上的my-tomcat挂掉了，这时候会自动扩展

```
[worker01-node]
docker rm -f containerid

[manager-node]
docker service ls
docker service ps my-tomcat

```

> (8)删除service

```
docker service rm my-tomcat

```

### 7.2.2 多机通信overlay网络[3.7的延续]

> `业务场景`：workpress+mysql实现个人博客搭建
>
> > <https://hub.docker.com/_/wordpress?tab=description>

#### 7.2.2.1 传统手动方式实现

##### 7.2.2.1.1 一台centos上，分别创建容器

```
01-创建mysql容器[创建完成等待一会，注意mysql的版本]
	docker run -d --name mysql -v v1:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=examplepass -e MYSQL_DATABASE=db_wordpress mysql:5.6
	
02-创建wordpress容器[将wordpress的80端口映射到centos的8080端口]
	docker run -d --name wordpress --link mysql -e WORDPRESS_DB_HOST=mysql:3306 -e WORDPRESS_DB_USER=root -e WORDPRESS_DB_PASSWORD=examplepass -e WORDPRESS_DB_NAME=db_wordpress -p 8080:80 wordpress
	
03-查看默认bridge的网络，可以发现两个容器都在其中
	docker network inspect bridge
	
04-访问测试
	win浏览器中输入：ip[centos]:8080，一直下一步

```

##### 7.2.2.1.2 使用docker compose创建

> docker-compose的方式还是在一台机器中，网络这块很清晰

```
01-创建wordpress-mysql文件夹
	mkdir -p /tmp/wordpress-mysql
	cd /tmp/wordpress-mysql
	
02-创建docker-compose.yml文件

```

`文件内容`

```yml
version: '3.1'

services:

  wordpress:
    image: wordpress
    restart: always
    ports:
      - 8080:80
    environment:
      WORDPRESS_DB_HOST: db
      WORDPRESS_DB_USER: exampleuser
      WORDPRESS_DB_PASSWORD: examplepass
      WORDPRESS_DB_NAME: exampledb
    volumes:
      - wordpress:/var/www/html

  db:
    image: mysql:5.7
    restart: always
    environment:
      MYSQL_DATABASE: exampledb
      MYSQL_USER: exampleuser
      MYSQL_PASSWORD: examplepass
      MYSQL_RANDOM_ROOT_PASSWORD: '1'
    volumes:
      - db:/var/lib/mysql

volumes:
  wordpress:
  db:

```

```
03-根据docker-compose.yml文件创建service
	docker-compose up -d
	
04-访问测试
	win10浏览器ip[centos]:8080，一直下一步
	
05-值得关注的点是网络
	docker network ls
	docker network inspect wordpress-mysql_default
```

#### 7.2.2.2 Swarm中实现

> 还是wordpress+mysql的案例，在docker swarm集群中怎么玩呢？

> (1)创建一个overlay网络，用于docker swarm中多机通信

```
【manager-node】
docker network create -d overlay my-overlay-net

docker network ls[此时worker node查看不到]
```

> (2)创建mysql的service

```
【manager-node】
01-创建service
docker service create --name mysql --mount type=volume,source=v1,destination=/var/lib/mysql --env MYSQL_ROOT_PASSWORD=examplepass --env MYSQL_DATABASE=db_wordpress --network my-overlay-net mysql:5.6

02-查看service
	docker service ls
	docker service ps mysql
```

> (3)创建wordpress的service

```
01-创建service  [注意之所以下面可以通过mysql名字访问，也是因为有DNS解析]
docker service create --name wordpress --env WORDPRESS_DB_USER=root --env WORDPRESS_DB_PASSWORD=examplepass --env WORDPRESS_DB_HOST=mysql:3306 --env WORDPRESS_DB_NAME=db_wordpress -p 8080:80 --network my-overlay-net wordpress

02-查看service
	docker service ls
	docker service ps mysql
	
03-此时mysql和wordpress的service运行在哪个node上，这时候就能看到my-overlay-net的网络

```

> (4)测试

```
win浏览器访问ip[manager/worker01/worker02]:8080都能访问成功

```

> (5)查看my-overlay-net

```
docker network inspect my-overlay-net

```

> (6)为什么没有用etcd？docker swarm中有自己的分布式存储机制

## 7.3 Routing Mesh

### 7.3.1 Ingress

> 通过前面的案例我们发现，部署一个wordpress的service，映射到主机的8080端口，这时候通过swarm集群中的任意主机ip:8080都能成功访问，这是因为什么？
>
> `把问题简化`：docker service create --name tomcat  -p 8080:8080 --network my-overlay-net tomcat

> (1)记得使用一个自定义的overlay类型的网络

```
--network my-overlay-net
```

> (2)查看service情况

```
docker service ls
docker service ps tomcat
```

> (3)访问3台机器的ip:8080测试

```
发现都能够访问到tomcat的欢迎页

```

### 7.4.2 Internal

> 之前在实战wordpress+mysql的时候，发现wordpress中可以直接通过mysql名称访问
>
> 这样可以说明两点，第一是其中一定有dns解析，第二是两个service的ip是能够ping通的
>
> `思考`：不妨再创建一个service，也同样使用上述tomcat的overlay网络，然后来实验
>
> docker service create --name whoami -p 8000:8000 --network my-overlay-net -d  jwilder/whoami

> (1)查看whoami的情况

```
docker service ps whoami

```

> (2)在各自容器中互相ping一下彼此，也就是容器间的通信

```
#tomcat容器中ping whoami
docker exec -it 9d7d4c2b1b80 ping whoami
64 bytes from bogon (10.0.0.8): icmp_seq=1 ttl=64 time=0.050 ms
64 bytes from bogon (10.0.0.8): icmp_seq=2 ttl=64 time=0.080 ms


#whoami容器中ping tomcat
docker exec -it 5c4fe39e7f60 ping tomcat
64 bytes from bogon (10.0.0.18): icmp_seq=1 ttl=64 time=0.050 ms
64 bytes from bogon (10.0.0.18): icmp_seq=2 ttl=64 time=0.080 ms
```

> (3)将whoami进行扩容

```
docker service scale whoami=3
docker service ps whoami     #manager,worker01,worker02
```

> (4)此时再ping whoami service，并且访问whoami服务

```
#ping
docker exec -it 9d7d4c2b1b80 ping whoami
64 bytes from bogon (10.0.0.8): icmp_seq=1 ttl=64 time=0.055 ms
64 bytes from bogon (10.0.0.8): icmp_seq=2 ttl=64 time=0.084 ms

#访问
docker exec -it 9d7d4c2b1b80 curl whoami:8000  [多访问几次]
I'm 09f4158c81ae
I'm aebc574dc990
I'm 7755bc7da921
```

`小结`：通过上述的实验可以发现什么？whoami服务对其他服务暴露的ip是不变的，但是通过whoami名称访问8000端口，确实访问到的是不同的service，就说明访问其实是像下面这张图。

也就是说whoami service对其他服务提供了一个统一的VIP入口，别的服务访问时会做负载均衡。

## 7.5 Stack

> docker stack deploy：https://docs.docker.com/engine/reference/commandline/stack_deploy/
>
> compose-file：https://docs.docker.com/compose/compose-file/
>
> 有没有发现上述部署service很麻烦？要是能够类似于docker-compose.yml文件那种方式一起管理该多少？这就要涉及到docker swarm中的Stack，我们直接通过前面的wordpress+mysql案例看看怎么使用咯。

> (1)新建service.yml文件

```yml
version: '3'

services:

  wordpress:
    image: wordpress
    ports:
      - 8080:80
    environment:
      WORDPRESS_DB_HOST: db
      WORDPRESS_DB_USER: exampleuser
      WORDPRESS_DB_PASSWORD: examplepass
      WORDPRESS_DB_NAME: exampledb
    networks:
      - ol-net
    volumes:
      - wordpress:/var/www/html
    deploy:
      mode: replicated
      replicas: 3
      restart_policy:
        condition: on-failure
        delay: 5s
        max_attempts: 3
      update_config:
        parallelism: 1
        delay: 10s

  db:
    image: mysql:5.7
    environment:
      MYSQL_DATABASE: exampledb
      MYSQL_USER: exampleuser
      MYSQL_PASSWORD: examplepass
      MYSQL_RANDOM_ROOT_PASSWORD: '1'
    volumes:
      - db:/var/lib/mysql
    networks:
      - ol-net
    deploy:
      mode: global
      placement:
        constraints:
          - node.role == manager

volumes:
  wordpress:
  db:

networks:
  ol-net:
    driver: overlay

```

> (2)根据service.yml创建service

```
docker statck deploy -c service.yml my-service

```

> (3)常见操作

```
01-查看stack具体信息
	docker stack ls
	NAME                SERVICES            ORCHESTRATOR
	my-service          2                   Swarm
	
02-查看具体的service
	docker stack services my-service
	
ID                  NAME                   MODE                REPLICAS            IMAGE               PORTS
icraimlesu61        my-service_db          global              1/1                 mysql:5.7           
iud2g140za5c        my-service_wordpress   replicated          3/3                 wordpress:latest    *:8080->80/tcp

03-查看某个service
	docker service inspect my-service-db
	
"Endpoint": {
            "Spec": {
                "Mode": "vip"
            },
            "VirtualIPs": [
                {
                    "NetworkID": "kz1reu3yxxpwp1lvnrraw0uq6",
                    "Addr": "10.0.1.5/24"
                }
            ]
        }


```

> (4)访问测试

win浏览器ip[manager,worker01,worker02]:8080



# docker pull下载镜像报错解决

~~~

Get https://registry-1.docker.io/v2/library/apline/manifests/latest: net/http: TLS handshake timeout

1、安装dig工具：【yum install bind-utils】

2、查找可用映射：【dig @114.114.114.114 registry-1.docker.io】
;; ANSWER SECTION:
registry-1.docker.io.	48	IN	A	52.87.94.70
registry-1.docker.io.	48	IN	A	54.165.149.19


3、添加映射：【vim /etc/hosts】
54.165.149.19 registry-1.docker.io
~~~



# temp



[^Dockerfile]: 根据当前目录下的Dockerfile文件生成一个 image,而images实例化出container
[^volume name]: volume name 在docker中唯一