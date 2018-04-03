package com.seanshubin.condorcet.domain

import java.util.regex.Pattern

data class Ballot(val id: String,
                  val confirmation: String,
                  val rankings: List<Ranking>) {
    init{
        if(candidates().distinct().size != rankings.size)
            throw RuntimeException("ballot for $id has duplicate candidates: ${candidates().joinToString(",")}")
    }
    fun pairwisePreferences():List<Pair<String, String>> {
        val result = mutableListOf<Pair<String, String>>()
        for (i in 0 until rankings.size-1) {
            for(j in i+1 until rankings.size){
                result.add(Pair(rankings[i].candidate, rankings[j].candidate))
            }
        }
        return result
    }
    fun candidates():List<String> = rankings.map {it.candidate}
    companion object {
        private val spacePattern = Pattern.compile("\\s+")!!
        fun fromString(s: String): Ballot {
            val words = s.split(spacePattern)
            val id = words[0]
            val confirmation = words[1]
            val rankings = (2 until words.size step 2).map { i -> Ranking(words[i].toInt(), words[i + 1]) }
            return Ballot(id, confirmation, rankings)
        }
    }
}
