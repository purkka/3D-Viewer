package graphics.scene

import graphics.{Matrix4, RenderQueue, TransformFunc, Vec4}
import graphics.mesh.Mesh
import scalafx.scene.shape.Polygon

import scala.collection.mutable.ArrayBuffer

class MeshObject(mesh: Mesh) extends Object {
    override def render(target: RenderQueue, mvp: Matrix4, n: Matrix4, transformFunc: TransformFunc, lights: Vector[Light], camera: Camera): Unit = {
        mesh.project(target, mvp, n, transformFunc, lights, camera)
    }
}
