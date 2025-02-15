package uk.martinhackett

import customtags.*
import scalatags.Text.tags
import scalatags.Text.tags2
import scalatags.Text

object live extends Target:
  def path: os.SubPath = os.sub / "live.html"

  final case class Video(
      title: String,
      embedLink: String,
      description: Modifier,
      credits: String
  )

  val videos: List[Video] = List(
    Video(
      title = "Hundred Years Gallery: Durrant, Hackett, Lash",
      embedLink =
        "https://www.youtube.com/embed/pFUowcDN2Lw?si=9vMP10rFS6MJ5kvT",
      description = frag"""
        Concert at Hundred Years Gallery. Hoxton, London, July 2023.
        Trio Phil Durrant, octave mandola; Martin Hackett, Korg MS10; Dominic Lash, guitar.
        The three possible solos and duos were followed by the full trio.
        This video shows Martin Hackett solo followed by MH in duo with Phil Durrant.
        Dominic Lash looks on.
      """,
      credits = "iPhone video by Kaddy Beck"
    )
  )

  def content: geny.Writable =
    wrapBody(
      *.cls := "flex",
      *.cls := "flex-col",
      tags2.main(
        *.cls := "flex-grow",
        *.cls := "container",
        *.cls := "mx-auto",
        *.cls := "px-4",
        h1(
          *.cls := "my-4",
          *.cls := "text-4xl",
          *.cls := "font-bold",
          *.cls := "text-center",
          *.cls := "pb-4",
          "Martin Hackett: Live"
        ),
        videos.map: video =>
          modifier(
            div(
              *.cls := "pb-8",
              *.cls := "text-center",
              *.cls := "md:text-left",
              *.cls := "grid",
              *.cls := "gap-4",
              *.cls := "grid-cols-1",
              *.cls := "md:grid-cols-[1fr_1fr]",
              *.cls := "lg:grid-cols-[1fr_1fr_1fr]",
              *.cls := "md:justify-items-start",
              h2(
                *.cls := "text-xl",
                *.cls := "font-semibold",
                *.cls := "md:col-span-2",
                *.cls := "lg:col-span-1",
                video.title
              ),
              iframe(
                *.cls := "mx-auto",
                *.cls := "md:mx-unset",
                *.width := 460,
                *.height := 315,
                *.src := video.embedLink,
                *.title := "YouTube video player",
                *.attr("frameborder") := 0,
                *.attr(
                  "allow"
                ) := "accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share",
                *.attr("referrerpolicy") := "strict-origin-when-cross-origin",
                *.attr("allowfullscreen").empty
              ),
              div(
                video.description,
                p(
                  *.cls := "pt-2",
                  *.cls := "italic",
                  video.credits
                )
              )
            )
          )
      )
    )
