package graphics.scene

import graphics.{Matrix4, N}

class Camera(fov: N) extends Object {
    def viewMatrix: Matrix4 = (Matrix4.newTranslation(position) * rotation.toRotationMatrix).inverted

    def projectionMatrix(aspectRatio: N): Matrix4 = Matrix4.newPerspective(aspectRatio, fov)

    // orthographic projection to test lighting
//    def projectionMatrix(aspectRatio: N): Matrix4 = Matrix4.newOrthographic(aspectRatio)
}
