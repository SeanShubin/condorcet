package com.seanshubin.condorcet.domain

private fun stringToLines(s: String): List<String> = s.split("""\r\n|\r|\n""")

object ListDifference {
    fun <T> diff(a: List<T>, b: List<T>): DifferenceResult {
        fun buildSeqDifference(index: Int, soFar: List<T>, remainA: List<T>, remainB: List<T>): DifferenceResult {
            fun composeSeqDifference(a: String, b: String): DifferenceResult {
                val heading = "sequences different at index $index"
                val sameMessage = soFar.asReversed().withIndex().map { composeSameAtIndex(it) }
                val differenceMessage = composeDifferentAtIndex(index, a, b)
                val messageLines = listOf(heading) + sameMessage + differenceMessage + listOf("remaining elements skipped")
                val isSame = false
                return DifferenceResult(isSame, messageLines)
            }

            return when {
                remainA.isNotEmpty() && remainB.isNotEmpty() ->
                    if (remainA[0] == remainB[0]) {
                        buildSeqDifference(index + 1, listOf(remainA[0]) + soFar, remainA.subList(1, remainA.size), remainB.subList(1, remainB.size))
                    } else {
                        composeSeqDifference(remainA[0].toString(), remainB[0].toString())
                    }
                remainA.isEmpty() && remainB.isNotEmpty() ->
                    composeSeqDifference("<missing>", remainB[0].toString())
                remainA.isNotEmpty() && remainB.isEmpty() ->
                    composeSeqDifference(remainA[0].toString(), "<missing>")
                else -> {
                    val isSame = true
                    val messageLines = listOf("sequences are identical") + soFar.asReversed().withIndex().map { composeSameAtIndex(it) }
                    DifferenceResult(isSame, messageLines)
                }
            }
        }

        return buildSeqDifference(0, emptyList(), a, b)
    }

    fun multilineStringDiff(a: String, b: String): DifferenceResult =
            diff(stringToLines(a), stringToLines(b))

    private fun <T> composeSameAtIndex(valueAndIndex: IndexedValue<T>): String {
        val (index, value) = valueAndIndex
        return "$index same        = $value"
    }

    private fun composeDifferentAtIndex(index: Int, a: String, b: String): List<String> =
            listOf("$index different-a = $a", "$index different-b = $b")

}