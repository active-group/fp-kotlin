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
- Währung -UND
 */

sealed interface Contract

data class ZeroCouponBond