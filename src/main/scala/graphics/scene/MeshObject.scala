package graphics.scene

import graphics.{Matrix4, TransformFunc}
import graphics.mesh.Mesh
import scalafx.scene.shape.Polygon

import scala.collection.mutable.ArrayBuffer

class MeshObject(mesh: Mesh) extends Object {
    override def render(target: ArrayBuffer[Polygon], mvp: Matrix4, transformFunc: TransformFunc): Unit = {
        target ++= mesh.project(mvp, transformFunc)
    }
}
