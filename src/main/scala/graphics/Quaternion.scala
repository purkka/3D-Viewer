package graphics

import scala.math._

class Quaternion(axis: Vec4, angle: N) {
    private val v = axis.normalized()

    private val x = v.x * sin(angle / 2)
    private val y = v.y * sin(angle / 2)
    private val z = v.z * sin(angle / 2)
    private val w = cos(angle / 2)

    def toRotationMatrix: Matrix4 = Matrix4(
        1 - 2 * y * y - 2 * z * z,  2 * x * y - 2 * z * w,      2 * x * z + 2 * y * w,      0,
        2 * x * y + 2 * z * w,      1 - 2 * x * x - 2 * z * z,  2 * y * z - 2 * x * w,      0,
        2 * x * z - 2 * y * w,      2 * y * z + 2 * x * w,      1 - 2 * x * x - 2 * y * y,  0,
        0,                          0,                          0,                          1,
    )
}

object Quaternion {
    def apply(axis: Vec4, angle: N): Quaternion = new Quaternion(axis, angle)
}
