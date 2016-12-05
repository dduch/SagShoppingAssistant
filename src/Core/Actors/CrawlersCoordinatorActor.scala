package Core.Actors

import akka.actor._
import Core.Messages._

/**
  * Created by mbryk on 05.12.2016.
  */
class CrawlersCoordinatorActor extends Actor {

  private var expectedResults = 0
  private var userActor: ActorRef = null // This currently constraints number of user agents to 1
  private var results: List[(String,Int)] = List()

  val sites = List("amazon", "ebay")
  val siteCrawlers = sites.map(site => {
    (site, context.actorOf(Props[SiteCrawlerActor]))
  })

  val resultsPerSite = 4
  val maxResults = resultsPerSite * sites.length
  val finalResultsNumber = 3

  def receive = {
    case ParsedQuery(query) =>
      expectedResults = maxResults
      userActor = sender()
      siteCrawlers.foreach(it => {
        val (site, crawler) = it
        crawler ! CrawlSite(site, query, resultsPerSite)
      })

    case PageAnalysisComplete(pageUrl, accuracy) =>
      results = (pageUrl, accuracy) :: results

      if (results.length == maxResults) {
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
