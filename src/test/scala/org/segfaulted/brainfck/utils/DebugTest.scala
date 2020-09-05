package org.segfaulted.brainfck.utils

import org.segfaulted.brainfck.ast._
import org.segfaulted.brainfck.utils.Debug._
import org.specs2.mutable.Specification

class DebugTest extends Specification {
  "formatAst" should {
    "correctly format a nested AST" in {
      val ast = Seq(
        Loop(Seq(
          Comment("Begin loop"),
          DecByte,
          IncPointer,
          IncByte,
          DecPointer
        ))
      )

      formatAst(ast) must_== """
        |Loop
        |  # Begin loop
        |  DecByte
        |  IncPointer
        |  IncByte
        |  DecPointer
        |""".stripMargin.trim
    }
  }
}
