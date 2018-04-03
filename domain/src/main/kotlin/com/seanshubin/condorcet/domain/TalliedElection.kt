package com.seanshubin.condorcet.domain

data class TalliedElection(val candidates: List<String>,
                           val voted: List<String>,
                           val didNotVote: List<String>,
                           val secretBallots: List<SecretBallot>,
                           val tally: List<TallyRow>) {
    fun toLines(): List<String> {
        return listOf("candidates (name)") +
                candidates.map{indent(it)} +
                listOf("voted (name)") +
                voted.map{indent(it)} +
                listOf("did-not-vote (name)")
    }

    fun indent(s:String):String = "    $s"
}
