package graphics.scene

import graphics.{Matrix4, TransformFunc}
import scalafx.scene.shape.Polygon

import scala.collection.mutable.ArrayBuffer
import scala.math.toRadians

class Scene(camera: Camera, objects: Vector[Object], transformFunc: TransformFunc) {


    def render: ArrayBuffer[Polygon] = {
        val polygons = ArrayBuffer[Polygon]()
        val v = Matrix4.newIdentity
        val p = Matrix4.newPerspective(800.0 / 600.0, toRadians(70))

        for (o <- objects) o.render(polygons, p * v * o.modelMatrix, transformFunc)

        polygons
    }

}
