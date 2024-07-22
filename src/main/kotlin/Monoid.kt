interface Monoid<A>: Semigroup<A> {
    fun neutral(): A
}

class ListMonoid<A>: ListSemigroup<A>(), Monoid<List<A>> {
    override fun neutral() = Empty
}

fun <A> listMonoid() = ListMonoid<A>()