package Nlp

import java.io.{FileInputStream, InputStream}

import opennlp.tools.cmdline.parser.ParserTool
import opennlp.tools.parser.{Parse, Parser, ParserFactory, ParserModel}

/**
  * Created by Dawid Dominiak on 2016-11-05.
  */
class NounsExtractor(sentence : String) {
  var nounPhrases  = List[String]()

  def extractNouns(): List[String] ={

    val stream : InputStream = new FileInputStream("en-parser-chunking.bin")
    try{
      val model : ParserModel  = new ParserModel(stream)
      val parser : Parser  = ParserFactory.create(model)
      val topParses : Array[Parse] = ParserTool.parseLine(sentence, parser, 1)
      topParses.foreach(x => getNounPhrases(x))
      return nounPhrases
    }
    catch {
      case e: Exception => e.printStackTrace
        null
    }
  }

  def getNounPhrases(p : Parse ) {
    if (p.getType == "NN" || p.getType == "NNS" || p.getType == "NNP" || p.getType == "NNPS"){
      nounPhrases ::= p.getCoveredText()
    }
    p.getChildren().foreach((child : Parse) => getNounPhrases(child))
  }
}
