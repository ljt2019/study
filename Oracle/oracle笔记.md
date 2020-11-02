# Oracle数据库

默认端口号【1521】，默认oracle实例(SID)【orcl】,有些是【xe】

 # 概念

相关的操作系统文件（即存储在计算机硬盘上的文件）集合，这些文件组织在一起，成为一个逻辑整体，即Oracle数据库，必须与内存中的实例结合才能对外提供数据管理服务

# 三个必须文件

- Data files：数据的存储仓库
- Controller files：记录数据库的名字、存储位置等基本信息，很小的二进制文件，自动生成
- Redo Log files：重做日志文件，例如数据恢复

# 三个非必需文件

- Parameter file：参数文件
- Password file：口令文件
- Archived Log files

# Oracle实例

位于物理内存里的数据结构，它由操作系统的多个后台进程和一个共享的内存池组成，共享的内存池可以被所有进程访问

# Oracle的7个服务

## 必须、建议启动项

1. OracleServiceORCL：数据库基础服务
2. OracleOraDb11g_home1TNSListener：数据库监听服务，远程访问，使用sqldeveloper，需要打开此服务（建议为启动）

## 非必须启动项

1. OracleJobSchedulerORCL：Oracle调用定时器服务
2. OracleMTSRecoveryService：服务端控制服务
3. OracleOraDb11g_home1ClrAgent：oracle数据 .net扩展的一部分
4. OracleDBConsoleorcl:基于web的控制台服务，在web端管理oracle时需要启动
5. OracleVssWriterORCL:拷贝写入服务

# SQL Plus 命令

1. 切换用户

   ~~~sql
   connect username/password
   ~~~

2. 执行系统doc命令

   ~~~sql
   【host <doc命令>】，例如：host mkdir c:\AAAAA\oracleTest
   ~~~

3. 导出记录到本地

   ~~~sql
   例如:1、【spool c:\AAAAA\oracleTest】2、【select * from book】3、【spool off】
   ~~~

4. 清屏

   ~~~sql
   clear screen
   ~~~

5. 执行SQL脚本语句

   ~~~sql
   sart c:\AAAAA\oracleTest\test.sql
   --或
   @c:\AAAAA\oracleTest\test.sql
   ~~~

6. 显示表结构

   ~~~sql
   desc
   ~~~

7. 显示错误信息

   ~~~sql
   show err
   ~~~

8. 退出

   ~~~sql
   exit
   ~~~

# 表空间

## 什么是表空间

表空间实际上是数据库上的逻辑存储结构，可以把表空间理解为在数据库中开辟一个空间，用于存放我们的数据库对象，一个数据库可以由一个或多个表空间构成；一个表空间由一个或多个数据文件构成。
	

## 分类

1. 永久表空间：系统的表空间，存储数据字典：统计信息、表信息、索引信息、用户信息等
2. 临时表空间：必须存在的一个表空间，数据中专站，当commit后会被清空，数据存储到了永久表空间中
3. UNDO表空间：commit后想回退，则数据就是存储在这个空间里边

### 创建表空间

***在sys或system用户下操作***

1. 永久

   ~~~sql
   create tablespace 表空间名 datafile '=xx.dbf' size 10m;
   ~~~

2. 临时

   ~~~sql
   create temporary tablespace 表空间名 tempfile 'xx.dbf' size 10m;
   ~~~

### 查看表空间存放位置 大写

1. 永久

   ~~~
   select file_name from dba_data_files where tablespace_name = 'TEST1_TABLESPACE';
   ~~~

2. 临时

   ~~~
   select file_name from dba_temp_files where tablespace_name = 'TEMPTEST_TABLESPACE';
   ~~~

3. 查看用户表空间

   ~~~sql
   --用户表空间
   select tablespace_name from user_tablespaces;
   --系统表空间
   select tablespace_name from dba_tablespaces;
   ~~~

4. 更改用户默认表空和临时表空间

   ~~~sql
   alter user 用户名 default tablespace 表空间名 temporary tablespace 临时表空间名;
   ~~~

### 设置表空间联机

- 联机(表示可读写状态)

  ~~~sql
  alter tablespace 表空间名 online;
  ~~~

- 脱机

  ~~~sql
  alter tablespace 表空间名 offline;
  ~~~


	### 查看表空间的状态

~~~sql
select status from dba_tablespaces where tablespace_name = '表空间名';
~~~

## 设置读写状态

默认可读可写

~~~sql
alter tablespace 表空间名 read only/read write;
~~~

## 增加数据文件

~~~sql
alter tablespace 表空间名 add datafile '数据文件名.dbf' size 10m;
~~~

## 删除数据文件

不能删除第一个创建的数据文件，如需删除，则要把表空间删除

~~~sql
alter tablespace 表空间名 drop datafile '数据文件名.dbf';
~~~

