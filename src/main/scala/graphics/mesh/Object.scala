package graphics.mesh

import graphics._
import javafx.scene.paint

import scala.jdk.CollectionConverters._
import scalafx.scene.paint.Color
import scalafx.scene.shape.Polygon

import scala.collection.mutable.ArrayBuffer

class Object(vertices: Vector[Vec4], indices: Vector[Tri]) {
    // temporary color list for testing
    val colorList: Seq[Color] = AllColors.allColors().asScala.map(c => new Color(c)).toSeq

    // ArrayBuffer for performance reasons
    def render(projection: Matrix4, transform: TransformFunc): ArrayBuffer[Polygon] = {
        val polygons = ArrayBuffer[Polygon]()

        for ((i, c) <- indices.zipWithIndex) {
            val p = Polygon()
            i.map(vertices).iterator.map(projection * _).map(v => transform(v / v.w))
              .foreach(c => p.getPoints.addAll(c._1, c._2))
            p.fill = new Color(colorList((c * 42) % colorList.length).opacity(0.5))
            polygons += p
        }

        polygons
    }

    def validateIndices(): Unit = {
        assert(indices.forall(_.length == 3), "All indices must be of length 3")
        assert(indices.forall(_.forall(i => 0 <= i && i < vertices.length)), "Indices must point to existing triangles")
    }
    validateIndices()
}

