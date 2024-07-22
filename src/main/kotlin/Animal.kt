/*
Tier auf dem texanischen Highway ist eins der folgenden:
- Gürteltier -ODER-
- Klapperschlange
Fallunterscheidung
hier: gemischte Daten
*/
interface Animal

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
    fun runOver(): Dillo =
        // Dillo(Liveness.DEAD, this.weight)
        // "functional update"
        this.copy(liveness = Liveness.DEAD)
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
                       val thickness: Double) : Animal

// Tier überfahren
// gemischte Daten rein
fun runOverAnimal(animal: Animal): Animal =
    // Verzweigung
    when (animal) {
        is Dillo -> TODO()
        // is Rattlesnake -> TODO()
    }

