package org.segfaulted.brainfck.interpreter

import org.segfaulted.brainfck.ast._
import org.segfaulted.brainfck.parser.BrainfckParser

class BrainfckInterpreter {
  import BrainfckInterpreter._

  protected var dataPointer: Int = 0
  protected val memory: Array[Int] = Array.fill(MemorySize)(0)

  def run(input: String): Unit = {
    val ast = BrainfckParser.parseSource(input)
    execute(ast)
  }

  protected def execute(instructions: Seq[Instruction]): Unit = {
    instructions.foreach(execute)
  }

  protected def execute(instruction: Instruction): Unit = instruction match {
    case IncDataPointer => dataPointer += 1
    case DecDataPointer => dataPointer -= 1
    // TODO: Try +=
    case IncByte => memory(dataPointer) = memory(dataPointer) + 1
    case DecByte => memory(dataPointer) = memory(dataPointer) - 1
    case InputByte => memory(dataPointer) = System.in.read()
    case OutputByte => print(memory(dataPointer).toChar)
    case Comment(text: String) => ()
    case Loop(body: Seq[Instruction]) => body.foreach(execute)
  }
}

object BrainfckInterpreter {
  val MemorySize = 30_000

  def run(input: String): Unit = new BrainfckInterpreter().run(input)
}
