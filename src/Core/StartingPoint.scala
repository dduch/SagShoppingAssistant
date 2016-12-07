import akka.actor.{ActorSystem, Props}
import Nlp.NounsExtractor
import Core.Actors.{CrawlersCoordinatorActor, UserAgentActor}
import Core.Messages._
import Core.Web.{OnlineShops, WebDownloader}

// Starting point for application
object StartingPoint{

  def main(args: Array[String]) = {

    val actorSystem = ActorSystem()
    val crawlerCoordinatorActorName = "crawler-coordinator"
    val crawlerCoordinatorActor = actorSystem.actorOf(Props[CrawlersCoordinatorActor], name = crawlerCoordinatorActorName)
    val userAgentActor = actorSystem.actorOf(Props(new UserAgentActor("/user/" + crawlerCoordinatorActorName)))

    // Extract nouns from customer's input
    val input = scala.io.StdIn.readLine("What do you want to buy, Sir?\n")

    val nouns : List[String] = new NounsExtractor(input).extractNouns
    nouns.foreach(x => System.out.println(x))

    if (true) {
      userAgentActor ! RawQuery(nouns(0))
    }
    else {
      // Try to connect to shop's webpage
      try {
        val content = new WebDownloader().get(OnlineShops.BONANZA_SEARCH_URL + nouns.head)
        java.awt.Desktop.getDesktop().browse(java.net.URI.create(OnlineShops.BONANZA_BASE_URL + content))
        System.out.println(content)
      } catch {
        case e: Exception => e.printStackTrace
      }
    }
  }
}