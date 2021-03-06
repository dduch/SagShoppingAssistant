package Core.Actors

import akka.actor._
import akka.routing.RoundRobinPool
import Core.Messages._
import Core.Web.WebParser

/**
  * Created by mbryk on 05.12.2016.
  */
class SiteCrawlerActor(parser : WebParser) extends Actor {

  // Number of page analyze actors
  val numberOfWorkers = 3

  // Router for page analyze actors
  val workerRouter = context.actorOf(
    Props[PageAnalyzerActor].withRouter(RoundRobinPool(numberOfWorkers))
  )

  def receive = {
    case CrawlSite(query, expectedResultsNumber) => {
      val searchQuery = query mkString " "
      // Parse the shop's webpage, extract links with matching query
      val links = parser.parseResultsSite(searchQuery)

      // Let the page analyzer actor analyze the product's webpage
      (links.slice(0,expectedResultsNumber)).foreach(link => {
        workerRouter forward AnalyzePage(link, query)
      })
    }
  }
}