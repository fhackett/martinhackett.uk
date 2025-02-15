package uk.martinhackett

import customtags.*
import scalatags.Text.tags
import scalatags.Text.tags2
import scalatags.Text

object index extends Target:
  def path: os.SubPath = os.sub / "index.html"

  def content: geny.Writable =
    wrapBody(
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
          h1(
            *.cls := "text-4xl",
            *.cls := "bg-white",
            *.cls := "font-bold",
            *.cls := "pb-6",
            "Martin Hackett"
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
              ("Live", "/live")
            )
              .map: (name, href) =>
                a(
                  *.href := href,
                  *.cls := "bg-white",
                  *.cls := "ml-4",
                  name
                )
          )
        )
      )
    )
