package com.seanshubin.condorcet.domain

data class Win constructor(private val map: Map<Int, Loss>) {
    companion object {
        val EMPTY = Win(emptyMap<Int, Loss>().withDefault { Loss.EMPTY })
        private fun fromWinnerLoser(winner: Int, loser: Int): Win {
            val loss = Loss.fromIndex(loser)
            return Win(mapOf(Pair(winner, loss)).withDefault { Loss.EMPTY })
        }
        fun fromWinnerLoser(winnerLoser: Pair<Int,Int>): Win {
            val (winner, loser) = winnerLoser
            return fromWinnerLoser(winner, loser)
        }
    }

    operator fun plus(that: Win): Win {
        val keys: List<Int> = (this.map.keys + that.map.keys).toList()
        val pairs: List<Pair<Int, Loss>> = keys.map {
            Pair(it, this.map.getValue(it) + that.map.getValue(it))
        }
        return Win(mapOf(*pairs.toTypedArray()).withDefault { Loss.EMPTY })
    }

    operator fun times(multiplyBy:Int):Win {
        return Win(mapOf(*map.map { (key, value) -> Pair(key, value * multiplyBy) }.toTypedArray()).withDefault { Loss.EMPTY })
    }

    fun getValue(winner:Int, loser:Int):Int = map.getValue(winner).getValue(loser)
    fun remove(ids:List<Int>):Win =
        Win(mapOf(*map.map { (win, loss) -> Pair(win, loss.remove(ids)) }.toTypedArray()).filter{ (win, _) -> !ids.contains(win) })
    fun splitUndefeated():Pair<Win, Win> {
        TODO()
    }
}
fun Iterable<Win>.sum(): Win {
    var sum: Win = Win.EMPTY
    for (element in this) {
        sum += element
    }
    return sum
}


