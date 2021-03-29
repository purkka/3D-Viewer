import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.animation.AnimationTimer
import scalafx.scene.shape._
import scalafx.scene.paint._
import scalafx.animation._

import graphics._

object Main extends JFXApp {
    // for the object
    def toRadians(d: N): Double = d * math.Pi / 180

    // add points (triangle, projectedPoints)
    def addPoints(t: scalafx.scene.shape.Polygon, v: Vector[Vec4]): Boolean = {
//        println(s"u.w on ${v.head.w}")
        def toPos(w: Vec4): (N, N) = (0.5 * width * (w.x + 1.0), 0.5 * height * (1.0 - w.y))
        val p = v.map(u => toPos(u / u.w))
        t.getPoints.addAll(
            p.head._1, p.head._2,
            p(1)._1, p(1)._2,
            p(2)._1, p(2)._2,
        )
    }



    val width = 800;
    val height = 600;

    //    val projection = Matrix4.newPerspective(width / height.toDouble, toRadians(70))
    //    val scaling = Matrix4.newScaling(Vec4(0.3, 0.3, 0.3))
    //    val translation = Matrix4.newTranslation(Vec4(0, 0, -3))

    // triangle
    val a = Vec4(-0.5, -0.5, 0)
    val b = Vec4(-0.5, 0.5, 0)
    val c = Vec4(0.5, -0.5, 0)

    // additional point to form a square
    val d = Vec4(0.5, 0.5, 0)

    val s = Vector(a, b, c)
    val s2 = Vector(b, c, d)

    // for the animation
    stage = new JFXApp.PrimaryStage {
        title.value = "3D viewer"
        scene = new Scene(800, 600) {
            var lastTime = 0L
            var angle = 0.0
            val rotationlSpeed = 0.5
            val timer: AnimationTimer = AnimationTimer(t => {
                if (lastTime > 0) {
                    val delta = (t - lastTime) / 1e9
                    angle += rotationlSpeed * delta

                    // Artificial lag
                    //java.lang.Thread.sleep(1000)


                    val m =
                        Matrix4.newPerspective(width.toDouble / height.toDouble, toRadians(70)) *
                        Matrix4.newTranslation(Vec4(0 ,0, -3)) *
//                      Matrix4.newTranslation(Vec4(Main.width / 2, Main.height / 2, 0))
                      new Quaternion(Vec4(0, 1, 0), angle).toRotationMatrix *
//                      Matrix4.newScaling(Vec4(200, 200, 0)) *
                      Matrix4.newTranslation(Vec4(0, 0, -1))
//                    Matrix4.newIdentity

                    val projectedPoints = s.map(m * _)
                    val projectedPoints2 = s2.map(m * _)

                    val triangle = Polygon()
                    val triangle2 = Polygon()

                    addPoints(triangle, projectedPoints)
                    addPoints(triangle2, projectedPoints2)
                    //                    triangle.getPoints.addAll(
                    //                        projectedPoints.head.x,
                    //                        projectedPoints.head.y,
                    //                        projectedPoints(1).x,
                    //                        projectedPoints(1).y,
                    //                        projectedPoints(2).x,
                    //                        projectedPoints(2).y,
                    //                    )

                    triangle.fill = Color.BlueViolet
                    triangle2.fill = Color.SeaGreen

                    content = List(triangle, triangle2)

                }
                lastTime = t
            })
            timer.start


            //            var lastTime = 0L
            //            val speed = 100
            //            val timer = AnimationTimer(t => {
            //                if (lastTime > 0) {
            //                    val delta = (t - lastTime) / 1e9
            //                    val dx = 400 - c.x.value
            //                    val dy = 300 - c.y.value
            //                    val dist = scala.math.sqrt(dx * dx + dy * dy)
            //                    c.x = c.x.value + dx / dist * speed * delta
            //                    c.y = c.y.value + dy / dist * speed * delta
            //                }
            //                lastTime = t
            //            })
            //            timer.start
        }
    }
}