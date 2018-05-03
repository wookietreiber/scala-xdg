# freedesktop.org for Scala

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d75c6a961304408fbf73fd37268b65b7)](https://www.codacy.com/app/wookietreiber/scala-xdg?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=wookietreiber/scala-xdg&amp;utm_campaign=Badge_Grade)

Libraries for Scala to access the standards defined by [freedesktop.org][fdo] (formerly *X Desktop Group*).

## Base Directory Specification

[![Latest version](https://index.scala-lang.org/wookietreiber/scala-xdg/scala-xdg-basedir/latest.svg)](https://index.scala-lang.org/wookietreiber/scala-xdg/scala-xdg-basedir)
[![Scaladoc](http://javadoc-badge.appspot.com/com.github.wookietreiber/scala-xdg-basedir_2.12.svg?label=scaladoc)](http://javadoc-badge.appspot.com/com.github.wookietreiber/scala-xdg-basedir_2.12)

The **basedir** sub-project contains an implementation of the [Base Directory Specification][basedir].

```scala
import org.freedesktop._

// returns path in user config directory
// ~/.config/git/config
val a: String = basedir.config("git", "config")

// creates and returns in user config directory
// ~/.config/app.conf
val b: String = basedir.config.create("app.conf")

// locates first existing in all (including global) base directories
// could be ~/.config/awesome/rc.lua
// or could be /etc/xdg/awesome/rc.lua if user does not have one
val c: Option[String] = basedir.config.locate("awesome", "rc.lua")

// same works with cache, data and runtime base directories
```


[fdo]: http://www.freedesktop.org/
[basedir]: http://standards.freedesktop.org/basedir-spec/basedir-spec-latest.html
