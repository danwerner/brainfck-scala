package org.segfaulted.brainfck.utils

import org.segfaulted.brainfck.ast._

object Debug {

  val FormatIndent = 2

  private def formatInstruction(depth: Int, instruction: Instruction): String = {
    val prefix = " " * (FormatIndent * depth)
    val out = instruction match {
      case Comment(text) => "# " + text
      case Loop(body) => "Loop\n" + formatInstructions(depth + 1, body)
      case inst => inst.toString
    }
    prefix + out
  }

  private def formatInstructions(depth: Int, instructions: Seq[Instruction]): String = {
    instructions.map(formatInstruction(depth, _)).mkString("\n")
  }

  def formatAst(ast: Seq[Instruction]): String = formatInstructions(0, ast)

  def printAst(ast: Seq[Instruction]): Unit = println(formatAst(ast))
}
