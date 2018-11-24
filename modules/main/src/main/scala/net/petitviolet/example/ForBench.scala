package net.petitviolet.example

import org.openjdk.jmh.annotations._

import scala.util.Random

@State(Scope.Thread)
class ForBench {
  private lazy val a = Random.nextInt()
  private lazy val b = Random.nextInt()

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  def bench_add() = {
    add(a, b)
  }

  private def add(i: Int, j: Int): Int = i + j
}
