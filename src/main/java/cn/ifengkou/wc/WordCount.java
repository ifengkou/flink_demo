package cn.ifengkou.wc;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.util.Arrays;

public class WordCount {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //从文件中读取数据
        String inputPath = "H:\\workspace\\flink_demo\\src\\main\\resources\\hello.txt";
        DataSet<String> inputDataSet = env.readTextFile(inputPath);

        //对数据集进行处理，按空格分割，然后对单词进行统计

        DataSet<Tuple2<String, Integer>> resultSet = inputDataSet.flatMap(new MyFlatMapper())
                .groupBy(0)
                .sum(1);

        resultSet.print();


    }

    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String,Integer>>{

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> collector) throws Exception {
            String[] words = value.split(" ");
            Arrays.stream(words).forEach(word ->{
                collector.collect(new Tuple2<>(word, 1));
            });
        }
    }
}
