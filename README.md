以atm取款机的RPC远程调用过程为例进行模拟实现 <br>
要求有登陆、注册功能 <br>
内部有取款、存款、查余额的功能 <br>
使用代理 <br>
 <br>
 ### 实际实现
 atm机使用了clientProxy获取client的代理，利用了client提供的传输datapak的功能，
 server使用处理线程接收到datapak后解包，反射调用指定的方法，将结果放到datapak的
 result对象中并传回。
 
 ##### 参考
 <a href="https://blog.csdn.net/zl_1079167478/article/details/79461512">
 Java分布式组件 - - RPC（手写一个RPC）</a?