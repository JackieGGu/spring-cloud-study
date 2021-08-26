#### apollo启动配置



##### 1. Meta Server配置

1. System Property方式

   ```
   -Dapollo.configService=http://config-service-url
   
   如：
   java -jar -Dapollo.configService=http://vm006:8080 app.jar
   ```

2. server.properties方式

   ```
   apollo.meta=http://config-service-url
   
   如：
   apollo.meta=http://vm006:8080
   ```

   

##### 2. 本地缓存目录配置

1. System Property方式

   ```
   -Dapollo.cacheDir=PATH
   
   如：
   java -jar -Dapollo.cacheDir=D:\Project\cache\apollo app.jar
   ```

2. server.properties方式

   ```
   apollo.cacheDir=PATH
   
   如：
   apollo.cacheDir=D:\Project\cache\apollo
   ```

   

##### 3. 客户端指定网卡IP

使用System Property方式

```
-Dhost.ip=IP

如：
java -jar -Dhost.ip=192.168.60.2 app.jar
```

