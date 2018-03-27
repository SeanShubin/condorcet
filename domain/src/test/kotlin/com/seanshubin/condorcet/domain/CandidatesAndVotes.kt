package com.seanshubin.condorcet.domain

data class CandidatesAndVotes(private val candidates: List<String>, private val votes: Win) {
    override fun toString(): String {
        val rows = listOf(topRow(), *candidates.withIndex().map { row(it) }.toTypedArray())
        val lines = TableUtil.createTable(rows)
        return lines.joinToString("\n")
    }

    private fun topRow(): List<Any> =
            listOf("", *candidates.toTypedArray())

    private fun row(indexedName: IndexedValue<String>): List<Any> {
        val (winIndex, name) = indexedName
        return listOf(name, *candidates.indices.map { votes.getValue(winIndex, it) }.toTypedArray())
    }

    private fun tally():List<List<String>>{

    }

    private fun undefeated():List<Int> = votes.undefeated()

    private fun remove(ids:List<Int>):CandidatesAndVotes=
        CandidatesAndVotes(removeCandidates(ids), votes.remove(ids))

    private fun tallyIdToString(resultIds:List<List<Int>>):List<List<String>> =
        resultIds.map { tiedIdsToStrings(it) }

    private fun tiedIdsToStrings(ids:List<Int>):List<String> =
            ids.map { candidates[it] }
    private fun removeCandidates(ids:List<Int>):List<String> =
            candidates.withIndex().filter { !ids.contains(it.index) }.map { it.value }

    companion object {
        private val newLinePattern = Regex("""\r\n|\r|\n""")
        private val quantifiedRankingPattern = Regex("""(\d+)\s+(\w+)""")
        fun parseFromQuantifiedSingleLetterCandidates(text: String): CandidatesAndVotes {
            val lines = text.split(newLinePattern)
            return parseFromQuantifiedSingleLetterCandidates(lines)
        }

        private fun parseFromQuantifiedSingleLetterCandidates(lines: List<String>): CandidatesAndVotes {
            val quantifiedRankings = lines.map { lineToQuantifiedRanking(it) }
            val candidates = quantifiedRankings.flatMap { it.rankedCandidates }.distinct().sorted()
            val candidateToIndexMap = mapOf(*candidates.withIndex().map { (index, candidate) -> Pair(candidate, index) }.toTypedArray())
            val votes = buildVotes(candidateToIndexMap, quantifiedRankings)
            return CandidatesAndVotes(candidates, votes)
        }

        private fun lineToQuantifiedRanking(line: String): QuantifiedRanking {
            val matchResult = quantifiedRankingPattern.matchEntire(line)
                    ?: throw RuntimeException(
                            "Unable to match '$line' to pattern '${quantifiedRankingPattern.pattern}'")
            val (quantityString, candidateLetters) = matchResult.destructured
            val quantity = quantityString.toInt()
            val candidates = candidateLetters.map { it.toString() }
            return QuantifiedRanking(quantity, candidates)
        }

        private fun buildVotes(candidateToIndexMap: Map<String, Int>, quantifiedRankings: List<QuantifiedRanking>): Win {
            return quantifiedRankings.map { buildVote(candidateToIndexMap, it) }.sum()
        }

        private fun buildVote(candidateToIndexMap: Map<String, Int>, quantifiedRanking: QuantifiedRanking): Win {
            val (quantity, rankedNames) = quantifiedRanking
            val ranked = namesToRanked(candidateToIndexMap, rankedNames)
            return buildVote(ranked) * quantity
        }

        private fun namesToRanked(candidateToIndexMap: Map<String, Int>, rankedNames: List<String>): List<Int> {
            return rankedNames.map { candidateToIndexMap.getValue(it) }
        }

        private fun buildVote(ranked: List<Int>): Win {
            val rankedPairs = listToRankedPairs(ranked)
            val wins = rankedPairs.map { Win.fromWinnerLoser(it) }
            return wins.sum()
        }

        private fun listToRankedPairs(ranked: List<Int>): List<Pair<Int, Int>> {
            val toRankedPairs = { winIndex: Int ->
                (winIndex + 1..ranked.lastIndex).map { Pair(ranked.get(winIndex), ranked.get(it)) }
            }
            return ranked.indices.flatMap { toRankedPairs(it) }
        }
    }
}