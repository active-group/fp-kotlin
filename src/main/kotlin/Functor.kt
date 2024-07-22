interface Functor<F> {
    // Type arguments are not allowed for type parameters
    // fun <A, B> fmap(thing: F<A>, f: (A) -> B): F<B>
}