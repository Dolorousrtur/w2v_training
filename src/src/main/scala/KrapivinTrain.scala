/**
  * Created by dolorousrtur on 05.05.17.
  */

import java.io.File

import org.datavec.api.util.ClassPathResource
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer
import org.deeplearning4j.models.word2vec.{VocabWord, Word2Vec}
import org.deeplearning4j.text.documentiterator.FileDocumentIterator
import org.deeplearning4j.text.sentenceiterator.{BasicLineIterator, CollectionSentenceIterator, SentenceIterator}
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor
import org.deeplearning4j.text.tokenization.tokenizerfactory.{DefaultTokenizerFactory, TokenizerFactory}
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.deeplearning4j.text.sentenceiterator.FileSentenceIterator

import collection.JavaConversions._

object KrapivinTrain extends App{

  org.apache.log4j.BasicConfigurator.configure()


  val source = scala.io.Source.fromFile("/home/dolorousrtur/Documents/Projects/w2v_training/src/src/main/resources/Krapivin.json")


  val lines = source.getLines()
  val krapivin  = lines.mkString("[",",","]")

  var articles = parse(krapivin)

//  articles = parse(source.reader())


  val texts = articles.children.map(a => compact(render(a.children(0))))

  println(articles)


  val iter: SentenceIterator = new CollectionSentenceIterator(texts)
//  val iter2 = new FileIterator()

//  val resource = new ClassPathResource("texts_2")
//  val file = new File("texts_36K")
//  val iter2 = new FileSentenceIterator(file)




  val t: TokenizerFactory = new DefaultTokenizerFactory

  /*
      CommonPreprocessor will apply the following regex to each token: [\d\.:,"'\(\)\[\]|/?!;]+
      So, effectively all numbers, punctuation symbols and some special symbols are stripped off.
      Additionally it forces lower case for all tokens.
  */
  t.setTokenPreProcessor(new CommonPreprocessor)

  println("Before creation")

  val vec = new Word2Vec.Builder()
    .minWordFrequency(5)
    .iterations(20)
    .epochs(20)
    .layerSize(100)
    .seed(42)
    .windowSize(5)
    .iterate(iter)
    .tokenizerFactory(t)
    //    .lookupTable(new InMemoryLookupTable[VocabWord]())
    .build

  println("After creation")

  vec.fit()
  println("After fit")

  WordVectorSerializer.writeWord2VecModel(vec, "wordvectors")

//
//  println(compact(render(articles.children(0).children(0))))
//
//  val wv36K = WordVectorSerializer.readWord2VecModel("wordvectors_36K")
//
//  println(wv36K.vocab().numWords())

//  println(wv36K.similarWordsInVocabTo("exoplanet", 0.9))


}
