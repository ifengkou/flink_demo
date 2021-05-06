package cn.ifengkou.wc;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class StreamWordCount {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置并行度
        //env.setParallelism(1);
        //从文件中读取数据
        //String inputPath = "H:\\workspace\\flink_demo\\src\\main\\resources\\hello.txt";
        //DataStream<String> inputDataStream = env.readTextFile(inputPath);

        // 用parameter tool 工具从程序启动参数中提取配置项
        ParameterTool tool = ParameterTool.fromArgs(args);

        // 从socket 文本流监听数据
        DataStream<String> inputDataStream = env.socketTextStream(tool.get("host"),tool.getInt("port"));


        DataStream<Tuple2<String, Integer>> resultStream = inputDataStream.flatMap(new WordCount.MyFlatMapper())
                .keyBy(0)
                .sum(1);


        resultStream.print();
        env.execute();
    }
}
