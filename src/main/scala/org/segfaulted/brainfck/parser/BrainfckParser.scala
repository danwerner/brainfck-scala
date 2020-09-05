package org.segfaulted.brainfck.parser

import org.segfaulted.brainfck.ast._

import scala.util.parsing.combinator._

/**
 * https://en.wikipedia.org/wiki/Brainfuck
 */
class BrainfckParser extends RegexParsers {

  private val validChars = raw"><+-.,\[\]"

  /**
   * `>` increment the data pointer (to point to the next cell to the right).
   */
  def incDataPointer: Parser[IncPointer.type] = ">" ^^^ IncPointer

  /**
   * `<` decrement the data pointer (to point to the next cell to the left).
   */
  def decDataPointer: Parser[DecPointer.type] = "<" ^^^ DecPointer

  /**
   * `+` increment (increase by one) the byte at the data pointer.
   */
  def incByte: Parser[IncByte.type] = "+" ^^^ IncByte

  /**
   * `-`	decrement (decrease by one) the byte at the data pointer.
   */
  def decByte: Parser[DecByte.type] = "-" ^^^ DecByte

  /**
   * `.`	output the byte at the data pointer.
   */
  def outputByte: Parser[OutputByte.type] = "." ^^^ OutputByte

  /**
   * `,`	accept one byte of input, storing its value in the byte at the data pointer.
   */
  def inputByte: Parser[InputByte.type] = "," ^^^ InputByte

  /**
   * [	if the byte at the data pointer is zero, then instead of moving the instruction pointer
   *    forward to the next command, jump it forward to the command after the matching ] command.
   *
   * ]	if the byte at the data pointer is nonzero, then instead of moving the instruction pointer
   *    forward to the next command, jump it back to the command after the matching [ command.
   */
  def loop: Parser[Loop] = ("[" ~> rep(expr) <~ "]") ^^ Loop

  def comment: Parser[Comment] = ("[^" + validChars + "]+").r ^^ { s => Comment(s.trim) }

  def expr: Parser[Instruction] = incDataPointer | decDataPointer | incByte | decByte | outputByte | inputByte | loop | comment

  def program: Parser[Seq[Instruction]] = rep1(expr)
}

object BrainfckParser extends BrainfckParser {
  def parseSource(input: String): Seq[Instruction] = parse(program, input) match {
    case Success(result, _) => result
    case NoSuccess(msg, _) => throw new RuntimeException(msg)
  }
}