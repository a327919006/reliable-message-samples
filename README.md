# RMQ对接示例

[TOC]

## 介绍
RMQ对接示例，请先根据[RMQ项目依赖](https://www.showdoc.cc/rmq?page_id=1812484091467621 "RMQ项目依赖")，准备好RMQ运行环境。

## 示例源码仓库地址

|  源码仓库地址 |
| ------------ |
| https://gitee.com/NuLiing/reliable-message-samples  |
| https://github.com/a327919006/reliable-message-samples |

## 示例代码流程说明
示例代码模拟简单的充值业务，上层系统为支付系统，负责处理支付订单，下层系统为业务系统，负责处理充值订单以及操作账户金额。
```
1. 调用下层系统充值接口，生成充值订单与支付订单。
2. 模拟银行回调上层系统支付成功接口。上层系统调用RMQ预发送消息 --> 执行业务操作（修改支付订单状态）--> 异步调用RMQ确认发送消息。
3. 下层业务系统收到消息 --> 执行业务操作（修改支付订单状态，修改账户余额）--> 调用RMQ确认消费消息。
```

## 初始化示例代码数据库
下载项目源码并解压，执行 "数据库初始化SQL脚本"，正常情况下会自动创建数据库（**reliable-message-sample**）以及生成**3**张表。"数据库初始化SQL脚本" 路径为:
```
/reliable-message-samples/sql/rmq-sample-init.sql
```

## 运行示例代码
#### 运行RMQ
先根据RMQ中文文档《[快速入门](https://www.showdoc.cc/rmq?page_id=1815635527586509 "快速入门")》，运行RMQ系统。

------------

### 配置、运行示例代码
#### 配置
配置文件路径：
```
/reliable-message-samples/spring-boot-sample/src/main/resources/application.yaml
```
配置文件说明：
```yaml
# 运行端口
server:
  port: 10010

mybatis:
  typeAliasesPackage: com.cn.rmq.sample.model.po
  mapperLocations: classpath:com/cn/rmq/sample/mapper/*.xml

spring:
  # 数据库连接配置
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/reliable-message-sample?useUnicode=true&characterEncoding=utf-8
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
    hikari:
      connection-test-query: SELECT 1
  # ActiveMQ配置
  activemq:
    broker-url: tcp://127.0.0.1:61616
    user: admin
    password: admin

# Dubbo配置
dubbo:
  application:
    name: spring-boot-sample
    logger: slf4j
  registry:
    address: zookeeper://127.0.0.1:2181
  protocol:
    name: dubbo
    port: 20882
  scan:
    base-packages: com.cn.rmq.sample.service.impl
```

#### 运行
模块基于SpringBoot构建，可使用Maven命令(mvn package)打成jar包运行（java -jar）。
调试阶段可直接在IDE中运行ServiceApplication。文件路径为：
```
/reliable-message-samples/spring-boot-sample/src/main/java/com/cn/rmq/service/BootSampleApplication.java
```

------------

## 调用示例接口
示例代码集成了Swagger组件，方便接口调试。
```
访问地址： http://127.0.0.1:10010/swagger-ui.html
```

#### 生成订单
调用接口成功后将生成待支付的充值订单与支付订单。接口响应数据data为订单ID，用于下一步模拟支付成功回调。
![生成订单](https://www.showdoc.cc/server/api/common/visitfile/sign/dc4ca3b42f64b83021759835fd30beec?showdoc=.jpg "生成充值订单")

![生成订单响应](https://www.showdoc.cc/server/api/common/visitfile/sign/9025e2f453b387df842988bbc425fab2?showdoc=.jpg)

#### 支付回调
模拟银行支付成功回调，成功调用接口后可查看数据库数据，充值订单、支付订单状态改变成已支付，账户金额已增加。
![支付成功回调](https://www.showdoc.cc/server/api/common/visitfile/sign/f3daf005a81e3065ff1efb1b91449901?showdoc=.jpg "支付成功回调")

![支付回调接口响应](https://www.showdoc.cc/server/api/common/visitfile/sign/0d888eaa8937726cddea3919291903a3?showdoc=.jpg "支付回调接口响应")


