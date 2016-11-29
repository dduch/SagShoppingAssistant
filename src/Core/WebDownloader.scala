package Core

import java.net.{URL, HttpURLConnection}

/**
  * Created by Dawid Dominiak on 2016-11-17.
  */
class WebDownloader {
  def get(url: String,
          connectTimeout: Int = 25000,
          readTimeout: Int = 25000,
          requestMethod: String = "GET") =
  {
    val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)
    val inputStream = connection.getInputStream
    val content = scala.io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close
    content
  }
}
