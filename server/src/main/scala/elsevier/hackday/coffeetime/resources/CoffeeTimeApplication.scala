package elsevier.hackday.coffeetime.resources

import cats.Applicative
import cats.data.Kleisli
import cats.effect._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.{HttpApp, HttpRoutes, Request, Response, StaticFile}
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._
import org.http4s.server.middleware._
import org.http4s.server.staticcontent.{FileService, ResourceService, fileService, resourceService, resourceServiceBuilder}

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.global
import elsevier.hackday.coffeetime.BuildInfo
import elsevier.hackday.coffeetime.resources.Routes.httpApp
import org.http4s.dsl.Http4sDsl

case class Version(major: Int, minor: Int, patch: Int)

object Main extends IOApp {

  private val blockingPool = Executors.newFixedThreadPool(4)
  private val blocker = Blocker.liftExecutorService(blockingPool)

  //  import Sync._
  //  private val assetService = fileService(FileService.Config("./assets", blocker))
  //
  //  import Async._
  //
  //  private val applicationService = HttpRoutes.of[IO] {
  //    case GET -> Root / "api" / "version" => {
  //      Ok(BuildInfo.version.asJson)
  //    }
  //    case request @ GET -> "web" /: rest => {
  //      static(rest.toString(), blocker, request)
  //    }
  //    case request @ GET -> "static" /: rest => {
  //      println(s"Loding ${rest}")
  //      static("static/" + rest.toString(), blocker, request)
  //    }
  ////    case request @ GET -> Root / "manifest.json" => {
  ////      static("manifest.json", blocker, request)
  ////    }
  //    case request @ GET -> Root / resource => {
  //      static(resource, blocker, request)
  //    }
  //    case request @ GET -> "static" /: resource => {
  //      static(s"static/${resource}", blocker, request)
  //    }
  //  }.orNotFound
  //
  //  def static(file: String, blocker: Blocker, request: Request[IO]) = {
  //    println(s"Loading static resource ${file}")
  //    StaticFile.fromResource(s"web/${file}", blocker, Some(request)).getOrElseF(NotFound())
  //  }

  def run(args: List[String]): IO[ExitCode] = {
    val portParameter = sys.env.getOrElse("PORT", null)

    val port = if (portParameter == null) 8080 else portParameter.toInt
    val host = if (portParameter == null) "localhost" else "0.0.0.0"

    BlazeServerBuilder[IO](global)
      .bindHttp(port, host)
      .withHttpApp(CORS(httpApp[IO](blocker)))
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}