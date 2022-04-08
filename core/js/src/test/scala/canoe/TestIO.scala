package canoe

import cats.effect.{ContextShift, IO, Timer}
import fs2.Stream

import org.scalajs.macrotaskexecutor.MacrotaskExecutor

object TestIO {

  implicit class IOStreamOps[A](stream: Stream[IO, A]) {
    def toList(): List[A] = stream.compile.toList.unsafeRunSync()

    def value(): A = toList().head

    def count(): Int = toList().size

    def run(): Unit = stream.compile.drain.unsafeRunSync()
  }

  implicit val globalContext: ContextShift[IO] = IO.contextShift(MacrotaskExecutor)
  implicit val globalTimer: Timer[IO] = IO.timer(MacrotaskExecutor)
}
