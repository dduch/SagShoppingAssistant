package Nlp

import java.io.FileInputStream

import Core.Web.WebDownloader
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}

/**
  * Created by Vitalik on 2016-12-07.
  */

// Class responsible for analyzing the relevence of found webpage
class PageAnalyzer {
  def isPageRelevant(pageUrl: String, query: List[String]): Double = {
    // Init tokenizer
    val tokenizer = new TokenizerME(PageAnalyzer.model)

    // Get webpage and tokenize it
    val doc = new WebDownloader().get(pageUrl)
    var tokens  = Array[String]()
    tokens = tokenizer.tokenize(doc.text())

    // Count the number of occurrences of query at the webpage
    var score : Int = 0
    for(token <- tokens){
      for(word <- query)
        if(token.equalsIgnoreCase(word)){
          score += 1
      }
    }
    // Return the frequency of occurrences as a page relevancy score
    score/(tokens.length+1)
  }
}

object PageAnalyzer{
  // Create tokenizer model
  val english = new FileInputStream("en-token.bin")
  val model = new TokenizerModel(english)
}