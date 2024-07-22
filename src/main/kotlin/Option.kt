sealed interface Option<out A>

object None: Option<Nothing>
data class Some<A>(val value: A): Option<A>

// Index eines Elements in einer Liste finden
fun <A> listIndex(list: List<A>, element: A): Option<Int> =
    when (list) {
        is Empty -> None
        is Cons ->
            if (list.first == element)
                Some(0)
            else
                when (val res =  listIndex(list.rest, element)) {
                    is None -> None
                    is Some -> Some(res.value + 1)
                }
    }

fun <A, B> optionMap(option: Option<A>, f: (A) -> B): Option<B> =
    when (option) {
        is None -> None
        is Some -> Some(f(option.value))
    }