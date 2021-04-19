package graphics.scene

import graphics.{N, Vec4}
import scalafx.scene.paint.Color

class DirectionalLight(direction: Vec4, override val color: Color) extends Light {
    private val dirNorm = direction.normalized()

    override def incidence(position: Vec4, normal: Vec4): N = (-dirNorm.dot(normal)).max(0)
}
