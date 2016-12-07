package Core.Web

/**
  * Created by Dawid Dominiak on 2016-12-07.
  */

// Parser for Bonanza online shop
class BonanzaParser extends WebParser {

  // For given name of product return list of webpage links to this product
  def parseResultsSite (phrase: String) : List[String] = {

    val doc = new WebDownloader().get(OnlineShops.BONANZA_SEARCH_URL + phrase)
    val searchResults = doc.getElementById("results_page-1")
    val items = searchResults.getElementsByClass("item_title")

    var productsLinks  = List[String]()
    //TODO only one link is returned, should be more
    productsLinks ::= OnlineShops.BONANZA_BASE_URL + items.first().select("a").attr("href")

    return productsLinks
  }
}
