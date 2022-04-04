# SpringCloud注意事项
    * 注意SpringCloud版本和SpringBoot版本。
        https://spring.io/projects/spring-cloud
    * 注意配置文件的优先级：bootstrap.XXXX > application.xxxxx,且SpringBoot2.4需单引入spring-boot-starter-bootstrap，才能读取bootstrap.XXXX
    * 官网才是永远的神。   
    * eureka原数据文件：spring-cloud-netflix-eureka-client-XXXXX.jar\spring-configuration-metadata.json 
    * 持续踩坑中，不做大自然的搬运工。。。。   
    
# SpringCloud组件学习及相关注意事项(基于 SpringCloud 2020.0.5 ,SpringBoot版本 2.5.X )
##### SpringCloudEureka 
   * 简介
        * 基于Netflix实现的服务注册中心。分为服务端和应用端，服务端为服务注册中心，应用端可向服务端注册自己的服务。
   * 介绍
        * Eureka分为服务端和应用端，应用端又可分为2个角色：消费者和提供者，消费者(eureka.client.registerWithEureka:false)可不需要将自己的服务向服务中心注册，提供者(eureka.client.registerWithEureka:true)需向服务中心注册自己，
            * 消费者工作原理：
                * 启动时，拉去注册中心的服务信息，并生成一个注册表缓存到本地。
                * 在运行过程中，定时更新服务注册信息。
                * 通过http请求调用远程服务。
            * 提供者工作原理：
                * 启动时就会向注册中心发起REST请求， 提供自己的一些元数据（IP 地址、端口，运行状况指示符 URL，主页等）向服务中心注册。
                * 在运行过程中，定时向注册中心发送renew心跳，证明“我还活着”
                * 停止服务提供者，向注册中心发起cancel请求，清空当前服务注册信息。
        * 核心配置：
            * eureka.instance:
                hostname:ip名
                name:应用名
            * eureka.client:
                 * fetchRegistery: 是否获取服务中心的信息,默认为ture，注册中心需改成false
                 * registerWithEureka: 是否注册自己，默认为ture,注册中心需改成false
                 * serviceUrl:
                      * defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ 
                      * 注：服务中心需要配置。由于是自己，因此与该项目的端口匹配。不设置默认访问8761端口。集群用，隔开。
            * 详细配置见：https://www.cnblogs.com/zyon/p/11023750.html
                 
##### SpringCloudConfig 
   * 简介 
        * 配置式管理服务，用于管理项目中所涉及的配置数据。配置中心可以位于github丶SVN或者本地。分为服务端丶应用端。

   * 介绍
        * cloud-config分为服务端和客户端，服务端用于拉去配置文件，并将读取到的配置文件信息在服务端做缓存。客户端主要获取服务端的配置，并将客户端的数据缓存
        即应用端->服务端->仓库。
   * 服务端:
        * 基本属性配置 
            * spring.cloud.config.git:
                * uri:git地址
                * default-label: 分支，默认master
                * username:git账号
                * password:git密码
                * search-paths:配置文件所在目录，存在子目录中需要配置。
            
        * 使用见spring-cloud-config项目。该项目可供客户端的直连模式或者从eureka注册中心获取。
   * 客户端基本配置: 应用端配置说明见resources/bootstrap.yml
        * spring.cloud.config.git:   
            * name: zsh-config server的服务名
            * label: master  分支
            * profile: dev 
            * uri:  配置中心服务地址
            * 注：以上为配置的最基本的属性，
        * 注：
            * 直连模式（即不把server注册到eureka）,单纯的springBootWeb项目。检查spring-cloud-starter-config.pom文件若该文件里没有引入spring-cloud-config-client,需手动引入，否则拉取不到配置
              * 不配uri：默认连接8888端口。
              * springBoot2.4以上的，uri属性可直接改，也可用spring.config.import=optional:configserver:XXXXXXX来设置，configserver固定值.优先级：spring.config.import >spring.cloud.config.uri。  
              * 项目见spring-cloud-config-client
              
            * 基于Discovery Server/Client的应用。
              * 不需要引入spring-cloud-starter-client依赖，更改属性配置即可。
              * 项目见Spring-cloud-config-discovery-client。
        * 使用见spring-cloud-config-client项目。
   * 服务端如何将git服务文件改成本地配置文件？
        <br>在配置文件中指明 spring.profiles.active: native,默认搜索项目同级config目录下。如需改地址：需指定spring.cloud.config.server.native.search-locations属性。
        * 注意事项：本地一定要指明active：native，若以github环境运行，后续改成本地，不加该属性，代码会重置到之前版本。原因：读取了缓存，千万注意。
   * 应用端访问规则
        * /{application}/{profile}【/{label}】加上label 表示指定分支，浏览器访问可看全路径<br>  
        * /{application}-{profile}.yml【.properties】 支持本地和github，推荐，兼容本地和git,简单明了，不会有歧义 <br> 
        * /{label}/{application}-{profile}.yml【.properties】 <br>
        * **属性说明**
            * application: 服务端应用名，对应spring.application.name属性。
            * label: 分支名
            * profile: 文件名
   * 应用端config如何刷新
        * 手动刷新：基于spring-boot-start-actuator组件实现，该组件具有监控程序在运行时的状态，actuator含有多个监控点。其中refresh,用于刷新监控。详细说明见SpringBootBase项目。
            * 使用：
                * 在配置文件中声明 
                    * management:
                      * server:
                        * port: 8082 # 默认为服务的端口，可改成其他端口
                      * endpoints:
                      * health:
                          * show-details: always
                        * web:
                          * base-path: /actuator # 请求路径
                          * exposure:
                            * include: "*" # 默认只开发health端口，
                * 在具体类加上@RefreshScope注解
                * 检查开发的方法: /actuator
                * 调用refresh（POST请求）刷新 
                * 详细见spring-cloud-client
        * 自动刷新：基于SpringCloudBus(消息总件)组件实现，底层基于mq刷新
        * 注：本地配置中心若引入的为外部文件自动刷新，github需要配置webhooks。（可内网穿透工具实现）。若引入了eureka依赖可不用引入actuator组件。
  
