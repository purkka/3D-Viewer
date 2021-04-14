package graphics.scene

import graphics._
import scalafx.scene.Node
import scalafx.scene.shape.Polygon

import scala.collection.mutable

class Scene(camera: Camera, objects: Vector[Object], canvas: Canvas) {
    val result = new mutable.PriorityQueue[(N, Node)]()(Ordering.by(t => t._1))

    def render: RenderQueue = {
        val v = camera.viewMatrix
        val p = camera.projectionMatrix(canvas.width.toDouble / canvas.height.toDouble)

        for (o <- objects) {
            o.render(result, p * v * o.modelMatrix, o.modelMatrix, canvas.transform, camera)
        }

        result
    }
}
