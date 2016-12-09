package Nlp

import java.io.FileInputStream

import Core.Web.WebDownloader
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}

/**
  * Created by Vitalik on 2016-12-07.
  */

// Class resposible for analyzing the relevence of found webpage
class PageAnalyzer {
  def isPageRelevant(pageUrl: String, query: String): Int = {

    // Initialuze tokenizer
    val tokenizer = new TokenizerME(PageAnalyzer.model)

    // Get webpage and tokenize it
    val doc = new WebDownloader().get(pageUrl)
    var tokens  = Array[String]()
    tokens = tokenizer.tokenize(doc.toString())

    // Count the number of occurences of query at the webpage
    var score : Int = 0
    for(token <- tokens){
      if(token.equalsIgnoreCase(query)){
        score += 1
      }
    }

    // Return the number of occurences as a page relevancy score
    score
  }
}

object PageAnalyzer{
  // Create tokenizer model
  val english = new FileInputStream("en-token.bin")
  val model = new TokenizerModel(english)
}

