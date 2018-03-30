package com.seanshubin.condorcet.domain

import kotlin.math.max
import kotlin.math.min

class Matrix(private val rows: List<List<Int>>) {
    private var rowCount: Int = 0
    private var columnCount: Int = 0

    init {
        for (row in rows) {
            rowCount += 1
            if (columnCount == 0) {
                columnCount = row.size
            } else if (columnCount != row.size) {
                throw RuntimeException("all rows must be the same size")
            }
        }
    }

    fun get(rowIndex: Int, columnIndex: Int): Int {
        return rows[rowIndex][columnIndex]
    }

    fun schulzeStrongestPaths(): Matrix {
        val size = if (rowCount == columnCount)
            rowCount
        else
            throw UnsupportedOperationException(
                    "This method is only valid for square matrices," +
                            " this matrix has $rowCount rows and $columnCount columns")
        val strongestPaths = createEmptyMutableMatrixData(rowCount, columnCount)
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (i != j) {
                    if (rows[i][j] > rows[j][i]) {
                        strongestPaths[i][j] = rows[i][j]
                    } else {
                        strongestPaths[i][j] = 0
                    }
                }
            }
        }
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (i != j) {
                    for (k in 0 until size) {
                        if (i != k && j != k) {
                            strongestPaths[j][k] =
                                    max(strongestPaths[j][k], min(strongestPaths[j][i], strongestPaths[i][k]))
                        }
                    }
                }
            }
        }
        return Matrix(strongestPaths)
    }

    private fun createEmptyMutableMatrixData(rowCount: Int, columnCount: Int): MutableList<MutableList<Int>> =
            mutableListOf(*(0 until rowCount).map { mutableListOf(*(0 until columnCount).map { 0 }.toTypedArray()) }.toTypedArray())
}

fun matrixOfRows(vararg rows: List<Int>): Matrix = Matrix(rows.toList())
fun matrixOfSizeWithDefault(rowCount: Int, columnCount: Int, default: Int): Matrix =
        Matrix(listOf(*(0 until rowCount).map { listOf(*(0 until columnCount).map { default }.toTypedArray()) }.toTypedArray()))
