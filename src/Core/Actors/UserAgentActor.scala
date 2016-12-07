package Core.Actors

import akka.actor._
import Core.Messages._
import Nlp.NounsExtractor

/**
  * Created by mbryk on 05.12.2016.
  */

// Main, top-level actor of a system, that is responsible for input parsing and results showing
class UserAgentActor(crawlerAddress: String) extends Actor {

  // Parse raw user input into product's name
  def parseQuery(query: String): String = {
    val nouns: List[String] = new NounsExtractor(query).extractNouns
    //TODO what if more than one noun is in the query?
    val parsedQuery = nouns(0)
    parsedQuery
  }

  def receive = {
    // Receive raw query and send a parsed query to crawlers cordinator actor
    case RawQuery(query) =>
      val parsedQuery = parseQuery(query)
      context.actorSelection(crawlerAddress) ! ParsedQuery(parsedQuery)

    case QueryResults(results) =>
      // Show results in a new browser's tabs
      results.foreach(x => java.awt.Desktop.getDesktop().browse(java.net.URI.create(x)))

  }
}
