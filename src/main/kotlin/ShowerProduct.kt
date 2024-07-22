// Duschprodukt:
// - Seife -ODER-
// - Shampoo -ODER-
// - Duschgel
sealed interface ShowerProduct

// Seife hat folgende Eigenschaften:
// - pH-Wert
data class Soap(val pH: Double): ShowerProduct

// Shampoo hat folgende Eigenschaften:
// - Haartyp (Schuppen, fettig, normal)
enum class Hairtype { DANDRUFF, OILY, NORMAL }

data class Shampoo(val hairtype: Hairtype): ShowerProduct

// Duschgel besteht aus:
// - Seife -UND-
// - Shampoo
// data class Showergel(val soap: Soap, val shampoo: Shampoo)
//    : ShowerProduct

// Kombinator
// "closure of operations"
data class Mixture(val product1: ShowerProduct, // Selbstbezug
    val product2: ShowerProduct): ShowerProduct

// Seifentanteil eines Duschproducts
fun soapProportion(product: ShowerProduct): Double =
    when (product) {
        is Soap -> 1.0
        is Shampoo -> 0.0
        is Mixture ->
            (soapProportion(product.product1) +
                soapProportion(product.product2)) / 2.0
    }