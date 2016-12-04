import Core.WebDownloader
import akka.actor.{ActorSystem, Props}
import Nlp.NounsExtractor
import Core.OnlineShops

// Starting point for application
object StartingPoint{

  def main(args: Array[String]) = {

    // Extract nouns from customer's input
    val input = scala.io.StdIn.readLine("What do you want to buy, Sir?\n")
    val nouns : List[String] = new NounsExtractor(input).extractNouns
    nouns.foreach(x => System.out.println(x))

    // Try to connect to shop's webpage
    try {
      val content = new WebDownloader().get(OnlineShops.BONANZA_SEARCH_URL + nouns.head)
      java.awt.Desktop.getDesktop().browse(java.net.URI.create(OnlineShops.BONANZA_BASE_URL + content))
      System.out.println(content)
    } catch {
      case e: Exception => e.printStackTrace
    }
  }
  //val system = ActorSystem()
}