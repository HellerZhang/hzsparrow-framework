# hzsparrow-spring-boot

#### 介绍
基于SpringBoot的hzsparrow工具，麻雀虽小五脏俱全。致力于打造解决实际开发中遇到的大量简单问题，给出较为通用的解决方式，以及提供多种可以方便开发的工具类。用最少的代码解决开发问题是我们的最高最求。

#### 软件架构
hzsparrow-spring-boot基于spring-boot2.1.9版本，想提升版本的同学，可自行在hzsparrow-framework-parent项目中修改版本依赖。


#### 安装教程
目前hzsparrow-spring-boot发布了SNAPSHOT版本到一个Maven私服，需要的可以私信我。
自己比较懂Maven的，可自行打包。

#### 使用说明
在项目的pom文件中引入hzsparrow-framework-spring-boot-starter即可
    <groupId>com.hzsparrow.framework</groupId>
        <artifactId>hzsparrow-framework-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
目前支持
阿里大于sms，在项目配置文件中配置阿里大于相关配置
hzsparrow:
  aliyunsms:
    access-key-id: 
    access-secret: 
文件上传下载工具
hzsparrow:
  fileupload:
    serverType: local
    local:
      rootPath:
      secondPath: 
具体配置可自行查看配置描述
#### 参与贡献
    1、

