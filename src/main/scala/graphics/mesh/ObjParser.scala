package graphics.mesh
import graphics.{scene, _}

import scala.jdk.CollectionConverters._
import java.io.{BufferedReader, FileReader, IOException}
import scala.collection.mutable.ArrayBuffer

object ObjParser {
    def loadMesh(file: String): Mesh = {
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
                val objExc = new CorruptedObjFileException("Reading the obj data failed.")

                objExc.initCause(e)

                throw objExc
        } finally {
            reader.foreach(_.close())
        }

        if (vertices.isEmpty || indices.isEmpty) throw new CorruptedObjFileException("Invalid obj file")

        new Mesh(vertices.toVector, indices.toVector)
    }

    private def addVertex(data: Seq[String]): Vec4 = {
        if (data.length != 3) throw new CorruptedObjFileException("Invalid vertex")
        Vec4.pointFromSeq(data.map(_.toDouble))
    }

    // Wavefront OBJ indexes from 1 while this application indexes from 0 (thus "- 1")
    private def addIndex(data: Seq[String]): Tri = {
        if (data.length != 3) throw new CorruptedObjFileException("Invalid index")
        data.map(_.toInt - 1)
    }
}
