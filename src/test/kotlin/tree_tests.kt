import java.lang.IndexOutOfBoundsException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LinearTreeTests {
    /*

    This tree:
           A
        /     \
       B       C
     /   \   /   \
     D   E   F   G
    / \
    H I

     */
    private val tree = LinearTree<Char>().apply {
        insert('A')
        insert('B')
        insert('C')
        insert('D')
        insert('E')
        insert('F')
        insert('G')
        insert('H')
        insert('I')
    }

    @Test
    fun `We get root's children`() {
        val (index, root) = tree.root
        assertEquals('A', root)

        val (leftIdx, left) = tree.leftChildOf(index)
        assertEquals(1, leftIdx)
        assertEquals('B', left)

        val (rightIdx, right) = tree.rightChildOf(index)
        assertEquals(2, rightIdx)
        assertEquals('C', right)
    }

    @Test
    fun `We get a node's parent`() {
        val (index, parent) = tree.parentOf(5)
        assertEquals('C', parent)
        assertEquals(2, index)
    }

    @Test
    fun `Invalid positions are exceptions`() {
        assertFailsWith<IndexOutOfBoundsException> { tree.leftChildOf(10) }
    }

    @Test
    fun `Get number of nodes`() {
        assertEquals(9, tree.nodes)
    }
}

class TreeTraverseTests {
    private val root = Node(
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

    @Test
    fun `pre-order traverse`() {
        val result = mutableListOf<Char>()
        root.preOrder { result.add(it) }

        assertEquals(listOf('a', 'b', 'd', 'e', 'g', 'h', 'c', 'f'), result)
    }

    @Test
    fun `in-order traverse`() {
        val result = mutableListOf<Char>()
        root.inOrder { result.add(it) }

        assertEquals(listOf('d', 'b', 'g', 'e', 'h', 'a', 'c', 'f'), result)
    }

    @Test
    fun `post-order traverse`() {
        val result = mutableListOf<Char>()
        root.postOrder { result.add(it) }

        assertEquals(listOf('d', 'g', 'h', 'e', 'b', 'f', 'c', 'a'), result)
    }

    @Test
    fun `breadth-first traverse`() {
        val result = mutableListOf<Char>()
        root.levelOrder { result.add(it) }

        assertEquals(listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'), result)
    }
}
