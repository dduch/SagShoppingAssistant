package Core.Actors

import akka.actor._
import Core.Messages._
import Core.Web._

/**
  * Created by mbryk on 05.12.2016.
  */

// Actor responsible for coordination of webpage crawlers and communication with main actor
class CrawlersCoordinatorActor extends Actor {

  // Parent actor
  private var userActor: ActorRef = _ // this currently constraints number of user agents to 1

  // Result of crawler's activity that is passed to parent actor
  private var results: List[(String,Int)] = List()

  // Site crawlers (actors)
  var siteCrawlers = List(context.actorOf(Props(new SiteCrawlerActor(new AmazonParser()))))
  siteCrawlers ::= (context.actorOf(Props(new SiteCrawlerActor(new AliexpressParser()))))
  siteCrawlers ::= (context.actorOf(Props(new SiteCrawlerActor(new BonanzaParser()))))
  siteCrawlers ::= (context.actorOf(Props(new SiteCrawlerActor(new EbayParser()))))
  siteCrawlers ::= (context.actorOf(Props(new SiteCrawlerActor(new OverstockParser()))))

  // Find 5 best results for each online shop
  val resultsPerSite = 5
  // In the end show 5 best results
  val finalResultsNumber = 5

  def receive = {

    // Receive parsed query, then pass it to each site crawler
    case ParsedQuery(query) =>
      userActor = sender()
      siteCrawlers.foreach(it => {
        val (crawler) = it
        crawler ! CrawlSite(query, resultsPerSite)
      })

    // Received when a crawler finishes its job
    case PageAnalysisComplete(pageUrl, accuracy) =>
      // Append new result (url and its accuracy score)
      results = (pageUrl, accuracy) :: results

      // Send best results to parent actor
      //TODO 10 is temporary number here, should be tuned later
      if (results.length == 10) {
        userActor ! QueryResults(
          results.sortWith((l,r) => {
            val (_,lAccuracy) = l
            val (_, rAccuracy) = r
            lAccuracy > rAccuracy
          }).map(x => {
            val (link,_) = x
            link
          }).take(finalResultsNumber)
        )
      }
  }
}