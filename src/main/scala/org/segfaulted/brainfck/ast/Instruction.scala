package org.segfaulted.brainfck.ast

trait Instruction

object IncDataPointer extends Instruction
object DecDataPointer extends Instruction

object IncByte extends Instruction
object DecByte extends Instruction

object InputByte extends Instruction
object OutputByte extends Instruction

case class Comment(text: String) extends Instruction

case class Loop(body: Seq[Instruction]) extends Instruction