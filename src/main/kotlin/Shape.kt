// "Geometric Region Server"

// Eine (geometrische) Figur ist eins der folgenden:
// - ein Kreis
// - ein Quadrat
// - eine Überlagerung zweier geometrischer Figuren

// 1. Datenmodellierung
// 2. Funktion, die ermittelt, ob ein gegebener Punkt
//    innerhalb oder außerhalb einer Figur ist

data class Point(val x: Double, val y: Double)

sealed interface Shape

data class Circle(val center: Point, val radius: Double): Shape

data class Square(val llCorner: Point, val sideLength: Double): Shape

data class Overlay(val shape1: Shape, val shape2: Shape): Shape

fun distance(point1: Point, point2: Point): Double {
    val dx = point1.x - point2.x
    val dy = point1.y - point2.y
    return Math.sqrt(dx * dx + dy * dy)
}


fun isIn(point: Point, shape: Shape): Boolean =
    when (shape) {
        is Circle ->
            distance(point, shape.center) <= shape.radius
        is Square -> {
            val ll = shape.llCorner
            (ll.x <= point.x) && (point.x <= ll.x + shape.sideLength) &&
                    (ll.y <= point.y) && (point.y <= ll.y + shape.sideLength)
        }
        is Overlay ->
            isIn(point, shape.shape1) || isIn(point, shape.shape2)
    }