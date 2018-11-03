# spring-boot-starter-schemacrawler

### 说明

> 基于 schemacrawler 整合Spring Boot 方便获取数据库元信息

1. [Schemacrawler 官方地址：http://www.schemacrawler.com/](http://www.schemacrawler.com/)
2. [Schemacrawler GitHub ：https://github.com/schemacrawler/SchemaCrawler](https://github.com/schemacrawler/SchemaCrawler)
3. 拷贝 Oracle-Drivers 的Oracle数据库驱动到你的Maven Repository 文件夹中（或者排除不需要的依赖）

### Maven

``` xml
<dependency>
	<groupId>com.github.vindell</groupId>
	<artifactId>spring-boot-starter-schemacrawler</artifactId>
	<version>1.0.3.RELEASE</version>
</dependency>
```