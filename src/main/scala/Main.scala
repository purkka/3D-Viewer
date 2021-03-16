import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.animation.AnimationTimer
import scalafx.scene.shape._
import scalafx.scene.paint._
import scalafx.animation._

import graphics._

object Main extends JFXApp {
    // for the object
    def toRadians(d: N) = d * math.Pi / 180

    val width = 800;
    val height = 600;

    val projection = Matrix4.newPerspective(width / height.toDouble, toRadians(70))
    val scaling = Matrix4.newScaling(Vec4(0.3, 0.3, 0.3))
    val translation = Matrix4.newTranslation(Vec4(0, 0, -3))

    // for the animation
    stage = new JFXApp.PrimaryStage {
        title.value = "3D viewer"
        scene = new Scene(800, 600) { // miten tänne aikaisemmin määritetty "width"


            // triangle
            val a = Vec4(0, 0, 0)
            val b = Vec4(0, 1, 0)
            val c = Vec4(1, 0, 0)

            val s = Vector(a, b, c)


            var lastTime = 0L
            var angle = 0.0
            val rotationlSpeed = 0.5
            val timer: AnimationTimer = AnimationTimer(t => {
                if (lastTime > 0) {
                    val delta = (t - lastTime) / 1e9
                    angle += rotationlSpeed * delta

                    // Artificial lag
                    //java.lang.Thread.sleep(1000)

                    val m = Matrix4.newTranslation(Vec4(Main.width / 2, Main.height / 2, 0)) *
                      new Quaternion(Vec4(0, 0, 1), angle).toRotationMatrix *
                      Matrix4.newScaling(Vec4(200, 200, 0)) *
                      Matrix4.newTranslation(Vec4(-0.5, -0.5, 0))

                    val projectedPoints = s.map(m * _)

                    val triangle = Polygon()
                    triangle.getPoints.addAll(
                        projectedPoints.head.x,
                        projectedPoints.head.y,
                        projectedPoints(1).x,
                        projectedPoints(1).y,
                        projectedPoints(2).x,
                        projectedPoints(2).y,
                    )

                    triangle.fill = Color.BlueViolet

                    content = List(triangle)

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