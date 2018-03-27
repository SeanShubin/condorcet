package com.seanshubin.condorcet.domain

data class QuantifiedRanking(val quantity:Int, val rankedCandidates:List<String>){
    init {
        if(rankedCandidates.distinct().size != rankedCandidates.size)
            throw RuntimeException("duplicate candidates not allowed")
    }
}
