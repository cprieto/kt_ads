import kotlin.math.pow

fun main(args: Array<String>) {
    val tree = Node(29)
    tree.insert(15)
    tree.insert(35)
    tree.insert(20)
    tree.insert(25)

    val items = mutableListOf<Int>()
    tree.levelOrder { items.add(it) }

    println(items)
}
