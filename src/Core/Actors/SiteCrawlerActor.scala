package Core.Actors

import akka.actor._
import akka.routing.RoundRobinPool
import Core.Messages._
import Core.Web.WebParser

/**
  * Created by mbryk on 05.12.2016.
  */
class SiteCrawlerActor(parser : WebParser) extends Actor {

  val numberOfWorkers = 3

  val workerRouter = context.actorOf(
    Props[PageAnalyzerActor].withRouter(RoundRobinPool(numberOfWorkers))
  )

  def receive = {
    case CrawlSite(siteSearchUrl, query, expectedResultsNumber) => {
      println("Crawling query: " + query + " on page: " + siteSearchUrl)
      // ... get search result page , extract links
      val links = parser.parseResultsSite(siteSearchUrl)
      links.foreach(link => {
        // ... add some delay to not ddos the site
        workerRouter forward AnalyzePage(link, "", query)
      })
    }
  }
}
