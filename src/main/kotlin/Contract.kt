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
 Ich bekomme am 24.12.2024 92€ und
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

// "Ich bezahle ..."

enum class Direction { LONG, SHORT }

// data class WithDirection(val direction: Direction, val contract: Contract): Contract

data class Reverse(val contract: Contract): Contract

// "Ich bezahle am 24.12.2024 100$."
val c5 = Reverse(
    zeroCouponBond("2024-12-24", 100.0, Currency.USD))

// "Ich bekomme ..."

val c6 = Reverse(c5)

// das gleiche wie c5
// val c7 = WithDirection(Direction.LONG, c5)

// Halbgruppe
data class Combine(val contract1: Contract, val contract2: Contract): Contract

val fxSwap1 = Combine(zeroCouponBond("2024-12-24", 92.0, Currency.EUR),
    Reverse(zeroCouponBond("2024-12-24", 100.0, Currency.USD)))

fun fxSwap(date: Date, amount1: Amount, currency1: Currency,
           amount2: Amount, currency2: Currency) =
    /*
    Combine(zeroCouponBond(date, amount1, currency1),
    Reverse(zeroCouponBond(date, amount2, currency2)))
     */
    Later(date, Combine(Multiple(amount1, One(currency1)),
        Reverse(Multiple(amount2, One(currency2)))))

object Zero: Contract

fun combine(contract1: Contract, contract2: Contract): Contract =
    when (contract1) {
        is Zero -> contract2
        else ->
            when (contract2) {
                Zero -> contract1
                else -> Combine(contract1, contract2)
            }
    }

data class Payment(val direction: Direction,
                   val date: Date,
                   val amount: Amount,
                   val currency: Currency) {
    fun scale(factor: Amount): Payment =
        this.copy(amount = this.amount * factor)
    fun reverse(): Payment =
        this.copy(direction =
          when (this.direction) {
            Direction.LONG -> Direction.SHORT
            Direction.SHORT -> Direction.LONG
          })
}

val p1 = Pair(12, "Mike")
fun foo(p: Pair<Int, String>): Int {
    val (p1a, p1b) = p
    return p1a
}

// Zahlungen bis heute + Residualvertrag
fun denotation(contract: Contract, today: Date)
  : Pair<List<Payment>, Contract> =
    when (contract) {
        is One ->
            Pair(Cons(Payment(Direction.LONG, today, 1.0, contract.currency), Empty),
                Zero)
        is Multiple -> {
            val (payments, residual) = denotation(contract.contract, today)
            Pair(listMap(payments) { it.scale(contract.amount) },
                 Multiple(contract.amount, residual))
        }
        is Later ->
            if (today >= contract.date)
                denotation(contract.contract, today)
            else
                Pair(Empty, contract)
        is Reverse ->
        {
            val (payments, residual) = denotation(contract.contract, today)
            Pair(listMap(payments) { it.reverse() },
                Reverse(residual))
        }
        is Combine -> {
            val (payments1, residual1) = denotation(contract.contract1, today)
            val (payments2, residual2) = denotation(contract.contract1, today)
            Pair(append(payments1, payments2),
                combine(residual1, residual2))

        }
        is Zero ->
            Pair(Empty, Zero)
    }

val c7 = Multiple(100.0,
    Combine(One(Currency.EUR),
        Later("2024-12-24", One(Currency.EUR))))

