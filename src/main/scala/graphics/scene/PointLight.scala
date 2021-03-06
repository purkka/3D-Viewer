package graphics.scene

import graphics.{N, Vec4}
import scalafx.scene.paint.Color

/**
 * A point light.
 * */
class PointLight(pos: Vec4, override var color: Color) extends Light {
    override def incidence(position: Vec4, normal: Vec4): N = (-(position - pos).normalized().dot(normal)).max(0)
}
