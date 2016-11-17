package Nlp

import java.io.{FileInputStream, IOException, InputStream}

import opennlp.tools.namefind.{NameFinderME, TokenNameFinderModel}
import opennlp.tools.parser.Parse
import opennlp.tools.util.Span

/**
  * Created by Dawid Dominiak on 2016-11-05.
  */
class NamesExtractor(sentence : Array[String]) {
  val modelIn : InputStream = new FileInputStream("en-ner-person.bin")

  def extractNames : Array[String] ={
    try {
      val model : TokenNameFinderModel = new TokenNameFinderModel(modelIn)
      val nameFinder : NameFinderME = new NameFinderME(model)
      return Span.spansToStrings(nameFinder.find(sentence), sentence)
    }
    catch{
      case e: Exception => e.printStackTrace
        null
    }
    finally {
      if (modelIn != null) {
        try {
          modelIn.close();
        }
        catch{
          case e: Exception => e.printStackTrace
        }
      }
    }
  }
}
