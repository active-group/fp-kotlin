interface Monoid<A>: Semigroup<A> {
    fun neutral(): A
}

class ListMonoid<A>: ListSemigroup<A>(), Monoid<List<A>> {
    override fun neutral() = Empty
}

fun <A> listMonoid() = ListMonoid<A>()

class OptionMonoid<A>(val aSemigroup: Semigroup<A>): Monoid<Option<A>>, OptionSemigroup<A>(aSemigroup) {
    override fun neutral(): Option<A> = None
}