/*
- Einfaches Beispiel:
 "Ich bekomme am 24.12.2024 100€."
  "zero-coupon bond" / Zero-Bond

- Beispiel zerlegen in "atomare" Bestandteile / "Ideen"
  Ansatzpunkt: Attribute

  - Währung
    "Ich bekomme 1€ jetzt."

 - Vielfaches
   "Ich bekomme 100€ jetzt."

 - Datum
   "Ich bekomme am 24.12.2024 100€."

 Currency Swap:
 Ich bekomme am 24.12.2024 100€ und
 ich bezahle am 24.12.2024 100$.
 */

/* Ein Vertrag ist einer der folgenden:
- Zero-Coupon-Bond -ODER- ...
- Put -ODER-
- Call -ODER-
- ..
 */

/* Ein Zero-Coupon hat folgende Eigenschaften:
- Datum -UND-
- Betrag -UND-
- Währung
 */

sealed interface Contract

typealias Date = String
typealias Amount = Double

enum class Currency { EUR, USD, GBP, JPY }

/*
data class ZeroCouponBond(val date: Date,
                          val amount: Amount,
                          val currency: Currency): Contract

val zcb1 = ZeroCouponBond("2024-12-24", 100.0, Currency.EUR)
*/

data class One(val currency: Currency): Contract

// "Ich bekomme 1€ jetzt."
val c1 = One(Currency.EUR)

// data class Money(val amount: Amount, val currency: Currency): Contract
// refaktorisieren:
data class Multiple(val amount: Amount, val contract: Contract): Contract

// "Ich bekomme 100€ jetzt."
val c2 = Multiple(100.0, One(Currency.EUR))

// "Ich bekomme 5000€ jetzt."
val c3 = Multiple(50.0, Multiple(100.0, One(Currency.EUR)))

data class Later(val date: Date, val contract: Contract): Contract

val c4 = Later("2024-12-24", Multiple(100.0, One(Currency.EUR)))

fun zeroCouponBond(date: Date,
                   amount: Amount,
                   currency: Currency): Contract =
    Later(date, Multiple(amount, One(currency)))

val zcb1 = zeroCouponBond("2024-12-24", 100.0, Currency.EUR)
