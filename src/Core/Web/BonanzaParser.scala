package Core.Web
import org.jsoup.nodes._
import org.jsoup.select._
import Core.Web.{OnlineShops}

/**
  * Created by Dawid Dominiak on 2016-12-07.
  */
class BonanzaParser extends WebParser {

  def parseResultsSite (phrase: String) : List[String] = {
    val doc = new WebDownloader().get(OnlineShops.BONANZA_SEARCH_URL + phrase)
    val searchResults = doc.getElementById("results_page-1")
    val items = searchResults.getElementsByClass("item_title")

    var productsLinks  = List[String]()
    productsLinks ::= OnlineShops.BONANZA_BASE_URL + items.first().select("a").attr("href")

    return productsLinks
  }
}
