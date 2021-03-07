package elsevier.hackday.coffeetime.resources

import cats.effect.{Blocker, ContextShift, Sync}
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.Router
import org.http4s.server.staticcontent.{FileService, fileService, resourceServiceBuilder}

object Routes {

  // TODO: Move to class

  def jokeRoutes[F[_] : Sync](/* J: Jokes[F]*/): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "joke" =>
        Ok("joke")
    }
  }

  def assetService[F[_] : Sync: ContextShift](blocker: Blocker): HttpRoutes[F] =
    resourceServiceBuilder("web", blocker).toRoutes

  def httpApp[F[_] : Sync: ContextShift](blocker: Blocker): HttpApp[F] =
    Router(
      "/api" -> jokeRoutes[F],
      "/" -> assetService(blocker)
    ).orNotFound
}