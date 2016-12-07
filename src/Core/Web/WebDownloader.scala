package Core.Web

import net.ruippeixotog.scalascraper.browser.Browser
import org.jsoup.select.Elements
import org.jsoup.nodes.Element
/**
  * Created by Dawid Dominiak on 2016-11-17.
  */
class WebDownloader {
  def get(url: String,
          connectTimeout: Int = 5000,
          readTimeout: Int = 5000,
          requestMethod: String = "GET") : Element =
  {
    val browser = new Browser()
    val doc : Element = browser.get(url)
    return doc
  }
}
