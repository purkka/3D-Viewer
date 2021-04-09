import scalafx.application.JFXApp
import scalafx.animation.AnimationTimer
import graphics._
import graphics.mesh.{Cube, Mesh, ObjParser}
import graphics.scene.{Camera, MeshObject, Object}

import scala.math.toRadians

object Main extends JFXApp {
    // for the object
//    def toRadians(d: N): Double = d * math.Pi / 180

    val width = 800
    val height = 600


//    val obj: Mesh = ObjParser.loadMesh("resources/donut.obj")
    val cube = new MeshObject(ObjParser.loadMesh("resources/cube.obj"))
    cube.position = Vec4(0 , 0, -3)

    val camera = new Camera(toRadians(70))
    camera.position = Vec4(0, 3, 0)
    camera.rotation = Quaternion(Vec4(1, 0, 0), toRadians(-45))

    val canvas = new Canvas(width, height)
    val sc = new graphics.scene.Scene(camera, Vector(cube), canvas)

    var angle = 0.0
    val rotationalSpeed = 0.5

    val timer = new Timer(delta => {
        angle += rotationalSpeed * delta
        cube.rotation = Quaternion(Vec4(0, 1, 0), 0.9 * angle)
        canvas.draw(sc.render)
    })

    // for the animation
    stage = new JFXApp.PrimaryStage {
        title.value = "3D viewer"
        scene = canvas
    }

    timer.start()
}