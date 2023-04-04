import com.soywiz.korma.geom.PointInt
import kotlin.math.sqrt

private fun distance(p1: PointInt, p2: PointInt): Double {
    val dx = p1.x - p2.x
    val dy = p1.y - p2.y
    return sqrt(dx * dx + dy * dy.toDouble())
}

private fun isMutualNeighbor(p1: PointInt, p2: PointInt, points: List<PointInt>): Boolean {
    val dist = distance(p1, p2)
    for (point in points) {
        if (point != p1 && point != p2) {
            val dist1 = distance(p1, point)
            val dist2 = distance(p2, point)
            if (dist1 < dist && dist2 < dist) {
                return false
            }
        }
    }
    return true
}

fun getRNG(points: List<PointInt>): List<Edge2> {
    val edges = mutableListOf<Edge2>()
    for (i in 0 until points.size - 1) {
        for (j in i + 1 until points.size) {
            if (isMutualNeighbor(points[i], points[j], points)) {
                edges.add(Edge2(points[i], points[j]))
            }
        }
    }
    return edges
}

data class Edge2(val source: PointInt, val target: PointInt)
