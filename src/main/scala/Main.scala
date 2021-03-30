import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.animation.AnimationTimer
import scalafx.scene.shape._
import scalafx.scene.paint._
import graphics._
import graphics.mesh.{Cube, ObjParser}

object Main extends JFXApp {
    // for the object
    def toRadians(d: N): Double = d * math.Pi / 180

    val width = 800
    val height = 600

    // screen space transform function
    def transform(w: Vec4): (N, N) = (0.5 * width * (w.x + 1.0), 0.5 * height * (1.0 - w.y))

    val obj: mesh.Object = ObjParser.loadObject("resources/donut.obj")

    // for the animation
    stage = new JFXApp.PrimaryStage {
        title.value = "3D viewer"
        scene = new Scene(800, 600) {
            var lastTime = 0L
            var angle = 0.0
            val rotationalSpeed = 0.5
            val timer: AnimationTimer = AnimationTimer(t => {
                if (lastTime > 0) {
                    val delta = (t - lastTime) / 1e9
                    angle += rotationalSpeed * delta

                    // Artificial lag
                    //java.lang.Thread.sleep(1000)

                    val m =
                        Matrix4.newPerspective(width.toDouble / height.toDouble, toRadians(70)) *
                        Matrix4.newTranslation(Vec4(0 , 0, -3)) *
//                      Matrix4.newTranslation(Vec4(Main.width / 2, Main.height / 2, 0))
//                      new Quaternion(Vec4(0, 1, 0), angle).toRotationMatrix *
                      new Quaternion(Vec4(0, 1, 1), 0.9 * angle).toRotationMatrix
//                      new Quaternion(Vec4(1, 0, 0), 1.2 * angle).toRotationMatrix
//                      Matrix4.newScaling(Vec4(200, 200, 0)) *
//                      Matrix4.newTranslation(Vec4(0, 0, -0.5))

                    content = obj.render(m, transform)

                }
                lastTime = t
            })
            timer.start
        }
    }
}