import akka.actor.{ActorSystem, Props}
import Core.Actors.{CrawlersCoordinatorActor, UserAgentActor}
import Core.Messages._
import Nlp.NounsExtractor
import Nlp.PageAnalyzer

// Starting point for application
object StartingPoint{
  val promts : List[String] = List("What do you want to buy, Sir?\n", "Do you want to buy something more, Sir?\n")
  def main(args: Array[String]) = {

    // Init static objects
    NounsExtractor
    PageAnalyzer

    //Create actor system
    val actorSystem = ActorSystem()

    var loop = 0
    while(true) {
      loop = loop + 1
      //Create main actor (userAgentActor) and crawler actor
      val crawlerCoordinatorActorName = "crawler-coordinator"+loop.toString
      val crawlerCoordinatorActor = actorSystem.actorOf(Props[CrawlersCoordinatorActor], name = crawlerCoordinatorActorName)
      val userAgentActor = actorSystem.actorOf(Props(new UserAgentActor("/user/" + crawlerCoordinatorActorName)))

      // Extract nouns from customer's input
      val input = scala.io.StdIn.readLine(if (loop==0) promts(0) else promts(1))

      // Start actor's role
      userAgentActor ! RawQuery(input)
    }
  }
}