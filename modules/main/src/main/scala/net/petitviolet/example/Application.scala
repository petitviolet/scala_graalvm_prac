package net.petitviolet.example

import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{ HttpApp, Route }

class Application extends HttpApp {
  override protected def routes: Route = path(Segment) { path =>
    logRequest(s"path: $path", Logging.InfoLevel) {
      complete((StatusCodes.OK, s"path: $path"))
    }
  }
}

object Main extends App {

  private val app = new Application()

  app.startServer("0.0.0.0", 8080)
}
