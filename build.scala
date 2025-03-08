//> using scala 3
//> using options -Werror -deprecation -feature -Yexplicit-nulls
//> using dep com.lihaoyi::os-lib:0.11.3
//> using dep com.lihaoyi::os-lib-watch:0.11.3
//> using dep com.lihaoyi::scalatags:0.13.1
package uk.martinhackett

import scala.util.Using
import java.io.Closeable

object dirs:
  val public = os.pwd / "public"
  val prebuild = os.pwd / "prebuild"

@main
def build(): Unit =
  val targets: List[Target] = List(
    index,
    `404`,
    live,
  )

  val publicFiles =
    os.walk(dirs.public)
      .view
      .map(_.relativeTo(dirs.public))
      .toSet

  if !os.exists(dirs.prebuild)
  then os.makeDir(dirs.prebuild)
  else
    val prebuildFiles =
      os.walk(dirs.prebuild)
        .view
        .filterNot(_.startsWith(dirs.prebuild / "node_modules"))
        .filter(_ != dirs.prebuild / "bun.lockb")
        .map(_.relativeTo(dirs.prebuild))

    val forCleanup = prebuildFiles
      .filterNot(publicFiles)
      .filterNot(targets.view.map(_.path).toSet)

    forCleanup
      .map(dirs.prebuild / _)
      .tapEach(path => println(s"cleaning up $path"))
      .foreach(os.remove.all)

  publicFiles.foreach: file =>
    val shouldTouch =
      !os.exists(dirs.prebuild / file)
        || (os
          .stat(dirs.public / file)
          .mtime
          .compareTo(os.stat(dirs.prebuild / file).mtime) > 0)

    if shouldTouch
    then
      println(s"updating ${dirs.prebuild / file}")
      os.copy.over(
        from = dirs.public / file,
        to = dirs.prebuild / file,
        createFolders = true,
      )

  os.proc("bun", "install").call(cwd = dirs.prebuild)

  targets.foreach: target =>
    println(s"regenerating ${dirs.prebuild / target.path}")
    os.write.over(dirs.prebuild / target.path, target.content)

@main
def dev(): Unit =
  def waitingMsg(): Unit =
    println(s"watching for changes in ${dirs.public}, Ctrl^C to end")

  build()
  waitingMsg()

  Using.resource(
    os.watch.watch(
      Seq(dirs.public),
      changes =>
        println(s"saw changes:")
        changes.foreach: path =>
          println(s"  $path")
        println("rebuilding...")
        build()
        waitingMsg(),
    ),
  ): _ =>
    // have a seat and let the other threads work
    Thread.currentThread().join()
