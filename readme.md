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

<br/>

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

###### Ribbon失败重试机制

**方案一：使用对RestTemplate添加拦截器的方式**

1. 引入spring-retry包

   ```xml
   <dependency>
     <groupId>org.springframework.retry</groupId>
     <artifactId>spring-retry</artifactId>
   </dependency>
   ```

2. 使用@LoadBalanced注解修饰RestTemplate实例的声明

   ```java
   /**
    * 在使用Ribbon负载均衡时必须使用@LoadBalanced注解
    */
   @Bean
   @LoadBalanced
   public RestTemplate restTemplate() {
       // 配置超时时间
       SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
       factory.setConnectTimeout(2000);
       factory.setReadTimeout(3000);
       return new RestTemplate(factory);
   }
   ```

3. 对服务之间调用进行负载均衡配置和失败重试配置

   全局配置

   ```yaml
   ribbon:
     # 负载均衡配置
     NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule
     # 无论什么类型请求失败都进行重试, 否则只会对GET请求重试
     OkToRetryOnAllOperations: false
     # 切换服务的重试次数
     MaxAutoRetriesNextServer: 2
     # 对当前服务的重试次数
     MaxAutoRetries: 0
     
     # 连接超时时间(该配置在此方式中无效, 可采用在声明RestTemplate实例时进行超时时间的全局配置, 如上)
     # ConnectTimeout: 2000
     # 请求超时时间(该配置在此方式中无效, 可采用在声明RestTemplate实例时进行超时时间的全局配置, 如上)
     # ReadTimeout: 3000
   ```

   针对某个服务配置

   ```yaml
   # 这个服务的名称
   application-name:
     ribbon:
       # 负载均衡配置
       NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule
       # 无论什么类型请求失败都进行重试, 否则只会对GET请求重试
       OkToRetryOnAllOperations: false
       # 切换服务的重试次数
       MaxAutoRetriesNextServer: 2
       # 对当前服务的重试次数
       MaxAutoRetries: 0
       
       # 连接超时时间(该配置在此方式中无效)
       # ConnectTimeout: 2000
       # 请求超时时间(该配置在此方式中无效)
       # ReadTimeout: 3000
   ```

