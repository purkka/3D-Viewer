package graphics

import scalafx.scene.shape.Polygon
import scalafx.scene.{Node, Scene}

import scala.collection.mutable.ArrayBuffer

class Canvas(width: Int, height: Int) extends Scene(width, height) {
    // screen space transform function
    def transform(w: Vec4): (N, N) = (0.5 * width * (w.x + 1.0), 0.5 * height * (1.0 - w.y))

    // TODO: replace polygon with node
    def draw(nodes: ArrayBuffer[Polygon]): Unit = content = nodes

}
