# SpringCloud注意事项
    * 注意SpringCloud版本和SpringBoot版本。
        https://spring.io/projects/spring-cloud
    * 注意配置文件的优先级：bootstrap.XXXX > application.xxxxx
    * 官网才是永远的神。   
    * eureka原数据文件：spring-cloud-netflix-eureka-client-XXXXX.jar\spring-configuration-metadata.json 
    
# SpringCloud组件学习及相关注意事项(基于 SpringCloud 2020.0.5 ,SpringBoot版本 2.5.X )
###### SpringCloudEureka:Cloud核心组件
   * 简介
        * 基于Netflix实现的服务注册中心。分为服务端和应用端，服务端为服务注册中心，应用端可向服务端注册自己的服务。
   * 工作原理
        * Eureka分为服务端和应用端，应用端又可分为2个角色：消费者和提供者，消费者(eureka.client.registerWithEureka:false)可不需要将自己的服务向服务中心注册，提供者(eureka.client.registerWithEureka:true)需向服务中心注册自己，
            * 消费者工作原理：
                * 启动时，拉去注册中心的服务信息，并生成一个注册表缓存到本地。
                * 在运行过程中，定时更新服务注册信息。
                * 通过http请求调用远程服务。
            * 提供者工作原理：
                * 启动时就会向注册中心发起register请求， 提供自己的一些元数据（IP 地址、端口，运行状况指示符 URL，主页等）向服务中心注册。
                * 在运行过程中，定时向注册中心发送renew心跳，证明“我还活着”
                * 停止服务提供者，向注册中心发起cancel请求，清空当前服务注册信息。
                 
###### SpringCloudConfig 
   * 简介 
        * 配置式管理服务，用于管理项目中所涉及的配置数据。配置中心可以位于github丶SVN或者本地。分为服务端丶应用端。

   * 工作原理？
        * cloud-config分为服务端和客户端，服务端用于拉去配置文件，并将读取到的配置文件信息在服务端做缓存。客户端主要获取服务端的配置，并将客户端的数据缓存
        即应用端->服务端->仓库。
        
   * 服务端如何将git服务文件改成本地配置文件？
        <br>在配置文件中指明 spring.profiles.active: native,默认搜索项目同级config目录下。如需改地址：需指定spring.cloud.config.server.native.search-locations属性。
        * 注意事项：本地一定指明active：native，若以github环境运行，后续改成本地，不加该属性，代码会重置到之前版本。原因：读取了缓存，千万注意。
   * 应用端访问规则？
        * /{application}/{profile}【/{label}】加上label 表示指定分支，浏览器访问可看全路径<br>  
        * /{application}-{profile}.yml【.properties】 支持本地和github，推荐，兼容本地和git,简单明了，不会有歧义 <br> 
        * /{label}/{application}-{profile}.yml【.properties】 <br>
        * **属性说明**
            * application: 服务端应用名，对应spring.application.name属性。
            * label: 分支名
            * profile: 文件名
   * 应用端config如何刷新？
        * 手动刷新：基于spring-boot-start-actuator组件实现，该组件具有监控程序在运行时的状态，actuator含有多个监控点。其中refresh,用于刷新监控。详细说明见SpringBootBase项目。
            使用：
        * 自动刷新：
        * 注：本地配置中心若引入的为外部文件自动刷新，github需要配置webhooks。（可内网穿透工具实现）。若引入了eureka依赖可不用引入actuator组件。
   * spring-cloud-config: 服务端配置说明见resources/application.yml
   * spring-cloud-config-client: 应用端配置说明见resources/bootstrap.yml
 

