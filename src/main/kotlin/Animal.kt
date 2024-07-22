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

enum class Liveness { DEAD, ALIVE }
