package Core.Web

/**
  * Created by Dawid Dominiak on 2016-12-07.
  */

// Common trait for all classes responsible for searching a product in a given online shop
trait WebParser {
  def parseResultsSite (phrase: String) : List[String]
}
object WebParser {
  // Maximal number of links per one online shop
  val maxLinkNumber = 20
}
