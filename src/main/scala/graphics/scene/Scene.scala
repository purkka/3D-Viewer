package graphics.scene

import graphics.{Canvas, Matrix4, TransformFunc}
import scalafx.scene.shape.Polygon

import scala.collection.mutable.ArrayBuffer
import scala.math.toRadians

class Scene(camera: Camera, objects: Vector[Object], canvas: Canvas) {
    def render: ArrayBuffer[Polygon] = {
        val result = ArrayBuffer[Polygon]()
        val v = camera.viewMatrix
        val p = camera.projectionMatrix(canvas.width.toDouble / canvas.height.toDouble)

        for (o <- objects) {
            o.render(result, p * v * o.modelMatrix, canvas.transform)
        }

        result
    }

}
