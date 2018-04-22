package org.freedesktop.basedir

/** $Info
  *
  * @define TypeInfo configuration
  * @define FileInfo configuration file
  */
object config extends Basedir {

  private[basedir] def home: String =
    sys.env.getOrElse("XDG_CONFIG_HOME", s"${sys.env("HOME")}/.config")

  private[basedir] def global: List[String] =
    sys.env.get("XDG_CONFIG_DIRS") map {
      _.split(":").toList
    } getOrElse {
      List("/etc/xdg", "/etc")
    }

  private[basedir] def dirs: List[String] =
    home :: global

  def locate(path: String*): Option[String] =
    dirs.map(dir => dir/path) collectFirst {
      case file if file.toPath.exists => file
    }

}
