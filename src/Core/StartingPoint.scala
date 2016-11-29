import Core.WebDownloader
import akka.actor.{ActorSystem, Props}
import Nlp.NounsExtractor

object StartingPoint{

  def main(args: Array[String]) = {
    val input = scala.io.StdIn.readLine("What do you want to buy, Sir?\n")

    val nouns : List[String] = new NounsExtractor(input).extractNouns
    nouns.foreach(x => System.out.println(x))

    try {
      val content = new WebDownloader().get("https://www.amazon.com/")
      System.out.println(content)
    } catch {
      case e: Exception => e.printStackTrace
    }
  }
  //val system = ActorSystem()
}