package com.seanshubin.condorcet.prototype

class Parser(val nameGeneratorFactory: NameGeneratorFactory) {
    private val quantifiedRankingPattern = Regex("""(\d+)\s+(\w+)""")
    private val nameGenerator = nameGeneratorFactory.createNameGenerator()

    fun conciseToInput(lines: List<String>): List<String> {
        val abbreviatedQuantifiedRankings = lines.map { lineToQuantifiedRanking(it) }
        val candidateAbbreviations = candidatesFor(abbreviatedQuantifiedRankings)
        val candidateMap = constructCandidateMap(candidateAbbreviations)
        val quantifiedRankings = expandCandidateAbbreviations(abbreviatedQuantifiedRankings, candidateMap)
        quantifiedRankings.forEach { println(it) }
        TODO()
//        val rankings = addVoterIds()
//        val inputLines = ballots.map{ballotToInputLine(candidates, it)}
//        return inputLines
    }

    private fun expandCandidateAbbreviations(abbreviatedQuantifiedRankings: List<QuantifiedRanking>, candidateMap: Map<String, String>): List<QuantifiedRanking> =
            abbreviatedQuantifiedRankings.map { expandCandidateAbbreviations(it, candidateMap) }

    private fun expandCandidateAbbreviations(abbreviatedQuantifiedRanking: QuantifiedRanking, candidateMap: Map<String, String>): QuantifiedRanking =
            abbreviatedQuantifiedRanking.copy(ranked = expandAbbreviations(abbreviatedQuantifiedRanking.ranked, candidateMap))

    private fun expandAbbreviations(abbreviated: List<String>, candidateMap: Map<String, String>): List<String> =
            abbreviated.map { candidateMap.getValue(it) }

    fun lineToQuantifiedRanking(line: String): QuantifiedRanking {
        val matchResult = quantifiedRankingPattern.matchEntire(line)
                ?: throw RuntimeException("Unable to match '$line' to pattern '${quantifiedRankingPattern.pattern}'")
        val (quantityString, candidateLetters) = matchResult.destructured
        val quantity = quantityString.toInt()
        val candidates = candidateLetters.map { it.toString() }
        return QuantifiedRanking(quantity, candidates)
    }

    fun candidatesFor(quantifiedRankings: List<QuantifiedRanking>): List<String> =
            quantifiedRankings.flatMap { it.ranked }.distinct()

    fun constructCandidateMap(candidates: List<String>): Map<String, String> {
        val names = nameGenerator.generateNames(candidates.size)
        return (candidates zip names).toMap()
    }

}

/*

QuantifiedRanking
3 abc

QuantifiedRanking
3 alice bob carol

Ranking
dave  alice bob carol
eve   alice bob carol
peggy alice bob carol

Ballot
dave  1 alice 2 bob 3 carol
eve   1 alice 2 bob 3 carol
peggy 1 alice 2 bob 3 carol
 */