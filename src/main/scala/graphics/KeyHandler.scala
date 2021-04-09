package graphics

import graphics.scene.Camera
import scalafx.scene.input.{KeyCode, KeyEvent}

import scala.collection.mutable

class KeyHandler(camera: Camera, velocity: Double) {
    val keyStates: mutable.Map[KeyCode, Boolean] = mutable.HashMap[KeyCode, Boolean]().withDefaultValue(false)

    def handle(ke: KeyEvent): Unit = ke.eventType match {
        case KeyEvent.KeyPressed => keyStates(ke.code) = true
        case KeyEvent.KeyReleased => keyStates(ke.code) = false
    }

    def update(delta: Double): Vec4 = {
        var direction = Vec4(0, 0, 0)
        val cameraBase = camera.rotation.toRotationMatrix

        val right = Vec4(cameraBase(0, 0), cameraBase(0, 1), cameraBase(0, 2))
        val up = Vec4(cameraBase(1, 0), cameraBase(1, 1), cameraBase(1, 2))
        val back = Vec4(cameraBase(2, 0), cameraBase(2, 1), cameraBase(2, 2))

        if (keyStates(KeyCode.E)) direction = direction + up
        if (keyStates(KeyCode.Q)) direction = direction - up

        if (keyStates(KeyCode.W)) direction = direction - back
        if (keyStates(KeyCode.S)) direction = direction + back

        if (keyStates(KeyCode.A)) direction = direction - right
        if (keyStates(KeyCode.D)) direction = direction + right

        direction.normalized() * velocity * delta
    }
}
