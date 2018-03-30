package com.seanshubin.condorcet.prototype

data class Ballot(val voterId: String, val rankedCandidates: List<RankedCandidate>)