4. 源码分析

   1) 从@LoadBalanced注解作为入口，可得知该注解所在jar包的spring自动装配配置文件spring.factories如下

   ```
   # AutoConfiguration
   org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
   org.springframework.cloud.client.CommonsClientAutoConfiguration,\
   org.springframework.cloud.client.ReactiveCommonsClientAutoConfiguration,\
   org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClientAutoConfiguration,\
   org.springframework.cloud.client.discovery.composite.reactive.ReactiveCompositeDiscoveryClientAutoConfiguration,\
   org.springframework.cloud.client.discovery.noop.NoopDiscoveryClientAutoConfiguration,\
   org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration,\
   org.springframework.cloud.client.discovery.simple.reactive.SimpleReactiveDiscoveryClientAutoConfiguration,\
   org.springframework.cloud.client.hypermedia.CloudHypermediaAutoConfiguration,\
   org.springframework.cloud.client.loadbalancer.AsyncLoadBalancerAutoConfiguration,\
   # 这个类就是我们要找的负载均衡的自动配置类
   org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration,\
   org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerBeanPostProcessorAutoConfiguration,\
   org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerClientAutoConfiguration,\
   org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancerAutoConfiguration,\
   org.springframework.cloud.client.serviceregistry.ServiceRegistryAutoConfiguration,\
   org.springframework.cloud.commons.httpclient.HttpClientConfiguration,\
   org.springframework.cloud.commons.util.UtilAutoConfiguration,\
   org.springframework.cloud.configuration.CompatibilityVerifierAutoConfiguration,\
   org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration
   # Environment Post Processors
   org.springframework.boot.env.EnvironmentPostProcessor=\
   org.springframework.cloud.client.HostInfoEnvironmentPostProcessor
   # Failure Analyzers
   org.springframework.boot.diagnostics.FailureAnalyzer=\
   org.springframework.cloud.configuration.CompatibilityNotMetFailureAnalyzer
   
   ```

   2) LoadBalancerAutoConfiguration源码如下

   ```java
   @Configuration(proxyBeanMethods = false)
   @ConditionalOnClass(RestTemplate.class)
   @ConditionalOnBean(LoadBalancerClient.class)
   @EnableConfigurationProperties(LoadBalancerRetryProperties.class)
   public class LoadBalancerAutoConfiguration {
   
       /**
        * 注入我们声明RestTemplate实例
        */
   	@LoadBalanced
   	@Autowired(required = false)
   	private List<RestTemplate> restTemplates = Collections.emptyList();
   
   	@Autowired(required = false)
   	private List<LoadBalancerRequestTransformer> transformers = Collections.emptyList();
   
   	@Bean
   	public SmartInitializingSingleton loadBalancedRestTemplateInitializerDeprecated(
   			final ObjectProvider<List<RestTemplateCustomizer>> restTemplateCustomizers) {
   		return () -> restTemplateCustomizers.ifAvailable(customizers -> {
   			for (RestTemplate restTemplate : LoadBalancerAutoConfiguration.this.restTemplates) {
   				for (RestTemplateCustomizer customizer : customizers) {
                       /*
                        * 步骤6
                        * 对RestTemplate实例进行自定义配置
                        */
   					customizer.customize(restTemplate);
   				}
   			}
   		});
   	}
   
       /**
        * 步骤4
        * 创建LoadBalancerRequestFactory(负载均衡请求工厂)实例, 并注入LoadBalancerClient实例
        */
   	@Bean
   	@ConditionalOnMissingBean
   	public LoadBalancerRequestFactory loadBalancerRequestFactory(
   			LoadBalancerClient loadBalancerClient) {
   		return new LoadBalancerRequestFactory(loadBalancerClient, this.transformers);
   	}
   
   	@Configuration(proxyBeanMethods = false)
   	@ConditionalOnMissingClass("org.springframework.retry.support.RetryTemplate")
   	static class LoadBalancerInterceptorConfig {
   
   		//......
   
   	}
   
   	@Configuration(proxyBeanMethods = false)
   	@ConditionalOnClass(RetryTemplate.class)
   	public static class RetryAutoConfiguration {
   
   		//......
   
   	}
   
   	@Configuration(proxyBeanMethods = false)
   	@ConditionalOnClass(RetryTemplate.class)
   	public static class RetryInterceptorAutoConfiguration {
   
           /**
            * 步骤5
            * 创建RetryLoadBalancerInterceptor(重试负载均衡拦截器)实例, 并注入LoadBalancerClient、LoadBalancerRequestFactory、LoadBalancedRetryFactory等实例
            */
   		@Bean
   		@ConditionalOnMissingBean
   		public RetryLoadBalancerInterceptor loadBalancerRetryInterceptor(
   				LoadBalancerClient loadBalancerClient,
   				LoadBalancerRetryProperties properties,
   				LoadBalancerRequestFactory requestFactory,
   				LoadBalancedRetryFactory loadBalancedRetryFactory) {
   			return new RetryLoadBalancerInterceptor(loadBalancerClient, properties,
   					requestFactory, loadBalancedRetryFactory);
   		}
   
   		@Bean
   		@ConditionalOnMissingBean
   		public RestTemplateCustomizer restTemplateCustomizer(
   				final RetryLoadBalancerInterceptor loadBalancerInterceptor) {
   			return restTemplate -> {
   				List<ClientHttpRequestInterceptor> list = new ArrayList<>(
   						restTemplate.getInterceptors());
                   /*
                    * 步骤7
                    * 对RestTemplate实例进行添加RetryLoadBalancerInterceptor(重试负载均衡拦截器)
                    */
   				list.add(loadBalancerInterceptor);
   				restTemplate.setInterceptors(list);
   			};
   		}
   
   	}
   
   }
   ```

   3) 再根据Spring Cloud Netflix Ribbon源码中的spring.factories配置文件，可找到Ribbon的自动配置类RibbonAutoConfiguration

   ```
   org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
   org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration
   ```

   ```java
   @Configuration
   @Conditional(RibbonAutoConfiguration.RibbonClassesConditions.class)
   @RibbonClients
   @AutoConfigureAfter(
   		name = "org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration")
   // 声明该配置类在LoadBalancerAutoConfiguration加载之前进行加载
   @AutoConfigureBefore({ LoadBalancerAutoConfiguration.class,
   		AsyncLoadBalancerAutoConfiguration.class })
   @EnableConfigurationProperties({ RibbonEagerLoadProperties.class,
   		ServerIntrospectorProperties.class })
   @ConditionalOnProperty(value = "spring.cloud.loadbalancer.ribbon.enabled",
   		havingValue = "true", matchIfMissing = true)
   public class RibbonAutoConfiguration {
   
   	@Autowired(required = false)
   	private List<RibbonClientSpecification> configurations = new ArrayList<>();
   
   	@Autowired
   	private RibbonEagerLoadProperties ribbonEagerLoadProperties;
   
   	@Bean
   	public HasFeatures ribbonFeature() {
   		return HasFeatures.namedFeature("Ribbon", Ribbon.class);
   	}
   
       /**
        * 步骤1
        * 创建SpringClientFactory(Spring客户端工厂)实例, 并注入相关配置
        */
   	@Bean
   	@ConditionalOnMissingBean
   	public SpringClientFactory springClientFactory() {
   		SpringClientFactory factory = new SpringClientFactory();
   		factory.setConfigurations(this.configurations);
   		return factory;
   	}
   
       /**
        * 步骤2
        * 创建LoadBalancerClient(负载均衡客户端)实例, 并注入SpringClientFactory实例
        */
   	@Bean
   	@ConditionalOnMissingBean(LoadBalancerClient.class)
   	public LoadBalancerClient loadBalancerClient() {
   		return new RibbonLoadBalancerClient(springClientFactory());
   	}
   
       /**
        * 步骤3
        * 创建LoadBalancedRetryFactory(负载均衡重试工厂)实例, 并注入SpringClientFactory实例
        */
   	@Bean
   	@ConditionalOnClass(name = "org.springframework.retry.support.RetryTemplate")
   	@ConditionalOnMissingBean
   	public LoadBalancedRetryFactory loadBalancedRetryPolicyFactory(
   			final SpringClientFactory clientFactory) {
   		return new RibbonLoadBalancedRetryFactory(clientFactory);
   	}
   
   	@Bean
   	@ConditionalOnMissingBean
   	public PropertiesFactory propertiesFactory() {
   		return new PropertiesFactory();
   	}
   
   	@Bean
   	@ConditionalOnProperty("ribbon.eager-load.enabled")
   	public RibbonApplicationContextInitializer ribbonApplicationContextInitializer() {
   		return new RibbonApplicationContextInitializer(springClientFactory(),
   				ribbonEagerLoadProperties.getClients());
   	}
   
   	@Configuration(proxyBeanMethods = false)
   	@ConditionalOnClass(HttpRequest.class)
   	@ConditionalOnRibbonRestClient
   	protected static class RibbonClientHttpRequestFactoryConfiguration {
   
   		//......
   
   	}
   
   	@Target({ ElementType.TYPE, ElementType.METHOD })
   	@Retention(RetentionPolicy.RUNTIME)
   	@Documented
   	@Conditional(OnRibbonRestClientCondition.class)
   	@interface ConditionalOnRibbonRestClient {
   
   	}
   
   	private static class OnRibbonRestClientCondition extends AnyNestedCondition {
   
   		//......
   
   	}
   
   	static class RibbonClassesConditions extends AllNestedConditions {
   
   		//......
   
   	}
   
   }
   ```

