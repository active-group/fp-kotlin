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

sealed interface Validation<out A>
data class Valid<A>(val value: A): Validation<A>
data class Invalid(val errors: List<String>): Validation<Nothing>

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

