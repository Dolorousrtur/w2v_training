import dl4j_examples_scala.builds.Libs

name := "scalapain_sbt"

version := "1.0"

scalaVersion := "2.11.7"

lazy val commonResolvers = Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype release Repository" at "http://oss.sonatype.org/service/local/staging/deploy/maven2/",
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository/"
)

lazy val commonSettings = Seq(
  version := "1.0",
  scalaVersion := "2.11.7",
  classpathTypes += "maven-plugin",
  resolvers ++= commonResolvers
)


val sparkVersion="2.1.0"

// Spark Mllib
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion
)

libraryDependencies ++= Seq(
//    Libs.datavecDataCodec,
//    Libs.guava,
//    Libs.imageIOCore,
//    Libs.jfreeChart,
//    Libs.jcommon,
//    Libs.dl4jCore,
    Libs.dl4jNlp,
//    Libs.dl4jUi,
//    Libs.httpClient,
    Libs.nd4jNativePlatform
  )

//resolvers ++= Seq(
//  "JBoss Repository" at "http://repository.jboss.org/nexus/content/repositories/releases/",
//  "Spray Repository" at "http://repo.spray.cc/",
//  "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
//  "Akka Repository" at "http://repo.akka.io/releases/",
//  "Twitter4J Repository" at "http://twitter4j.org/maven2/",
//  "Apache HBase" at "https://repository.apache.org/content/repositories/releases",
//  "Twitter Maven Repo" at "http://maven.twttr.com/",
//  "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
//  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
//  "Second Typesafe repo" at "http://repo.typesafe.com/typesafe/maven-releases/",
//  "Mesosphere Public Repository" at "http://downloads.mesosphere.io/maven",
//  Resolver.sonatypeRepo("public")
//)