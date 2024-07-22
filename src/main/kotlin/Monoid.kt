interface Monoid<A>: Semigroup<A> {
    fun neutral(): A
}

class ListMonoid<A>: ListSemigroup<A>() {
    fun neutral() = Empty
}