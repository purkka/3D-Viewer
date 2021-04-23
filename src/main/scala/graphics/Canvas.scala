package graphics

import scalafx.scene.{Node, SubScene}
import scalafx.Includes._
import scalafx.scene.input.{KeyEvent, MouseEvent}
import scalafx.scene.paint.Color


class Canvas(width: Int, height: Int, mouseHandler: MouseHandler, keyHandler: KeyHandler) extends SubScene(width, height) {
    fill = Color.Black

    // screen space transform function
    def transform(w: Vec4): (N, N) = (0.5 * width * (w.x + 1.0), 0.5 * height * (1.0 - w.y))

    def draw(nodes: RenderQueue): Unit = {
        val a: Seq[(N, Node)] = nodes.dequeueAll
        content = a.map(_._2)
    }

    // moving the scene
    onMouseDragged = (me: MouseEvent) => mouseHandler.handle(me)
    onMousePressed = (me: MouseEvent) => {
        this.requestFocus()
        mouseHandler.handle(me)
    }

    onKeyPressed = (ke: KeyEvent) => keyHandler.handle(ke)
    onKeyReleased = (ke: KeyEvent) => keyHandler.handle(ke)
}
