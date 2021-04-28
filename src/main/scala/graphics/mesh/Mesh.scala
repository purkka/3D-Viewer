package graphics.mesh

import graphics._
import graphics.scene.{Camera, Light}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Polygon

/**
 * A mesh contains the vertices and indices to form a 3D object.
 * */
class Mesh(vertices: Vector[Vec4], indices: Vector[Tri]) {
    def project(target: RenderQueue, projection: Matrix4, normalProjection: Matrix4, transform: TransformFunc,
                lights: Vector[Light], camera: Camera): Unit = {

        def angle(n: Vec4, e: Vec4, c: Vec4): N = n.dot((e - c).normalized())

        for (i <- indices) {
            val points = i.map(vertices)
            val pointsNormal = points.map(normalProjection * _)
            val center = points.reduce(_ + _) / i.length
            val centerNormal = normalProjection * center
            val n = (pointsNormal(1) - pointsNormal.head).cross(pointsNormal(2) - pointsNormal.head).normalized() // normal vector
            val d = angle(n, camera.position, centerNormal) // dot product


            if (d > 0 && pointsNormal.forall(inViewingFrustum(_, camera))) {
                val p = Polygon()
                points.map(projection * _).map(v => transform(v / v.w))
                  .foreach(c => p.getPoints.addAll(c._1, c._2))

                var color = Vec4(0, 0, 0)

                for (ls <- lights) {
                    val mult = ls.incidence(centerNormal, n) * 255

                    val lc = Vec4(ls.color.red, ls.color.green, ls.color.blue)
                    color = color + lc * mult
                }

                def cclamp(c: Int): Int = c.clamp(0, 255) // color clamp

                val finalColor = Color.rgb(cclamp(color.x.toInt), cclamp(color.y.toInt), cclamp(color.z.toInt))

                p.stroke = finalColor // otherwise there are gaps in the object
                p.fill = finalColor
                target.enqueue(((camera.position - centerNormal).length, p))
            }
        }
    }

    // frustum culling of near clipping plane
    private def inViewingFrustum(point: Vec4, camera: Camera): Boolean = {
        val cameraBase = camera.rotation.toRotationMatrix
        val back = Vec4(cameraBase(2, 0), cameraBase(2, 1), cameraBase(2, 2))
        (point - camera.position).dot(back) / back.dot(back) < 0
    }

    private def validateIndices(): Unit = {
        if (indices.exists(_.length != 3)) throw new CorruptedObjFileException("All indices must be of length 3")
        if (indices.exists(_.exists(i => i < 0 || i >= vertices.length))) throw new CorruptedObjFileException("Indices must point to existing triangles")
    }

    validateIndices()
}

