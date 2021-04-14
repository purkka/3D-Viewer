package graphics.mesh

import graphics._

object Triangle extends Mesh(
    Vector(
        Vec4(1, 0, 0),
        Vec4(0, 2, 0),
        Vec4(-1, 0, 0),
    ),
    Vector(
        Seq(0, 1, 2)
    )
) {}
