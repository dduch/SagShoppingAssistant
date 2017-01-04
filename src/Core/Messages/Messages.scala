package Core.Messages

/**
  * Created by mbryk on 05.12.2016.
  */
sealed trait CrawlerMessage
case class CrawlSite(query: List[String], expectedResultsNumber: Int) extends CrawlerMessage
case class AnalyzePage(pageUrl: String, query: List[String]) extends CrawlerMessage
case class PageAnalysisComplete(url: String, accuracy: Double) extends CrawlerMessage

sealed trait UserMessage
case class RawQuery(query: String) extends UserMessage
case class ParsedQuery(query: List[String]) extends UserMessage
case class QueryResults(foundUrls: List[String]) extends UserMessage


