/*
- "Ich bekomme am 24.12.2024 100€."
  "zero-coupon bond" / Zero-Bond
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

data class ZeroCouponBond(val date: Date,
                          val amount: Amount,
                          val currency: Currency)
