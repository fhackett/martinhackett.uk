package uk.martinhackett

trait Renderable:
  def render: scalatags.Text.Modifier
