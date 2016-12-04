package Nlp

import java.io.FileInputStream
import opennlp.tools.cmdline.parser.ParserTool
import opennlp.tools.parser.{Parse, ParserFactory, ParserModel}

// Class responsible for noun extraction from customer's query (natural language)
class NounsExtractor(customersQuery : String) {

  // List of found nouns
  var nouns  = List[String]()

  // Method for extracting nouns from a query
  def extractNouns(): List[String] = {
    // Initialize English dictionary
    val english = new FileInputStream("en-parser-chunking.bin")

    // Parse a query
    try {
      val model = new ParserModel(english)
      val parser = ParserFactory.create(model)
      val topParses = ParserTool.parseLine(customersQuery, parser, 1)
      topParses foreach (x => getNouns(x))

      // Return nouns
      nouns
    }
    catch {
      case e: Exception => e.printStackTrace
        null
    }
  }

  // Get nouns for a parser
  def getNouns(p : Parse ) {
    //println(p.getType+" "+p.getCoveredText)

    // Check for a type corresponding to nouns
    if (p.getType == "NN" || p.getType == "NNS" || p.getType == "NNP" || p.getType == "NNPS"){
      // Append found noun to a list of nouns
      nouns ::= p.getCoveredText()
    }
    // Check the children
    p.getChildren() foreach ((child: Parse) => getNouns(child))
  }
}