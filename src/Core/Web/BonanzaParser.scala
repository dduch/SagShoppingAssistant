package Core.Web

/**
  * Created by Dawid Dominiak on 2016-12-07.
  */

// Parser for Bonanza online shop
class BonanzaParser extends WebParser {

  // For given name of product return list of webpage links to this product
  def parseResultsSite (phrase: String) : List[String] = {

    var productsLinks  = List[String]()

    try {
      val doc = new WebDownloader().get(OnlineShops.BONANZA_SEARCH_URL + phrase)
      val searchResults = doc.getElementById("results_page-1")

      if(searchResults != null){
        val items = searchResults.getElementsByClass("item_title")
        if(items != null){
          // Get no more than a maxLinkNumber first links to products
          for(i <- 0 until WebParser.maxLinkNumber) {
            // Catch error when there are fewer links
            try {
              productsLinks ::= OnlineShops.BONANZA_BASE_URL + items.get(i).select("a").attr("href")
            } catch {
              case e: java.lang.IndexOutOfBoundsException => Unit
            }
          }
        }
      }
      else{
        println("Bonanza: no results for phrase: " + phrase)
      }
    } catch {
        case e: java.lang.NullPointerException => println("Bonanza connection problem")
    }

    productsLinks
  }
}