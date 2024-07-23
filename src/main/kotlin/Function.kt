fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C =
    { a -> f(g(a)) }

// Haskell B. Curry
// Moses Schönfinkel
fun <A, B, C> curry(f: (A, B) -> C):
            (A) -> ((B) -> C) =
    { a -> { b -> f(a, b)}}

fun <A, B, C> uncurry(f:  (A) -> ((B) -> C)): (A, B) -> C =
    { a, b -> f(a)(b) }

fun <A, B, C, D> curry(f: (A, B, C) -> D):
        (A) -> ((B) -> (C) -> D) =
    { a -> { b -> { c -> f(a, b, c) }}}

// Trick aus dem frühen Arrow
interface Kind<out F, out A>

class ForList private constructor() {
    companion object
}

typealias ListOf<A> = Kind<ForList, A>

fun <A> ListOf<A>.fix() = this as List<A>

interface Functor<F> {
    // where does the A go?
    fun <A, B> map(f: (A) -> B, thingk: Kind<F, A>): Kind<F, B>
}
