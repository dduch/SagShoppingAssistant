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

    // Get no more than a hundred first links to products
    val maxLinkNumber = 100
    for(i <- 0 until maxLinkNumber) {
      // Catch error when there are fewer links
      try {
        productsLinks ::= OnlineShops.BONANZA_BASE_URL + items.get(i).select("a").attr("href")
      } catch {
        case e: java.lang.IndexOutOfBoundsException => Unit
      }
    }
    return productsLinks
  }
}
