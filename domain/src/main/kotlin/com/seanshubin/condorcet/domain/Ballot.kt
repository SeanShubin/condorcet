package com.seanshubin.condorcet.domain

import java.util.regex.Pattern

data class Ballot(val id: String,
                  val confirmation: String,
                  val rankings: List<Ranking>) {
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
