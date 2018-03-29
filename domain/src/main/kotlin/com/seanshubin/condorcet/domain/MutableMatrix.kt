package com.seanshubin.condorcet.domain

class MutableMatrix(val initialData: List<List<Int>>) {
    private val data: MutableList<MutableList<Int>> = mutableListOf()
    private var rowCount: Int = 0
    private var columnCount: Int = 0

    init {
        for (row in data) {
            val newRow = mutableListOf<Int>()
            for (cell in row) {
                newRow.add(cell)
            }
            rowCount += 1
            if (columnCount == 0) {
                columnCount = newRow.size
            } else if (columnCount != newRow.size) {
                throw RuntimeException("all rows must be the same size")
            }
        }
    }

    fun get(rowIndex: Int, columnIndex: Int): Int {
        return data.get(rowIndex).get(columnIndex)
    }

    fun set(rowIndex: Int, columnIndex: Int, value: Int) {
        data.get(rowIndex).set(columnIndex, value)
    }
}

fun mutableMatrixOfRows(vararg rows: List<Int>): MutableMatrix = MutableMatrix(rows.toList())
fun matrixOfSizeWithDefault(rowCount: Int, columnCount: Int, default: Int): MutableMatrix =
        MutableMatrix(listOf(*(0 until rowCount).map { listOf(*(0 until columnCount).map { default }.toTypedArray()) }.toTypedArray()))
