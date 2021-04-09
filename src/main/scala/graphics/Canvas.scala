package graphics

import scalafx.scene.shape.Polygon
import scalafx.scene.{Node, Scene}
import scalafx.Includes._
import scalafx.scene.input.{KeyEvent, MouseEvent}

import scala.collection.mutable.ArrayBuffer

class Canvas(width: Int, height: Int, mouseHandler: MouseHandler, keyHandler: KeyHandler) extends Scene(width, height) {
    // screen space transform function
    def transform(w: Vec4): (N, N) = (0.5 * width * (w.x + 1.0), 0.5 * height * (1.0 - w.y))

    // TODO: replace polygon with node
    def draw(nodes: ArrayBuffer[Polygon]): Unit = content = nodes

    // moving the scene
    onMousePressed = (me: MouseEvent) => mouseHandler.handle(me)
    onMouseDragged = (me: MouseEvent) => mouseHandler.handle(me)

    onKeyPressed = (ke: KeyEvent) => keyHandler.handle(ke)
    onKeyReleased = (ke: KeyEvent) => keyHandler.handle(ke)
}
