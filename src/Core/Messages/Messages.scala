package Core.Messages

/**
  * Created by mbryk on 05.12.2016.
  */
sealed trait CrawlerMessage
case class CrawlSite(siteSearchUrl: String, query: String, expectedResultsNumber: Int) extends CrawlerMessage
case class AnalyzePage(pageUrl: String, pageContent: String, query: String) extends CrawlerMessage
case class PageAnalysisComplete(url: String, accuracy: Int) extends CrawlerMessage

sealed trait UserMessage
case class RawQuery(query: String) extends UserMessage
case class ParsedQuery(query: String) extends UserMessage
case class QueryResults(foundUrls: List[String]) extends UserMessage


