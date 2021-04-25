package graphics

import scala.math._

/**
 * This 3D engine uses 4-dimensional matrices for transformations and projections.
 * */
class Matrix4(data: Array[N]) {
    assert(data.length == 16, "Matrix4 requires exactly 16 row-major elements")

    private val range = 0 to 3

    def apply(column: Int, row: Int): N = data(row * 4 + column)

    private def update(column: Int, row: Int, value: N): Unit = data(row * 4 + column) = value

    def *(m: Matrix4): Matrix4 = {
        val d = Matrix4.newZero()
        for (i <- range; j <- range) {
            for (k <- range) {
                d(i, j) += this (k, j) * m(i, k)
            }
        }
        d
    }

    def *(v: Vec4): Vec4 = Vec4(
        this (0, 0) * v.x + this (1, 0) * v.y + this (2, 0) * v.z + this (3, 0) * v.w,
        this (0, 1) * v.x + this (1, 1) * v.y + this (2, 1) * v.z + this (3, 1) * v.w,
        this (0, 2) * v.x + this (1, 2) * v.y + this (2, 2) * v.z + this (3, 2) * v.w,
        this (0, 3) * v.x + this (1, 3) * v.y + this (2, 3) * v.z + this (3, 3) * v.w,
    )

    def *(n: N): Matrix4 = new Matrix4(data.map(_ * n))


    private def det3: N =
        this (0, 0) * this (1, 1) * this (2, 2) - this (0, 0) * this (1, 2) * this (2, 1) +
          this (1, 0) * this (2, 1) * this (0, 2) - this (1, 0) * this (2, 2) * this (0, 1) +
          this (2, 0) * this (0, 1) * this (1, 2) - this (2, 0) * this (0, 2) * this (1, 1)

    def det: N = {
        var result = 0.0
        var sign = 1.0

        for (i <- 0 until 4) {
            val sub = Matrix4.newZero()
            for (j <- 0 until 3; k <- 0 until 3) {
                sub.update(j, k, this (if (j < i) j else j + 1, k + 1))
            }
            result += sub.det3 * this (i, 0) * sign
            sign *= -1
        }
        result
    }

    private def rcp(a: N): N = if (a != 0.0) 1.0 / a else 0.0

    def inverted: Matrix4 = {
        val r = Matrix4.newZero()
        var d = 0.0
        var si = 1.0
        val subRange = 0 to 2
        for (i <- range) {
            var sj = si
            for (j <- range) {
                val sub = Matrix4.newZero()
                for (k <- subRange; l <- subRange) {
                    sub(k, l) = this (if (k < j) k else k + 1, if (l < i) l else l + 1)
                }
                val dd = sub.det3 * sj
                r(i, j) = dd
                d += this (j, i) * dd
                sj = -sj
            }
            si = -si
        }
        r * rcp(d) * 4
    }

    override def toString: String = data.toVector.toString()
}

object Matrix4 {
    def newZero(): Matrix4 = new Matrix4(Array.fill(16)(0))

    /** Affine transformations */

    def newIdentity(): Matrix4 = new Matrix4(Array(
        1, 0, 0, 0,
        0, 1, 0, 0,
        0, 0, 1, 0,
        0, 0, 0, 1,
    ))

    def newScaling(v: Vec4): Matrix4 = new Matrix4(Array(
        v.x, 0, 0, 0,
        0, v.y, 0, 0,
        0, 0, v.z, 0,
        0, 0, 0, 1,
    ))

    def newTranslation(v: Vec4): Matrix4 = new Matrix4(Array(
        1, 0, 0, v.x,
        0, 1, 0, v.y,
        0, 0, 1, v.z,
        0, 0, 0, 1,
    ))

    // Rotation matrix will be implemented through quaternions.

    /** Projections */

    def newOrthographic(aspectRatio: N): Matrix4 = new Matrix4(Array(
        1 / aspectRatio, 0, 0, 0,
        0, 1, 0, 0,
        0, 0, 1, 0,
        0, 0, 0, 1,
    ))

    def newPerspective(aspectRatio: N, fovRadians: N): Matrix4 = {
        val tanHalfFov = tan(fovRadians / 2)

        new Matrix4(Array(
            1.0 / (aspectRatio * tanHalfFov), 0, 0, 0,
            0, 1.0 / tanHalfFov, 0, 0,
            0, 0, 1, 0,
            0, 0, -1, 0,
        ))
    }
}

