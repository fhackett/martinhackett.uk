package uk.martinhackett

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scalatags.Text.tags
import scalatags.Text.tags2
import scalatags.text.Builder
import scalatags.Text

import scala.collection.mutable

object customtags:
  object noStyles extends Text.Modifier:
    def applyTo(t: Builder): Unit = ()

  export Text.short.{a as _, p as _, hr as _, h2 as _, h3 as _, li as _, *}

  trait CustomTag:
    def tag: Text.Tag
    def styles: Modifier

    def apply(mods: Text.Modifier*): Text.Modifier =
      tag(styles, mods)

  object a extends CustomTag:
    val tag = tags.a
    val styles = List(
      *.cls := "no-underline",
      *.cls := "color-black",
      *.cls := "hover:font-bold"
    )

  object p extends CustomTag:
    val tag = tags.p
    val styles =
      *.cls := "mt-0"

  object h2 extends CustomTag:
    val tag = tags.h2
    val styles =
      *.cls := "mt-0"

  object h3 extends CustomTag:
    val tag = tags.h3
    val styles =
      modifier(
        *.cls := "mt-0",
        *.cls := "mb-2"
      )

  object li extends CustomTag:
    val tag = tags.li
    val styles = noStyles

  object hr extends CustomTag:
    val tag = tags.hr
    val styles = List(
      *.cls := "border-solid-1",
      *.cls := "border-gray-50"
    )

import customtags.*

trait Target:
  final def href: String = path.segments.mkString("/")
  def path: os.SubPath
  def content: geny.Writable

def wrapBody(content: Modifier*): geny.Writable =
  *.doctype("html"):
    html(
      *.lang := "en",
      head(
        meta(*.charset := "utf-8"),
        meta(
          *.name := "viewport",
          *.content := "width=device-width, initial-scale=1"
        ),
        link(*.rel := "icon", *.href := "/favicon.svg"),
        script(*.`type` := "module", *.src := "./main.js"),
        tags2.title("Martin Hackett")
      ),
      body(
        *.cls := "font-[LexendDeca]",
        *.cls := "bg-white",
        *.cls := "m-0",
        *.cls := "p-0",
        *.cls := "min-h-screen",
        content,
        footer(
          *.cls := "text-center",
          *.cls := "pb-3",
          *.cls := "bg-white",
          span(
            "© ",
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")),
            " Martin Hackett, Finn Hackett"
          )
        )
      )
    )

def wrapHeader(content: Modifier*): geny.Writable =
  wrapBody(
    *.cls := "flex",
    *.cls := "flex-col",
    content,
    tags.span(*.cls := "flex-grow")
  )

extension (ctx: StringContext)
  def frag(modifiers: Text.Modifier*): Text.Modifier =
    StringContext.checkLengths(modifiers, ctx.parts)

    val paragraphs =
      mutable.ArrayBuffer(mutable.ArrayBuffer.empty[Text.Modifier])

    enum InitMarker:
      case Part(part: String)
      case Line(line: String)

    ctx.parts.iterator
      .zip(modifiers.iterator.map(Some.apply) ++ Iterator.single(None))
      .flatMap:
        case ("", None) => Iterable.empty
        case ("", Some(mod)) =>
          Iterator.single((InitMarker.Part(""), Some(mod)))
        case (part, modOpt) =>
          val partLines = part.linesIterator.toSeq
          assert(partLines.nonEmpty, s"part with no lines? part was \"$part\"")
          if partLines.size == 1
          then Iterator.single(InitMarker.Part(partLines.head) -> modOpt)
          else
            Iterator.single(InitMarker.Part(partLines.head) -> None)
              ++ (partLines.view
                .drop(1)
                .dropRight(1)
                .map: line =>
                  InitMarker.Line(line.stripLeading()) -> None)
              ++ Iterator.single(
                InitMarker.Line(partLines.last.stripLeading()) -> modOpt
              )
      .foreach:
        case (InitMarker.Part(""), None) =>
        case (InitMarker.Part(part), modOpt) =>
          paragraphs.last += part
          modOpt.foreach(paragraphs.last += _)
        case (InitMarker.Line(""), modOpt) =>
          paragraphs += mutable.ArrayBuffer.empty
          modOpt.foreach(paragraphs.last += _)
        case (InitMarker.Line(line), modOpt) =>
          paragraphs.last += "\n"
          paragraphs.last += line
          modOpt.foreach(paragraphs.last += _)

    // FIXME: why does this still ouput empty paragraphs?
    paragraphs.iterator
      .filter(_.nonEmpty)
      .map(_.toSeq)
      .map(p(_*))
      .toSeq
