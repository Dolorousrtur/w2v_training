/**
  * Created by dolorousrtur on 17.02.17.
  */

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.clustering.{BisectingKMeans, KMeans}
import org.apache.spark.ml.linalg.{Vector, Vectors}
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.rdd.RDD
//import org.apache.spark.implicits_


//object Main extends App {
//
//  val spark: SparkSession = SparkSession
//    .builder()
//    .appName("sciclustering").master("local")
//    .getOrCreate()
//
//  import spark.implicits._
//
//  val training = Seq(
//    "a b c d e spark",
//    "b d",
//    "spark f g h",
//    "hadoop mapreduce",
//    "need more strings",
//    "more more more",
//    "strings are not real"
//  ).toDF("text")
//
//  //val rows = dataset.map{x => Row(x:_*)}
//
//  //val rdd = spark.cre//makeRDD[RDD](rows)
//
//  //val training = spark.createDataFrame(Seq()).toDF("id", "text")
//
//  val pipeLine: Pipeline = {
//    val tokenizer = new Tokenizer()
//      .setInputCol("text")
//      .setOutputCol("words")
//
//    val vectorizer = new HashingTF()
//      .setInputCol("words")
//      .setOutputCol("features")
//
//
//
//    val pipeline = new Pipeline()
//      .setStages(Array(tokenizer, vectorizer))
//
//    pipeline
//  }
//
////  println(pipeLine.fit(training).transform(training).select("features").rdd.map(_(0).asInstanceOf[Int]).collect().toList)
//  val features = pipeLine.fit(training).transform(training).select("features")
//
//  val kmeans = new BisectingKMeans()
//    .setK(2).setSeed(1L)
//    .setFeaturesCol("features")
//    .setPredictionCol("tag")
//
//  val model = kmeans.fit(features)
//
//  val preds = model.transform(features)
//
//  val numpreds = preds.collect().map(r => r.get(1).asInstanceOf[Int])
//
//  numpreds.foreach(println)
//
//// preds.collect().foreach(r => println(r.get(1)))
//
////  val cost = model.computeCost(features)
////  println(s"Within Set Sum of Squared Errors = $cost")
////
////  println("Cluster Centers: ")
////  val centers = model.clusterCenters
////  centers.foreach(println)
//
////  val spark: SparkSession = SparkSession
////    .builder()
////    .appName("sciclustering").master("local")
////    .getOrCreate()
////
////  // $example on$
////  // Loads data.
////  val dataset = spark.read.format("libsvm").load("/home/dolorousrtur/Documents/Projects/misc/scalapain_sbt/data.txt")
////
////  // Trains a bisecting k-means model.
////  val bkm = new BisectingKMeans().setK(6).setSeed(1)
////  val model = bkm.fit(dataset)
////
////  // Evaluate clustering.
////  val cost = model.computeCost(dataset)
////  println(s"Within Set Sum of Squared Errors = $cost")
////
////  // Shows the result.
////  println("Cluster Centers: ")
////  val centers = model.clusterCenters
////  centers.foreach(println)
//  // $example off$
//
////  println(model.transform(dataset).collect().foreach(println))
//  spark.stop()
//}