## 删除表空间

如何想同时把表空间中的数据文件也连同删除，则需加后边的可选项

~~~sql
drop tablespace 表空间名 [including contents];
~~~

#  用户管理 

1. sys：超级用户

   可以完成数据库得所有管理任务，以sysdb或asysoper的权限登陆

   ~~~sql
   conn sys/Ly123456 @orcl as sysdba;
   ~~~

2. system

   用来创建一些用于查看管理信息得表或试图，以普通用户(normal)的权限登陆

   ~~~sql
   conn scott/123456 @orcl as normal;
   ~~~

3. sysman

4. scott：示例用户，用来初学者学习

5. 查看所有用户

   针对管理员的数据字典

   ~~~sql
   select username from dba_users
   ~~~

6. 查看当前账户下的所有表信息

   普通用户的数据字典，查看当前连接用户的用户信息

   ~~~sql
   select table_name from user_tables;
   ~~~

7. 解锁用户

   ~~~sql
   alter user 用户名 identified by 用户密码 account unlock;
   ~~~

8. 锁定用户

   ~~~sql
   alter user 用户名 account lock;
   ~~~

9. 更改用户密码

   ~~~sql
   alter user 用户名 identified by 新密码;
   ~~~

10. 创建用户

    指定表空间和临时表空间，不然会默认为系统的表空间[USERS/TEMP])：一般创建之后赋予权限

    ~~~sql
    create user 用户名 identified by 密码 default tablespace 表空间名 temporary tablespace 临时表空间名;
    --授权
    grant connect resource to 用户名;
    --例如
    create user linjt identified by 123456 default tablespace test_tablespace temporary tablespace temptest_tablespace;
    ~~~

11. 删除用户

    加上cascade表示级联将用户连接以及其所创建的东西全部删除

    ~~~sql
    drop user 用户名 cascade;
    ~~~

12. 连接切换用户

    ~~~sql
    connect username/password;
    ~~~

# 角色管理

## 角色定义

即一组权限的集合；用户可以给角色赋予指定的权限，然后将角色赋给相应的用户


## 	系统的三种角色

1. CONNECT(连接角色)：只可以登陆Oracle，不可以创建实体和数据库结构
2. RESOURCE(资源角色)：可以创建实体（表、视图...），但不可以创建数据库结构
3. DBA(数据管理员角色)：系统最高权限，只有DBA才可以创建数据库结构



## 角色管理

1. 创建角色

   ~~~sql
   create role 角色名;
   ~~~

2. 删除角色

   ~~~sql
   drop role 角色名;
   ~~~

3. 赋予角色

   对于普通用户授予connect和resource权限，DBA授予dba权限

   ~~~sql
   grant 角色 to 用户名1,用户2
   --例如
   grant connect to linjt;
   ~~~

4. 回收角色

   ~~~sql
   revoke 角色 from 用户名1,用户2
   --例如
   revoke connect from linjt;
   ~~~

# 权限管理 

## 什么是权限

指执行特定命令或访问数据库对象的权利

## 权限的作用

数据库安全性(系统安全、数据安全)

## 权限的分类

1. 系统权限：允许用户执行特定的数据库动作，如创建表、创建索引、连接实例等；

   > 查看系统权限
   >
   > ~~~sql
   > select * from system_privilege_map;
   > ~~~
   >
   > 授予系统权限
   >
   > ~~~sql
   > grant privilege [,privilege...] to user [,user|role,public...];
   > --例如
   > grant create table,create view to manager;
   > ~~~
   >
   > 回收权限
   >
   > ~~~sql
   > revoke {privilege|role} from {user|role|public}
   > --例如
   > revoke create table,create sequence from manager;
   > ~~~

2. 对象(实体)权限：允许用户操作一些特定的对象，如读取视图、表，可更新某些列、执行存储过程等(select、update、insert、delete、all)

   > 查看所有对象权限
   >
   > ~~~sql
   > select * from table_privilege_map;
   > ~~~
   >
   > 授予对象权限
   >
   > ~~~sql
   > grant object_priv | all [(columns)] on object to {user|role|public}
   > --例如
   > grant select,update,insert on scott.emp to manager2
   > --
   > grant manager2 to user01
   > ~~~
   >
   > 回收
   >
   > ~~~sql
   > revoke {privilege[,privilege...]|all} on object from {user[,user...]|role|public}
   > --例如
   > revoke all on scott.emp from user01
   > ~~~


====== 数据定义语言(DDL) ======

1、create table：创建数据库表【create table tiger_test (id number,name varchar2(50))】

2、create index：创建数据库索引

3、drop index：删除数据库索引  

4、drop table：删除数据库表结构【drop table student;】

5、truncate：删除表中的所有数据，比delete删除要快【truncate table student;】

