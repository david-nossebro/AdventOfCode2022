package utils

import org.testng.annotations.Test

class CommonUtilsTest {

    @Test
    fun `generic overlaps works for different types`() {
        assert(setOf(1, 2, 3) overlaps setOf(3, 4, 5)) { "'overlap' not working for set" }
        assert("123" overlaps "345") { "'overlap' not working for String" }
        assert(IntRange(1, 3) overlaps IntRange(3, 5)) { "'overlap' not working for IntRange" }
        assert(listOf(1, 3, 8) overlaps listOf(3, 5, 12)) { "'overlap' not working for list" }
    }

    @Test
    fun `generic containsAllOrIsContainedIn works for different types`() {
        assert(setOf(1, 2, 3) containsAllOrIsContainedIn setOf(1, 2, 3)) { "'containsAllOrIsContainedIn' not working for Set<Int>" }
        assert("123" containsAllOrIsContainedIn "123") { "'containsAllOrIsContainedIn' not working for String" }
        assert(IntRange(1, 3) containsAllOrIsContainedIn IntRange(1, 3)) { "'containsAllOrIsContainedIn' not working for IntRange" }
        assert(listOf(1, 3) containsAllOrIsContainedIn listOf(1, 3)) { "'containsAllOrIsContainedIn' not working for IntRange" }
    }

    // Why
    @Test
    fun `generic intersect works for different types`() {
        assert(setOf(1, 2, 3) intersect setOf(3, 4, 5) == setOf(3)) { "'intersect' not working for Set<Int>" }
        assert("123" intersect "345" == "3") { "'intersect' not working for String" }
        assert(IntRange(1, 3) intersect IntRange(3, 5) == setOf(3)) { "'intersect' not working for IntRange" }
        assert(listOf(1, 3) intersect listOf(3, 5) == setOf(3)) { "'intersect' not working for List" }
    }

}