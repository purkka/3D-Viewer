package graphics.scene

import graphics._

abstract class Object {
    var position: Vec4 = Vec4(0, 0, 0)
    var rotation: Quaternion = Quaternion(Vec4(1, 0, 0), 0)

    def render(target: RenderQueue, mvp: Matrix4, n: Matrix4, transformFunc: TransformFunc, camera: Camera, objPos: Vec4 = position): Unit = {}

    def modelMatrix: Matrix4 = Matrix4.newTranslation(position) * rotation.toRotationMatrix
}
