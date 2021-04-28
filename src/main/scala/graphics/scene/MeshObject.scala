package graphics.scene

import graphics._
import graphics.mesh.Mesh

/**
 * A 3D object from a mesh.
 * */
class MeshObject(mesh: Mesh) extends Object {
    override def render(target: RenderQueue, mvp: Matrix4, n: Matrix4, transformFunc: TransformFunc, lights: Vector[Light], camera: Camera): Unit = {
        mesh.project(target, mvp, n, transformFunc, lights, camera)
    }
}
