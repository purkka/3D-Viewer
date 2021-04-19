package graphics.scene

import graphics.{N, Vec4}
import scalafx.scene.paint.Color

class AmbientLight(override val color: Color = Color.rgb(20, 20, 20)) extends Light {
    override def incidence(direction: Vec4, normal: Vec4): N = 1
}
