package graphics

import scala.math._

/**
 * A 4-dimensional vector. The last component determines if the vec is a point or direction.
 * */
case class Vec4(x: N, y: N, z: N, w: N = 1) {
    // w = 1 -> point
    // w = 0 -> vector (direction)
    val length: N = sqrt(x * x + y * y + z * z)

    def normalized(): Vec4 = this / length

    def /(value: N): Vec4 = Vec4(x / value, y / value, z / value, w)

    def *(value: N): Vec4 = Vec4(x * value, y * value, z * value, w)
}

object Vec4 {
    def pointFromSeq(s: Seq[N]): Vec4 = {
        assert(s.length == 3, "Points must have 3 components")
        Vec4(s.head, s(1), s(2))
    }
}