6、alter table：更改表的结构，增加、修改、删除列

  1)、修改列：【alter table student modify id varchar(32);】

  2)、删除列：【alter table student drop column name;】

  3)、修改列名：【alter table student rename cloumn 旧列名 to 新列名】

7、添加表级约束：【alter table student add [constraint student_pk] primary key(id);】
   添加列级约束：【alter table student modify clounm_name datatype not null;】

8、删除约束：

  1)、禁用或激活约束：【alter table table_name disable|enable constraint constraint_nam】

  2)、彻底删除约束【alter table table_name drop constraint constraint_nam】

  3)、彻底删除主键约束：【alter table table_name drop priamry key】

  4)、彻底删除非空约束：【alter table table_name modify clounm_name [datatype] null;】


====== 数据操纵语言(DML) ======



====== 数据控制语言(DCL) ======

1、grant：将权限或角色授予用户或其他角色（授予访问权限），一般创建之后赋予权限：【grant connect resource to 用户名】

2、revoke：从用户或数据库角色回收权限（撤销访问权限）

3、lock：对数据库特定部分进行锁定

4、unlock：解锁数据库，使用管理者system身份登录：【alter user hr identified by hr(默认密码为hr) account unlock;】
	
	
====== 事务控制语言(TCL) ======

1、什么是事物：由数据库的若干操作组成的一个单元，这些操作要么都完成，要么全都取消，从而保证数据满足一致性要求。

2、为什么要使用事物：保证数据安全有效，当执行事物操作(DML语句)时，oracle会在被作用的表上加表锁，以防止其他用户改变表结构，同时会在被作用的行上加行锁，以防止其他事物在相应的行行执行DML操作

1、commit：提交事务处理

2、rollback：回滚

3、savepoint：设置保存点【savepoint a】【rollback to a】


====== 约束 ======

1、约束的概念：自动保持数据库完整性的一种方法，通过限制字段中的数据、记录中的数据和表之间的数据来保证数据的完整性

2、语法格式：【[constraint constraint_name(约束段名)] <约束类型>】，约束不指定名，系统会给定一个名称

3、常见的约束：

  1)、主键约束(primary key)：它是唯一确定表中每条记录的标识，不能为空不能重复，只能有一个，可以由2多列组成，例如：【primary key(id,code)】

  2)、唯一约束：unique | constraint unique

    (1)、用于指定一个或多个列组合值具有唯一性，以防止在列中输入重复值。例如身份证号的唯一性
    
    (2)、唯一约束的列允许为空
    
    (3)、一个表汇总允许有多个唯一约束
    
    (4)、可以把唯一约束定义在多个列上

  3)、默认约束：【default constraint】

  4)、非空约束(not null)：确保列不能为null,属于列级约束

  5)、检查约束：对输入列或整个表中的值设置检查条件，以限制输入值，保证数据的完整性【check constraint】
      例如：【alter table student add constraint student_sex_ck check(sex='男' or sex='女');】

  6)、外键键约束：用于建立和加强两个表数据之间的链接的一列或多列

    (1)、列级约束(从表中定义)：【depID varchar2(20) references department(depID)】
    
    (2)、表级约束：【alter table student constraint student_dep_fk foreign key(depID) references department(depID) on delete cascade】
    
    (3)、主表的字段必须是主键列或唯一列
    
    (4)、从表中外键字段的值必须来自主表相应字段的值，或者为null
    
    (5)、主从表相应字段必须是同一数据类型


4、表级约束与列级约束定义上的区别

  1)、表级约束：可以单独定义

  2)、列级约束：【column [contraint constraint_nam] constraint_type】必须跟在列后边定义


====== Oracle中常见的数据字段 ======

  1、查看当前下的用户信息：【user_users】

  2、查看当前有权限访问的用户信息：【all_users】

  3、查看数据库所有用户信息：【dba_users】



====== Oracle中常见的数据类型 ====== oracle中一个汉字占3个字节

1、字符类型：

	1)、char：表示固定长度类型，列长度在1到2000个字节之间
	
	2)、varchar2：表示可变长度字符串，最大长度为4000字节

2、数值类型：number[(p[,s])]
	
	1)、p表示精度，s表示小数点位数，可以存储整数、浮点数等数值类型，最高精度为38位
	
	2)、例如：number(5,0):表示最多存储5位整数
		例如：number(5,2):表示可存储999.99的浮点数

3、日期类型：主要存储日期和时间值 ，包括年月日时分秒，主要的日期类型是 date

  1)、按特定格式显示日期：【alter session set nls_date_format='YYYY-MM-DD';】

  2)、简体中文显示日期：【alter session set nls_language='SIMPLIFIED CHINESE';】

  3)、美国英文显示日期：【alter session set nls_language='AMERICAN';】

