import scalafx.scene.Node
import scala.collection.mutable

/**
 * A package object containing type definitions used in the program.
 * */
package object graphics {
    type N = Double

    type Tri = Seq[Int]

    type TransformFunc = Vec4 => (N, N)

    type RenderQueue = mutable.PriorityQueue[(N, Node)]

    implicit class NClamp(n: N) {
        def clamp(min: N, max: N): N = math.max(min, math.min(max, n))
    }

    implicit class IntClamp(n: Int) {
        def clamp(min: Int, max: Int): Int = math.max(min, math.min(max, n))
    }
}
