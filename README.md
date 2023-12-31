# YSJTHQ
+ 一师讲堂后端第三次架构升级+代码重构

## 迭代版本链接 

+ 版本二 跳转仓库链接[github](https://github.com/firefox123456/yishijiangtang)
+ 版本一 跳转仓库链接[gitee](https://gitee.com/huangqi_lixin/online_education)
+ 前端代码 [gitee](https://gitee.com/huangqi_lixin/online-education-front)

## 项目启动(后台)

1. 拉取代码（等待依赖下载完成）
2. 下载Nacos环境,Sentinel Jar包，Redis环境(此处可以参考下载博客)
3. 按自己下载的工具环境，对项目进行配置生效
4. 启动后端项目（SpringBoot项目启动）

## 项目启动（前台）

1. 进入终端模式，分别进入前台和admin前台项目执行npm install下载依赖
2. 分别对两个项目执行npm run dev启动前台

如有需求，欢迎咨询（QQ(1793425543)，邮箱同号）。

## 技术选型

+ 使用Nacos充当服务中心+配置中心
+ 使用Gateway充当网关，并整合Sentinel在网关处对各个服务的外部流量进行限制，否则按照自定义限流规则返回。
+ 在各个微服务接口通过Sentinel对服务流量分别进行限制，防止内部调用过于频繁导致服务宕机。
+ 使用阿里云云效Maven仓库对Jar包进行管理，方便多人代码协作。
+ 使用Redis对数据进行冷热分离，缓存热点数据，提高系统QPS承受能力。
+ 使用AOP+logback生成本地日志记录，方便追踪处理线上问题，（支持ELK拓展）。
+ 线程池的使用，异步线程池在登录时发送登录邮件给用户。
+ RabbitMQ的使用，使用DirectExchange+Queue，实现用户异步退出，并发送邮件，把接口速度300ms提升到100ms。