4、LOB类型：

	1)、clob：字符lob，可以存储大量的字符数据
	
	2)、blob二进制lob，可以存储较大的二进制对象，如图形、视频剪辑、声音文件等


​	
====== select ======

1、定义列别名
  列别名具有以下特征和用途：
  • 可重命名列标题
  • 有助于计算
  • 紧跟在列名后（列名和别名之间也可以加上可选
  关键字 AS）
  • 如果别名包含空格或特殊字符，或者区分大小写，
  则需要使用双引号

2、连接运算符：select last_name,job_id, last_name || ' is a '|| job_id as "Employees" from emp;
  连接运算符具有以下特征和用途：
  • 将列或字符串链接到其它列
  • 由两条竖线 (||) 表示
  • 创建一个由字符表达式生成的列

3、文字字符串
  • 文字是指 SELECT 语句中包含的字符、数字或日期。
  • 日期和字符文字值必须放在单引号内。
  • 每个字符串在每个返回行中输出一次。

4、其它引号 (q) 运算符：可以添加特殊字符
  • 指定您自己的引号分隔符。
  • 选择任一分隔符。
  • 提高可读性和易用性。

5、比较运算符
  <> 不等于，与mysql中有所区别【!=】
  IS NULL 为空值

6、使用 LIKE 运算符执行模式匹配
  • 使用 LIKE 运算符可执行通配符搜索，查找有效搜索
  字符串值。
  • 搜索条件可包含文字字符或数字：
  % 表示零个或多个字符。
  _ 表示一个字符。

7、替代变量
  • 使用替代变量：
  – 使用单与号 (&) 及双与号 (&&) 替代变量可临时存储值
  • 可将替代变量作为以下项的补充：
  – WHERE 条件
  – ORDER BY 子句
  – 列表达式
  – 表名
  – 整个 SELECT 语句
  使用替代变量指定数值：【SELECT * FROM emp WHERE deptno = &employee_num;】
  使用替代变量指定字符值和日期值：【SELECT * FROM emp WHERE job_id = '&job_title';】
  指定列名、表达式和文本：【SELECT deptno, last_name, job_id,&column_name FROM emp WHERE &condition ORDER BY &order_column;】
  使用双与号替代变量,双与号 (&&)可以将变量保存起来，而不必每次都输入

8、使用 DEFINE 命令
  创建变量：【DEFINE employee_num = 200;】
  删除变量：【UNDEFINE employee_num;】

9、使用 VERIFY 命令

10、SQL函数
  单行函数：
  • 处理数据项
  • 接受参数并返回一个值
  • 对每个返回行进行处理
  • 为每行返回一个结果
  • 可能会修改数据类型
  • 可以嵌套
  • 接受参数，这些参数可以是列或表达式

11、大小写转换函数
  LOWER('SQL Course') sql course
  UPPER('SQL Course') SQL COURSE
  INITCAP('SQL Course') Sql Course

12、字符处理函数
  CONCAT('Hello', 'World') HelloWorld
  SUBSTR('HelloWorld',1,5) Hello
  LENGTH('HelloWorld') 10 
  INSTR('HelloWorld', 'W') 6
  LPAD(salary,10,'*') *****24000
  RPAD(salary, 10, '*') 24000***** 
  REPLACE ('JACK and JUE','J','BL') BLACK and BLUE 
  TRIM('H' FROM 'HelloWorld') elloWorld

13、数字函数,DUAL 是可用于查看函数和计算结果的公用表。
  • ROUND：将值舍入到指定的小数位【select ROUND(45.926, 2) from DUAL --> 45.93】
  • TRUNC：将值截断到指定的小数位【select TRUNC(45.926, 2) from DUAL --> 45.92】
  • MOD：返回除法运算的余数【select MOD(1600, 300) from DUAL --> 100】

14、处理日期，默认的日期显示格式为 DD-MON-RR
  MONTHS_BETWEEN 两个日期之间的月数，计算工作年数和月数：【SELECT hire_date,trunc(months_between(SYSDATE,hire_date)/12) FROM emp;】



  2)、DECODE 函数：【DECODE(col|expression, search1, result1 [, search2, result2,...,] [, default])】
  【SELECT
      last_name,
      job_id,
      salary,
      DECODE(job_id,'IT_PROG',1.10 * salary,'ST_CLERK',1.15 * salary,'SA_REP',1.20 * salary,salary) revised_salary
  FROM
      emp;】

16、何谓组函数：组函数会对行集进行计算，为每个组提供一个结果。

  1)、语法：【SELECT group_function(column), ... FROM table [WHERE condition] [ORDER BY column];】

  2)、可以对数字数据使用 AVG 和 SUM 函数。

  3)、可以对数字、字符和日期数据类型使用 MIN 和 MAX 函数

  4)、COUNT(*) 将返回表中的行数

  5)、COUNT(expr) 将返回 expr 为非空值的行的数量

