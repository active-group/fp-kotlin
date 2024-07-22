sealed interface Option<out A>

object None: Option<Nothing>
data class Some<A>(val value: A): Option<A>

// Index eines Elements in einer Liste finden
fun <A> listIndex(list: List<A>, element: A): Option<Int> =
    when (list) {
        is Empty -> None
        is Cons -> TODO()
    }