package com.seanshubin.condorcet.domain

class VotesAndCandidates(candidates: List<String>, votes: Map<Int, Int>) {
    companion object {
        val newLinePattern = Regex("""\r\n|\r|\n""")
        val quantifiedRankingPattern = Regex("""(\d+)\s+(\w+)""")
        fun parseFromQuantifiedSingleLetterCandidates(text: String): VotesAndCandidates {
            val lines = text.split(newLinePattern)
            println(lines.size)
            return parseFromQuantifiedSingleLetterCandidates(lines)
        }
        fun parseFromQuantifiedSingleLetterCandidates(lines: List<String>): VotesAndCandidates {
            val quantifiedRankings = lines.map { lineToQuantifiedRanking(it) }
            quantifiedRankings.forEach{ println(it) }
//        val candidates = quantifiedRankings.flatMap { it.candidates }.distinct.sorted
            throw RuntimeException("not implemented")
        }

        fun lineToQuantifiedRanking(line: String): QuantifiedRanking {
            val matchResult = quantifiedRankingPattern.matchEntire(line)
                    ?: throw RuntimeException(
                            "Unable to match '${line}' to pattern '${quantifiedRankingPattern.pattern}'")
            val (quantityString, candidateLetters) = matchResult.destructured
            val quantity = quantityString.toInt()
            val candidates = candidateLetters.map{ it.toString() }
            return QuantifiedRanking(quantity, candidates)
        }

    }
}