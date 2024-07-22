/*
Tiere auf dem texanischen Highway:

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

data class Dillo(val liveness: Liveness,
                 val weight: Weight)

// lebendiges Güretltier, 10kg
val dillo1 = Dillo(Liveness.ALIVE, 10.0)
// totes Gürteltier, 8kg
val dillo2 = Dillo(Liveness.DEAD, 8.0)
// data class: Standard-Implementierungen
// für Konstruktor, equals, hashCode, toString, copy