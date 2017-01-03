package Core.Web

/**
  * Created by Dawid Dominiak on 2016-12-17.
  */
class EbayParser extends WebParser {

  // For given name of product return list of webpage links to this product
  def parseResultsSite (phrase: String) : List[String] = {

  val doc = new WebDownloader().get(OnlineShops.EBAY_SEARCH_URL + phrase)
  val searchResults = doc.getElementById("GalleryViewInner")
  val items = searchResults.getElementsByClass("sresult")

  var productsLinks  = List[String]()

  // Get no more than a maxLinkNumber first links to products
  for(i <- 0 until WebParser.maxLinkNumber) {
  // Catch error when there are fewer links
  try {
  productsLinks ::= items.get(i).select("a").attr("href")
} catch {
  case e: java.lang.IndexOutOfBoundsException => Unit
}
}
  productsLinks
}
}