17、组函数和空值

  1)、组函数将忽略列中的空值：【SELECT AVG(commission_pct) FROM emp; 】

  2)、NVL 函数会强制组函数包括空值：【SELECT AVG(NVL(commission_pct, 0)) 2 FROM emp;】

18、SELECT 列表中未出现在组函数中的所有列都必须包含在GROUP BY 子句中
   【SELECT deptno, AVG(salary) FROM emp GROUP BY deptno ;】

19、按多个列进行分组：将 EMPLOYEES 表中每种职务的薪金相加，并按部门进行分组
   【SELECT deptno, job_id, SUM(salary) FROM emp WHERE deptno > 40 GROUP BY deptno, job_id;】

20、使用 HAVING 子句限定组结果：【SELECT deptno, MAX(salary) FROM emp GROUP BY deptno HAVING MAX(salary)>10000 ;】

21、嵌套组函数：【】

22、自然连接（NATURAL JOIN）是一种特殊的等值连接，将表中具有相同名称的列自动进行匹配。自然连接不必指定任何连接条件。

23、非等值联接检索记录：查询员工工资以及工资所属级别
   【SELECT * FROM emp e,salgrade sg WHERE e.sal BETWEEN sg.losal AND sg.hisal;】

24、笛卡尔积，以下情况时将形成笛卡尔积，使用连接条件可以避免笛卡尔全集（n-1个连接条件，n为表个数）

  1)、联接条件被忽略

  2)、联接条件无效

  3)、第一个表中的所有行被联接到第二个表中的所有行

25、子查询

  1)、先执行子查询（内部查询），再执行主查询（外部查询）

  2)、主查询会使用子查询的结果

  3)、单行子查询：【 select empno,ename,sal from emp where sal<(select max(sal) from emp) and sal>(select min(sal) from emp); 】

  4)、多行子查询：多行子查询 返回多行数据的子查询
    (1)、使用in运算符：【 select empno,ename,sal from emp where deptno in (select deptno from dept where dname <> 'sales'); 】
    (2)、使用any运算符：【 select depno,ename,sal from emp where sal > any (select sal from emp where deptno =10) and depno <> 10; 】
    (3)、使用all运算符：【 select depno,ename,sal from emp where sal > all (select sal from emp where deptno=30); 】

26、排除信息【<> ALL】：【 SELECT * FROM emp WHERE deptno <> ALL ( SELECT deptno FROM emp WHERE deptno = 20); 】

27、for update : 会对正在操作的行进行锁定

28、聚合函数过滤用having而不是where：【 SELECT e.deptno ID,COUNT(1),MAX(e.sal) FROM emp e GROUP BY e.deptno HAVING  MAX(e.sal) > 3000; 】

29、EXISTS
  比如 a,b 关联列为 a.id = b.id，现在要取 a 中的数据，其中id在b中也存在：
  【select * from a where exists(select 1 from b where a.id = b.id);】
  或者：
  现在要取 a 中的数据，其中id在b中 不存在：
  【select * from a where not exists(select 1 from b where a.id = b.id);】

  In 和 exists 对比：若子查询结果集比较小，优先使用 in，若外层查询比子查询小，优先使用 exists。

30、集合运算符准则：SELECT 列表中的表达式在数量和类型上必须匹配
  1)、UNION/UNION ALL：除非使用 UNION ALL 运算符，否则会自动删除重复行，第一个查询中的列名将显示在结果中。
  2)、INTERSECT：运算符将返回两个查询的共同行。
  3)、MINUS：运算符将返回由第一个查询选定的但没有出现在第二个查询结果集中的所有不同行。
    --1、要连接查询：求每个部门员工数以及总人数
    SELECT * FROM (
        SELECT dept.dname name,COUNT(emp.deptno) cnt 
        FROM emp,dept 
        WHERE dept.deptno = emp.deptno(+) 
        GROUP BY dept.dname
        UNION ALL
        SELECT '总数' name,COUNT(*) cnt FROM emp
      ) t ORDER BY cnt;
      
    --2、视图：求每个部门员工数以及总人数
    SELECT dept.dname, DECODE(c.cnt,0,c.cnt)
    FROM dept
         ,(SELECT emp.deptno,COUNT(emp.deptno) cnt FROM emp GROUP BY emp.deptno) c 
    WHERE dept.deptno = c.deptno(+)
    UNION ALL
    SELECT '员工总数', COUNT(emp.deptno) cnt FROM emp;

