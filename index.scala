package uk.martinhackett

import customtags.*
import scalatags.Text.tags
import scalatags.Text.tags2
import scalatags.Text

object index extends Target:
  def path: os.SubPath = os.sub / "index.html"

  // Armenian numerals 1-10, taken from https://en.wikipedia.org/wiki/Armenian_numerals
  val armenianNumerals: List[String] = List(
    "Ա", "Բ", "Գ", "Դ", "Ե", "Զ", "Է", "Ը", "Թ", "Ժ",
  )

  def content: geny.Writable =
    wrapBody(
      *.cls := "bg-[url(/martinhackett-main-bg.png)]",
      *.cls := "bg-fixed",
      *.cls := "bg-cover",
      *.cls := "flex",
      *.cls := "flex-col",
      tags2.main(
        *.cls := "flex-grow",
        *.cls := "container",
        *.cls := "mx-auto",
        *.cls := "grid",
        *.cls := "justify-center",
        *.cls := "content-center",
        div(
          h2(
            *.cls := "text-4xl",
            *.cls := "bg-white",
            *.cls := "font-bold",
            "Martin Hackett",
          ),
          div(
            *.cls := "grid",
            *.cls := "grid-cols-1",
            *.cls := "text-2xl",
            *.cls := "space-y-2",
            *.cls := "justify-items-start",
            List(
              ("Recordings", "/recordings"),
              ("Paintings", "/paintings"),
              ("Texts", "/texts"),
              ("Live Performance", "/live"),
            )
            .zip(armenianNumerals)
            .map(_ :* _)
            .map: (name, href, num) =>
              a(
                *.href := href,
                *.cls := "bg-white",
                *.cls := "ml-4",
                span(
                  *.cls := "inline-block",
                  *.cls := "w-1em",
                  *.cls := "pr-1",
                  num, ". ",
                ),
                name,
              ),
          ),
        ),
      ),
    )
