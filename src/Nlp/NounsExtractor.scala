package Nlp

import java.io.FileInputStream
import opennlp.tools.cmdline.parser.ParserTool
import opennlp.tools.parser.{Parse, ParserFactory, ParserModel}
import opennlp.tools.lemmatizer.SimpleLemmatizer

// Class responsible for noun extraction from customer's query (natural language)
class NounsExtractor(customersQuery : String) {

  // List of found nouns
  var nouns  = List[String]()
  var adjectives = List[String]()

  // Method for extracting nouns from a query
  def extractNouns() : List[String] = {
    // Parse a query
    try {
      val parser = ParserFactory.create(NounsExtractor.model)
      val topParses = ParserTool.parseLine(customersQuery, parser, 1)
      topParses foreach (x => getNouns(x))

      // Return nouns
      nouns
    }
    catch {
      case e: Exception => e.printStackTrace()
        null
    }
  }

  // Get nouns for a parser
  def getNouns(p : Parse ) {
    //println(p.getType+" "+p.getCoveredText)

    // Check for a type corresponding to nouns
    if (p.getType == "NN" || p.getType == "NNS" || p.getType == "NNP" || p.getType == "NNPS"){
      // Append found noun in a single form to a list of nouns
      nouns ::= NounsExtractor.lemmatizer.lemmatize(p.getCoveredText, p.getType)
    }
    // Check the children
    p.getChildren foreach ((child: Parse) => getNouns(child))
  }

  // Method for extracting adjectives from a query
  def extractAdjectives() : List[String] = {
    // Parse a query
    try {
      val parser = ParserFactory.create(NounsExtractor.model)
      val topParses = ParserTool.parseLine(customersQuery, parser, 1)
      topParses foreach (x => getAdjectives(x))

      // Return adjectives
      adjectives
    }
    catch {
      case e: Exception => e.printStackTrace()
        null
    }
  }

  // Get adjectivess for a parser
  def getAdjectives(p : Parse ) {
    //println(p.getType+" "+p.getCoveredText)

    // Check for a type corresponding to adjectives
    if (p.getType == "JJ"){
      // Append found adjective in a single form to a list of adjectives
      adjectives ::= p.getCoveredText
    }
    // Check the children
    p.getChildren foreach ((child: Parse) => getAdjectives(child))
  }

}

object NounsExtractor{
  // Initialize English dictionary
  val english = new FileInputStream("en-parser-chunking.bin")
  val englishLemmatizer = new FileInputStream("en-lemmatizer.dict")
  val lemmatizer = new SimpleLemmatizer(englishLemmatizer)
  val model = new ParserModel(english)
}