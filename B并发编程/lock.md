# 锁的基本原理是

基于将多线程并行任务通过某一种机制实现线程的串行执行，从而达到线程安全性的目的。

# Lock 简介

  在 Lock 之前，只能基于 synchronized 关键字来解决并发安全问题。但是 synchronized 不适合于所有的并发场景。Lock 可以解决而且更加灵活

## ReentrantLock

表示重入锁，它是唯一一个实现了 Lock 接口的类。重入锁指的是线程在获得锁之后，再次获取该锁不需要阻塞，而是直接关联一次计数器增加重入次数，目的是避免线程的死锁

## ReentrantReadWriteLock

适合读多写少的场景，读和读不互斥、读和写互斥、写和写互斥。也就是说涉及到影响数据变化的操作都会存在互斥

## StampedLock

ReentrantReadWriteLock的改进版，大量的读线程存在，可能会引起写线程的饥饿。StampedLock 是一种乐观的读策略，使得乐观锁完全不会阻塞写线程

# AQS 是什么
同步队列 AQS，全称 AbstractQueuedSynchronizer,分两种，独占和共享

1. 独占锁：每次只能有一个线程持有锁，比如 ReentrantLock 就是以独占方式实现的互斥锁
2. 共享锁：允许多个线程同时获取锁，并发访问共享资源，比如ReentrantReadWriteLock

## 基本实现

* 锁的基本要素
  1. 一个共享的数据记录锁的状态(有锁/无锁)，compareAndSetState(0, 1)
  2. State:锁标记 0 是无锁，>=1 是有锁，可能重入
  3. CAS(obj,offset,expect,update)：CAS（Compare-and-Swap），即比较并替换，是一种实现并发算法时常用到的技术。

## 自旋
~~~java
private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            if (t == null) { // Must initialize
                if (compareAndSetHead(new Node()))
                    tail = head;
            } else {
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
}
~~~



  

  

  

  

  

  

  

  

  