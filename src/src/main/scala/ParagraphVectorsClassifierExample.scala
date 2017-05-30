/**
  * Created by dolorousrtur on 02.05.17.
  */


import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory

import collection.JavaConversions._
import java.io.{ByteArrayInputStream, FileNotFoundException}
import java.nio.charset.StandardCharsets
import java.util.List

import org.apache.commons.math3.ml.clustering.{DoublePoint, KMeansPlusPlusClusterer}
import org.datavec.api.util.ClassPathResource
import org.deeplearning4j.clustering.kmeans.KMeansClustering
import org.deeplearning4j.clustering.cluster.Point
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer
import org.deeplearning4j.text.documentiterator.{LabelledDocument, LabelsSource}
import org.deeplearning4j.text.sentenceiterator.interoperability.SentenceIteratorConverter
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareListSentenceIterator
import org.nd4j.linalg.cpu.nativecpu.NDArray;


case class Test(var opt: Option[Int])

object Main extends App {


  org.apache.log4j.BasicConfigurator.configure()

  val training = Seq("Abstract: Deep Bidirectional LSTM (DBLSTM) recurrent neural networks have recently been shown to give state-of-the-art performance on the TIMIT speech database. However, the results in that work relied on recurrent-neural-network-specific objective functions, which are",


    "Abstract: Several variants of the long short-term memory (LSTM) architecture for recurrent neural networks have been proposed since its inception in 1995. In recent years, thes$ networks have become the state-of-the-art models for a variety of machine learning ",


    "Long short-term memory (LSTM; Hochreiter & Schmidhuber, 1997) can solve numerous tasks not solvable by previous learning algorithms for recurrent neural networks (RNNs). We ide$tify a weakness of LSTM networks processing continual input streams that are not a ",


    "Abstract Neural networks have become increasingly popular for the task of language modeling. Whereas feed-forward networks only exploit a fixed context length to predict the ne$t word of a sequence, conceptually, standard recurrent neural networks can take into ",


    "Abstract The temporal distance between events conveys information essential for numerous sequential tasks such as motor control and rhythm detection. While Hidden Markov Models tend to ignore this information, recurrent neural networks (RNNs) can in principle learn to ",


    "Abstract: Previous work on learning regular languages from exemplary training sequences showed that long short-term memory (LSTM) outperforms traditional recurrent neural netwo$ks (RNNs). We demonstrate LSTMs superior performance on context-free language ",


    "This annual update reports on developments in the global HIV/AIDS epidemic and draws on the most recent data available to give global and regional estimates of its scope and hu$an toll. Despite promising developments in global efforts to address the AIDS epidemic, ",


    "In this paper, we present bidirectional Long Short Term Memory (LSTM) networks, and a modified, full gradient version of the LSTM learning algorithm. We evaluate Bidirectional $STM (BLSTM) and several other network architectures on the benchmark task of framewise ",


    "Objective: Cryptococcal meningitis is one of the most important HIV-related opportunistic infections, especially in the developing world. In order to help develop global strate$ies and priorities for prevention and treatment, it is important to estimate the burden of cryptococcal ",


    "Background: People with AIDS have heightened cancer risk from immunosuppression. HAART has been available since 1996 and has reduced AIDS-related mortality, but there are few large-scale studies on cancer trends. Methods: AIDS and cancer registries in 11 US ",


    "The introduction of highly active antiretroviral therapy (HAART) has produced a dramatic reduction in mortality among HIV-infected individuals [1–4]. Whereas the level of adherence to HAART is closely associated with suppression of the HIV viral load in plasma [5–14], a ",


    "Background: HIV-1 infection is characterized by chronic generalized CD8 and CD4 T cell hyperactivation, the biological effect of which is not understood. Objective: To study the relation between chronic immune activation and CD4 T cell depletion in HIV-1 infection. ",


    "Objectives: Efavirenz is an effective antiretroviral agent, but central nervous system side effects occur commonly, and population (racial) differences in pharmacokinetics and response have been reported. Efavirenz is metabolized by cytochrome P4502B6 (CYP2B6). ",


    "Wolters Kluwer Health may email you for journal alerts and information, but is committed to maintaining your privacy and will not share your personal information without your express consent. For more information, please refer to our Privacy Policy.   ... Skip Navigation Links Home > October 11,"
  )

  val source = new LabelsSource("DOC_")

  val delimeter = "{]"

  val labelled = training.map(t => source.nextLabel() + delimeter + t).mkString("\n")


  val trainingStream =  new ByteArrayInputStream(labelled.getBytes(StandardCharsets.UTF_8));

  val iterator = new LabelAwareListSentenceIterator(trainingStream, delimeter)

  println(iterator.nextSentence())

//  val labelled = training.map(abs => new LabelledDocument())

  val tokenizerFactory = new DefaultTokenizerFactory()
  tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor())


  val a = Some(3)



//  val labeledIterator = new SentenceIteratorConverter(iterator, source)

  val wvModel = WordVectorSerializer.readWord2VecModel("/home/dolorousrtur/Documents/Projects/scigraph/wordvectors_36K", true)





//  val resource = new ClassPathResource("/simple.pv")

  val paragraphVectors = new ParagraphVectors.Builder()
//      .useExistingWordVectors(wvModel)
    .learningRate(0.025)
    .minLearningRate(0.005)
    .batchSize(1000)
    .epochs(20)
    .lookupTable(wvModel.getLookupTable)
    .vocabCache(wvModel.getVocab)
    .trainWordVectors(true)
    .iterate(iterator)
    .tokenizerFactory(tokenizerFactory)
    .seed(57)
    .build



  paragraphVectors.fit()

  println(paragraphVectors.inferVector(training.get(0)))






//  paragraphVectors.getWordVector("DOC_1").foreach(d => {println(d + " ")})
  println(paragraphVectors.similarity("DOC_0", "DOC_1"))
  println(paragraphVectors.similarity("DOC_0", "DOC_8"))
//
//  println(paragraphVectors.inferVector("contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology contrast HIV pathology"))
//  paragraphVectors.getWordVector("contrast").foreach(println)

//  println(123)
//
//  val vectors = training.map(paragraphVectors.inferVector(_))
//
//  vectors.foreach(println)
//
//  println(123)
//
  val vectors = training.indices map {i => paragraphVectors.getWordVector(paragraphVectors.getLabelsSource.getLabels.get(i))}

  println(vectors)

  val maxIterationCount = 5
  val clusterCount = 2
  val distanceFunction = "cosinesimilarity"
  val kmc = KMeansClustering.setup(clusterCount, maxIterationCount, distanceFunction)

  val arrays = vectors map {v => new NDArray(Array(v)) }
  val points = Point.toPoints(arrays)


  val kmpp = new KMeansPlusPlusClusterer[DoublePoint](2)
  val clpoints = vectors.map(p => new DoublePoint(p))
  println("\n\n")
  val centroidClusters = kmpp.cluster(clpoints)

  centroidClusters(0).getPoints

  val cs = kmc.applyTo(points)

//  val clusterMarks = cs.classifyPoints(points)

  val clusters = cs.getClusters

  println(clusters.length)

  for ((p, i) <- points.zipWithIndex) {
    println(i, p.getId)
  }

  println

  for (cl <- clusters) {
    println(cl.getPoints.length)
    cl.getPoints.map(_.getId).foreach(println)
    println(cl.getId)
  }
//
//  val vectorizer = new TfidfVectorizer()
//
//  vectorizer.fit()



//  vectorizer.
}