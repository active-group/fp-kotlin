/*
Yaron Minsky: "Make illegal states unrepresentable."
1. mit Typen sicherstellen, daß nur "legale" Objekte möglich sind
2. "illegale" Objekte gar nicht erst herstellen
 */

data class Person(val name: String, val age: Int)

fun makePerson(name: String, age: Int): Option<Person> =
    if (age >= 0)
        Some(Person(name, age))
    else
        None

sealed interface Validation<A>
data class Valid<A>(val value: A)
data class Invalid(val errors: List<String>)