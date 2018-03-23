package com.seanshubin.condorcet.domain

import org.junit.Test
import kotlin.test.assertEquals

class TableUtilTest {
    @Test
    fun table() {
        val input = listOf(
                listOf("Alice", "Bob", "Carol"),
                listOf("Dave", "Eve", "Mallory"),
                listOf("Peggy", "Trent", "Wendy"))
        val expected = listOf(
                "╔═════╤═════╤═══════╗",
                "║Alice│Bob  │Carol  ║",
                "╟─────┼─────┼───────╢",
                "║Dave │Eve  │Mallory║",
                "╟─────┼─────┼───────╢",
                "║Peggy│Trent│Wendy  ║",
                "╚═════╧═════╧═══════╝"
        )
        val actual = TableUtil.createTable(input)
        assertEquals(expected, actual)

    }
}
