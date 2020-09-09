import kotlin.math.pow

fun main(args: Array<String>) {
    val root = Node(
        'a',
        Node('b',
            Node('d'),
            Node('e',
                Node('g'),
                Node('h')
            )
        ),
        Node('c', right = Node('f'))
    )

    val items = mutableListOf<Char>()
    root.postOrder { items.add(it) }

    println(items)
}
