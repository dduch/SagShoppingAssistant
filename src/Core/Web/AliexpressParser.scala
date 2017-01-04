package Core.Web

/**
  * Created by Dawid Dominiak on 2016-12-17.
  */
class AliexpressParser extends WebParser {

  // For given name of product return list of webpage links to this product
  def parseResultsSite(phrase: String): List[String] = {

    var productsLinks = List[String]()

    try {
      val doc = new WebDownloader().get(OnlineShops.ALIEXPRESS_SEARCH_URL + phrase)
      val searchResults = doc.getElementById("hs-list-items")
      val items = searchResults.getElementsByClass("item")

      // Get no more than a maxLinkNumber first links to products
      for (i <- 0 until WebParser.maxLinkNumber) {
        // Catch error when there are fewer links
        try {
          val productlink: String = items.get(i).select("a").attr("href")
          if (productlink != null && !(productlink.contains("http") || productlink.contains("https"))) {
            productsLinks ::= OnlineShops.DEFAULT_WEB_PROTOCOL + productlink
          }
          else {
            productsLinks ::= productlink
          }
        } catch {
          case e: java.lang.IndexOutOfBoundsException => Unit
        }
      }
    } catch {
      case e: java.lang.NullPointerException => println("Aliexpress connection problem")
    }

    productsLinks
  }
}