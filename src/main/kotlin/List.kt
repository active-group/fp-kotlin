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

fun <A> repeat(a: A, n: Int): List<A> {
    var list: List<A> = Empty
    var i: Int = 0;
    while (i < n) {
        list = Cons(a, list)
        i = i + 1
    }
    return list
}

// Elemente einer Liste addieren
fun listSum(list: List<Int>): Int =
    when (list) {
        is Empty -> 0 // neutrales Element von +
        is Cons ->
            list.first + // Kontext
                    listSum(list.rest) // Selbstbezug
    }

// Funktion wie +, *

// Algebra

// Halbgruppe:
// Typ T
// combine: (T, T) -> T
// combine(combine(a, b), c) = combine(a, combine(b, c))

// Monoid
// Halbgruppe +
// neutral: T
// combine(neutral, a) = combine(a, neutral) = a

// Duschprodukte:
// combine(combine(a, b), c) - a, b 25%, c 50%
// combine(a, combine(b, c)) - a 50%, b, c 25%

// Assoziavität
// (a + b) + c = a + (b + c)
// (a * b) * c = a * (b * c)

tailrec
fun listSum1(list: List<Int>, acc: Int): Int =
    when (list) {
        is Empty -> acc
        is Cons -> // kein Kontext: tail call
            listSum1(list.rest, acc + list.first)
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
fun <A, B> listMap(list: List<A>, f: (A) -> B): List<B> =
    when (list) {
        is Empty -> Empty
        is Cons ->
            Cons(f(list.first),
                listMap(list.rest, f))
    }

fun <A, B> listFold(list: List<A>, root: B, combine: (A, B) -> B): B =
    when (list) {
        is Empty -> root
        is Cons ->
            combine(list.first,
                    listFold(list.rest, root, combine)) // Selbstbezug
    }

fun <A> monoidFold(list: List<A>, monoid: Monoid<A>): A =
    when (list) {
        is Empty -> monoid.neutral()
        is Cons ->
            monoid.combine(list.first,
                           monoidFold(list.rest, monoid))
    }

fun <A> empty(): List<A> = Empty

fun <A, B> listMap2(list: List<A>, f: (A) -> B): List<B> =
    listFold(list, empty()) { listDotFirst, rec ->
        Cons(f(listDotFirst), rec)
    }

// Type mismatch: Required Empty, Found Cons<B>

// Aufgabe:
// 2 Listen aneinanderhängen, endrekursiv
// möglicherweise wird eine Hilfsfunktion benötigt

fun <A> append(list1: List<A>, list2: List<A>): List<A> =
    when (list1) {
        is Empty -> list2
        is Cons ->
            Cons(list1.first,
                 append(list1.rest, list2))
    }

// 1 2 3
// 4 5 6
// -> 1 2 3 - 4 5 6
// acc: Die Elemente des Endergebnisses bis auf list1
fun <A> append2F(list1: List<A>, acc: List<A>): List<A> =
    when (list1) {
        is Empty -> acc
        is Cons ->
            append2F(list1.rest, Cons(list1.first, acc))
    }

fun <A> append2(list1: List<A>, list2: List<A>): List<A> {
    var input = list1
    var acc = list2
    while (input != Empty) {
        // val cons = input as Cons
        input = (input as Cons).rest
        acc = Cons((input as Cons).first, acc)
    }
    return acc
}


fun <A> reverse(list: List<A>): List<A> =
    append2(list, Empty)

fun <A> appendT(list1: List<A>, list2: List<A>): List<A> =
    append2(reverse(list1), list2)