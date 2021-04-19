package graphics.mesh

import graphics._
import graphics.scene.{Camera, DirectionalLight, Light}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Polygon

import scala.jdk.CollectionConverters._

class Mesh(vertices: Vector[Vec4], indices: Vector[Tri]) {
    // temporary color list for testing
    val colorList: Seq[Color] = AllColors.allColors().asScala.map(c => new Color(c)).toSeq

    // ArrayBuffer for performance reasons
    def project(target: RenderQueue, projection: Matrix4, normalProjection: Matrix4, transform: TransformFunc,
                lights: Vector[Light], camera: Camera): Unit = {

        def angle(n: Vec4, e: Vec4, c: Vec4): N = n.dot((e - c).normalized())

        for ((i, c) <- indices.zipWithIndex) {
            val points = i.map(vertices)
            val pointsNormal = points.map(normalProjection * _)
            val center = points.reduce(_ + _) / i.length
            val centerNormal = normalProjection * center
            val n = (pointsNormal(1) - pointsNormal.head).cross(pointsNormal(2) - pointsNormal.head).normalized() // normal vector
//            val d = n.dot((camera.position - centerNormal).normalized()) // dot product (angle)
//            val l = n.dot((/*light*/ Vec4(0, 10, 0) - centerNormal).normalized()) // light angle
            val d = angle(n, camera.position, centerNormal)
//            val l = angle(n, Vec4(0, 1, 0), centerNormal)


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

                def cclamp(c: Int): Int = math.max(0, math.min(255, c)) // color clamp
//                def cclamp(c: Int): Int = (math.max(l, 0) * math.max(0, math.min(255, c / lightSources.size))).toInt + 35 // color clamp

//                val color = Color.rgb(cclamp(red), cclamp(green), cclamp(blue))


//                val colorValue = (math.max(l, 0) * 120).toInt + 35
//                val color = Color.rgb(colorValue, colorValue, colorValue)

                val finalColor = Color.rgb(cclamp(color.x.toInt), cclamp(color.y.toInt), cclamp(color.z.toInt))

                p.stroke = finalColor
                p.fill = finalColor
                target.enqueue(((camera.position - centerNormal).length, p))

                // testing triangle centers
//                val cp = projection * center
//                val a = transform(cp / cp.w)
//                val cir = Circle(a._1, a._2, 2, Color.LimeGreen)
//                target.enqueue((Double.NegativeInfinity, cir))
            }
        }
    }

    // frustum culling of near clipping plane
    def inViewingFrustum(point: Vec4, camera: Camera): Boolean = {
        val cameraBase = camera.rotation.toRotationMatrix
        val back = Vec4(cameraBase(2, 0), cameraBase(2, 1), cameraBase(2, 2))
        (point - camera.position).dot(back) / back.dot(back) < 0
    }

    def validateIndices(): Unit = {
        assert(indices.forall(_.length == 3), "All indices must be of length 3")
        assert(indices.forall(_.forall(i => 0 <= i && i < vertices.length)), "Indices must point to existing triangles")
    }

    validateIndices()
}

