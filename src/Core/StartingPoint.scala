import akka.actor.{ActorSystem, Props}
import Core.Actors.{CrawlersCoordinatorActor, UserAgentActor}
import Core.Messages._
import Nlp.NounsExtractor
import Nlp.PageAnalyzer

// Starting point for application
object StartingPoint{

  def main(args: Array[String]) = {

    // Init static objects
    NounsExtractor
    PageAnalyzer

    //Create actor system
    val actorSystem = ActorSystem()

    //Create main actor (userAgentActor) and crawler actor
    val crawlerCoordinatorActorName = "crawler-coordinator"
    val crawlerCoordinatorActor = actorSystem.actorOf(Props[CrawlersCoordinatorActor], name = crawlerCoordinatorActorName)
    val userAgentActor = actorSystem.actorOf(Props(new UserAgentActor("/user/" + crawlerCoordinatorActorName)))

    // Extract nouns from customer's input
    val input = scala.io.StdIn.readLine("What do you want to buy, Sir?\n")

    // Start actor's role
    userAgentActor ! RawQuery(input)
  }
}