import graphics._

object test extends App {
    val vector = Vec4(1, 2, 3)
    val vector2 = Vec4(2, 2, 1)

    val matrix = new Matrix4(Array(
        1, 0, 0, 1,
        2, 1, 1, 0,
        3, 0, 1, 0,
        1, 1, 2, 0,
    ))

    // vector cross product
    // should be {-4, 5, -2}
    println(vector cross vector2)

    // matrix * matrix
    // should be {{2, 1, 2, 1}, {7, 1, 2, 2}, {6, 0, 1, 3}, {9, 1, 3, 1}}
    println(matrix * matrix)

    // determinant
    // should be 4
    val det = matrix.det
    assert(det == 4, "Determinant should be 4")

    // inverted matrix should be {{0, 1/4, 1/4, -(1/4)}, {0, 5/4, -(3/4), -(1/4)}, {0, -(3/4), 1/4, 3/4}, {1, -(1/4), -(1/4), 1/4}}
    // or {{0., 0.25, 0.25, -0.25}, {0., 1.25, -0.75, -0.25}, {0., -0.75, 0.25, 0.75}, {1., -0.25, -0.25, 0.25}}
    val inverted = matrix.inverted
    println(inverted)
}