**方案二：使用Ribbon的RestClient的方式**

1. 使用@LoadBalanced注解修饰RestTemplate实例的声明

   ```java
   /**
    * 在使用Ribbon负载均衡时必须使用@LoadBalanced注解
    */
   @Bean
   @LoadBalanced
   public RestTemplate restTemplate() {
       return new RestTemplate();
   }
   ```

2. 对服务之间调用进行负载均衡配置和失败重试配置

   ```yaml
   ribbon:
     restclient:
       # 启用Ribbon的RestClient, 必须配置该项
       enabled: true
     eager-load:
       # 对以下服务的Ribbon客户端进行饥饿加载, 以逗号隔开
       clients: application-name1,application-name2
   ```

   全局配置

   ```yaml
   ribbon:
     # 负载均衡配置
     NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule
     # 无论什么类型请求失败都进行重试, 否则只会对GET请求重试
     OkToRetryOnAllOperations: false
     # 切换服务的重试次数
     MaxAutoRetriesNextServer: 2
     # 对当前服务的重试次数
     MaxAutoRetries: 0
     # 连接超时时间
     ConnectTimeout: 2000
     # 请求超时时间
     ReadTimeout: 3000
   ```

      针对某个服务配置

   ```yaml
   # 这个服务的名称
   application-name:
     ribbon:
       # 负载均衡配置
       NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule
       # 无论什么类型请求失败都进行重试, 否则只会对GET请求重试
       OkToRetryOnAllOperations: false
       # 切换服务的重试次数
       MaxAutoRetriesNextServer: 2
       # 对当前服务的重试次数
       MaxAutoRetries: 0
       # 连接超时时间
       ConnectTimeout: 2000
       # 请求超时时间
       ReadTimeout: 3000
   ```

