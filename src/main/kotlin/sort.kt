import kotlin.math.pow

private fun <T> MutableList<T>.swap(i: Int, j: Int) {
    this[i] = this[j].also { this[j] = this[i] }
}

/**
 * Sorts a mutable list using the _bubble sort_ algorithm
 * sorting is done **in place**
 */
fun <T : Comparable<T>> MutableList<T>.bubbleSort() {
    val size = this.size
    if (size < 2) return

    for (last in size downTo 0) {
        var swapped = false
        for (current in 1 until last) {
            if (this[current - 1] > this[current])
                this.swap(current, current - 1).also { swapped = true }
        }
        if (!swapped) break // short circuit in case list is already sorted no swap is done
    }
}

/**
 * Sorts a mutable list using the _selection sort_ algorithm
 * sorting is done **in place**
 */
fun <T : Comparable<T>> MutableList<T>.selectionSort() {
    val size = this.size
    if (size < 2) return

    for (current in 0 until size) {
        var smallest = current
        for (next in current + 1 until size)
            if (this[next] < this[smallest]) smallest = next
        if (smallest != current) this.swap(current, smallest)
    }
}

/**
 * Sorts a mutable list using the _insertion sort_ algorithm
 * sorting is done **in place**
 */
fun <T : Comparable<T>> MutableList<T>.insertionSort() {
    for (current in 0 until this.size) {
        var idx = current
        val value = this[current]

        for (i in idx - 1 downTo 0) { // We compare with all the previous elements
            if (value < this[i]) {
                // If bigger, we move current element to the right and check the element at the right
                this[(i + 1)] = this[i].also { idx-- }
            }
        }

        this[idx] = value // This is the right place for the item in the list
    }
}

/**
 * Sorts a mutable list using the quicksort algorithm
 * sorting is done **in place**
 *
 * @param low Int starting point for sorting this collection
 * @param high Int end point for sorting this collection
 */
fun <T : Comparable<T>> MutableList<T>.quickSort(low: Int = 0, high: Int = this.size - 1) {
    fun partition(low: Int, high: Int): Int {
        val current = this[high]
        var idx = low

        for (j in idx until high) {
            if (this[j] <= current) {
                this.swap(j, idx).also { idx++ }
            }
        }
        this.swap(high, idx)
        return idx
    }

    if (low >= high) return

    val pivot = partition(low, high)
    this.quickSort(low, pivot - 1)
    this.quickSort(pivot + 1, high)
}

/**
 * Sorts a mutable list using the _merge sort_ algorithm
 * sorting is done **in place**
 *
 * @param lo Int start indx to do in place sort
 * @param hi Int end index to do in place sort
 */
fun <T : Comparable<T>> MutableList<T>.mergeSort(lo: Int = 0, hi: Int = this.size - 1) {
    fun merge(lo: Int, hi: Int, mid: Int) {
        var a = 0;
        var b = (hi - mid) // b is the middle point of the collection
        val aux = this.slice(lo..hi) // We copy the part we need to sort

        for (idx in lo..hi) {
            this[idx] = when {
                a >= (hi - mid) -> aux[b++] // No remaining elements in the left side
                b >= aux.size -> aux[a++] // No remaining elements in the right side
                aux[a] < aux[b] -> aux[a++]
                else -> aux[b++]
            }
        }
    }

    if (hi <= lo) return // No elements to process, list already sorted!

    val mid = lo + (hi - lo) / 2
    this.mergeSort(lo, mid)
    this.mergeSort(mid + 1, hi)

    merge(lo, hi, mid)
}

/**
 * Sorts a list of discrete integer types T using the counting sort method
 * this is done **in place**
 *
 * @param max T the upper level for the type we need to sort
 */
fun MutableList<Int>.countingSort(max: Int) {
    val counter = Array<Int>(max + 1) { 0 } // This is because we need an array containing up to max

    this.forEach { counter[it]++ }
    println(counter.toList())

    var pos = 0
    for (idx in counter.indices) {
        while (counter[idx] > 0) {
            this[pos++] = idx
            counter[idx]--
        }
    }
}

/**
 * Sorts a list of integers using radix sort
 * to keep the theme this is sorted **in-place** (it could be more efficient if not)
 *
 * @param digits Int number of max digits in the list to sort
 * @param current Int not directly used, the current digit being sorted
 */
tailrec fun MutableList<Int>.radixSort(digits: Int, current: Int = 0) {
    if (digits == current) return

    val result = Array<Int>(this.size) { 0 }
    val positions = Array<Int>(10) { 0 }
    val power = 10.0.pow(current)
    val position = { x: Int -> ((x / power) % 10).toInt() }

    // Normal counting sort
    for (elem in this) {
        val pos = position(elem)
        positions[pos]++
    }

    // This is the accumulative number for each location
    for (idx in 1 until positions.size) {
        positions[idx] += positions[idx - 1]
    }

    // Now we place each element in the correct place in the result
    for (idx in this.size - 1 downTo 0) {
        val elem = this[idx]
        val pos = position(elem)

        val loc = (positions[pos] - 1).also { positions[pos]-- }
        result[loc] = elem
    }

    // Replace all the elements in the current mutable list
    for ((idx, elem) in result.withIndex()) {
        this[idx] = elem
    }

    this.radixSort(digits, current + 1)
}

/**
 * Sorts a list of integers using bucket sort
 * this sorting is done **in-place** and specific to the bucket and position function
 *
 * @param max Int the max number in the list, used to calculate the position in the buckets
 * @param buckets Array<MutableList<Int>> buckets to place the elements when classifying
 * @param position (Int) -> Int function to locate the element in the correct bucket
 */
fun MutableList<Int>.bucketSort(
    max: Int,
    buckets: Array<MutableList<Int>> = Array(this.size) { mutableListOf() },
    position: (Int) -> Int = { x: Int -> (x * this.size) / max }
) {
    // Place each element in a bucket, this depends of the type of data
    for (elem in this) {
        val pos = position(elem) - 1
        buckets[pos].add(elem)
    }

    // Now we relocate all the elements back
    var idx = 0
    for (bucket in buckets) {
        if (bucket.isEmpty()) continue

        // We use insert sort because, well, it is finite and simple
        bucket.insertionSort()

        for (elem in bucket) this[idx++] = elem
    }
}