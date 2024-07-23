interface Monoid<A>: Semigroup<A> {
    fun neutral(): A
}

class ListMonoid<A>: ListSemigroup<A>(), Monoid<List<A>> {
    override fun neutral() = Empty
}

fun <A> listMonoid() = ListMonoid<A>()

class OptionMonoid<A>(val aMonoid: Monoid<A>): Monoid<Option<A>>, OptionSemigroup<A>(aMonoid) {
    override fun neutral(): Option<A> = None
}