import kotlin.test.*

class LinearSearchTest {
    private val items = listOf(12, 3, 11, 0, 2)
    @Test
    fun `It returns the index of the element`() {
        val res = items.linearSearch(0)
        assertEquals(3, res)
    }

    @Test
    fun `It returns -1 if the element is not found`() {
        val res = items.linearSearch(15)
        assertEquals(-1, res)
    }
}

class BinarySearchTest {
    private val items = listOf(0, 2, 3, 11, 12)

    @Test
    fun `It returns the index of the element`() {
        val res = items.linearSearch(12)
        assertEquals(4, res)
    }

    @Test
    fun `It returns -1 if not found`() {
        val res = items.linearSearch(15)
        assertEquals(-1, res)
    }
}