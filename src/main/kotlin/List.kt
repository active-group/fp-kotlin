/*
Liste ist eins der folgenden:
- die leere Liste -ODER-
- eine Cons-Liste bestehend aus erstem Element und Rest-Liste
                                                        ^^^^^
                                                        Selbstbezug
 */

sealed interface List

object Empty : List

/* Eine Cons-Liste besteht aus:
 - erstes Element -UND-
 - Rest-Liste
 */

data class Cons(val first: Int, val rest: List) : List

// 1elementige Liste: 5
val list1 = Cons(5, Empty)
// 2elementige Liste: 2 5
val list2 = Cons(2, Cons(5, Empty))
// 3elementige Liste: 7 2 5
val list3 = Cons(7, list2)
val list4 = Cons(10, list3)

// Elemente einer Liste addieren
fun listSum(list: List): Int =
    when (list) {
        is Empty -> 0 // neutrales Element von +
        is Cons ->
            list.first +
                    listSum(list.rest) // Selbstbezug
    }

// Elemente einer Liste multiplizieren
fun listProduct(list: List): Int =
    when (list) {
        is Empty -> 1 // neutrales Element von *
        is Cons ->
            list.first *
                listProduct(list.rest)
    }

// neutrales Element:
// x + 0 = 0 + x = x
// x * 1 = 1 * x = x
// x * 1 = 1 * x = x