package com.seanshubin.condorcet.domain

data class Election(val candidates: List<String>,
                    val eligibleToVote: List<String>,
                    val ballots: List<Ballot>) {
    fun tally(): TalliedElection {
        val voted = ballots.map { it.id }
        val didNotVote = eligibleToVote.filterNot { voted.contains(it) }
        val matrix = ballots.fold(MatrixBuilder.empty(candidates), MatrixBuilder.processBallot).build()
        val schulzeMatrix = matrix.schulzeStrongestPaths()
        val matrixTally = schulzeMatrix.schulzeTally()
        val tally = matrixTallyToTally(matrixTally)
        val secretBallots = ballots.map { ballotToSecretBallot(it) }
        return TalliedElection(candidates, voted, didNotVote, secretBallots, tally)
    }

    fun ballotToSecretBallot(ballot: Ballot): SecretBallot = SecretBallot(ballot.confirmation, ballot.rankings)
    fun matrixTallyToTally(tally: List<List<Int>>): List<TallyRow> =
        tally.mapIndexed{index, row -> createRow(index, row)}
    fun createRow(index:Int, row:List<Int>):TallyRow =
        TallyRow(index, row.map{candidates[it]})

    companion object {
        fun fromLines(lines: List<String>): Election =
                lines.fold(ElectionBuilder.EMPTY, ElectionBuilder.processLine).build()
    }
}
