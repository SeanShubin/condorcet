package com.seanshubin.condorcet.domain

data class TalliedElection(val candidates: List<String>,
                           val voted: List<String>,
                           val didNotVote: List<String>,
                           val secretBallots: List<SecretBallot>,
                           val tally: List<TallyRow>) {
    fun toLines(): List<String> {
        val tableFormatter = TableFormatter(wantInterleave = false,rowLeft ="", rowCenter = " ", rowRight = "")
        return listOf("candidates (name)") +
                candidates.sorted().map{indent(it)} +
                listOf("voted (name)") +
                voted.map{indent(it)} +
                listOf("did-not-vote (name)") +
                didNotVote.map{indent(it)} +
                listOf("ballots (confirmation { rank candidate })") +
                tableFormatter.createTable(secretBallots.sorted().map { it.toRow() }).map{indent(it.trim())} +
                listOf("tally (place { candidate })") +
                tableFormatter.createTable(tally.map { it.toRow() }).map{indent(it.trim())}
    }

    private fun indent(s:String):String = "    $s"
}
