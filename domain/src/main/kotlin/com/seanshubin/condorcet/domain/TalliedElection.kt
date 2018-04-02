package com.seanshubin.condorcet.domain

data class TalliedElection(val candidates: List<String>,
                           val voted: List<String>,
                           val didNotVote: List<String>,
                           val votes: List<SecretVote>,
                           val tally: List<TallyRow>)
