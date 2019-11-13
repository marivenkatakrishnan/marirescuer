package com.oracle.sss.job;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Arrays;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;

public class OCIReadWriteLong {

	public static void main(String[] args) throws Exception {
		int count = 0;
		int timeInterval = Integer.parseInt(args[2]);
		int totalRunningTime = Integer.parseInt(args[3]);
		int sleepDuration = totalRunningTime/timeInterval;

		SparkSession spark  = SparkSession.builder().appName("Longevity Test").getOrCreate();
		//SparkConf conf = new SparkConf().setAppName("MyLoadCSVJavaExampleApp").setMaster("local[*]");
		 
			while(count < timeInterval) 
			{     
		        
		        SparkConf conf = new SparkConf().setAppName("CSV Reader").setMaster("local[*]");
		        JavaSparkContext sc = new JavaSparkContext(conf);
		        JavaRDD<String> allRows = sc.textFile("in/trip_yellow_taxi.data");
		        System.out.println(allRows.take(5));
		        List<String> headers = Arrays.asList(allRows.take(1).get(0).split(","));

		        String field="VendorID";

		        JavaRDD<String>dataWithoutHeaders = allRows.filter(x -> !(x.split(",")[headers.indexOf(field)]).equals(field));

		        JavaRDD<Integer> VendorID = dataWithoutHeaders.map(x -> Integer.valueOf(x.split(",")[headers.indexOf(field)]));

		        for (Integer i:VendorID.collect()){
		            System.out.println(i);
		        }
				
						Thread.sleep(1000 * 60 * sleepDuration);
			}
	}
}
