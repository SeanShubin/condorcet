package com.seanshubin.condorcet.domain

data class TalliedElection(val candidates: List<String>,
                           val voted: List<String>,
                           val didNotVote: List<String>,
                           val votes: List<SecretBallot>,
                           val tally: List<TallyRow>) {
    fun toLines(): List<String> {
        TODO()
    }
}
