## Flume-elasticsearch-sink
---
基于github上另外一个名为 Flume-elasticsearch-sink 的项目进行改进，可以正确支持 ElasticSearch 5.5 的版本。
要最终运行，很关键的一点是，需要把以下jar拷贝到 flume/lib 下，否则运行时候缺少依赖包

compiler-0.9.3.jar
elasticsearch-5.5.1.jar
HdrHistogram-2.1.9.jar
hppc-0.7.1.jar
httpasyncclient-4.1.2.jar
httpcore-4.2.3.jar
httpcore-nio-4.4.5.jar	
jackson-core-2.8.1.jar
jackson-core-asl-1.9.3.jar
jackson-dataformat-cbor-2.8.1.jar
jackson-dataformat-smile-2.8.1.jar
jackson-dataformat-yaml-2.8.1.jar
jna-4.4.0.jar
lang-mustache-client-5.5.0.jar
log4j-api-2.8.2.jar
lucene-analyzers-common-6.6.0.jar
lucene-backward-codecs-6.6.0.jar
lucene-core-6.6.0.jar
lucene-grouping-6.6.0.jar
lucene-highlighter-6.6.0.jar
lucene-join-6.6.0.jar
lucene-memory-6.6.0.jar
lucene-misc-6.6.0.jar
lucene-queries-6.6.0.jar
lucene-queryparser-6.6.0.jar
lucene-sandbox-6.6.0.jar
lucene-spatial3d-6.6.0.jar
lucene-spatial-6.6.0.jar
lucene-spatial-extras-6.6.0.jar
lucene-suggest-6.6.0.jar
netty-3.5.12.Final.jar
netty-buffer-4.1.11.Final.jar
netty-codec-4.1.11.Final.jar
netty-codec-http-4.1.11.Final.jar
netty-common-4.1.11.Final.jar
netty-handler-4.1.11.Final.jar
netty-resolver-4.1.11.Final.jar
netty-transport-4.1.11.Final.jar
parent-join-client-5.5.0.jar
percolator-client-5.5.0.jar
reindex-client-5.5.0.jar
rest-5.5.0.jar
securesm-1.1.jar
snakeyaml-1.15.jar
t-digest-3.0.jar
transport-5.5.0.jar
transport-netty3-client-5.5.0.jar
transport-netty4-client-5.5.0.jar

以多线程的方式往ES导数，并实现正则解析

## 适应 ElasticSearch 5.5版本的flume sink组件

## 配置
---
| 配置项   |      默认值      |  含义 |
|----------|:-------------:|------:|
| threadNum   |  1 | 往ES中导数的线程数 |
| batchSize |    200   |   批量写的总数据条数，每个线程为batchSize/threadNum |
| categories | defaultCategory |    可配置多个category，空格分隔 |
| defaultCategory.regex | null |    正则解析串 |
| defaultCategory.fields | null |    解析出的对应字段名 |
| defaultCategory.timeField | null |    解析出的时间字段名 |
| defaultCategory.timeField | null |    解析出的时间字段格式 |
| defaultCategory.converFields | null |    需要转换格式的字段 |
| defaultCategory.converTypes | null |    转换后的格式 |
| defaultCategory.storeOrgLog | false |    是否需要保存解析前的原始日志数据 |


## demo
---
consumer.sinks.els.batchSize=2000
consumer.sinks.els.indexType=logs
consumer.sinks.els.clusterName=test_search
consumer.sinks.els.serializer=org.apache.flume.sink.elasticsearch.ElasticSearchLogStashRegexEventSerializer 
consumer.sinks.els.indexName=apache_access_log
consumer.sinks.els.threadNum=1
consumer.sinks.els.client=rest
consumer.sinks.els.hostNames=node1.server:9200
consumer.sinks.els.type=org.apache.flume.sink.elasticsearch.ElasticSearchSink

#--regex
consumer.sinks.els.serializer.categories=defaultCategory
consumer.sinks.els.serializer.defaultCategory.regex=^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \\"(.+?)\\" (\\d{3}) (\\d+) \\"([^\\"]+)\\" \\"([^\\"]+)\\"
consumer.sinks.els.serializer.defaultCategory.fields=log_ip,log_file,remote_addr,time_local,method,url,http_type,status,body_bytes_sent,http_referer,http_user_agent
\# consumer.sinks.els.serializer.defaultCategory.split=" "
consumer.sinks.els.serializer.defaultCategory.timeField=time_local
consumer.sinks.els.serializer.defaultCategory.timeFormat=dd/MMM/yyyy:HH:mm:ss Z
consumer.sinks.els.serializer.defaultCategory.converFields=status,body_bytes_sent
consumer.sinks.els.serializer.defaultCategory.converTypes=int,int
consumer.sinks.els.serializer.defaultCategory.storeOrgLog=false

