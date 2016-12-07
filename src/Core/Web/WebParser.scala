package Core.Web

/**
  * Created by Dawid Dominiak on 2016-12-07.
  */
trait WebParser {
  def parseResultsSite (phrase: String) : List[String]
}
