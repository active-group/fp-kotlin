interface Semigroup<T> {
    // combine(combine(a, b), c) = combine(a, combine(b, c))
    fun combine(t1: T, t2: T): T
}

open class ListSemigroup<A>: Semigroup<List<A>> {
    override fun combine(t1: List<A>, t2: List<A>): List<A> =
        append(t1, t2)
}

fun <A> listSemigroup() = ListSemigroup<A>()

class PairSemigroup<A, B>(val semiA: Semigroup<A>, val semiB: Semigroup<B>):
        Semigroup<Pair<A, B>> {
    override fun combine(pair1: Pair<A, B>, pair2: Pair<A, B>): Pair<A, B> =
        Pair(semiA.combine(pair1.first, pair2.first),
             semiB.combine(pair1.second, pair2.second))
}

val pair1: Pair<Int, String> = Pair(5, "Mike")