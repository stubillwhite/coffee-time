package elsevier.hackday.coffeetime.services

import elsevier.hackday.coffeetime.BuildInfo

case class Version(major: Int, minor: Int, patch: Int)

class VersionService {
  val version: String = BuildInfo.version
}
