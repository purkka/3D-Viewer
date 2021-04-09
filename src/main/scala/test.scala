import graphics.{Matrix4, N}

object test extends App {
    // unit tests for matrix inversion
    val matrix = new Matrix4(Array(
        1, 0, 0, 1,
        2, 1, 1, 0,
        3, 0, 1, 0,
        1, 1, 2, 0,
    ))

    // martix x matrix
    // should be {{2, 1, 2, 1}, {7, 1, 2, 2}, {6, 0, 1, 3}, {9, 1, 3, 1}}
    println(matrix * matrix)

    // determinant should be 4
    val det = matrix.det
    assert(det == 4, "Determinant should be 4")

    // inverted matrix should be {{0, 1/4, 1/4, -(1/4)}, {0, 5/4, -(3/4), -(1/4)}, {0, -(3/4), 1/4, 3/4}, {1, -(1/4), -(1/4), 1/4}}
    // or {{0., 0.25, 0.25, -0.25}, {0., 1.25, -0.75, -0.25}, {0., -0.75, 0.25, 0.75}, {1., -0.25, -0.25, 0.25}}
    val inverted = matrix.inverted
    println(inverted)
}
