abstract class Hash() {
    protected open var buckets: Array<Int?> = arrayOfNulls(7)
    protected open fun hash(value: Int): Int = value % buckets.size

    abstract fun add(value: Int)

    open fun exists(value: Int): Boolean {
        val key = hash(value)
        return buckets[key] == value
    }

    val size: Int
        get() = buckets.size
}

class HashCollisionException(message: String): Exception(message)

/**
 * A hash table that once is a collision it throws an exception
 */
class CollisionHash(size: Int = 7): Hash() {
    override fun add(value: Int) {
        val key = hash(value)
        if (buckets[key] != null && buckets[key] != value)
            throw HashCollisionException("The value $value produces a collision in the hash")
        buckets[key] = value
    }
}

/**
 * A hash table that extends the hash by grow if a collision happens
 *
 * @param growRate Double grow rate to extend the hash if collision happens
 */
class ExtendHash(val growRate: Double = 1.5): Hash() {
    private fun rehash() {
        val newSize = (size * growRate).toInt()
        val items = buckets.copyOf()

        buckets = arrayOfNulls(newSize)
        for (elem in items.filterNotNull())
            add(elem)
    }

    override fun add(value: Int) {
        val key = hash(value)
        if (buckets[key] != null && buckets[key] != value) {
            rehash()
            add(value)
        }
        else buckets[key] = value
    }
}

/**
 * A hash table that uses linear probing if a collision happens
 *
 * @param initialSize Int initial size for buckets
 */
class LinearProbingHash(initialSize: Int): Hash() {
    override var buckets: Array<Int?> = arrayOfNulls(initialSize)
    override fun add(value: Int) {
        var key = hash(value)
        if (buckets[key] != null && buckets[key] == value)
            linearProbing(key, value)
        else buckets[key] = value
    }

    private fun linearProbing(key: Int, value: Int) {
        for ((idx, content) in buckets.withIndex()) {
            when {
                content == value -> break
                content == null -> buckets[idx] = value
            }
        }

    }
}
