import scalafx.application.JFXApp
import scalafx.animation.AnimationTimer
import graphics._
import graphics.mesh.{Cube, Mesh, ObjParser}
import graphics.scene.{Camera, MeshObject, Object}

object Main extends JFXApp {
    // for the object
//    def toRadians(d: N): Double = d * math.Pi / 180

    val width = 800
    val height = 600

    // screen space transform function
    def transform(w: Vec4): (N, N) = (0.5 * width * (w.x + 1.0), 0.5 * height * (1.0 - w.y))

//    val obj: Mesh = ObjParser.loadMesh("resources/donut.obj")
    val donut = new MeshObject(ObjParser.loadMesh("resources/donut.obj"))
    donut.position = Vec4(0 , 0, -3)

    val sc = new graphics.scene.Scene(new Camera(), Vector(donut), transform)

    // for the animation
    stage = new JFXApp.PrimaryStage {
        title.value = "3D viewer"
        scene = new scalafx.scene.Scene(800, 600) {
            var lastTime = 0L
            var angle = 0.0
            val rotationalSpeed = 0.5
            val timer: AnimationTimer = AnimationTimer(t => {
                if (lastTime > 0) {
                    val delta = (t - lastTime) / 1e9
                    angle += rotationalSpeed * delta

                    // Artificial lag
                    //java.lang.Thread.sleep(1000)

                    donut.rotation = Quaternion(Vec4(0, 1, 1), 0.9 * angle)

//                    val m =
//                        Matrix4.newPerspective(width.toDouble / height.toDouble, toRadians(70)) *
//                        Matrix4.newTranslation(Vec4(0 , 0, -3)) *
//                      Matrix4.newTranslation(Vec4(Main.width / 2, Main.height / 2, 0))
//                      new Quaternion(Vec4(0, 1, 0), angle).toRotationMatrix *
//                      new Quaternion(Vec4(0, 1, 1), 0.9 * angle).toRotationMatrix
//                      new Quaternion(Vec4(1, 0, 0), 1.2 * angle).toRotationMatrix
//                      Matrix4.newScaling(Vec4(200, 200, 0)) *
//                      Matrix4.newTranslation(Vec4(0, 0, -0.5))

//                    content = donut.project(m, transform)

                    content = sc.render

                }
                lastTime = t
            })
            timer.start
        }
    }
}