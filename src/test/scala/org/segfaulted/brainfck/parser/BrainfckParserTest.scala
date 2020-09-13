package org.segfaulted.brainfck.parser

import org.segfaulted.brainfck.ast._
import org.specs2.mutable.Specification

class BrainfckParserTest extends Specification {
  "BrainfckParser" should {

    "parse a simple program" in {
      val ast = Seq(
        Loop(Seq(
          DecByte,
          IncPointer,
          IncByte,
          DecPointer
        ))
      )

      BrainfckParser.parseSource("[->+<]") must_== ast
    }

    "parse a simple multi-line program with comments" in {
      val source =
        """
          |[
          |  Begin loop
          |  ->
          |  +<
          |]
          |""".stripMargin

      val ast = Seq(
        Loop(Seq(
          Comment("Begin loop"),
          DecByte,
          IncPointer,
          IncByte,
          DecPointer
        ))
      )

      BrainfckParser.parseSource(source) must_== ast
    }
  }
}
