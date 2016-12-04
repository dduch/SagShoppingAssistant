package Nlp.Test

import Nlp.NounsExtractor
import org.scalatest.FunSuite

/**
  * Created by Vitalik on 2016-12-04.
  */
class NounsExtractorTest extends FunSuite {

  test("testNouns1") {
    val nouns: List[String] = new NounsExtractor("I want to buy a cap").extractNouns
    assert(nouns(0) == "cap")
    assert(nouns.length == 1)
  }
  test("testNouns2"){
    val nouns : List[String] = new NounsExtractor("Please show me some telephones").extractNouns
    assert(nouns(0)=="telephone")
    assert(nouns.length == 1)
  }
}
