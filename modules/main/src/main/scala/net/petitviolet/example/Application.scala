package net.petitviolet.example

import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.server.blaze._

import scala.io.StdIn

class Logger() {
  def info(msg: => String): Unit = println(msg)
}

class Application {
  private val logger = org.slf4j.LoggerFactory.getLogger(getClass)
//  private val logger = new Logger()

  def startServer(host: String, port: Int, isTimeTest: Boolean = false) = {
    val server = BlazeBuilder[IO]
      .bindHttp(port, host)
      .mountService(service, "/")
      .start
      .unsafeRunSync()

    logger.info("start server.")
    if (isTimeTest) {
      logger.info("start shutting down immediately.")
    } else {
      val _ = StdIn.readLine("press ENTER to stop server \n")
      logger.info("start shutting down server.")
    }

    server.shutdown.unsafeRunSync()

    logger.info("shutting down completed.")
  }

  private lazy val service = HttpService[IO] {
    case request @ GET -> Root / path ⇒
      val response = s"path: $path, req = $request"
      logger.info(response)
      Ok(response)
  }
}

object Main {
  case class HttpConfig(host: String, port: Int)
  def main(args: Array[String]): Unit = {
    val isTimeTest = args contains "TimeTest"
    @annotation.tailrec
    def options(input: List[String], config: HttpConfig): HttpConfig = {
      input match {
        case Nil                      => config
        case "--host" :: host :: tail => options(tail, config.copy(host = host))
        case "--port" :: port :: tail => options(tail, config.copy(port = port.toInt))
        case _ :: tail                => options(tail, config)
      }
    }
    val config = options(args.toList, HttpConfig("0.0.0.0", 8080))
    new Application().startServer(config.host, config.port, isTimeTest)
  }
}
