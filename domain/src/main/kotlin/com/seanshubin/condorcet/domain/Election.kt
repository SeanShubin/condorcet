package com.seanshubin.condorcet.domain

data class Election(val candidates: List<String>,
                    val eligibleToVote: List<String>,
                    val votes: List<Ballot>) {
    fun tally(): TalliedElection {
        TODO()
    }

    companion object {
        fun fromLines(lines: List<String>): Election {
            val accumulate: (ElectionBuilder, String) -> ElectionBuilder =
                    { accumulator, line -> ElectionBuilder.processLine(accumulator, line) }
            return lines.fold(ElectionBuilder.EMPTY, accumulate).build()
        }
    }
}
