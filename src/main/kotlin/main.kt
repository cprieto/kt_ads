import kotlin.math.pow

fun main(args: Array<String>) {
    val items = mutableListOf(243, 637, 371, 598, 442, 137)
    items.bucketSort(637)
    println(items)
}