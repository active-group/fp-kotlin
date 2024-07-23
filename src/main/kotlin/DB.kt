/*
put("Mike", 100)
x = get("Mike")
put("Mike", x+1)
y = get("Mike")
return (x+y).toString()

Beschreibung ^^^ eines DB-Programms als Objekt ("DSL")

 */

typealias Key = String
typealias Value = Int

/*
sealed interface DBCommand<A> // A Typ des Resultats

data class Put(val key: Key, val value: Value): DBCommand<Nothing>
data class Get(val key: Key): DBCommand<Nothing>
data class Return<A>(val result: A): DBCommand<A>

val p1 =
    Cons(
        Put("Mike", 100),
        Cons(Get("Mike"),
    )
 */

sealed interface DB<A> {
    // in Stream<A>
    // <B> Stream<B> flatMap(Function<A, Stream<B>> mapper)
    // fun <B> flatMap(next: (A) -> Stream<B>): Stream<B>

    // Funktor
    // fun <B> map( f:    (A) -> B): F<B>

    // applikativer Funktop
    // fun <B> amap(vf: F<(A) -> B): F<B>

    // Monade
    // fun <B> splice(next: (A) -> F<B>): F<B>

    // this und (Ergebnis von) next aneinanderhängen
    fun <B> splice(next: (A) -> DB<B>): DB<B> =
        when (this) {
            is Get ->
                Get(this.key) { value ->
                    this.continuation(value).splice(next)
                }
            is Put ->
                Put(this.key, this.value) { unit ->
                    this.continuation(unit).splice(next)
                }
            is Return ->
                next(this.result)
        }
}
data class Get<A>(val key: Key, val continuation: (Value) -> DB<A>): DB<A>
data class Put<A>(val key: Key, val value: Value,
    val continuation: (Unit) -> DB<A>): DB<A>
data class Return<A>(val result: A): DB<A>

// Typkonstruktor, Return und splice zusammen: Monade

val prog1: DB<String> =
    Put("Mike", 50) { _ -> // Unit
        Get("Mike") { x ->
            Put("Mike", x+1) { // _ -> kann man auch weglassen
                Get("Mike") { y ->
                    Return((x+y).toString())
                }
            }
        }
    }

fun get(key: Key): DB<Value> = Get(key) { Return(it) }
fun put(key: Key, value: Value): DB<Unit> =
    Put(key, value) { Return(it) }

val prog1a: DB<String> =
    put("Mike", 50).splice { _ ->
        get("Mike").splice { x ->
            put("Mike", x+1).splice {
                get("Mike").splice { y ->
                    Return ((x+y).toString())
                }
            }
        }
    }

fun <A> runDB(program: DB<A>, db: Map<Key, Value>): A =
    when (program) {
        is Get -> {
            val value = db.get(program.key)!!
            runDB(program.continuation(value), db)
        }
        is Put -> {
            val dbNew = db + Pair(program.key, program.value)
            // db[program.key] = program.value
            runDB(program.continuation(Unit), dbNew)
        }
        is Return -> program.result
    }