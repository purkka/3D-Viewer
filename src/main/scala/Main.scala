import scalafx.application.JFXApp
import graphics._
import graphics.mesh._
import graphics.scene._
import javafx.geometry.Insets
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.Image
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.stage.FileChooser

import java.io.{File, FileInputStream}
import scala.math.toRadians

/**
 * This is the main class running the application.
 * */
object Main extends JFXApp {
    // click and drag to move camera direction
    val camera = new Camera(toRadians(70))
    camera.position = Vec4(0, 0, 3)
    val mouseHandler = new MouseHandler(camera)
    val keyHandler = new KeyHandler(camera, 1)

    // the scene's lights
    private val light0 = new AmbientLight()
    private val light1 = new DirectionalLight(Vec4(-1, -1, -1), Color.LightGray)
    private val light2 = new PointLight(Vec4(-5, 0, 0), Color.Black)

    var obj = new MeshObject(Cube) // default object
    val canvas = new Canvas(900, 600, mouseHandler, keyHandler)
    val sc = new graphics.scene.Scene(camera, Vector(obj), Vector(light0, light1, light2), canvas) // virtual scene

    // for animating the scene
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

    // choose file button
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

    // toggle rotation button
    val rotationButton = new Button("Toggle rotation")
    rotationButton.setOnAction(_ => rotation = !rotation)

    // color sliders for the lights
    val colorSlider1: ColorChooser = new ColorChooser(Color.LightGray) {
        def update(c: Color): Unit = light1.color = c
    }
    val colorSlider2: ColorChooser = new ColorChooser(Color.Black) {
        def update(c: Color): Unit = light2.color = c
    }

    // GUI text
    val cs1Label = new Label("Adjust directional light")
    cs1Label.setStyle("-fx-font-weight: bold")
    val cs2Label = new Label("Adjust point light")
    cs2Label.setStyle("-fx-font-weight: bold")

    // GUI layout
    private val vhbox = new VBox(fileButton, rotationButton)
    vhbox.setPadding(new Insets(20, 10, 20, 10))
    vhbox.setSpacing(10)
    private val vboxcs1 = new VBox(cs1Label, colorSlider1)
    vboxcs1.setSpacing(5)
    private val vboxcs2 = new VBox(cs2Label, colorSlider2)
    vboxcs2.setSpacing(5)
    private val hbox = new HBox(vhbox, vboxcs2, vboxcs1)
    hbox.setPadding(new Insets(10, 10, 10, 10))
    hbox.setSpacing(10)
    private val vbox = new VBox(canvas, hbox)

    val scc = new scalafx.scene.Scene(vbox)

    // main stage
    stage = new JFXApp.PrimaryStage {
        title.value = "3D Object Viewer"
        scene = scc
    }

    stage.icons.add(new Image(new FileInputStream("files/3Dlogo.png")))
    stage.setResizable(false)

    timer.start()
}