​    --3、分析函数：求每个部门员工数以及总人数
​    SELECT dept.dname, c.count_id 
​    FROM (select distinct emp.deptno deptno,count(emp.deptno) 
​          over(partition by emp.deptno 
​          order by emp.deptno) count_id
​          from emp
​          ) c,
​         dept
​    WHERE dept.deptno = c.deptno(+)
​    UNION ALL
​    SELECT '员工总数', COUNT(emp.deptno) FROM emp;
​    
​    --4、使用rollup函数：求每个部门员工数以及总人数
​    select decode(grouping(dept.dname),1,'员工总数',dept.dname)
​           ,count(emp.deptno)
​    from dept,emp
​    where dept.deptno=emp.deptno(+)
​    group by rollup(dept.dname)
​    ORDER BY count(emp.deptno);

# 条件运算符

## case when

1. case 表达式返回的是一个确定的 value，如果没有 else，若前面的都不匹配，则返回 null
2. 简单 case 中的表达式，when 后面的表达式类型应该全部保持一致
3. 所有的 then 后面的 return_value 类型要保持一致 

~~~sql
case when type_id = 'SALE' then '销售' when type_id = 'PURCHASE' then '采购' else '其他' end as orderType;
~~~

## decode()

~~~sql
decode(sex,'0','女','男')
~~~

# 两时间之差，返回SECOND数

~~~sql
select TIMESTAMPDIFF(SECOND,start_time,end_time)
~~~



33、将表重新置于读/写模式：【ALTER TABLE emp READ ONLY;】、【ALTER TABLE emp READ WRITE;】

34、创建视图：【CREATE OR REPLACE VIEW EMPVU80 AS SELECT deptno, job, sal FROM emp WHERE deptno = 20;】

35、拒绝DML操作：【WITH READ ONLY】

36、确保对视图执行的 DML 操作只在视图范围内起作用：【WITH CHECK OPTION】
CREATE OR REPLACE VIEW empvu20 AS SELECT * FROM emp WHERE deptno = 20 WITH CHECK OPTION CONSTRAINT empvu20_ck ;

# 创建序列

~~~sql
CREATE SEQUENCE deptid_seq
INCREMENT BY 10
START WITH 120
MAXVALUE 9999
NOCACHE
NOCYCLE;
~~~

# 格式时间

~~~sql
date_format(_re.time,'%Y-%m-%d %H:%i:%s') as timeStr
~~~

# 创建索引

~~~sql
CREATE [UNIQUE][BITMAP]INDEX index_name ON table (column[, column]...);
CREATE INDEX index_name ON emp(last_name);
~~~

# 删除索引

~~~sql
DROP INDEX index_name;
~~~

# rollup和grouping函数

  ~~~sql
SELECT DECODE(GROUPING(GROUP_ID), 1, '总量', GROUP_ID),
       DECODE(GROUPING(JOB), 1, '部门编号为【' || GROUP_ID || '】的总量', JOB),
       SUM(SALARY)
FROM GROUP_TEST
 GROUP BY ROLLUP(GROUP_ID, JOB);
  ~~~



41、with 子句：使用 WITH AS 语句可以为一个子查询语句块 定义一个名称
  1)、SQL 可读性增强。比如对于特定 with 子查询取个有意义的名字等。
  2)、with 子查询只执行一次，将结果存储在用户临时表空间中，可以引用多次，增强性能。
  3)、例子：查询出部门的总薪水大于所有部门平均总薪水的部门
  WITH 
    a AS (SELECT emp.deptno deptno,SUM(emp.sal) cnt FROM emp GROUP BY emp.deptno)
    ,b AS (SELECT SUM(cnt)/COUNT(deptno) dep_avg FROM a)
  SELECT dept.dname,cnt FROM a,dept WHERE dept.deptno = a.deptno AND cnt > (SELECT dep_avg FROM b)

# 集合查询

~~~sql
 SUM(NVL(XSCJHZB.XF, 0)) OVER(PARTITION BY XSCJHZB.XN, XSCJHZB.XH) AS QDZXF,
~~~

# merge into 合并资料

 ## 语法：(其中 as 可以省略)

~~~sql
  MERGE INTO table_name AS table_alias
  USING (table|view|sub_query) AS alias
  ON (join condition)
  WHEN MATCHED THEN
  UPDATE SET
  col1 = col_val1,
  col2 = col2_val --9i 不可以有 where 条件，10g 可以
  WHEN NOT MATCHED THEN
  INSERT (column_list)—多个列以逗号分割 //可以不指定列
  VALUES (column_values); --9i 不可以有 where 条件，10g 可以
~~~

## 案例

~~~sql
  --10g新特性，满足条件的插入和更新
  merge into acct a
  using subs b on (a.msid=b.msid)
  when MATCHED then
  update set a.areacode=b.areacode
  where b.ms_type=0
  when NOT MATCHED then
  insert(msid,bill_month,areacode)
  values(b.msid,'200702',b.areacode)
  where b.ms_type=0; 
~~~

# 层次查询

## 语法格式

