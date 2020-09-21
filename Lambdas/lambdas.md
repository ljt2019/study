# Functional Interface

一个接口仅有一个抽象方法，比如Runnable、Comparator等。在任何一个需要Functional Interface对象的地方，都可以使用lambda表达式。

# Method Reference

将lambda表达式的参数作为参数传递给一个方法，他们的执行效果是相同的，则该lambda表达式可以使用Method Reference表达，以下两种方式是等价的：

~~~java
(x) -> System.out.println(x)
System.out::println
~~~

## Method Reference主要有三种形式

1. Object::instanceMethod
2. Class::staticMethod
3. Class::instanceMethod

  对于前两种方式，对应的lambda表达式的参数和method的参数是一致的，比如：

~~~java
  System.out::println
  (x) -> System.out.println(x)
~~~

##   Math::pow 

~~~java
  (x, y) -> Math.pow(x, y)
~~~

  对于第三种方式，对应的lambda表达式的语句体中，第一个参数作为对象，调用method，将其它参数

~~~java
  String::compareToIgnoreCase
  (s1, s2) -> s1.compareToIgnoreCase(s2)
~~~

  Constructor Reference与Method Reference类似，只不过是特殊的method：new，具体调用的是哪个构造函数，由上下文环境决定，比如：

~~~java
  List<String> labels = ...;
  Stream<Button> stream = labels.stream().map(Button::new);
~~~

  Button::new等价于(x) -> Button(x)，所以调用的构造函数是：Button(x);除了创建单个对象，也可以创建对象数组，如下面两种方式等价：

~~~java
  int[]::new 
  (x) -> new int[x]
~~~

#  Map 

## map遍历

~~~java
itemsMap.forEach((k,v)->System.out.println("key : " + k + " value : " + v));
~~~



#  List 

## 排序

### list升序

~~~java
    //原始版
    words.sort((Word first, Word second) -> first.getName().compareTo(second.getName()));
    //精简版
    Collections.sort(words, Comparator.comparing(Word::getName));
    //精简版，推荐使用这种
    words.sort(Comparator.comparing(Word::getName));
	//多重排序（连个都升序）
	words.sort(Comparator.comparing(Word::getName).thenComparingInt(Word::getCountry));
	//多重排序（第一个升，第二个降）
    words.sort(Comparator.comparing(Word::getName).reversed().thenComparingInt(Word::getCountry).reversed());
~~~

### list降序 reversed

~~~java
words.sort(Comparator.comparing(Word::getName).reversed());
~~~

## list遍历

~~~java
words.forEach(System.out::println);
~~~

~~~java
Stream.iterate(0, i -> i + 1).limit(words.size()).forEach(i -> {
	System.out.println(words.get(i));
});
~~~

~~~java
IntStream.range(0,words.size()).forEach(i->{
    System.out.println(words.get(i));
});
~~~





## list转Map

### key去重

~~~java
Map<String, Word> wordMapMap = wordList.stream().collect(Collectors.toMap(Word::getName, k -> k, (k1, k2) -> k1));
~~~

### key不能重复

~~~java
Map<Integer,Word> userMap = wordList.stream().collect(Collectors.toMap(Word::getCountry, k -> k));
~~~

## list分组

~~~java
Map<String, List<Word>> groupMap = wordList.stream().collect(Collectors.groupingBy(Word::getName));
~~~

## list  多重分组
~~~java
Map<String, Map<Integer, List<Word>>> map2 = words.stream().collect(Collectors.groupingBy(Word::getName,Collectors.groupingBy(Word::getCountry)));
~~~

## list 匹配过滤

~~~java
List<Word> filterList = wordList.stream().filter(v -> v.getName().equals("tiger")).collect(Collectors.toList());
wordList.stream().filter(v -> v.getName().contains("老")).forEach(System.out::println);
List<Word> filterList = wordList.stream().filter(v -> v.getCountry() > 2).collect(Collectors.toList());

//多条件匹配
Predicate<Word> equals1 = v -> v.getCountry().equals(1);
Predicate<Word> contains1 = v -> v.getName().contains("tiger");
List<Word> filterList = wordList.stream().filter(contains1.and(equals1)).collect(Collectors.toList());

//数值匹配
List<Double> filteredCost = cost.stream().filter(x -> x > 25.0).collect(Collectors.toList());
~~~

## list统计求和

~~~java
int sum = wordList.stream().mapToInt(Word::getCountry).sum();
double sum = words.stream().mapToDouble(Word::getCountry).sum();
~~~

## list分组统计个数

~~~java
Map<String, Long> collectCnt = wordList.stream().collect(Collectors.groupingBy(Word::getName, Collectors.counting()));
~~~

## list分组统计

~~~java
Map<String, LongSummaryStatistics> map3 = words.stream().collect(Collectors.groupingBy(Word::getName,Collectors.summarizingLong(Word::getCountry)));
~~~

## list多重分组统计

~~~java
Map<String, Map<Integer, LongSummaryStatistics>> map3 = words.stream().collect(Collectors.groupingBy(Word::getName,Collectors.groupingBy(Word::getCountry,Collectors.summarizingLong(Word::getCountry))));
~~~

## list去重（需根据实际情况重写Word的equals与hashCode）distinct

~~~java
List<Word> list1= list.stream().distinct().collect(Collectors.toList());
~~~

## list数据拼装

~~~java
String result = words.stream().map(Word::getName).collect(Collectors.joining("," , "[" , "]"));
~~~

## list对象集合转属性集合

~~~java
List<String> result = words.stream().map(Word::getName).collect(Collectors.toList());
List<String> result = words.stream().map(y -> y.getName().concat(".jpg")).collect(Collectors.toList());
~~~


​		

# 数组 

## 数组排序

### 数组升序

~~~java
//原始
Arrays.sort(people, (first, second) -> Integer.compare(first.length(),  second.length()));
//精简版
Arrays.sort(people, Comparator.comparingInt(String::hashCode));
~~~

## 数组降序

~~~java
Arrays.sort(people, (second, first) -> Integer.compare(first.length(), second.length()));
~~~

## 数组统计某元素个数

~~~java
long num = Arrays.stream(name).filter(x -> x.equals("tiger")).count();
~~~

## 数组去重过滤并转化成list

~~~java
List<String> filterList = Arrays.stream(name).filter(x -> !x.equals("赵强")).collect(Collectors.toList());
~~~

## 数组过滤，并对元素加后缀

~~~java
List<String> filterList = Arrays.stream(name).filter(x -> !x.equals("tiger")).map(y -> y.concat(".jpg")).collect(Collectors.toList());
~~~

## 数组统计求和      

~~~java
int num = Arrays.stream(arr).reduce(0, Integer::sum);
double sum =  Arrays.asList(10.0, 20.0, 30.0).stream().map(x -> x + x * 0.05).reduce(Double::sum).get();
double sum = Stream.of(10.0, 20.0, 30.0).map(x -> x + x * 0.05).reduce(Double::sum).get();
int sum = Stream.of(1, 2, 3, 4, 5).map(x -> x + x * 5).reduce(Integer::sum).get();
~~~

# 求和函数

~~~java
  BinaryOperator<Integer> add = Integer::sum;
  Integer x = add.apply(20, 30);
~~~

# 线程 

~~~java
private static void lambdaRunnable() {
    int age = 20;
    Runnable r2 = () -> {
        System.out.println(age);
        System.out.println("Hello world two!"+age);
    };
    r2.run();
}
~~~
