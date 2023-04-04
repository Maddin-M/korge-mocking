import com.soywiz.korma.geom.PointInt
import kotlin.math.pow
import kotlin.math.sqrt

class PrimAlgorithmInt(
    private val vertices: Array<PointInt>,
) {
    private val size = vertices.size
    private val visited = BooleanArray(size) { false }
    private val distances = DoubleArray(size) { Double.MAX_VALUE }
    private val parents = IntArray(size) { -1 }

    fun run(startNode: Int = 0): List<Pair<PointInt, PointInt>> {
        distances[startNode] = 0.0
        for (i in 0 until size - 1) {
            val current = getClosestUnvisited()
            visited[current] = true
            for (j in 0 until size) {
                if (!visited[j]) {
                    val distance = euclideanDistance(vertices[current], vertices[j])
                    if (distance < distances[j]) {
                        distances[j] = distance
                        parents[j] = current
                    }
                }
            }
        }

//        return List(vertices.slice(1 until vertices.size - 1).size) { i -> vertices[i] to vertices[parents[i]] }

        return vertices
            .sliceArray(1 until size)
            .mapIndexed { i, _ -> vertices[i + 1] to vertices[parents[i + 1]] }
    }

    private fun getClosestUnvisited(): Int {
        var minDistance = Double.MAX_VALUE
        var closestNode = -1
        for (i in 0 until size) {
            if (!visited[i] && distances[i] < minDistance) {
                minDistance = distances[i]
                closestNode = i
            }
        }
        return closestNode
    }

    fun printMST() {
        println("Minimum Spanning Tree:")
        for (i in 1 until size) {
            println("Edge: ${parents[i]} - $i, weight: ${distances[i]}")
        }
    }

    fun printPoints() {
        println("Minimum Spanning Tree:")
        for (i in 1 until size) {
            println("Edge: ${vertices[parents[i]]} - ${vertices[i]}")
        }
    }

    private fun euclideanDistance(a: PointInt, b: PointInt): Double {
        return sqrt((a.x - b.x).toDouble().pow(2.0) + (a.y - b.y).toDouble().pow(2.0))
    }
}

fun main() {
    val vertices = arrayOf(
        PointInt(0, 0),
        PointInt(2, 0),
        PointInt(1, 1),
        PointInt(0, 2),
        PointInt(2, 2),
    )
    val prim = PrimAlgorithmInt(vertices)
    prim.run(0)
    prim.printMST()
}
