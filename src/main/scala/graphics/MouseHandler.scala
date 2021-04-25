package graphics

import graphics.scene.Camera
import scalafx.scene.input.MouseEvent

import scala.math.toRadians

class MouseHandler(camera: Camera) {
    private var cameraStoredX = 0.0
    private var cameraStoredY = 0.0
    private var cameraCurrentX = 0.0
    private var cameraCurrentY = 0.0

    private var startEvent = new MouseEvent(null)

    def handle(me: MouseEvent): Unit = {
        me.eventType match {
            case MouseEvent.MousePressed =>
                startEvent = me
                cameraStoredX = cameraCurrentX
                cameraStoredY = cameraCurrentY
            case MouseEvent.MouseDragged =>
                cameraCurrentX = cameraStoredX + (me.x - startEvent.x) * 0.1
                cameraCurrentY = (cameraStoredY + (me.y - startEvent.y) * 0.1).clamp(-90, 90)
                camera.rotation = (Quaternion(Vec4(0, 1, 0), toRadians(cameraCurrentX)) *
                  Quaternion(Vec4(1, 0, 0), toRadians(cameraCurrentY))).normalized()
        }
    }
}
