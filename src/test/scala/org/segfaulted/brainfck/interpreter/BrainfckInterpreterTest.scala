package org.segfaulted.brainfck.interpreter

import java.io.ByteArrayOutputStream

import org.segfaulted.brainfck.ast._
import org.specs2.execute.{AsResult, Result}
import org.specs2.mutable.Specification
import org.specs2.specification.ForEach

class BrainfckInterpreterTest extends Specification with ForEach[BrainfckInterpreter] {

  def foreach[R: AsResult](f: BrainfckInterpreter => R): Result = {
    AsResult(f(new BrainfckInterpreter))
  }

  "BrainfckInterpreter" >> {

    "should start in a clean state" in { in: BrainfckInterpreter =>
      in.dataPointer must_== 0
      in.memory must(haveLength(30000))
      in.memory.sum must_== 0
    }

    "execute(IncPointer)" should {
      "increment the data pointer to point to the next cell" in { in: BrainfckInterpreter =>
        in.execute(IncPointer)

        in.dataPointer must_== 1
      }

      "increment the data pointer multiple times when executed multiple times" in { in: BrainfckInterpreter =>
        in.execute(IncPointer)
        in.execute(IncPointer)
        in.execute(IncPointer)

        in.dataPointer must_== 3
      }
    }

    "execute(IncByte)" should {
      "increment the byte at the current data pointer" in { in: BrainfckInterpreter =>
        in.execute(IncPointer)
        in.execute(IncPointer)
        in.execute(IncByte)

        in.memory.take(4) must_== Array(0, 0, 1, 0)
      }

      "increment the byte multiple times when called multiple times" in { in: BrainfckInterpreter =>
        in.execute(IncPointer)
        in.execute(IncByte)
        in.execute(IncByte)
        in.execute(IncByte)

        in.memory.take(4) must_== Array(0, 3, 0, 0)
      }
    }

    "run(ast)" should {
      "execute a simple program that prints 'Hello World'" in { in: BrainfckInterpreter =>
        val out = new ByteArrayOutputStream
        Console.withOut(out) {
          BrainfckInterpreter.run("++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.")
        }

        out.toString must_== "Hello World!\n"
      }
    }
  }
}