import scalafx.application.JFXApp
import scalafx.animation.AnimationTimer
import graphics._
import graphics.mesh.{Cube, Mesh, ObjParser}
import graphics.scene.{Camera, MeshObject, Object}
import scalafx.scene.input.MouseEvent

import scala.math.toRadians

object Main extends JFXApp {
    // for the object
//    def toRadians(d: N): Double = d * math.Pi / 180

    val width = 800
    val height = 600


//    val obj: Mesh = ObjParser.loadMesh("resources/donut.obj")
    val cube = new MeshObject(ObjParser.loadMesh("resources/monke.obj"))
    cube.position = Vec4(0 , 0, -3)

    val camera = new Camera(toRadians(70))
    camera.position = Vec4(0, 3, 0)
    val mouseHandler = new MouseHandler(camera)

    val keyHandler = new KeyHandler(camera, 1)

    val canvas = new Canvas(width, height, mouseHandler, keyHandler)
    val sc = new graphics.scene.Scene(camera, Vector(cube), canvas)

    var angle = 0.0
    val rotationalSpeed = 0.5

    val timer = new Timer(delta => {
        angle += rotationalSpeed * delta
//        camera.rotation = (Quaternion(Vec4(1, 0, 0), angle) * camera.rotation).normalized()
        cube.rotation = Quaternion(Vec4(0, 1, 0), 0.9 * angle)
        camera.position = camera.position + keyHandler.update(delta)
        canvas.draw(sc.render)
    })

    // for the animation
    stage = new JFXApp.PrimaryStage {
        title.value = "3D viewer"
        scene = canvas
    }

    timer.start()
}