import scalafx.scene.input.{KeyEvent, MouseEvent}

package object graphics {
    type N = Double

    type Tri = Seq[Int]

    type TransformFunc = Vec4 => (N, N)
}
