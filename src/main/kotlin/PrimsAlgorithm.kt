import kotlin.math.pow
import kotlin.math.sqrt

data class Vertex(val x: Double, val y: Double)

private class PrimAlgorithm(private val vertices: Array<Vertex>, private val size: Int) {
    private val visited = BooleanArray(size) { false }
    private val distances = DoubleArray(size) { Double.POSITIVE_INFINITY }
    private val parents = IntArray(size) { -1 }

    fun run(startNode: Int) {
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
    }

    private fun getClosestUnvisited(): Int {
        var minDistance = Double.POSITIVE_INFINITY
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

    private fun euclideanDistance(a: Vertex, b: Vertex): Double {
        return sqrt((a.x - b.x).pow(2) + (a.y - b.y).pow(2))
    }
}

fun main() {
    val vertices = arrayOf(
        Vertex(0.0, 0.0),
        Vertex(2.0, 0.0),
        Vertex(1.0, 1.0),
        Vertex(0.0, 2.0),
        Vertex(2.0, 2.0),
    )
    val prim = PrimAlgorithm(vertices, 5)
    prim.run(0)
    prim.printMST()
}
