import kotlin.math.floor

/**
 * Search an element in an unsorted list
 * this has a time performance of $O(n)$
 *
 * @param what T what element to search
 * @return Int the index of the element if found or -1 otherwise
 */
fun <T> List<T>.linearSearch(what: T): Int {
    for ((idx, value) in this.withIndex())
        if (value == what) return idx
    return -1
}

/**
 * Search an element in a sorted list
 * this has a time performance of $O(log n)$
 *
 * @param what T the element to search in the list
 * @return Int the index of the element if found or -1 otherwise
 */
fun <T: Comparable<T>> List<T>.binarySearch(what: T): Int {
    var start = 0
    var end = this.size - 1

    while (start <= end) {
        val middle = floor(((start + end)/2).toDouble()).toInt()
        when {
            this[middle] > what -> end = middle - 1
            this[middle] < what -> start = middle + 1
            else -> return middle
        }
    }
    return -1
}
