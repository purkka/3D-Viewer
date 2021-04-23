package graphics.scene

import graphics.{N, Vec4}
import scalafx.scene.paint.Color

trait Light {
    def incidence(position: Vec4, normal: Vec4): N
    var color: Color
}
