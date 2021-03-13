# docker安装 nacos

安装好了后，访问【http://ip:8848/nacos】，账号密码都是nacos

~~~shell
docker run -d --name nacos -p 8848:8848 --privileged=true -e MODE=standalone -v /opt/nacos/init.d/custom.properties:/home/nacos/init.d/custom.properties -v /opt/nacos/logs:/home/nacos/logs --restart always  nacos/nacos-server
~~~



