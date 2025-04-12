package site

trait Renderable:
  def render: scalatags.Text.Modifier
