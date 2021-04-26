import graphics._
import graphics.mesh.{CorruptedObjFileException, ObjParser}
import graphics.scene.MeshObject
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UnitTests extends AnyFlatSpec with Matchers {
    val vector: Vec4 = Vec4(1, 2, 3)
    val vector2: Vec4 = Vec4(2, 2, 1)

    val matrix = new Matrix4(Array(
        1, 0, 0, 1,
        2, 1, 1, 0,
        3, 0, 1, 0,
        1, 1, 2, 0
    ))

    def e(a: N, b: N, error: N = 0.001):Boolean = a <= b + error && a >= b - error // equal with slight error
    def fa(in: Vector[(N, N)]): Boolean = in.forall({case (a, b) => e(a, b)}) // forall: tuple elements equal

    "Matrix(column, row)" should "return element" in {
        matrix(0, 2) should be (3)
    }

    "Matrix multiplication" should "work correctly" in {
        val mxm = matrix * matrix
        val mxm_res = new Matrix4(Array(
            2, 1, 2, 1,
            7, 1, 2, 2,
            6, 0, 1, 3,
            9, 1, 3, 1,
        ))

        assert(fa(mxm.toVector.zip(mxm_res.toVector)))

        val mxv = matrix * vector
        val mxv_res = Vec4(2, 7, 6, 9)

        assert(fa(mxv.toVector.zip(mxv_res.toVector)))

        val n = 2
        val mxn = matrix * n
        val mxn_res = new Matrix4(Array(
            2, 0, 0, 2,
            4, 2, 2, 0,
            6, 0, 2, 0,
            2, 2, 4, 0,
        ))

        assert(fa(mxn.toVector.zip(mxn_res.toVector)))
    }

    "Matrix determinant" should "work correctly" in {
        matrix.det should be (4)
    }

    "Matrix inversion" should "work correctly" in {
        val inv = new Matrix4(Array(
            0.0, 0.25, 0.25, -0.25,
            0.0, 1.25, -0.75, -0.25,
            0.0, -0.75, 0.25, 0.75,
            1.0, -0.25, -0.25, 0.25,
        ))

        assert(fa(matrix.inverted.toVector.zip(inv.toVector)))
    }

    "Vector arithmetic operations" should "work correctly" in {
        val vlen = vector.length
        val vdiv = vector2 / 2
        val vmult = vector * 2
        val vadd = vector + vector2
        val vsub = vector - vector2
        val vdot = vector dot vector2
        val vcross = vector cross vector2

        assert(e(vlen, math.sqrt(14)))
        assert(fa(vdiv.toVector.zip(Vec4(1, 1, 0.5).toVector)))
        assert(fa(vmult.toVector.zip(Vec4(2, 4, 6).toVector)))
        assert(fa(vadd.toVector.zip(Vec4(3, 4, 4).toVector)))
        assert(fa(vsub.toVector.zip(Vec4(-1, 0, 2).toVector)))
        assert(e(vdot,9))
        assert(fa(vcross.toVector.zip(Vec4(-4, 5, -2, 0).toVector)))
    }

    "Parsing invalid files" should "throw an error" in {
        assertThrows[CorruptedObjFileException] {
            new MeshObject(ObjParser.loadMesh("resources/invalidObjects/invalidCube1.obj"))
        }
        assertThrows[CorruptedObjFileException] {
            new MeshObject(ObjParser.loadMesh("resources/invalidObjects/invalidCube2.obj"))
        }
        assertThrows[CorruptedObjFileException] {
            new MeshObject(ObjParser.loadMesh("resources/invalidObjects/invalidCube3.obj"))
        }
    }
}
