package site


import customtags.*
import scalatags.Text.tags
import scalatags.Text.tags2
import scalatags.Text

object bandcamp_releases extends Target:
  def path: os.SubPath = os.sub / "bandcamp_releases.html"

  final case class BandcampRelease(
    name: String,
    img: String,
    href: String,
  )

  val releases: List[BandcampRelease] = List(
    BandcampRelease(
      name = "3311",
      img = "/gfx/3311.png",
      href = "https://hundredyearsgallery.bandcamp.com/album/3311",
    ),
    BandcampRelease(
      name = "in air",
      img = "/gfx/in air.png",
      href = "https://beadrecords.bandcamp.com/album/in-air",
    ),
    BandcampRelease(
      name = "ha th wa",
      img = "/gfx/ha th wa.png",
      href = "https://emptybirdcagerecords.bandcamp.com/album/ha-th-wa",
    ),
    BandcampRelease(
      name = "Duos by Martin Hackett, Gordon Pym",
      img = "/gfx/Duos Hackett Pym.jpg",
      href = "https://okazo.bandcamp.com/album/duos",
    ),
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
          "Martin Hackett: BandCamp",
        ),
        div(
          *.cls := "pt-6",
          *.cls := "flex",
          *.cls := "flex-row",
          *.cls := "flex-wrap",
          *.cls := "container",
          *.cls := "justify-around",
          *.cls := "gap-6",
          releases.map: release =>
            modifier(
              a(
                *.href := release.href,
                *.cls := "border",
                *.cls := "border-1",
                img(
                  *.src := release.img,
                  *.alt := s"link to ${release.name}",
                  *.cls := "w-64",
                  *.cls := "min-h-64",
                ),
              ),
            )
        ),
      ),
    )
