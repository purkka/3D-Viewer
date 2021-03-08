import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.scene.canvas.Canvas
import scalafx.scene.shape._
import scalafx.scene.paint._
import scalafx.animation._

object Main extends JFXApp {
    stage = new JFXApp.PrimaryStage {
        title.value = "3D viewer"
        scene = new Scene(800, 600) {
            val c = Circle(10, 10, 10)
            c.fill = Color.BlueViolet

            content = List(c)

            var lastTime = 0L
            val speed = 2
            val timer = AnimationTimer(t => {
                if (lastTime > 0) {
                    val delta = (t - lastTime) / 1e9
                    val dx = 400 - c.centerX.value
                    val dy = 300 - c.centerY.value
                    val dist = scala.math.sqrt(dx * dx + dy * dy)
                    c.centerX = c.centerX.value + dx / dist * speed
                    c.centerY = c.centerY.value + dy / dist * speed
                }
                lastTime = t
            })
            timer.start
        }
//        width = 800
//        height = 600
    }

//    val root = new Pane
//    val scene = new Scene(root)
//    stage.scene = scene
//
//    val rectangle = new Rectangle {
//        x = 100
//        y = 100
//        width = 50
//        height = 50
//        fill = Blue
//    }
//
//    root.children += rectangle
}