package graphics.scene

import graphics.{N, Vec4}
import scalafx.scene.paint.Color

/**
 * An ambient light. This is a base color to differentiate unlit areas of an object from the background.
 * */
class AmbientLight(override var color: Color = Color.rgb(20, 20, 25)) extends Light {
    override def incidence(direction: Vec4, normal: Vec4): N = 1
}
