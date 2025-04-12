package site

import customtags.*
import scalatags.Text.tags
import scalatags.Text.tags2

object `404` extends Target:
  def path: os.SubPath = os.sub / "404.html"

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
          "[Not Found]",
        ),
        p(
          "I'm not sure how you got here...",
        ),
      )
