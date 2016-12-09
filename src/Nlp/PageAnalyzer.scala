package Nlp

import java.io.FileInputStream

import Core.Web.WebDownloader
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}

/**
  * Created by Vitalik on 2016-12-07.
  */
class PageAnalyzer {
  def isPageRelevant(pageUrl: String, query: String): Int = {

    // Create tokenizer model
    val english = new FileInputStream("en-token.bin")
    val model = new TokenizerModel(english)
    val tokenizer = new TokenizerME(model)

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