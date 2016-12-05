package Core.Actors

import akka.actor._
import akka.routing.RoundRobinPool
import Core.Messages._

/**
  * Created by mbryk on 05.12.2016.
  */
class SiteCrawlerActor extends Actor {

  val numberOfWorkers = 3

  val workerRouter = context.actorOf(
    Props[PageAnalyzerActor].withRouter(RoundRobinPool(numberOfWorkers))
  )

  def receive = {
    case CrawlSite(siteSearchUrl, query, expectedResultsNumber) => {
      println("Crawling query: " + query + " on page: " + siteSearchUrl)
      // ... get search result page , extract links

      for (i <- 0 until expectedResultsNumber) {
        // ... add some delay to not ddos the site
        workerRouter forward AnalyzePage(siteSearchUrl + i, "", query)
      }
    }
  }
}
