package com.seanshubin.condorcet.domain

data class Ranking(val rank: Int,
                   val candidate: String) {
    fun toRow(): List<Any> = listOf(rank, candidate)
}
