package Core.Actors

import akka.actor._
import Core.Messages._
import Core.Web.OnlineShops

/**
  * Created by mbryk on 05.12.2016.
  */
class UserAgentActor(crawlerAddress: String) extends Actor {

  def parseQuery(query: String): String = {
    // ... do some nlp stuff
    val parsedQuery = query + "_parsed"
    parsedQuery
  }

  def receive = {
    case RawQuery(query) =>
      println("Start processing query: " + query)
      val parsedQuery = parseQuery(query)
      context.actorSelection(crawlerAddress) ! ParsedQuery(query)

    case QueryResults(results) =>
      // ... show results
      results.foreach(x => java.awt.Desktop.getDesktop().browse(java.net.URI.create(x)))

  }
}
