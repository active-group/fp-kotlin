interface Semigroup<T> {
    fun combine(t1: T, t2: T): T
}

fun <A> listSemigroup(): Semigroup<List<A>> {

}