~~~sql
select [level],colum,expr... from table [where condition(s)] [start with condition(s)] [connect by prior condition(s)]; 
~~~

  2)、从id=2的员工开始查找其子节点，把整棵树删除
   【 select id from s_emp a tart with id=2 connect by prior id=manager_id); 】

  3)、给定一个具体的 emp 看是否对某个 emp 有管理权，也就是从给定的节点寻找，看其子树节点中能否找到这个节点。如果找到，返回，找不到，no rows returned. 
   【 select level,a.* from s_emp a where first_name='Elena' start with manager_id is null connect by prior id=manager_id; 】

  4)、找出每个部门的经理
    【select level,a.* from s_emp a start with manager_id is null connect by prior id=manager_id and dept_id !=prior dept_id; 】

45、子查询：非相关子查询效率高相关子查询
  1)、非相关子查询：非相关子查询是独立于外部查询的子查询，子查询总共执行一次，执行完毕后
将值传递给外部查询。

  2)、相关子查询：相关子查询的执行依赖于外部查询的数据，外部查询执行一行，子查询就执行一次。

46、行转列：
  1)、wm_concat：wm_concat(e.ename)
  2)、listagg：listagg(e.ename,',') WITHIN GROUP (ORDER BY e.job)

====== 分析函数，对数据提供多维分析 ======
1、分类
  1)、等级函数(ranking)
  2)、聚合函数(aggregate)
  3)、行比较函数(row comparison)
  4)、统计函数(statistical)

2、分子子句构成：
  1)、partition 子句：确定如何对行进行分组，如果没有 partition 子句，那么所有行为一组。
  2)、window 子句：确定当前行对应的窗口范围，从而用分析函数计算当前行对应的窗口的值，如果没有 window 子句，而有 order by那么默认窗口范围是组的首行到当前行，如果没有 order by，那么默认是组的首行到组的末行。
行。
  3)、order by 子句：确定组内的排序规则。

# 多行变一行（多变单）

```sql
(SELECT LISTAGG(isbn, ',') WITHIN GROUP(ORDER BY isbn) FROM t_jcgl_zdjcxx WHERE jxbh = rw.jxbh AND bjdm = BJSZ.BJDM group by jxbh) as isbn
或
--11g之后被摒弃，建议使用 LISTAGG(isbn, ',') WITHIN GROUP(ORDER BY isbn)
wm_concat(isbn)
```

# 一行变多行（单变多）

~~~sql
WITH t AS (SELECT 'G01-0005,9787508284309' txt FROM dual) 
SELECT replace(regexp_substr(txt,'[^,]+',1,level),',','') txt FROM t 
CONNECT BY level<=length(txt)-length(replace(txt,',',''))+1 
~~~

~~~sql
  --多行转一行
    SELECT  LISTAGG(cbs.cbsmc,',') within group(order by cbs.cbsmc) FROM jcgl_cbsxx cbs 
    WHERE cbs.cbsdm IN (
    SELECT jckxxb.cbs FROM jcgl_jckxxb jckxxb WHERE jckxxb.isbn IN (
	  --一行转多行
      SELECT replace(regexp_substr(jxrwb.jcbh,'[^,]+',1,level),',','') FROM dual 
      CONNECT BY level<=length(jxrwb.jcbh)-length(replace(jxrwb.jcbh,',',''))+1
      )
    )
  ) AS cbs,--出版社
~~~

# column_value

~~~sql
SELECT
      --多变单
      wm_concat(isbn)
  INTO
      v_jcbm --更新到 JXJHGL_JXRWB 的 jcbh 字段中
  FROM
      jcgl_jckxxb
  WHERE
      isbn IN (
          SELECT
              column_value
          FROM
              TABLE ( i_jcbm )
      );
~~~



plsql中代替遍历，
  【UPDATE jxjhgl_jxrwb jxjhgljxrwb
      SET
          jxjhgljxrwb.jcmc = v_jcmc,
          jxjhgljxrwb.jcbh = v_jcbm
  WHERE
      jxjhgljxrwb.jxbh IN (
          SELECT
              column_value
          FROM
              TABLE ( v_cbjxbh )
      );】

带(+)为从表
SELECT st.id
      ,st.name
      ,mj.name 
FROM student st
     ,major mj
WHERE st.major_id = mj.id(+);

表之间数据的相互 拷贝 ，从一张表中批量选中数据插入另外一张表中：
【insert into t2 (xjh,xsid) select xh|| '10' AS xjh ,xsid from t1  WHERE xx = yy;】

# UUID uuid

~~~sql
sys_guid()
~~~

# 自增序列

~~~sql
create SEQUENCE student_SEQ
INSERT INTO tbl_test VALUES(student_SEQ.nextval,'测试');
--mysql中
inert into [key存在的就报错] / replace into [key存在的就更新]
~~~

# 模糊查询,

