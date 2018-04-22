package org.freedesktop.basedir

/** $Info
  *
  * @define TypeInfo non-essential (cached) data
  */
object cache extends Basedir {

  private[basedir] def home: String =
    sys.env.getOrElse("XDG_CACHE_HOME", s"${sys.env("HOME")}/.cache")

  def locate(path: String*): Option[String] =
    List(home/path) collectFirst {
      case file if file.toPath.exists => file
    }

}
