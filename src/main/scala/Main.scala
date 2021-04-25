import scalafx.application.JFXApp
import graphics._
import graphics.mesh._
import graphics.scene._
import javafx.geometry.Insets
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.stage.FileChooser

import java.io.File
import scala.math.toRadians

object Main extends JFXApp {
    // click and drag to move camera direction
    val camera = new Camera(toRadians(70))
    camera.position = Vec4(0, 0, 3)
    val mouseHandler = new MouseHandler(camera)
    val keyHandler = new KeyHandler(camera, 1)

    private val light0 = new AmbientLight()
    private val light1 = new DirectionalLight(Vec4(-1, -1, -1), Color.LightGray)
    private val light2 = new PointLight(Vec4(-5, 0, 0), Color.Black)

    var obj = new MeshObject(Cube)
    val canvas = new Canvas(900, 600, mouseHandler, keyHandler)
    val sc = new graphics.scene.Scene(camera, Vector(obj), Vector(light0, light1, light2), canvas)

    private var angle = 0.0
    private val rotationalSpeed = 0.8
    private var rotation = true

    val timer = new Timer(delta => {
        if (rotation) {
            angle += rotationalSpeed * delta
            obj.rotation = Quaternion(Vec4(0, 1, 0), 0.9 * angle)
        }
        camera.position = camera.position + keyHandler.update(delta)
        canvas.draw(sc.render)
    })

    val fc = new FileChooser()
    private val initPath = new File(System.getProperty("user.dir"), "resources")
    if (initPath.isDirectory) fc.setInitialDirectory(initPath)

    val fileButton = new Button("Select OBJ file")
    fileButton.setOnAction(_ => {
        val selectedFile = fc.showOpenDialog(stage)
        if (selectedFile != null) {
            try {
                obj = new MeshObject(ObjParser.loadMesh(selectedFile.getAbsolutePath))
                sc.objects = Vector(obj)
                angle = 0.0
            } catch {
                case oe: CorruptedObjFileException =>
                    new ExceptionDialog(AlertType.Error, oe)
            }
        }
    })

    val rotationButton = new Button("Toggle rotation")
    rotationButton.setOnAction(_ => rotation = !rotation)

    val colorSlider1: ColorChooser = new ColorChooser(Color.LightGray) {
        def update(c: Color): Unit = light1.color = c
    }
    val colorSlider2: ColorChooser = new ColorChooser(Color.Black) {
        def update(c: Color): Unit = light2.color = c
    }

    val cs1Label = new Label("Adjust directional light")
    cs1Label.setStyle("-fx-font-weight: bold")
    val cs2Label = new Label("Adjust point light")
    cs2Label.setStyle("-fx-font-weight: bold")

    private val vhbox = new VBox(fileButton, rotationButton)
    vhbox.setPadding(new Insets(20, 10, 20, 10))
    vhbox.setSpacing(10)
    private val vboxcs1 = new VBox(cs1Label, colorSlider1)
    vboxcs1.setSpacing(5)
    private val vboxcs2 = new VBox(cs2Label, colorSlider2)
    vboxcs2.setSpacing(5)
    private val hbox = new HBox(vhbox, vboxcs1, vboxcs2)
    hbox.setPadding(new Insets(10, 10, 10, 10))
    hbox.setSpacing(10)
    private val vbox = new VBox(canvas, hbox)

    val scc = new scalafx.scene.Scene(vbox)

    // for the animation
    stage = new JFXApp.PrimaryStage {
        title.value = "3D Object Viewer"
        scene = scc
    }

    stage.setResizable(false)

    timer.start()
}