3. 源码分析

   i) 根据LoadBalancerAutoConfiguration类和RibbonAutoConfiguration类重新分析，如下：

   ```java
   @Configuration(proxyBeanMethods = false)
   @ConditionalOnClass(RestTemplate.class)
   @ConditionalOnBean(LoadBalancerClient.class)
   @EnableConfigurationProperties(LoadBalancerRetryProperties.class)
   public class LoadBalancerAutoConfiguration {
   
       /**
        * 注入我们声明RestTemplate实例
        */
   	@LoadBalanced
   	@Autowired(required = false)
   	private List<RestTemplate> restTemplates = Collections.emptyList();
   
   	@Autowired(required = false)
   	private List<LoadBalancerRequestTransformer> transformers = Collections.emptyList();
   
   	@Bean
   	public SmartInitializingSingleton loadBalancedRestTemplateInitializerDeprecated(
   			final ObjectProvider<List<RestTemplateCustomizer>> restTemplateCustomizers) {
   		return () -> restTemplateCustomizers.ifAvailable(customizers -> {
   			for (RestTemplate restTemplate : LoadBalancerAutoConfiguration.this.restTemplates) {
   				for (RestTemplateCustomizer customizer : customizers) {
                       /*
                        * 步骤7
                        * 对RestTemplate实例进行自定义配置
                        */
   					customizer.customize(restTemplate);
   				}
   			}
   		});
   	}
   
       /**
        * 步骤5
        * 创建LoadBalancerRequestFactory(负载均衡请求工厂)实例, 并注入LoadBalancerClient实例
        * 无意义
        */
   	@Bean
   	@ConditionalOnMissingBean
   	public LoadBalancerRequestFactory loadBalancerRequestFactory(
   			LoadBalancerClient loadBalancerClient) {
   		return new LoadBalancerRequestFactory(loadBalancerClient, this.transformers);
   	}
   
   	@Configuration(proxyBeanMethods = false)
   	@ConditionalOnMissingClass("org.springframework.retry.support.RetryTemplate")
   	static class LoadBalancerInterceptorConfig {
   
   		//......
   
   	}
   
   	@Configuration(proxyBeanMethods = false)
   	@ConditionalOnClass(RetryTemplate.class)
   	public static class RetryAutoConfiguration {
   
   		//......
   
   	}
   
   	@Configuration(proxyBeanMethods = false)
   	@ConditionalOnClass(RetryTemplate.class)
   	public static class RetryInterceptorAutoConfiguration {
   
           /**
            * 步骤6
            * 创建RetryLoadBalancerInterceptor(重试负载均衡拦截器)实例, 并注入LoadBalancerClient、LoadBalancerRequestFactory、LoadBalancedRetryFactory等实例
            * 因为该拦截器并不会注入到RestTemplate实例中, 所以创建出来也并不会应用到程序运行中, 从而导致第3、4、5步创建的实例都无意义
            */
   		@Bean
   		@ConditionalOnMissingBean
   		public RetryLoadBalancerInterceptor loadBalancerRetryInterceptor(
   				LoadBalancerClient loadBalancerClient,
   				LoadBalancerRetryProperties properties,
   				LoadBalancerRequestFactory requestFactory,
   				LoadBalancedRetryFactory loadBalancedRetryFactory) {
   			return new RetryLoadBalancerInterceptor(loadBalancerClient, properties,
   					requestFactory, loadBalancedRetryFactory);
   		}
   
   		@Bean
   		@ConditionalOnMissingBean
   		public RestTemplateCustomizer restTemplateCustomizer(
   				final RetryLoadBalancerInterceptor loadBalancerInterceptor) {
   			return restTemplate -> {
   				List<ClientHttpRequestInterceptor> list = new ArrayList<>(
   						restTemplate.getInterceptors());
   				list.add(loadBalancerInterceptor);
   				restTemplate.setInterceptors(list);
   			};
   		}
   
   	}
   
   }
   ```

   ```java
   @Configuration
   @Conditional(RibbonAutoConfiguration.RibbonClassesConditions.class)
   @RibbonClients
   @AutoConfigureAfter(
   		name = "org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration")
   // 声明该配置类在LoadBalancerAutoConfiguration加载之前进行加载
   @AutoConfigureBefore({ LoadBalancerAutoConfiguration.class,
   		AsyncLoadBalancerAutoConfiguration.class })
   @EnableConfigurationProperties({ RibbonEagerLoadProperties.class,
   		ServerIntrospectorProperties.class })
   @ConditionalOnProperty(value = "spring.cloud.loadbalancer.ribbon.enabled",
   		havingValue = "true", matchIfMissing = true)
   public class RibbonAutoConfiguration {
   
   	@Autowired(required = false)
   	private List<RibbonClientSpecification> configurations = new ArrayList<>();
   
   	@Autowired
   	private RibbonEagerLoadProperties ribbonEagerLoadProperties;
   
   	@Bean
   	public HasFeatures ribbonFeature() {
   		return HasFeatures.namedFeature("Ribbon", Ribbon.class);
   	}
   
       /**
        * 步骤1
        * 创建SpringClientFactory(Spring客户端工厂)实例, 并注入相关配置
        */
   	@Bean
   	@ConditionalOnMissingBean
   	public SpringClientFactory springClientFactory() {
   		SpringClientFactory factory = new SpringClientFactory();
   		factory.setConfigurations(this.configurations);
   		return factory;
   	}
   
       /**
        * 步骤3
        * 创建LoadBalancerClient(负载均衡客户端)实例, 并注入SpringClientFactory实例
        * 无意义
        */
   	@Bean
   	@ConditionalOnMissingBean(LoadBalancerClient.class)
   	public LoadBalancerClient loadBalancerClient() {
   		return new RibbonLoadBalancerClient(springClientFactory());
   	}
   
       /**
        * 步骤4
        * 创建LoadBalancedRetryFactory(负载均衡重试工厂)实例, 并注入SpringClientFactory实例
        * 无意义
        */
   	@Bean
   	@ConditionalOnClass(name = "org.springframework.retry.support.RetryTemplate")
   	@ConditionalOnMissingBean
   	public LoadBalancedRetryFactory loadBalancedRetryPolicyFactory(
   			final SpringClientFactory clientFactory) {
   		return new RibbonLoadBalancedRetryFactory(clientFactory);
   	}
   
   	@Bean
   	@ConditionalOnMissingBean
   	public PropertiesFactory propertiesFactory() {
   		return new PropertiesFactory();
   	}
   
   	@Bean
   	@ConditionalOnProperty("ribbon.eager-load.enabled")
   	public RibbonApplicationContextInitializer ribbonApplicationContextInitializer() {
   		return new RibbonApplicationContextInitializer(springClientFactory(),
   				ribbonEagerLoadProperties.getClients());
   	}
   
   	@Configuration(proxyBeanMethods = false)
   	@ConditionalOnClass(HttpRequest.class)
   	@ConditionalOnRibbonRestClient
   	protected static class RibbonClientHttpRequestFactoryConfiguration {
   
   		@Autowired
   		private SpringClientFactory springClientFactory;
   
   		@Bean
   		public RestTemplateCustomizer restTemplateCustomizer(
   				final RibbonClientHttpRequestFactory ribbonClientHttpRequestFactory) {
   			return restTemplate -> restTemplate
                       /*
                        * 步骤8
                        * 对RestTemplate注入RibbonClientHttpRequestFactor实例
                        */
   					.setRequestFactory(ribbonClientHttpRequestFactory);
   		}
   
           /**
            * 步骤2
            * 创建RibbonClientHttpRequestFactory(Ribbon客户端Http请求工厂)实例, 并注入SpringClientFactory实例
            */
   		@Bean
   		public RibbonClientHttpRequestFactory ribbonClientHttpRequestFactory() {
   			return new RibbonClientHttpRequestFactory(this.springClientFactory);
   		}
   
   	}
   
       /**
        * 因OnRibbonRestClientCondition被实例化, 所以导致使用该注解修饰的类将自动装配
        */
   	@Target({ ElementType.TYPE, ElementType.METHOD })
   	@Retention(RetentionPolicy.RUNTIME)
   	@Documented
   	@Conditional(OnRibbonRestClientCondition.class)
   	@interface ConditionalOnRibbonRestClient {
   
   	}
   
   	private static class OnRibbonRestClientCondition extends AnyNestedCondition {
   
   		OnRibbonRestClientCondition() {
   			super(ConfigurationPhase.REGISTER_BEAN);
   		}
   
   		@Deprecated // remove in Edgware"
   		@ConditionalOnProperty("ribbon.http.client.enabled")
   		static class ZuulProperty {
   
   		}
   
           /**
            * 前置条件
            * 若存在该配置, 则该类将被实例化, 从而导致OnRibbonRestClientCondition也将被实例化
            */
   		@ConditionalOnProperty("ribbon.restclient.enabled")
   		static class RibbonProperty {
   
   		}
   
   	}
   
   	static class RibbonClassesConditions extends AllNestedConditions {
   
   		//......
           
   	}
   
   }
   ```

   