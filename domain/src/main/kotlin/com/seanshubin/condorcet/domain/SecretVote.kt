package com.seanshubin.condorcet.domain

data class SecretVote(val confirmation: String,
                      val rankings: List<Ranking>)
