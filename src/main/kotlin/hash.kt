abstract class Hash() {
    protected open var buckets: Array<Int?> = arrayOfNulls(6)
    protected open fun hash(value: Int): Int = value % (size + 1)

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
class CollisionHash(size: Int = 6): Hash() {
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
        val resized = arrayOfNulls<Int?>(newSize)

        for (elem in buckets.filterNotNull())
            add(elem)

        buckets = resized
    }

    override fun add(value: Int) {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }
}
