import scalafx.application.JFXApp
import graphics._
import graphics.mesh._
import graphics.scene.{AmbientLight, Camera, DirectionalLight, MeshObject, PointLight}
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.stage.FileChooser

import scala.math.toRadians

object Main extends JFXApp {
    val width = 800
    val height = 600

    val obj = new MeshObject(ObjParser.loadMesh("resources/monke.obj"))
    val obj2 = new MeshObject(ObjParser.loadMesh("resources/monke.obj"))
    obj.position = Vec4(0 , 0, -3)
    obj2.position = Vec4(0 , 2, -3)

    // click and drag to move camera direction
    val camera = new Camera(toRadians(70))
    val mouseHandler = new MouseHandler(camera)
    val keyHandler = new KeyHandler(camera, 1)

    val light0 = new AmbientLight()
    val light1 = new DirectionalLight(Vec4(-1, -1, 0), Color.Red)
    val light2 = new DirectionalLight(Vec4(1, -1, 0), Color.Blue)
    val light3 = new DirectionalLight(Vec4(0, -1, -1), Color.Green)

    val canvas = new Canvas(width, height, mouseHandler, keyHandler)
    val sc = new graphics.scene.Scene(camera, Vector(obj, obj2), Vector(light0, light1, light2, light3), canvas)

    var angle = 0.0
    val rotationalSpeed = 0.8

    val timer = new Timer(delta => {
        angle += rotationalSpeed * delta
        obj.rotation = Quaternion(Vec4(0, 1, 0), 0.9 * angle)
        obj2.rotation = Quaternion(Vec4(0, 1, 0), -3 * angle)
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