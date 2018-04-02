package com.seanshubin.condorcet.domain

data class Vote(val id: String,
                val confirmation: String,
                val rankings: List<Ranking>)
