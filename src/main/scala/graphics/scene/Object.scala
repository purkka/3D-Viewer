package graphics.scene

import graphics.{Matrix4, Quaternion, TransformFunc, Vec4}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Polygon

import scala.collection.mutable.ArrayBuffer

abstract class Object {
    var position: Vec4 = Vec4(0, 0, 0)
    var rotation: Quaternion = Quaternion(Vec4(1, 0, 0), 0)

    def render(target: ArrayBuffer[Polygon], mvp: Matrix4, transformFunc: TransformFunc): Unit = {}

    def modelMatrix: Matrix4 = Matrix4.newTranslation(position) * rotation.toRotationMatrix
}
