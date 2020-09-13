package org.segfaulted.brainfck.interpreter

import org.segfaulted.brainfck.ast._
import org.segfaulted.brainfck.parser.BrainfckParser

class BrainfckInterpreter {
  import BrainfckInterpreter._

  protected[interpreter] var dataPointer: Int = 0
  protected[interpreter] val memory: Array[Int] = Array.fill(MemorySize)(0)

  def run(input: String): Unit = {
    val ast = BrainfckParser.parseSource(input)
    run(ast)
  }

  def run(ast: Seq[Instruction]): Unit = execute(ast)

  protected[interpreter] def execute(instructions: Seq[Instruction]): Unit = {
    instructions.foreach(execute)
  }

  protected[interpreter] def execute(instruction: Instruction): Unit = instruction match {
    case IncPointer => dataPointer += 1
    case DecPointer => dataPointer -= 1
    // TODO: Try +=
    case IncByte => memory(dataPointer) = memory(dataPointer) + 1
    case DecByte => memory(dataPointer) = memory(dataPointer) - 1
    case InputByte => memory(dataPointer) = Console.in.read()
    case OutputByte => print(memory(dataPointer).toChar)
    case Comment(text: String) => ()
    case Loop(body: Seq[Instruction]) =>
      while (memory(dataPointer) != 0) {
        body.foreach(execute)
      }
  }
}

object BrainfckInterpreter {
  val MemorySize = 30_000

  def run(input: String): Unit = new BrainfckInterpreter().run(input)
}
