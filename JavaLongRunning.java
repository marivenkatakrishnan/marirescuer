package com.oracle.sss.job;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;


public class JavaLongRunning {

	public static void main(String[] args) throws Exception {
		int count = 0;
		int timeInterval = Integer.parseInt(args[2]);
		int totalRunningTime = Integer.parseInt(args[3]);
		int sleepDuration = totalRunningTime/timeInterval;
		SimpleDateFormat format = new SimpleDateFormat("d-M-y");
		SparkConf conf = new SparkConf().setAppName("CSV Reader").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);
		while(count < timeInterval) 
		{     


			JavaRDD<String> allRows = sc.textFile(args[0]);
			System.out.println(allRows.take(5));
			/*
			 * List<String> headers = Arrays.asList(allRows.take(1).get(0).split(","));
			 * 
			 * String field="VendorID";
			 * 
			 * JavaRDD<String>dataWithoutHeaders = allRows.filter(x ->
			 * !(x.split(",")[headers.indexOf(field)]).equals(field));
			 * 
			 * JavaRDD<Integer> VendorID = dataWithoutHeaders.map(x ->
			 * Integer.valueOf(x.split(",")[headers.indexOf(field)]));
			 * 
			 * for (Integer i:VendorID.collect()){ System.out.println(i); }
			 */
			
			allRows.saveAsTextFile(args[1] + format.format(Calendar.getInstance().getTime()) + "dir" + (count + 1));
		    	      
			count = count + 1;
			Thread.sleep(1000 * 60 * sleepDuration);
		}
		sc.stop();	
	}

}
