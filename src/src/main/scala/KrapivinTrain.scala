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


  val filePath: String = new ClassPathResource("Krapivin.txt").getFile.getAbsolutePath

  // Strip white space before and after for each line
  val iter: SentenceIterator = new BasicLineIterator(filePath)
  // Split on white spaces in the line to get words
  val t: TokenizerFactory = new DefaultTokenizerFactory

  /*
      CommonPreprocessor will apply the following regex to each token: [\d\.:,"'\(\)\[\]|/?!;]+
      So, effectively all numbers, punctuation symbols and some special symbols are stripped off.
      Additionally it forces lower case for all tokens.
  */
  t.setTokenPreProcessor(new CommonPreprocessor)

  val vec = new Word2Vec.Builder()
    .minWordFrequency(5)
    .iterations(1)
    .layerSize(100)
    .seed(42)
    .windowSize(5)
    .iterate(iter)
    .tokenizerFactory(t)
    .build

  vec.fit()

  println("After creation")

  vec.fit()
  println("After fit")

  WordVectorSerializer.writeWord2VecModel(vec, "wordvectors")


}