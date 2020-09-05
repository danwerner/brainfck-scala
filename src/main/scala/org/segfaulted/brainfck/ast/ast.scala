package org.segfaulted.brainfck.ast

sealed trait Instruction

case object IncPointer extends Instruction

case object DecPointer extends Instruction

case object IncByte extends Instruction

case object DecByte extends Instruction

case object InputByte extends Instruction

case object OutputByte extends Instruction

case class Comment(text: String) extends Instruction

case class Loop(body: Seq[Instruction]) extends Instruction
