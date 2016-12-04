package Core

import java.net.{HttpURLConnection, URL}

import net.ruippeixotog.scalascraper.browser.Browser
import org.jsoup.select.Elements
/**
  * Created by Dawid Dominiak on 2016-11-17.
  */
class WebDownloader {
  def get(url: String,
          connectTimeout: Int = 5000,
          readTimeout: Int = 5000,
          requestMethod: String = "GET") : String =
  {
    /*val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)
    val inputStream = connection.getInputStream
    val content = scala.io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close
    content*/
    val browser = new Browser()
    val doc = browser.get(url)
    val searchResults = doc.getElementById("results_page-1")
    val items : Elements = searchResults.getElementsByClass("item_title")

    return items.first().select("a").attr("href").toString()
  }
}
