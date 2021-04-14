import scalafx.scene.Node
import scalafx.scene.input.{KeyEvent, MouseEvent}
import scalafx.scene.shape.Polygon

import scala.collection.mutable

package object graphics {
    type N = Double

    type Tri = Seq[Int]

    type TransformFunc = Vec4 => (N, N)

    type RenderQueue = mutable.PriorityQueue[(N, Node)]

    implicit class NClamp(n: N) {
        def clamp(min: N, max: N): N = math.max(min, math.min(max, n))
    }
}
