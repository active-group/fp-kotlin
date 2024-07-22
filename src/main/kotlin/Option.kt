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
                optionMap(listIndex(list.rest, element)) { it + 1 }
                /*
                when (val res =  listIndex(list.rest, element)) {
                    is None -> None
                    is Some -> Some(res.value + 1)
                }

                 */
    }

// Funktor
// Typkonstruktor (Typ mit Generic-Parameter)
// ... hat map-artige-Funktion

//fun <A, B> listMap(list:   List  <A>, f: (A) -> B): List  <B> =
fun <A, B> optionMap(option: Option<A>, f: (A) -> B): Option<B> =
    when (option) {
        is None -> None
        is Some -> Some(f(option.value))
    }