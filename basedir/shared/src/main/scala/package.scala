package org.freedesktop

import java.io.File.separator
import java.nio.file.attribute.PosixFilePermissions
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/** This package contains an implementation of the
  * [[http://standards.freedesktop.org/basedir-spec/basedir-spec-latest.html
  * XDG Base Directory Specification]].
  */
package object basedir {

  private[basedir] implicit class RichString(val base: String) extends AnyVal {
    def /(file: String): String =
      Paths.get(base, file).toString

    def /(path: Seq[String]): String =
      path.foldLeft(base)(_/_)

    def toPath: Path =
      Paths.get(base)
  }

  private[basedir] implicit class RichPath(val path: Path) extends AnyVal {
    def exists: Boolean =
      Files.exists(path)

    def mkdirs: Unit = {
      if (!path.exists) {
        val perms = PosixFilePermissions.fromString("rwx------")
        val attrs = PosixFilePermissions.asFileAttribute(perms)
        Files.createDirectories(path, attrs)
      }
    }

    def create: Unit = {
      if (!path.exists) {
        val parent = path.getParent
        if (!parent.exists) {
          Files.createDirectories(parent)
        }
        Files.createFile(path)
      }
    }
  }

  /** @define TypeInfo type
    * @define PathInfo path below base directory
    * @define Info Provides access to $TypeInfo files.
    */
  private[basedir] trait Basedir {
    private[basedir] def home: String

    /** Returns the path below the user-specific base directory.
      *
      * @param path $PathInfo
      *
      * @group location
      */
    def apply(path: String*): String =
      home/path

    /** Creates and returns the path below the user-specific base directory.
      *
      * @param path $PathInfo, the final argument is treated as a regular file
      *
      * @group location
      */
    def create(path: String*): String = {
      val file = apply(path: _*)

      home.toPath.mkdirs
      file.toPath.create

      file
    }

    /** Returns the first existing path below the base directories.
      *
      * @param path $PathInfo
      *
      * @group location
      */
    def locate(path: String*): Option[String]
  }

}
