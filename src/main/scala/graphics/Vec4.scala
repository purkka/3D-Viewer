package graphics

import scala.math._

/**
 * A 4-dimensional vector. The last component determines if the vec is a point or direction.
 * */
case class Vec4(x: N, y: N, z: N, w: N = 1) {
    // w = 1 -> point
    // w = 0 -> vector (direction)
    val length: N = sqrt(dot(this))

    def normalized(): Vec4 = if (length != 0.0) this / length else Vec4(0, 0, 0)

    def /(value: N): Vec4 = Vec4(x / value, y / value, z / value, w)

    def *(value: N): Vec4 = Vec4(x * value, y * value, z * value, w)

    def +(v: Vec4): Vec4 = Vec4(x + v.x, y + v.y, z + v.z, w)
    
    def -(v: Vec4): Vec4 = Vec4(x - v.x, y - v.y, z - v.z, w)

    def dot(v: Vec4): N = x * v.x + y * v.y + z * v.z

    def cross(v: Vec4): Vec4 = Vec4(v.z * y - v.y * z, -v.z * x + v.x * z, v.y * x - v.x * y, 0)
}

object Vec4 {
    def pointFromSeq(s: Seq[N]): Vec4 = {
        assert(s.length == 3, "Points must have 3 components")
        Vec4(s.head, s(1), s(2))
    }
}
