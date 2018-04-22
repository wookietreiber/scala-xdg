package org.freedesktop.basedir

/** $Info
  *
  * @define TypeInfo runtime
  */
object runtime extends Basedir {

  private[basedir] def home: String =
    sys.env.getOrElse("XDG_RUNTIME_DIR", s"/run/user/${sys.env("UID")}")

  def locate(path: String*): Option[String] =
    List(home/path) collectFirst {
      case file if file.toPath.exists => file
    }

}
