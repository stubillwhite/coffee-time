package elsevier.hackday.coffeetime.resources

import cats.effect.{Blocker, ContextShift, Sync}
import elsevier.hackday.coffeetime.services.VersionService
import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.Router
import org.http4s.server.staticcontent.resourceServiceBuilder

class Routes[F[_] : Sync: ContextShift](blocker: Blocker, versionService: VersionService) {

  private val apiRoutes: HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "version" =>
        Ok(versionService.version)
    }
  }

  private val staticContentRoutes: HttpRoutes[F] =
    resourceServiceBuilder("web", blocker).toRoutes

  val httpApp: HttpApp[F] =
    Router(
      "/api" -> apiRoutes,
      "/" -> staticContentRoutes
    ).orNotFound
}