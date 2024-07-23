import java.security.InvalidKeyException

/*
Yaron Minsky: "Make illegal states unrepresentable."
1. mit Typen sicherstellen, daß nur "legale" Objekte möglich sind
2. "illegale" Objekte gar nicht erst herstellen
 */

data class Person(val name: String, val age: Int)

fun makePersonOptional(name: String, age: Int): Option<Person> =
    if (name == "")
        None
    else
        if (age >= 0)
            Some(Person(name, age))
        else
            None

sealed interface Validation<out A> {
    fun <B> map(f: (A) -> B): Validation<B> =
        when (this) {
            is Valid -> Valid(f(this.value))
            is Invalid ->
                Invalid(this.errors)
        }

    // applikativer Funktor
    // Wunsch: beliebigstelliges map
    fun <B> amap(vf: Validation<(A) -> B>): Validation<B> =
        when (this) {
            is Valid ->
                when (vf) {
                    is Valid ->
                        Valid(vf.value(this.value))
                    is Invalid -> Invalid(vf.errors)
                }
            is Invalid ->
                when (vf) {
                    is Valid -> Invalid(this.errors)
                    is Invalid -> Invalid(append(this.errors, vf.errors))
                }
        }
}
data class Valid<A>(val value: A): Validation<A>
data class Invalid(val errors: List<String>): Validation<Nothing>

fun validateName(name: String): Validation<String> =
    if (name == "")
        Invalid(Cons("Name darf nicht leer sein", Empty))
    else
        Valid(name)

fun validateAge(age: Int): Validation<Int> =
    if (age >= 0)
        Valid(age)
    else
        Invalid(Cons("Alter darf nicht negativ sein", Empty))

fun <A, B, C> vmap2Old(valA: Validation<A>, valB: Validation<B>, f: (A, B) -> C):
        Validation<C> =
    when (valA) {
        is Valid ->
            when (valB) {
                is Valid ->
                    Valid(f(valA.value, valB.value))
                is Invalid ->
                    Invalid(valB.errors)
            }
        is Invalid ->
            when (valB) {
                is Valid -> Invalid(valA.errors)
                is Invalid ->
                    Invalid(append(valA.errors, valB.errors))
            }
    }

// fun <B> amap(vf: Validation<(A) -> B>): Validation<B> =

fun <A, B, C> vmap2(valA: Validation<A>, valB: Validation<B>, f: (A, B) -> C):
        Validation<C> =
    valB.amap(valA.amap(Valid(curry(f))))

fun <A, B, C, D> vmap3(valA: Validation<A>, valB: Validation<B>, valC: Validation<C>,
                       f: (A, B, C) -> D): Validation<D> =
    valB.amap(valA.amap(Valid(curry(f)))))


// valA.amap(Valid(curry(f))) führt zu
// Type mismatch:
// Required Validation<C>
// Found Validation<(B) -> C>




data class Person1(val name: String)

fun makePerson1(name: String): Validation<Person1> =
    validateName(name).map(::Person1)

fun makePerson(name: String, age: Int): Validation<Person> =
    if (name == "")
        if (age >= 0)
            Invalid(Cons("Name darf nicht leer sein", Empty))
        else
            Invalid(Cons("Name darf nicht leer sein", Cons("Alter darf nicht negativ sein", Empty)))
    else
        if (age >= 0)
            Valid(Person(name, age))
        else
            Invalid(Cons("Alter darf nicht negativ sein", Empty))

