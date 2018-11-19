package net.petitviolet.example

//import akka.event.Logging
//import akka.http.scaladsl.model.StatusCodes
//import akka.http.scaladsl.server.{ HttpApp, Route }

//class Application extends HttpApp {
//  override protected def routes: Route = path(Segment) { path =>
//    logRequest(s"path: $path", Logging.InfoLevel) {
//      complete((StatusCodes.OK, s"path: $path"))
//    }
//  }
//}
import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.server.blaze._
import org.slf4j.LoggerFactory

import scala.io.StdIn

class Application {
  private val logger = LoggerFactory.getLogger(getClass)

  def startServer(host: String, port: Int) = {
    val server = BlazeBuilder[IO]
      .bindHttp(port, host)
      .mountService(service, "/")
      .start
      .unsafeRunSync()

    logger.info("start server.")
    val _ = StdIn.readLine("press ENTER to stop server \n")
    logger.info("start shutting down server.")

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
  def main(args: Array[String]): Unit = {
    new Application().startServer("0.0.0.0", 8080)

  }
}
