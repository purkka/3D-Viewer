package graphics.mesh

import graphics._

/**
 * A hard coded demo object, a cube. This is also the default object.
 * */
object Cube extends Mesh(
    Vector(
        Vec4(0.5, 0.5, -0.5),
        Vec4(0.5, -0.5, -0.5),
        Vec4(0.5, 0.5, 0.5),
        Vec4(0.5, -0.5, 0.5),
        Vec4(-0.5, 0.5, -0.5),
        Vec4(-0.5, -0.5, -0.5),
        Vec4(-0.5, 0.5, 0.5),
        Vec4(-0.5, -0.5, 0.5),
    ),
    Vector(
        Seq(4, 2, 0),
        Seq(2, 7, 3),
        Seq(6, 5, 7),
        Seq(1, 7, 5),
        Seq(0, 3, 1),
        Seq(4, 1, 5),
        Seq(4, 6, 2),
        Seq(2, 6, 7),
        Seq(6, 4, 5),
        Seq(1, 3, 7),
        Seq(0, 2, 3),
        Seq(4, 0, 1),
    )
) {}
