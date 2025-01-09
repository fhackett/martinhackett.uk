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
          *.cls := "border-10",
          *.cls := "border-gray-400",
          *.cls := "border-solid",
          *.cls := "p-6",
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
              ("Sound", "/sound"),
              ("Image", "/image"),
              ("Text", "/text"),
              ("Live", "/live"),
            )
            .map: (name, href) =>
              a(
                *.href := href,
                *.cls := "bg-white",
                *.cls := "ml-4",
                name,
              ),
          ),
        ),
      ),
    )
