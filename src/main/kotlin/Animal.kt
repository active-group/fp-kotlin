/*
Tier auf dem texanischen Highway ist eins der folgenden:
- Gürteltier -ODER-
- Klapperschlange
Fallunterscheidung
hier: gemischte Daten
*/
sealed interface Animal {
    fun runOver(): Animal
    fun feed(): Animal
}

/*
Gürteltier hat folgende Eigenschaften:
- (lebendig ODER tot) - UND -
- Gewicht
zusammengesetzte Daten

Lebendigkeit ist eins der folgenden:
- lebendig -ODER-
- tot
Fallunterscheidung
hier: Aufzählung
 */

enum class Liveness {
    ALIVE,
    DEAD
}

// Gewicht ist eine Zahl (kg)
typealias Weight = Double

/*
Beschreibung des Zustands eines Gürteltiers
zu einem bestimmten Zeitpunkt.
 */

// data class: Standard-Implementierungen
// für Konstruktor, equals, hashCode, toString, copy
data class Dillo(val liveness: Liveness,
                 val weight: Weight): Animal {
    // Problem: das Gürteltier überfährt sich nicht selbst
    override fun runOver(): Animal =
        // Dillo(Liveness.DEAD, this.weight)
        // "functional update"
        this.copy(liveness = Liveness.DEAD)
    override fun feed(): Animal =
        Dillo(this.liveness, this.weight+1.0)
}

// lebendiges Güretltier, 10kg
val dillo1 = Dillo(Liveness.ALIVE, 10.0)
// totes Gürteltier, 8kg
val dillo2 = Dillo(Liveness.DEAD, 8.0)

// Dillo rein, Dillo rein
// jeweils zusammengesetzte Daten
fun runOverDillo(dillo: Dillo): Dillo =
    Dillo(Liveness.DEAD, dillo.weight)

/*
Eine Klapperschlange hat folgende Eigenschaften:
- Länge -UND-
- Dicke
zusammengesetzte Daten
 */
data class Rattlesnake(val length: Double,
                       val thickness: Double)
    : Animal {

    override fun runOver(): Animal =
        Rattlesnake(this.length, 0.0)
    override fun feed(): Animal =
        Rattlesnake(this.length, this.thickness+1.0)
}

data class HouseCat(val name: String, val lives: Int): Animal {
    override fun runOver(): Animal =
        HouseCat(this.name, this.lives-1)
    override fun feed(): Animal =
        HouseCat(this.name, this.lives+1)
}

val dillo1FedAndRunOver =
    // runOverAnimal(feedAnimal(dillo1))
    // dillo1.feed().runOver()
    dillo1.call(::runOverAnimal).call(::feedAnimal)

fun <A, B> A.call(f: (A) -> B): B =
    f(this)



/*
Neue Fälle in existierenden gemischten Daten:
- FP :-(
- OOP :-)

Neue Funktionen:
- FP :-)
- OOP :-(
 */



// Tier überfahren
// gemischte Daten rein
fun runOverAnimal(animal: Animal): Animal =
    // Verzweigung
    when (animal) {
        is Dillo -> runOverDillo(animal)
        is Rattlesnake ->
            Rattlesnake(animal.length, 0.0)
        is HouseCat ->
            HouseCat(animal.name, animal.lives-1)
    }

fun feedAnimal(animal: Animal): Animal =
    when (animal) {
        is Dillo ->
            Dillo(animal.liveness, animal.weight + 1.0)
        is Rattlesnake ->
            Rattlesnake(animal.length, animal.thickness+1.0)
        is HouseCat ->
            HouseCat(animal.name, animal.lives+1)
    }

// Klapperschlange 2m lang, 10cm dick
val snake1 = Rattlesnake(200.0, 10.0)