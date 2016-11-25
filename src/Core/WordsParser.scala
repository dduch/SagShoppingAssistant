import java.io.{FileInputStream, IOException, InputStream}

import Core.WebDownloader
import opennlp.tools.tokenize.{Tokenizer, TokenizerME, TokenizerModel}
import Nlp.NounsExtractor

/**
  * Created by Dawid Dominiak on 2016-11-05.
  *
  */


class WordsParser(input : String){

  def getSentenceTokens : Array[String] ={
    val stream : InputStream = new FileInputStream("en-token.bin")

    try {
      val model : TokenizerModel = new TokenizerModel(stream)
      val tokenizer : Tokenizer = new TokenizerME(model)
      return tokenizer.tokenize(input)
    }
    catch{
      case e: Exception => e.printStackTrace
        null
    }

    finally {
      if (stream != null) {
        try {
          stream.close()
        }
        catch{
          case e: Exception => e.printStackTrace
        }
      }
    }
  }
}

/*
object ShopAssistant {
  def main(args: Array[String]) = {
    val input = scala.io.StdIn.readLine("What do you want to buy?")

    /*val tokenized : Array[String] = new WordsParser(input).getSentenceTokens
    val names : Array[String] = new NamesExtractor(tokenized).extractNames

    tokenized.foreach(x => System.out.println(x))
    names.foreach(x => System.out.println(x))

    val nouns : List[String] = new NounsExtractor(input).extractNouns()
    nouns.foreach(x => System.out.println(x)) */

    try {
      val content = new WebDownloader().get("https://www.amazon.com/")
      System.out.println(content)
    } catch {
      case e: Exception => e.printStackTrace
    }
  }
}
*/