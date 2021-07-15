##### 1. Eureka控制台界面详解

###### Home

**System Status**

​	Environment：环境，默认值为test，在实际使用过程中，可以不用更改

​	Data center：数据中心，在Hoxton.SR12版本中默认值为default，网上其他博客大部分都解释默认值为MyOwn，预测是版本的不同该值也不同

​	Current time：当前服务器时间

​	Uptime：已经运行时长，单位：时:分

​	Lease expiration enabled：租约过期是否启用，该值与自我保护机制相关，自我保护关闭时该值为true，开启时为false

​	Renews threshold：每分钟最少续约数，算法公式：2 * n * 0.85，n表示节点数量（包括注册中心）

​	Renews (last min)：最后一分钟的续约数量（接受到的心跳数，不含当前，每分钟更新一次），当该值除以Renews threshold的结果小于等于1时会触发自我保护机制的开启

**红字提醒**

​	<font color=#FF0000>THE SELF PRESERVATION MODE IS TURNED OFF. THIS MAY NOT PROTECT INSTANCE EXPIRY IN CASE OF NETWORK/OTHER PROBLEMS.</font>

​	自我保护机制被关闭，这将导致在网络或者其他异常发生的情况下不会保护过期的实例

---

​	<font color=#FF0000>RENEWALS ARE LESSER THAN THE THRESHOLD. THE SELF PRESERVATION MODE IS TURNED OFF. THIS MAY NOT PROTECT INSTANCE EXPIRY IN CASE OF NETWORK/OTHER PROBLEMS. </font>

​	续约阀值过低，自我保护机制被关闭，这将导致在网络或者其他异常发生的情况下不会保护过期的实例

---

​	<font color=#FF0000>EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.</font>

​	紧急！因续约阀值过低，Eureka自我保护机制已启动，为安全起见，可能导致一些本已过期的实例不会被剔除注册中心

**DS Replicas**

​	Eureka集群节点副本

**Instances currently registered with Eureka**

​	当前注册到Eureka的实例

​	Application：实例名称

​	AMIs：暂时未知是什么

​	Availability Zones：可用区域数量，也就是该实例节点数

​	Status：实例状态（包括实例ID）

**General Info**

​	一般信息

​	total-avail-memory：总共可用内存

​	environment：环境名称

​	num-of-cpus：CPU数量（包括逻辑处理器）

​	current-memory-usage：当前已使用内存百分比

​	server-uptime：服务已经运行时长，单位：时:分

​	registered-replicas：向其他注册中心注册的副本地址

​	unavailable-replicas：不可用的副本地址

​	available-replicas：可用的副本地址

**Instance Info**

​	实例信息

​	ipAddr：当前实例IP地址

​	status：当前实例状态

###### LAST 1000 SINCE STARTUP

**Last 1000 cancelled leases**

​	最后1000个取消续约的实例

**Last 1000 newly registered leases**

​	最后1000个新注册续约的实例



##### 2. Ribbon负载均衡

###### 负载均衡介绍

负载均衡一般分为两种

- 服务端负载均衡
  - Nginx
  - F5(硬件设备)
- 客户端负载均衡
  - Ribbon

###### Ribbon负载均衡策略

- com.netflix.loadbalancer.RoundRobinRule

  轮询方式，默认方式

- com.netflix.loadbalancer.RandomRule

  随机方式

- com.netflix.loadbalancer.RetryRule

  轮询重试方式，重试默认采用的也是轮询

- com.netflix.loadbalancer.WeightedResponseTimeRule

  权重方式，以响应时间决定每个服务的权重，一般响应时间越短权重越高

- com.netflix.loadbalancer.BestAvailableRule

  最优方式，一般选择并发数最小的服务

- com.netflix.loadbalancer.AvailabilityFilteringRule

  可用性过滤方式，过滤掉那些根据特定规则（如：连接不上、并发数超过阀值）而不可用的服务，再在剩余服务中选择

- com.netflix.loadbalancer.ZoneAvoidanceRule

  区域性过滤 + 可用性过滤