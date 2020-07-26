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
   【docker run -d --name 容器名称 -p 容器原本端口:映射端口 镜像名称】
   【docker run -d --name nginx -p 8080:88 nginx】
   ```

   - -d：作为守护进程来执行
   - --name：指定要创建容器的名称
   - -p：将nginx本身80的端口映射到本地8080

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

# 创建 mysql高可用集群***pxc***

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

   ~~~
   docker network create --subnet=172.19.0.0/24 pxc-net
   ~~~

4. 创建3个数据挂载点**volume**

   ~~~
   docker volume create --name mysql_v1
   docker volume create --name mysql_v2
   docker volume create --name mysql_v3
   ~~~

5. 运行三个PXC容器

   ~~~
   docker run -d --name=mysql_node1 -p 3301:3306 -v mysql_v1:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=123456 --privileged  --net=pxc-net --ip 172.19.0.2 pxc
   ~~~

   注意：**下边两个节点(mysql_node2和mysql_node3)加入到mysql_node1节点中**

   ~~~
   docker run -d --name=mysql_node2 -p 3302:3306 -v v2:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -e CLUSTER_JOIN=mysql_node1 -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=123456 --privileged  --net=pxc-net --ip 172.19.0.3 pxc
   ~~~

   ~~~
   docker run -d --name=mysql_node3 -p 3303:3306 -v v3:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -e CLUSTER_JOIN=mysql_node1 -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=123456 --privileged  --net=pxc-net --ip 172.19.0.4 pxc
   ~~~

## 负载均衡[**haproxy**](https://hub.docker.com/_/haproxy)

1. 创建 haproxy 配置文件

# temp



[^Dockerfile]: 根据当前目录下的Dockerfile文件生成一个 image,而images实例化出container
[^volume name]: volume name 在docker中唯一