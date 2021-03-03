package elsevier.hackday.coffeetime.resources

import cats.effect._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.{HttpRoutes, Request, StaticFile}
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._

import org.http4s.server.middleware._
import org.http4s.server.staticcontent.{ResourceService, resourceService, resourceServiceBuilder}

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.global

import elsevier.hackday.coffeetime.BuildInfo

case class Version(major: Int, minor: Int, patch: Int)

object Main extends IOApp {

  private val blockingPool = Executors.newFixedThreadPool(4)
  private val blocker = Blocker.liftExecutorService(blockingPool)

  private val applicationService = HttpRoutes.of[IO] {
    case GET -> Root / "api" / "version" => {
      Ok(BuildInfo.version.asJson)
    }
    case request @ GET -> "web" /: rest => {
      static(rest.toString(), blocker, request)
    }
    case request @ GET -> "static" /: rest => {
      static("static/" + rest.toString(), blocker, request)
    }
    case request @ GET -> Root / "manifest.json" => {
      static("manifest.json", blocker, request)
    }
  }.orNotFound

  def static(file: String, blocker: Blocker, request: Request[IO]) =
    StaticFile.fromResource(s"web/${file}", blocker, Some(request)).getOrElseF(NotFound())

  def run(args: List[String]): IO[ExitCode] = {
    val portParameter = sys.env.getOrElse("PORT", null)

    val port = if (portParameter == null) 8080 else portParameter.toInt
    val host = if (portParameter == null) "localhost" else "0.0.0.0"

    BlazeServerBuilder[IO](global)
      .bindHttp(port, host)
      .withHttpApp(CORS(applicationService))
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}