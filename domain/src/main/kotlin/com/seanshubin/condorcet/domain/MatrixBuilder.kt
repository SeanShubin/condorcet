package com.seanshubin.condorcet.domain

data class MatrixBuilder(val matrix: Matrix) {
    fun processBallot(ballot: Ballot): MatrixBuilder = TODO()

    fun build(): Matrix = TODO()

    companion object {
        fun empty(size: Int): MatrixBuilder = MatrixBuilder(matrixOfSizeWithDefault(size, size, 0))
        val processBallot: (matrixBuilder: MatrixBuilder, ballot: Ballot) -> MatrixBuilder = { matrixBuilder, ballot ->
            matrixBuilder.processBallot(ballot)
        }
    }
}