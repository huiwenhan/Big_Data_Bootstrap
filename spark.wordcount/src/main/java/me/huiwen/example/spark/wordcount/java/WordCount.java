package me.huiwen.example.spark.wordcount.java;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.*;
import org.apache.spark.SparkConf;
import scala.Tuple2;


public class WordCount {

	 public static void main(String[] args) {
		    JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("Spark Count"));
		    final int threshold = Integer.parseInt(args[1]);

		    // split each document into words
		    JavaRDD<String> tokenized = sc.textFile(args[0]).flatMap(
		      new FlatMapFunction<String, String>() {
		        public Iterable<String> call(String s) {
		          return Arrays.asList(s.split(" "));
		        }
		      }
		    );

		    // count the occurrence of each word
		    JavaPairRDD<String, Integer> counts = tokenized.map(
		      new PairFunction<String, String, Integer>() {
		        public Tuple2<String, Integer> call(String s) {
		          return new Tuple2(s, 1);
		        }
		      }
		    ).reduceByKey(
		      new Function2<Integer, Integer, Integer>() {
		        public Integer call(Integer i1, Integer i2) {
		          return i1 + i2;
		        }
		      }
		    );

		    // filter out words with less than threshold occurrences
		    JavaPairRDD<String, Integer> filtered = counts.filter(
		      new Function<Tuple2<String, Integer>, Boolean>() {
		        public Boolean call(Tuple2<String, Integer> tup) {
		          return tup._2 >= threshold;
		        }
		      }
		    );

		    // count characters
		    JavaPairRDD<Character, Integer> charCounts = filtered.flatMap(
		      new FlatMapFunction<Tuple2<String, Integer>, Character>() {
		        public Iterable<Character> call(Tuple2<String, Integer> s) {
		          ArrayList<Character> chars = new ArrayList<Character>(s._1.length());
		          for (char c : s._1.toCharArray()) {
		            chars.add(c);
		          }
		          return chars;
		        }
		      }
		    ).map(
		      new PairFunction<Character, Character, Integer>() {
		        public Tuple2<Character, Integer> call(Character c) {
		          return new Tuple2(c, 1);
		        }
		      }
		    ).reduceByKey(
		      new Function2<Integer, Integer, Integer>() {
		        public Integer call(Integer i1, Integer i2) {
		          return i1 + i2;
		        }
		      }
		    );

		    System.out.println(charCounts.collect());
		  }

}
