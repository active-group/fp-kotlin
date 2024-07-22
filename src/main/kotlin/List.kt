/*
Liste ist eins der folgenden:
- die leere Liste -ODER-
- eine Cons-Liste bestehend aus erstem Element und Rest-Liste
                                                        ^^^^^
                                                        Selbstbezug
 */

sealed interface List<out A> {
}

// extension method
fun <A> List<A>.filter(p: (A) -> Boolean): List<A> =
    when (this) {
        is Empty -> Empty
        is Cons ->
            if (p(this.first))
               Cons(this.first,  this.rest.filter(p))
            else
                this.rest.filter(p)
    }

object Empty : List<Nothing>

/* Eine Cons-Liste besteht aus:
 - erstes Element -UND-
 - Rest-Liste
 */

data class Cons<A>(val first: A, val rest: List<A>) : List<A>

// 1elementige Liste: 5:Int
val list1: List<Int> = Cons(5, Empty)
// 2elementige Liste: 2 5
val list2 = Cons(2, Cons(5, Empty))
// 3elementige Liste: 7 2 5
val list3 = Cons(7, list2)
val list4 = Cons(10, list3)

// Elemente einer Liste addieren
fun listSum(list: List<Int>): Int =
    when (list) {
        is Empty -> 0 // neutrales Element von +
        is Cons ->
            list.first +
                    listSum(list.rest) // Selbstbezug
    }

// val sum1 = listSum(list1)

// Elemente einer Liste multiplizieren
fun listProduct(list: List<Int>): Int =
    when (list) {
        is Empty -> 1 // neutrales Element von *
        is Cons ->
            list.first *
                listProduct(list.rest)
    }

val highway = Cons(dillo1, Cons(dillo2, Cons(snake1, Empty)))

// neutrales Element:
// x + 0 = 0 + x = x
// x * 1 = 1 * x = x
// x * 1 = 1 * x = x

fun isEven(n: Int): Boolean =
    n % 2 == 0

fun listEvens(list: List<Int>): List<Int> =
    when (list) {
        is Empty -> Empty
        is Cons ->
            if (isEven(list.first))
                Cons(list.first, listEvens(list.rest))
            else
                listEvens(list.rest)
    }

// Funktionen höherer Ordnung
// "higher-order functions"
fun <A>listFilter(list: List<A>, p: (A) -> Boolean): List<A> =
    when (list) {
        is Empty -> Empty
        is Cons ->
            if (p(list.first))
                Cons(list.first, listFilter(list.rest, p))
            else
                listFilter(list.rest, p)
    }

// syntaktischer Zucker:
// letzter Argument ist eine Funktion
val list4Evens = listFilter(list4) { n -> isEven(n) }

val dillos = Cons(dillo1, Cons(dillo2, Empty))

// Eine Funktion auf alle Elemente einer Liste anwenden
fun <A> listMap(list: List<A>, f: (A) -> A): List<A> =
    when (list) {
        is Empty -> Empty
        is Cons ->
            Cons(f(list.first),
                listMap(list.rest, f))
    }