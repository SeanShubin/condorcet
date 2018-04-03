package com.seanshubin.condorcet.domain

data class Election(val candidates: List<String>,
                    val eligibleToVote: List<String>,
                    val ballots: List<Ballot>) {
    fun tally(): TalliedElection {
        val (voted, didNotVote) = seeWhoVoted()
        val matrix = ballots.fold(MatrixBuilder.empty(candidates.size), MatrixBuilder.processBallot).build()
        val schulzeMatrix = matrix.schulzeStrongestPaths()
        val matrixTally = schulzeMatrix.schulzeTally()
        val tally = matrixTallyToTally(matrixTally)
        val secretBallots = ballots.map { ballotToSecretBallot(it) }
        return TalliedElection(candidates, voted, didNotVote, secretBallots, tally)
    }

    fun seeWhoVoted(): Pair<List<String>, List<String>> = TODO()
    fun ballotToSecretBallot(ballot: Ballot): SecretBallot = TODO()
    fun matrixTallyToTally(tally: List<List<Int>>): List<TallyRow> = TODO()

    companion object {
        fun fromLines(lines: List<String>): Election =
                lines.fold(ElectionBuilder.EMPTY, ElectionBuilder.processLine).build()
    }
}