~~~sql
  <if test="mallId !=null and mallId !='' ">
  	AND instr(mall_id,#{mallId}) > 0
  </if>
~~~

# 判断字符是否相等		

~~~sql
  <if test="grade!= null and grade!= '' and grade == '1'.toString()">
      id = ''
  </if>
~~~

# 查询一行，ROWNUM

~~~sql
WHERE ROWNUM <= 15;
~~~

# 批量插入

~~~sql
<insert id="batchInsert"
        parameterType="java.util.List">
    INSERT INTO T_BSGL_BSKT_MXZY (
        BSKT_ID,
        NJZYFX_ID,
        CJR,
        CJSJ
    )
    <foreach collection="list" item="item" index="index" separator="UNION ALL" open="(" close=")">
        SELECT
            #{item.graduationTopicId,jdbcType=VARCHAR},
            #{item.gradeMajorId,jdbcType=VARCHAR},
            #{item.creator,jdbcType=VARCHAR},
            #{item.createTime,jdbcType=DATE}
        FROM dual
    </foreach>
</insert>
~~~

~~~sql
    <insert id="initMajorDevelopLink" >
        insert into T_PYFA_ZYPYHJSZ (
		ZYPYHJSZ_ID,
		ND,
		DW_ID,
		ZY_ID,
        XSLBM,
		ZHXGSJ
        )
        SELECT DISTINCT
        ZYPYFX.NJ||ZYPYFX.DW_ID||ZYPYFX.ZY_ID||ZYPYFX.XSLBM,
        nvl(ZYPYFX.NJ,'-') NJ,
        nvl(ZYPYFX.DW_ID,'-') DW_ID,
        ZYPYFX.ZY_ID,
        nvl(ZYPYFX.XSLBM,'-') XSLBM,
        sysdate
		FROM ly_yjs_hxsj.T_PYFA_ZYPYFX ZYPYFX
		WHERE ZYPYFX.ZYPYFX_ID in
            <foreach item="item" index="index" collection="majorDevelopDirectionIdList" open="(" separator="," close=")">
                #{item}
            </foreach>
        and NOT EXISTS (
        SELECT 1 FROM T_PYFA_ZYPYHJSZ ZYPYHJSZ
        WHERE ZYPYHJSZ.ZYPYHJSZ_ID=ZYPYFX.nj||ZYPYFX.Dw_Id||ZYPYFX.ZY_ID||ZYPYFX.XSLBM
        )
    </insert>
~~~



# 表复制备份,使用子查询创建表

~~~sql
CREATE  TABLE ly_gg_qx_zy_20200407 AS SELECT * FROM ly_gg_qx_zy where ...;
CREATE TABLE dept80 AS SELECT deptno, last_name,salary*12 ANNSAL,hire_date FROM emp  WHERE deptno = 80;
~~~

# 创建dblink，DataBase links dblink

~~~sql
drop database link hxsj;
CREATE DATABASE link hxsj CONNECT TO gzjwxt_hxk identified BY gzjwxt_hxk USING '(
  DESCRIPTION =
   (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.2.97)(PORT = 1521)) )
   (CONNECT_DATA = (SERVICE_NAME =orcl))
)';
~~~

# 查询表字段

~~~sql
select  *  from user_col_comments  where Table_Name='T_DMK_DMZ'
~~~

# nlssort方法/函数，排序

## 按拼音排序：

~~~sql
select * from MEMBER t order by NLSSORT(t.b,'NLS_SORT = SCHINESE_PINYIN_M')
~~~

## 按笔画排序：

~~~sql
select * from MEMBER t order by NLSSORT(t.b,'NLS_SORT = SCHINESE_STROKE_M')
~~~

## 按部首排序：

~~~sql
select * from MEMBER t order by NLSSORT(t.b,'NLS_SORT = SCHINESE_RADICAL_M')
~~~

# oracle 进阶 connect by 和level 的用法

为了快速的查询层级关系的关键字，在代理关系中，或者权限关系中，经常会有层层嵌套的场景，比如，同行数据的第一个字段是ID，第二个字段是parentID，parentID表示他的上级ID是谁

~~~

~~~



# 开窗函数over()

查询附件类型为pdf的文件名以及个数

~~~sql
SELECT fj.fjmc,fj.fjlx,COUNT(1)OVER() AS cnt FROM gzjwxt_hxk.T_XTGL_FJ fj WHERE INSTR(fj.fjlx,'pdf')>0
~~~

# 存在则不添加，保证数据唯一性

~~~sql
insert when (not exists (select 1 from T_PYGC_XMXX_XY where xmxxid || xy = #{projectInfoId} || #{collegeName})) then
into T_PYGC_XMXX_XY(xmxxid, xy) values (#{projectInfoId}, #{collegeName}) select 1 from dual
~~~





















