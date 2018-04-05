package com.seanshubin.condorcet.prototype

import com.seanshubin.condorcet.domain.Ballot
import com.seanshubin.condorcet.domain.Ranking
import com.seanshubin.condorcet.domain.TableFormatter
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

object SampleDataGenerator {
    fun generateNames(howMany: Int): List<String> = (1..howMany).map { generateName() }
    fun generateConfirmationNumbers(howMany: Int): List<String> = (1..howMany).map { generateConfirmationNumber() }
    fun generateInputLines(howManyCandidates: Int, howManyVoters: Int, howManyBallots: Int): List<String> {
        val candidates = generateNames(howManyCandidates)
        val voters = generateNames(howManyVoters)
        val votersThatVoted = chooseQuantity(howManyBallots, voters)
        val ballots = generateBallots(candidates, votersThatVoted)
        return listOf("candidates (name)") +
                candidates.map { indent(it) } +
                listOf("eligible-to-vote (name)") +
                voters.map { indent(it) } +
                listOf("ballots (name confirmation { rank Candidates })") +
                tableFormatter.createTable(ballots.map { it.toRow() }).map { indent(it) }
    }


    private val charset: Charset = StandardCharsets.UTF_8
    private val random: Random = Random()
    private val surnames = resourceNameToLines("surnames.txt").map { it.toLowerCase() }
    private val femaleNames = resourceNameToLines("female-names.txt").map { it.toLowerCase() }
    private val maleNames = resourceNameToLines("male-names.txt").map { it.toLowerCase() }
    private val givenNames = femaleNames + maleNames
    private val tableFormatter = TableFormatter(wantInterleave = false, rowLeft = "", rowCenter = " ", rowRight = "")

    private fun generateName(): String {
        val givenName = chooseRandom(givenNames)
        val surname = chooseRandom(surnames)
        return "$givenName-$surname"
    }

    private fun chooseRandom(choices: List<String>): String = choices.get(random.nextInt(choices.size))
    private fun resourceNameToLines(name: String): List<String> {
        val inputStream = getResourceAsStream(name)
        return inputStreamToLines(inputStream)
    }

    private fun getResourceAsStream(name: String): InputStream {
        return this.javaClass.classLoader.getResourceAsStream(name)
                ?: throw RuntimeException("Resource named '$name' not found")
    }

    private fun inputStreamToLines(inputStream: InputStream): List<String> {
        val reader = BufferedReader(InputStreamReader(inputStream, charset))
        try {
            return bufferedReaderToLines(reader)
        } finally {
            reader.close()
        }
    }

    private fun bufferedReaderToLines(reader: BufferedReader): List<String> {
        val mutableList = mutableListOf<String>()
        var line = reader.readLine()
        while (line != null) {
            mutableList.add(line)
            line = reader.readLine()
        }
        return mutableList
    }

    private fun generateConfirmationNumber(): String = UUID.randomUUID().toString()
    private fun chooseQuantity(quantity: Int, choices: List<String>): List<String> {
        val takeFrom = choices.toMutableList()
        val addTo = mutableListOf<String>()
        for (i in 1..quantity) {
            val index = random.nextInt(takeFrom.size)
            addTo.add(takeFrom[index])
            takeFrom.removeAt(index)
        }
        return addTo
    }

    private fun indent(s: String): String = "    $s"
    private fun generateBallots(candidates: List<String>, voters: List<String>): List<Ballot> {
        return voters.map { generateBallot(candidates, it) }
    }

    private fun generateBallot(candidates: List<String>, voter: String): Ballot {
        val confirmation = generateConfirmationNumber()
        val rankings = generateRankings(candidates)
        return Ballot(voter, confirmation, rankings)
    }

    private fun generateRankings(candidates: List<String>): List<Ranking> {
        val shuffledCandidates = shuffle(candidates)
        val shuffledRanks = shuffle((1..candidates.size).toList())
        val rankAndCandidateList = shuffledRanks zip shuffledCandidates
        return rankAndCandidateList.map { pairToRanking(it) }
    }

    private fun <T> shuffle(list: List<T>): List<T> {
        val takeFrom = list.toMutableList()
        val addTo = mutableListOf<T>()
        while (takeFrom.isNotEmpty()) {
            val index = random.nextInt(takeFrom.size)
            addTo.add(takeFrom[index])
            takeFrom.removeAt(index)

        }
        return addTo
    }

    private fun pairToRanking(pair: Pair<Int, String>): Ranking = Ranking(pair.first, pair.second)
}