##### SpringCloudFeign
   * 简介:模化的http应用模板，基于接口调用
   * 简单使用:
        * 在pom.xml引入依赖:spring-cloud-starter-openfeign
        * 在启动类上加入@EnableFeignClients注解
        * 在接口上加@FeignClient注解，声明服务的地址
        * 在方法上加RequestMapping类型的注解，声明请求地址
        * 在使用类的中类注入接口
        * 在类中的方法中调用接口
        * 项目见spring-cloud-eureka-consumer
    * 底层实现:
        * 当扫描到FeignClient注解会生成一个代理类
        * 根据声明规则，在底层解析出一个MethodHandler对象
        * 基于RequestBean动态生成request请求
        * 用Encoder将Bean包装成请求
        * 经过拦截器将请求和返回进行装饰处理
        * 记录日志
        * 用重试器发送http请求


##### SpringCloudRibbon
   * 简介:集成负载与请求于一身的组件，请求基于RestTemplate模板调用
   * 简单使用:
        * 请求
            * 引入eurekaClient即可（3以上的版本，自带了ribbon）
            * 向spring容器中注入RestTemplate对象
            * 在使用的类中注入RestTemplate对象
            * 调用RestTemplate.XXXXForObject方法
        * 负载
            * 在向容器注入的RestTemplate对象的方法上加上@LoadBalanced注解即可。默人复合规则
                * 可设置:轮询,随机，重试轮询，......,具体见官网
                * 注意事项:若想改规则，需向容器中注入对应的IRule，且需引入依赖
        * 项目见spring-cloud-eureka-consumer
        
        
 #### SpringCloudGateWay 替代 Zuul
   * 简介:可做为全局服务的入口。
   * 简单使用:
     * pom文件中引入spring-cloud-starter-gateway jar包
     * 在配置文件配置即可,配置内容如下:
        * routes:
           * - id: 随便取，id
           * uri: 转发的地址
           * predicates: # 规则
                * - Path=/spring-cloud
                
 #### SpringCloudHystrix
   * 简介:断路器，在SpringCloud中防止服务雪崩的主要手段之一。其有4种模式
     * 服务降级:当接口调用失败或超时就会调用fallback，立即返回信息给用户，避免阻塞。
     * 服务熔断:当某个接口调用的次数在某段时间内达到了设定的值或者达到错误率达到了某个值，才会走fallback方法
     * 服务隔离:可隔离服务之间的相互影响
     * 服务监控:可观察到某个服务在某断时间内的相关信息，如成功数量，失败数量，熔断数，服务请求频率丶超时等相关信息
   * 简单使用:
     * pom文件中引入hystrix的jar包
     * 启动类上加入@EnableHystrix注解
     * 调用的接口上加入@HystrixCommand注解，指定调用失败的方法。需要熔断的或者隔离的依旧再该注解中配置属性。
   * 注意事项:
     * 由于Hystrix组件不再维护，因此不推荐使用该组件，可用其他开源组件替代（如：Sentinel，Resilience4j）或者之间使用alibaba整套框架
   * 项目见:spring-cloud-eureka-consumer
 

    
                