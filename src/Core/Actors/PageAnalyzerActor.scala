package Core.Actors

import akka.actor._
import Core.Messages._
import Nlp.PageAnalyzer

/**
  * Created by mbryk on 05.12.2016.
  */

// Actor responsible for analyzing if a product's webpage is relevant to a search query
class PageAnalyzerActor extends Actor {

  def receive = {
    // Analyze if a page is relevant to a query
    case  AnalyzePage(pageUrl, query) => {
      println("Analyzing "+pageUrl)
      val accuracy = new PageAnalyzer().isPageRelevant(pageUrl, query)
      sender ! PageAnalysisComplete(pageUrl, accuracy)
    }
  }
}
