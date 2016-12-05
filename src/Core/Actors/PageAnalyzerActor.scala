package Core.Actors

import akka.actor._
import Core.Messages._

/**
  * Created by mbryk on 05.12.2016.
  */
class PageAnalyzerActor extends Actor {

  def parsePage(pageContent: String, query: String): Int = {
    // ... do some stuff
    scala.util.Random.nextInt(100)
  }

  def receive = {
    case  AnalyzePage(pageUrl, pageContent, query) => {
      val accuracy = parsePage(pageContent, query)
      println("Analyzing content of " + pageUrl)
      sender ! PageAnalysisComplete(pageUrl, accuracy)
    }
  }
}
