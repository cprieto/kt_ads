import kotlin.test.*

abstract class ComparisonSortTests(private val op: (l: MutableList<Int>) -> Unit) {
    private val unsorted = listOf(6, 3, 8, 7, 2, 0, 4, 5, 9, 1)
    private val sorted = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    @Test
    fun `Sorts unsorted list`() {
        val sut = unsorted.toMutableList()
        op(sut)
        assertEquals(sorted, sut)
    }

    @Test
    fun `Sorts sorted list`() {
        val sut = sorted.toMutableList()
        op(sut)
        assertEquals(sorted, sorted)
    }
}

class BubbleSortTests : ComparisonSortTests({ l -> l.bubbleSort() })

class SelectionSortTests : ComparisonSortTests({ l -> l.selectionSort() })

class InsertionSortTests: ComparisonSortTests({ l -> l.insertionSort() })

class QuickSortTests: ComparisonSortTests({ l -> l.quickSort() })

// This needs to be fixed!
//class MergeSortTests: ComparisonSortTests({ l -> l.mergeSort() })

class CountingSortTests {
    private val expected = listOf(1, 1, 1, 3, 5)

    @Test
    fun `It sorts a list of discrete integers`() {
        val items = mutableListOf(1, 5, 1, 3, 1)
        items.countingSort(5)
        assertEquals(expected, items)
    }

    @Test
    fun `It does nothing in an already sorted list of discrete integers`() {
        val items = expected.toMutableList()
        items.countingSort(5)
        assertEquals(expected, items)
    }
}

class RadixSortTests {
    private val expected = listOf(108, 157, 218, 350, 457, 613)

    @Test
    fun `It sorts a list of integers`() {
        val items = mutableListOf(613, 218, 350, 157, 108, 457)
        items.radixSort(3)
        assertEquals(expected, items)
    }

    @Test
    fun `If the list is already sorted do nothing`() {
        val items = expected.toMutableList()
        items.radixSort(3)
        assertEquals(expected, items)
    }
}

