package org.freedesktop.basedir

/** $Info
  *
  * @define TypeInfo data
  * @define FileInfo data file
  */
object data extends Basedir {

  private[basedir] def home: String =
    sys.env.getOrElse("XDG_DATA_HOME", s"${sys.env("HOME")}/.local/share")

  private[basedir] def global: List[String] =
    sys.env.get("XDG_DATA_DIRS") map {
      _.split(":").toList
    } getOrElse {
      List("/usr/local/share", "/usr/share")
    }

  private[basedir] def dirs: List[String] =
    home :: global

  def locate(path: String*): Option[String] =
    dirs.map(dir => dir/path) collectFirst {
      case file if file.toPath.exists => file
    }

}
