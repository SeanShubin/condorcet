package com.seanshubin.condorcet.domain

data class TalliedElection(val candidates: List<String>,
                           val voted: List<String>,
                           val didNotVote: List<String>,
                           val secretBallots: List<SecretBallot>,
                           val preferences: Matrix,
                           val strongestPaths: Matrix,
                           val tally: List<TallyRow>) {
    fun toLines(): List<String> {
        val tableFormatter = TableFormatter(wantInterleave = false, rowLeft = "", rowCenter = " ", rowRight = "")
        return listOf("candidates (name)") +
                candidates.sorted().map { indent(it) } +
                listOf("voted (name)") +
                voted.sorted().map { indent(it) } +
                listOf("did-not-vote (name)") +
                didNotVote.sorted().map { indent(it) } +
                listOf("ballots (confirmation { rank candidate })") +
                tableFormatter.createTable(secretBallots.sorted().map { it.toRow() }).map { indent(it.trim()) } +
                listOf("preference-matrix") +
                tableFormatter.createTable(preferences.rows).map { indent(it) } +
                listOf("strongest-path-matrix (for schulze method)") +
                tableFormatter.createTable(strongestPaths.rows).map { indent(it) } +
                listOf("tally (place { candidate })") +
                tableFormatter.createTable(tally.map { it.toRow() }).map { indent(it.trim()) }
    }

    private fun indent(s: String): String = "    $s"
}
