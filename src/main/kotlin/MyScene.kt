import com.soywiz.korev.Key
import com.soywiz.korge.input.keys
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korge.view.circle
import com.soywiz.korge.view.line
import com.soywiz.korge.view.solidRect
import com.soywiz.korge.view.xy
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.launch
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.PointInt
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class MyScene : Scene() {

    private val minWidth = 3
    private val maxWidth = 35

    private val minHeight = 3
    private val maxHeight = 35

    private val amountRooms = 20
    private val radius = 200

    private val tryToPlaceRoomRepeatAmount = 0

    override suspend fun SContainer.sceneMain() {
        cameraContainer(scaledWidth, scaledHeight, false) {
            val dungeon = Dungeon()
            val minStep = (radius / amountRooms).coerceAtLeast(1)
            for (currentSpread in minStep..radius step minStep) {
                tryToPlaceRoom(currentSpread, dungeon)
                    ?.also { dungeon.rooms.add(it) }
            }
            dungeon.rooms.forEach {
                solidRect(it.width, it.height).xy(it.x, it.y)
            }

            // TODO https://en.wikipedia.org/wiki/Prim%27s_algorithm
//            PrimAlgorithmInt(
//                dungeon.rooms
//                    .map { it.getRandomPoint(1) }
//                    .toTypedArray(),
//            ).run().forEach {
////                line(it.first.p, it.second.p, Colors.BLUE)
//                connectPoints(it.first, it.second)
//            }
            // TODO https://en.wikipedia.org/wiki/Delaunay_triangulation
//            Delaunator(dungeon.rooms.map { it.getRandomPoint(1) }).getEdges().forEach {
////                line(it.p1.p, it.p2.p, Colors.BLUE)
//                connectPoints(it.p1, it.p2)
//            }

            // TODO try: https://en.wikipedia.org/wiki/Minimum-weight_triangulation
            // TODO try: https://en.wikipedia.org/wiki/Gabriel_graph

            // TODO try: https://en.wikipedia.org/wiki/Relative_neighborhood_graph (looks the best on wiki)
            getRNG(dungeon.rooms.map { it.getRandomPoint(1) }).forEach {
                connectPoints(it.source, it.target)
            }

//            for (i in 0 until dungeon.rooms.size - 1) {
//                connectPointsWithRectangles(
//                    dungeon.rooms[i].getRandomPoint(border = 1),
//                    dungeon.rooms[i + 1].getRandomPoint(border = 1),
//                )
//                    .forEach { solidRect(it.width, it.height, Colors.BLUE).xy(it.x, it.y) }
//            }

            circle(
                radius.toDouble(),
                fill = Colors.TRANSPARENT_BLACK,
                stroke = Colors.RED,
                strokeThickness = 2.0,
            ).apply {
                x = scaledWidth * -0.5
                y = scaledHeight * -0.5
            }
        }.apply {
            cameraZoom = 2.0
            x = scaledWidth / 2 * cameraZoom
            y = scaledHeight / 2 * cameraZoom
        }

        keys {
            down { event ->
                when (event.key) {
                    Key.R -> launch { sceneContainer.changeTo<MyScene>() }
                    else -> Unit
                }
            }
        }
    }

    fun Container.connectPoints(p1: PointInt, p2: PointInt) {
        val dx = p2.x - p1.x
        val dy = p2.y - p1.y
        if (dx == 0 || dy == 0) {
            // Points are already aligned, generate a single line
            line(p1.x.toDouble(), p1.y.toDouble(), p2.x.toDouble(), p2.y.toDouble(), Colors.BLUE)
        } else {
            // Generate L-shaped lines
            val p3 = PointInt(p1.x + dx, p1.y)
            val p4 = PointInt(p3.x, p3.y + dy)
            line(p1.x.toDouble(), p1.y.toDouble(), p3.x.toDouble(), p3.y.toDouble(), Colors.BLUE)
            line(p3.x.toDouble(), p3.y.toDouble(), p4.x.toDouble(), p4.y.toDouble(), Colors.BLUE)
            line(p4.x.toDouble(), p4.y.toDouble(), p2.x.toDouble(), p2.y.toDouble(), Colors.BLUE)
        }
    }

    private fun randomPointOnCircumference(radius: Double): PointInt {
        val angle = Math.random() * 2 * PI
        val x = radius * cos(angle)
        val y = radius * sin(angle)
        return PointInt(x.toInt(), y.toInt())
    }

    private fun tryToPlaceRoom(currentSpread: Int, dungeon: Dungeon): DungeonRoom? {
        repeat(tryToPlaceRoomRepeatAmount + 1) {
            val newRoom = DungeonRoom(
                point = randomPointOnCircumference(currentSpread.toDouble()),
                width = (minWidth..maxWidth).random(),
                height = (minHeight..maxHeight).random(),
            )
            if (dungeon.rooms.none { it.overlapsWith(newRoom, 2) }) {
                return newRoom
            }
        }
        return null
    }

    // TODO chaos tunnels
//    fun connectCoordinates(coord1: PointInt, coord2: PointInt): List<DungeonRoom> {
//        val path = mutableListOf<DungeonRoom>()
//
//        if (coord1.x == coord2.x) {
//            // Same X coordinate, draw a vertical line
//            val minY = minOf(coord1.y, coord2.y)
//            val maxY = maxOf(coord1.y, coord2.y)
//            path.add(DungeonRoom(coord1.x, minY, 1, maxY - minY + 1))
//        } else if (coord1.y == coord2.y) {
//            // Same Y coordinate, draw a horizontal line
//            val minX = minOf(coord1.x, coord2.x)
//            val maxX = maxOf(coord1.x, coord2.x)
//            path.add(DungeonRoom(minX, coord1.y, maxX - minX + 1, 1))
//        } else {
//            // Different X and Y coordinates, draw an L-shaped path
//            val middleCoord = PointInt(coord2.x, coord1.y)
//            path.addAll(connectCoordinates(coord1, middleCoord))
//            path.addAll(connectCoordinates(middleCoord, coord2))
//        }
//
//        return path
//    }

    // TODO horizontal tunnels
//    private fun connectPointsWithRectangles(points: List<PointInt>): List<DungeonRoom> {
//        val sortedPoints = points.sortedWith(compareBy(PointInt::y, PointInt::x))
//        val rectangles = mutableListOf<DungeonRoom>()
//        var currentPoint = sortedPoints[0]
//        for (i in 1 until sortedPoints.size) {
//            val nextPoint = sortedPoints[i]
//            if (nextPoint.x == currentPoint.x) {
//                // Create a vertical rectangle
//                val rect = if (nextPoint.y > currentPoint.y) {
//                    DungeonRoom(currentPoint.x, currentPoint.y, 1, nextPoint.y - currentPoint.y)
//                } else {
//                    DungeonRoom(currentPoint.x, nextPoint.y, 1, currentPoint.y - nextPoint.y)
//                }
//                rectangles.add(rect)
//            } else if (nextPoint.y == currentPoint.y) {
//                // Create a horizontal rectangle
//                val rect = if (nextPoint.x > currentPoint.x) {
//                    DungeonRoom(currentPoint.x, currentPoint.y, nextPoint.x - currentPoint.x, 1)
//                } else {
//                    DungeonRoom(nextPoint.x, currentPoint.y, currentPoint.x - nextPoint.x, 1)
//                }
//                rectangles.add(rect)
//            } else {
//                // Create an L-shaped rectangle
//                val rect1 = if (nextPoint.x > currentPoint.x) {
//                    DungeonRoom(currentPoint.x, currentPoint.y, nextPoint.x - currentPoint.x, 1)
//                } else {
//                    DungeonRoom(nextPoint.x, currentPoint.y, currentPoint.x - nextPoint.x, 1)
//                }
//                val rect2 = if (nextPoint.y > currentPoint.y) {
//                    DungeonRoom(nextPoint.x, currentPoint.y, 1, nextPoint.y - currentPoint.y)
//                } else {
//                    DungeonRoom(nextPoint.x, nextPoint.y, 1, currentPoint.y - nextPoint.y)
//                }
//                rectangles.add(rect1)
//                rectangles.add(rect2)
//            }
//            currentPoint = nextPoint
//        }
//        return rectangles
//    }

    class PrimAlgorithm(
        private val vertices: Array<Point>,
        private val size: Int,
    ) {
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

        private fun euclideanDistance(a: Point, b: Point): Double {
            return sqrt((a.x - b.x).pow(2) + (a.y - b.y).pow(2))
        }
    }
}

class DungeonRoom(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    var connected: Boolean = false, // maybe not needed
) {
    constructor(
        point: PointInt,
        width: Int,
        height: Int,
    ) : this(point.x, point.y, width, height)

    fun overlapsWith(
        other: DungeonRoom,
        border: Int = 0,
    ) = x + width + border > other.x &&
        x < other.x + other.width + border &&
        y + height + border > other.y &&
        y < other.y + other.height + border

    fun getRandomPoint(border: Int = 0): PointInt {
        // Calculate the maximum X and Y values for the point inside the rectangle
        val maxX = width - 2 * border
        val maxY = height - 2 * border

        // Generate random X and Y values and adjust them to fit within the maximum values
        val randomX = Random.nextInt(border, border + maxX)
        val randomY = Random.nextInt(border, border + maxY)

        // Add the border space back to get the final random point
        val pointX = x + randomX
        val pointY = y + randomY

        return PointInt(pointX, pointY)
    }
}

data class Dungeon(
    val rooms: MutableList<DungeonRoom> = mutableListOf(),
)
