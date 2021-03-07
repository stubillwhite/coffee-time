package elsevier.hackday.coffeetime.resources

import cats.effect._
import elsevier.hackday.coffeetime.services.VersionService
import org.http4s.server.blaze._
import org.http4s.server.middleware._

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext.global

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val portParameter = sys.env.getOrElse("PORT", null)

    val port = if (portParameter == null) 8080 else portParameter.toInt
    val host = if (portParameter == null) "localhost" else "0.0.0.0"

    val versionService = new VersionService()

    val blockingPool = Executors.newFixedThreadPool(4)
    val blocker = Blocker.liftExecutorService(blockingPool)
    val routes = new Routes[IO](blocker, versionService)

    BlazeServerBuilder[IO](global)
      .bindHttp(port, host)
      .withHttpApp(CORS(routes.httpApp))
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
} 