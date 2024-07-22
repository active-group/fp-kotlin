interface Functor<F> {
    fun <A, B> fmap(thing: F<A>, f: (A) -> B): F<B>
}