import kotlin.test.*

class CollisionHashTests {

    @Test
    fun `In an empty hash nothing wrong happens`() {
        val hash = CollisionHash()
        hash.add(12)
        hash.add(2)

        assertTrue { hash.exists(12) }
        assertTrue { hash.exists(2) }
    }

    @Test
    fun `If the bucket is already occupied we throw`() {
        val hash = CollisionHash()
        hash.add(12)

        // 19 produces the same hash as 12
        assertFailsWith<HashCollisionException> { hash.add(19) }
    }

    @Test
    fun `If the bucket has already the same element nothing happens`() {
        val hash = CollisionHash()
        hash.add(12)
        hash.add(12)

        assertTrue { hash.exists(12) }
    }
}

class ExtendHashTests {
    @Test
    fun `In an empty hash nothing wrong happens`() {
        val hash = CollisionHash()
        hash.add(12)
        hash.add(2)

        assertTrue { hash.exists(12) }
        assertTrue { hash.exists(2) }
    }

    @Test
    fun `If the bucket is already occupied we extend and rehash`() {
        val hash = ExtendHash()
        val initial = hash.size

        hash.add(12)
        hash.add(19)

        assertTrue { hash.exists(12) }
        assertTrue { hash.exists(19) }
        assertEquals((initial * hash.growRate).toInt(), hash.size)
    }
}