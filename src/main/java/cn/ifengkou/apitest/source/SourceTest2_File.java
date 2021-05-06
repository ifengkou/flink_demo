package cn.ifengkou.apitest.source;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SourceTest2_File {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);


        DataStream<String> stringDataStream = env.readTextFile("H:\\workspace\\flink_demo\\src\\main\\resources\\sensor.txt");

        stringDataStream.print();

        env.execute();
    }
}
