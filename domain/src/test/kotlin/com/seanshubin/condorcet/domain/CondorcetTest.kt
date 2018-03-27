package com.seanshubin.condorcet.domain

import org.junit.Test

class CondorcetTest {
    @Test
    fun cycleSample() {
        val text = """
            |5 acbed
            |5 adecb
            |8 bedac
            |3 cabed
            |7 caebd
            |2 cbade
            |7 dceba
            |8 ebadc
            """.trimMargin()
        val candidatesAndVotes = CandidatesAndVotes.parseFromQuantifiedSingleLetterCandidates(text)
        val schulze = candidatesAndVotes.tally()
        println(candidatesAndVotes)
//        val election = Election.createFromQuantifiedSingleLetterCandidates(text)
//        val tally = election.tally()
//        tally.reportLines().foreach(println)
    }
}