import java.util.*
import kotlin.math.floor

class NoNodeException(message: String) : Exception(message)

class LinearTree<T> {
    private val store: MutableList<T> = mutableListOf()

    fun insert(value: T) = store.add(value)

    val nodes: Int
        get() = store.size

    val root: Pair<Int, T>
        get() = Pair(0, store[0])

    fun atIndex(index: Int): T {
        return store[index]
    }

    fun parentOf(index: Int): Pair<Int, T> {
        val position = floor(((index - 1) / 2).toDouble()).toInt()
        return Pair(position, store[position])
    }

    fun leftChildOf(index: Int): Pair<Int, T> {
        val position = floor((2 * index + 1).toDouble()).toInt()
        return Pair(position, store[position])
    }

    fun rightChildOf(index: Int): Pair<Int, T> {
        val position = floor((2 * index + 2).toDouble()).toInt()
        return Pair(position, store[position])
    }
}

class Node<T : Comparable<T>>(
    private val data: T,
    private var left: Node<T>? = null,
    private var right: Node<T>? = null
) {
    fun preOrder(operation: (T) -> Unit) {
        operation(data)
        left?.preOrder(operation)
        right?.preOrder(operation)
    }

    fun inOrder(operation: (T) -> Unit) {
        left?.inOrder(operation)
        operation(data)
        right?.inOrder(operation)
    }

    fun postOrder(operation: (T) -> Unit) {
        left?.postOrder(operation)
        right?.postOrder(operation)
        operation(data)
    }

    fun levelOrder(operation: (T) -> Unit) {
        val queue = LinkedList<Node<T>>()
        queue.add(this)
        while (!queue.isEmpty()) {
            val elem = queue.pop()
            operation(elem.data)

            elem.left?.let { queue.add(it) }
            elem.right?.let { queue.add(it) }
        }
    }

    fun insert(value: T) {
        if (value < data)
            if (left != null) left?.insert(value) else left = Node(value)
        else if (right != null) right?.insert(value) else right = Node(value)
    }
}
