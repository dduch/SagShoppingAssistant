package Core.Web

/**
  * Created by Dawid Dominiak on 2016-12-17.
  */
class AmazonParser extends WebParser {

  // For given name of product return list of webpage links to this product
  def parseResultsSite (phrase: String) : List[String] = {

    var productsLinks  = List[String]()

    try {
      val doc = new WebDownloader().get(OnlineShops.AMAZON_SEARCH_URL + phrase)
      val searchResults = doc.getElementById("resultsCol")

      if(searchResults != null){
        val items = searchResults.getElementsByClass("s-item-container")

        if(items != null){
          // Get no more than a maxLinkNumber first links to products
          for(i <- 0 until WebParser.maxLinkNumber) {
            // Catch error when there are fewer links
            try {
              productsLinks ::= items.get(i).select("a").attr("href")
            } catch {
              case e: java.lang.IndexOutOfBoundsException => Unit
            }
          }
        }
      }
      else{
        println("Amazon: no results for phrase: " + phrase)
      }
    } catch {
      case e: java.lang.NullPointerException => println("Amazon connection problem")
    }
    productsLinks
  }
}
