interface Semigroup<T> {
    // combine(combine(a, b), c) = combine(a, combine(b, c))
    fun combine(t1: T, t2: T): T
}

class ListSemigroup<A>: Semigroup<List<A>> {
    override fun combine(t1: List<A>, t2: List<A>): List<A> =
        append(t1, t2)
}

fun <A> listSemigroup() = ListSemigroup<A>()