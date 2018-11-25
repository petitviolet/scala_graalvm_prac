# Scala on GraalVM example

## Prepare

We should set `GRAALVM_HOME` environmental value.

```console
$ export GRAALVM_HOME=/path/to/graalvm
$ $GRAALVM_HOME/bin/java -version
openjdk version "1.8.0_192"
OpenJDK Runtime Environment (build 1.8.0_192-20181024123616.buildslave.jdk8u-src-tar--b12)
GraalVM 1.0.0-rc9 (build 25.192-b12-jvmci-0.49, mixed mode)
```

## Run Scala on GraalVM

### Run web app

Use [sbt-assembly](https://github.com/sbt/sbt-assembly) to generate fat-jar, and run it.

```console
$ sbt main/assembly
...
$ cd build
$ $GRAALVM_HOME/bin/java -jar main.jar
...
```

### Run micro-benchmark

Use [sbt-jmh](https://github.com/ktoso/sbt-jmh) to run benchmark.

```console
$ sbt -java-home $GRAALVM_HOME 'project main' 'jmh:run  -i 10 -wi 10 -f1 -t 1'
```

The code for benchmark is inspired by [GraalVM demos: Graal Performance Examples for Java](https://www.graalvm.org/docs/examples/java-performance-examples/).

## Run Scala with native-image

Use `native-image` to generate native image.

```console
$ sbt main/assembly
...
$ cd build
$ java -jar ./main.jar # spin up is too slow!
...
$ native-image -jar ./main.jar -H:+ReportUnsupportedElementsAtRuntime -H:IncludeResources=".*conf|.*xml" --verbose -H:Name=app
...
$ ./app # spin up faster
...
```

