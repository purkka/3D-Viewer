package graphics.mesh

import graphics._

object Cube extends Mesh(
    Vector(
        Vec4(0.5, 0.5, 0.5),
        Vec4(-0.5, 0.5, 0.5),
        Vec4(-0.5, -0.5, 0.5),
        Vec4(0.5, -0.5, 0.5),
        Vec4(0.5, 0.5, -0.5),
        Vec4(-0.5, 0.5, -0.5),
        Vec4(-0.5, -0.5, -0.5),
        Vec4(0.5, -0.5, -0.5),
    ),
    Vector(
        Seq(0, 1, 2),
        Seq(0, 2, 3),
        Seq(0, 3, 4),
        Seq(3, 4, 7),
        Seq(0, 1, 4),
        Seq(1, 4, 5),
        Seq(6, 2, 7),
        Seq(2, 3, 7),
        Seq(6, 2, 5),
        Seq(1, 2, 5),
        Seq(6, 5, 7),
        Seq(4, 5, 7),
    )
) {}
