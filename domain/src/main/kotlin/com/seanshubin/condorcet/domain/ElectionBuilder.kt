package com.seanshubin.condorcet.domain

data class ElectionBuilder(val mode: Mode = Mode.unknown,
                           val candidates: List<String> = emptyList(),
                           val eligibleToVote: List<String> = emptyList(),
                           val ballots: List<Ballot> = emptyList()) {
    enum class Mode(val parseName: String) {
        candidate("candidates") {
            override fun processDataLine(builder: ElectionBuilder, line: String): ElectionBuilder =
                    builder.copy(candidates = builder.candidates + listOf(line))
        },
        voter("eligible-to-vote") {
            override fun processDataLine(builder: ElectionBuilder, line: String): ElectionBuilder =
                    builder.copy(eligibleToVote = builder.eligibleToVote + listOf(line))
        },
        ballot("ballots") {
            override fun processDataLine(builder: ElectionBuilder, line: String): ElectionBuilder =
                    builder.copy(ballots = builder.ballots + listOf(Ballot.fromString(line)))
        },
        unknown("unknown") {
            override fun processDataLine(builder: ElectionBuilder, line: String): ElectionBuilder {
                TODO("not implemented")
            }
        };

        companion object {
            fun fromString(s: String): Mode {
                for (value in values()) {
                    if (value.parseName == s.toLowerCase()) {
                        return value;
                    }
                }
                return unknown;
            }
        }

        abstract fun processDataLine(builder: ElectionBuilder, line: String): ElectionBuilder
    }

    fun processLine(line: String): ElectionBuilder {
        if (line.startsWith(" ")) {
            return processDataLine(line.trim())
        } else {
            return switchModes(line)
        }
    }

    fun processDataLine(line: String): ElectionBuilder =
            mode.processDataLine(this, line)

    fun switchModes(line: String): ElectionBuilder {
        val firstWord = line.split(" ")[0]
        return copy(mode = Mode.fromString(firstWord))
    }

    fun build(): Election = Election(candidates, eligibleToVote, ballots)

    companion object {
        val EMPTY = ElectionBuilder()
        fun processLine(electionBuilder: ElectionBuilder, line: String): ElectionBuilder {
            return electionBuilder.processLine(line)
        }
    }
}
