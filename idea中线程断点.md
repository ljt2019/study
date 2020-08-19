

# 断点

所有的断点都选择线程调试模式

![image-20200816114912404](C:\Users\tiger\AppData\Roaming\Typora\typora-user-images\image-20200816114912404.png)

![image-20200816115934004](C:\Users\tiger\AppData\Roaming\Typora\typora-user-images\image-20200816115934004.png)

![image-20200816120022401](C:\Users\tiger\AppData\Roaming\Typora\typora-user-images\image-20200816120022401.png)

选择执行的线程

![image-20200816120118807](C:\Users\tiger\AppData\Roaming\Typora\typora-user-images\image-20200816120118807.png)



# 代码示例

测试代码

~~~java
package com.tiger.singleton;

public class SingletonLazy {
    private SingletonLazy() { }
    private static SingletonLazy instance = null;

    //不加 synchronized 测试违反单例实验
    public  static SingletonLazy getInstance() {
        if (instance == null) {
            instance = new SingletonLazy();
        }
        return instance;
    }
}
~~~

~~~java
package com.tiger.singleton;

/**
 * @description:
 * @author: tiger
 * @create: 2020-08-16 10:59
 */
public class Test {

    public static void main(String[] args) {

        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName().concat(":").concat(String.valueOf(SingletonLazy.getInstance())));
        };

        Thread thread1 = new Thread(runnable, "线程1");
        Thread thread2 = new Thread(runnable, "线程2");
        thread1.start();
        thread2.start();

        System.out.println("end");
    }
}

~~~



