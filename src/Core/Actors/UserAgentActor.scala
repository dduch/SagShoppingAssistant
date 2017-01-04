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
  def parseQuery(query: String): List[String] = {
    val ne = new NounsExtractor(query)
    val nouns: List[String] = ne.extractNouns()
    val adjectives: List[String] = ne.extractAdjectives()
    val parsedQuery: List[String] = nouns.head :: adjectives
    parsedQuery
  }

  def receive = {
    // Receive raw query and send a parsed query to crawlers coordinator actor
    case RawQuery(query) =>
      val parsedQuery = parseQuery(query)
      context.actorSelection(crawlerAddress) ! ParsedQuery(parsedQuery)

    // Show results in a new browser's tabs
    case QueryResults(results) =>
      results.foreach(x => java.awt.Desktop.getDesktop.browse(java.net.URI.create(x)))

  }
}