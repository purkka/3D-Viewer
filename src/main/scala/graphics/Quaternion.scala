package graphics

import scala.math._

class Quaternion private(val x: N, val y: N, val z: N, val w: N) {
    def toRotationMatrix: Matrix4 = new Matrix4(Array(
        1 - 2 * y * y - 2 * z * z,  2 * x * y - 2 * z * w,      2 * x * z + 2 * y * w,      0,
        2 * x * y + 2 * z * w,      1 - 2 * x * x - 2 * z * z,  2 * y * z - 2 * x * w,      0,
        2 * x * z - 2 * y * w,      2 * y * z + 2 * x * w,      1 - 2 * x * x - 2 * y * y,  0,
        0,                          0,                          0,                          1,
    ))

    val length: N = sqrt(x * x + y * y + z * z + w * w)

    def normalized(): Quaternion = this / length

    def /(value: N): Quaternion = new Quaternion(x / value, y / value, z / value, w / value)

    def *(q: Quaternion) = new Quaternion(
        q.x * w + q.w * x + q.z * y - q.y * z,
        q.y * w - q.z * x + q.w * y + q.x * z,
        q.z * w + q.y * x - q.x * y + q.w * z,
        q.w * w - q.x * x - q.y * y - q.z * z,
    )

    def toAxisAngle: (Vec4, N) = (Vec4(x, y, z).normalized(), 2 * atan2(Vec4(x, y, z).length, w))
}

object Quaternion {
    def apply(axis: Vec4, angle: N): Quaternion = {
        val v = axis.normalized()

        val x = v.x * sin(angle / 2)
        val y = v.y * sin(angle / 2)
        val z = v.z * sin(angle / 2)
        val w = cos(angle / 2)

        new Quaternion(x, y, z, w)
    }
}
