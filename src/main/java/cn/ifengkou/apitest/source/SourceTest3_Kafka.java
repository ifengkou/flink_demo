package cn.ifengkou.apitest.source;

import org.apache.commons.collections.map.HashedMap;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.internals.KafkaTopicPartition;

import java.util.Map;
import java.util.Properties;

public class SourceTest3_Kafka {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        env.setParallelism(1);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        //properties.setProperty("zookeeper.connect", "ark1.analysys.xyz:2181");
        // 下面这些次要参数
        properties.setProperty("group.id", "consumer-group");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("auto.offset.reset", "latest");

        /*Map<KafkaTopicPartition, Long> offsets = new HashedMap();
        offsets.put(new KafkaTopicPartition("sensor", 0), 34500L);*/


        FlinkKafkaConsumer consumer = new FlinkKafkaConsumer<String>("sensor",new SimpleStringSchema(),properties);
        //consumer.setStartFromSpecificOffsets(offsets);
        //consumer.setStartFromEarliest();
        DataStream<String> dataStream = env.addSource(consumer);

        dataStream.print();

        env.execute();
    }
}
