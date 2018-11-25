package net.petitviolet.example

import org.openjdk.jmh.annotations._

@State(Scope.Thread)
class ForBench {
  private val sentence = "In 2017 I would like to run ALL languages in one VM."
  private val answer = 7

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  def bench_upperCaseCount() = {
    upperCaseCount(sentence)
  }

  private def upperCaseCount(args: String) = {
    val start = System.nanoTime()
    val sentence = String.join(" ", args)
    require(sentence.filter(Character.isUpperCase).length == answer)
    val end = System.nanoTime()
    end - start
  }
}
