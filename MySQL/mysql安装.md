# docker安装

首先准备docker环境，接着执行下面命令

参考网址【https://blog.csdn.net/zhaluo_dehezi/article/details/85162886】

~~~shell
docker run -itd --name mysql01 -p 3301:3306 -v /my/own/mysql01data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=a9k62c mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
~~~

~~~shell
docker run -it --name mysql02 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root --privileged=true -v /opt/docker/mysql/conf/my.cnf:/etc/mysql/my.cnf -v /opt/docker/mysql/data:/var/lib/mysql -v /opt/docker/mysql/logs:/var/log/mysql -d --restart=always mysql:5.7 
~~~



# linux安装

自行百度

# window安装mysql

傻瓜式安装包