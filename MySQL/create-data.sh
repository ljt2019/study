#!/bin/bash

i=1;
MAX_INSERT_ROW_COUNT=$1;
while [ $i -le $MAX_INSERT_ROW_COUNT ]
do
    mysql -uroot -proot afs -e "insert into afs_test (name,age,createTime) values ('HELLO$i',$i % 99,NOW());"
    d=$(date +%M-%d\ %H\:%m\:%S)
    echo "INSERT HELLO $i @@ $d"    
    i=$(($i+1))
    sleep 0.05
done

exit 0