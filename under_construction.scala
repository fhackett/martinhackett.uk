package uk.martinhackett

import customtags.*
import scalatags.Text.tags
import scalatags.Text.tags2

class under_construction(val path: os.SubPath, name: String) extends Target:
  def content: geny.Writable =
    wrapHeader:
      tags2.main(
        *.cls := "container",
        *.cls := "mx-auto",
        *.cls := "flex",
        *.cls := "flex-col",
        *.cls := "items-center",
        h2(
          *.cls := "mt-4",
          *.cls := "text-2xl",
          s"Under construction: $name",
        ),
      )
