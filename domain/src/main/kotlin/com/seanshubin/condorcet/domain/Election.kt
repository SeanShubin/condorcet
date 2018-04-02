package com.seanshubin.condorcet.domain

data class Election(val candidates: List<String>,
                    val eligibleToVote: List<String>,
                    val votes: List<Vote>)
