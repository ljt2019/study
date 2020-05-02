
1、nfs(network file system)网络文件系统，是FreeBSD支持的文件系统中的一种，允许网络中的计算机之间通过TCP/IP网络共享资源

2、选择 master 节点作为 nfs 的server，所以在master节点上

3、安装nfs服务端

  1)、【yum install -y nfs-utils】，所有节点执行
  
  2)、创建nfs目录【mkdir -p /nfs/data/】、【mkdir -p /nfs/data/mysql】
  
  3)、授予权限【chmod -R 777 /nfs/data】
  
  4)、编辑export文件【vi /etc/exports】
      /nfs/data *(rw,no_root_squash,sync)
	  
  5)、使得配置生效【exportfs -r】
  
  6)、查看生效【exportfs】
  
  7)、启动rpcbind、nfs服务【systemctl restart rpcbind && systemctl enable rpcbind】、【systemctl restart nfs && systemctl enable nfs】
  
  8)、查看rpc服务的注册情况【rpcinfo -p localhost】
  
  9)、showmount【showmount -e master-ip】，例如【showmount -e 192.168.3.51】
	
4、所有 node 上安装客户端
  【systemctl start nfs && systemctl enable nfs】