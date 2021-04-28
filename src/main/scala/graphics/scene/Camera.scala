package graphics.scene

import graphics.{Matrix4, N}

/**
 * This is the camera, from the point of which the user sees the scene.
 * */
class Camera(fov: N) extends Object {
    def viewMatrix: Matrix4 = (Matrix4.newTranslation(position) * rotation.toRotationMatrix).inverted

    def projectionMatrix(aspectRatio: N): Matrix4 = Matrix4.newPerspective(aspectRatio, fov)
}
