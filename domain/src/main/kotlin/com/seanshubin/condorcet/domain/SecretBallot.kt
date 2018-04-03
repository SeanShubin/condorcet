package com.seanshubin.condorcet.domain

data class SecretBallot(val confirmation: String,
                        val rankings: List<Ranking>)
