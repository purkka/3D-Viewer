package graphics.mesh
import graphics.{scene, _}

import scala.jdk.CollectionConverters._
import java.io.{BufferedReader, FileReader, IOException}
import scala.collection.mutable.ArrayBuffer

object ObjParser {
    def loadMesh(file: String): Mesh = {
        // TODO: add error handling

        val vertices = ArrayBuffer[Vec4]()
        val indices = ArrayBuffer[Tri]()

        var reader: Option[BufferedReader] = None
        try {
            reader = Some(new BufferedReader(new FileReader(file)))

            for (line <- reader.get.lines().iterator().asScala) {
                val tokens = line.split("\\s+")
                tokens.head match {
                    case "v" => vertices += addVertex(tokens.tail)
                    case "f" => indices += addIndex(tokens.tail)
                    case _ =>
                }
            }
        } catch {
            case e: IOException =>
            // ERRORS HERE
        } finally {
            reader.foreach(_.close())
        }

        new Mesh(vertices.toVector, indices.toVector)
    }

    private def addVertex(data: Seq[String]): Vec4 = Vec4.pointFromSeq(data.map(_.toDouble))

    // Wavefront OBJ indexes from 1 while this application indexes from 0 (thus "- 1")
    private def addIndex(data: Seq[String]): Tri = data.map(_.toInt - 1)

}
