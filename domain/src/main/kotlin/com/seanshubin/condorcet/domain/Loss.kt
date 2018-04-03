package com.seanshubin.condorcet.domain

data class Loss constructor(private val map: Map<Int, Int>) {
    companion object {
        val EMPTY = Loss(emptyMap<Int, Int>().withDefault { 0 })
        fun fromIndex(index: Int): Loss = Loss(mapOf(Pair(index, 1)).withDefault { 0 })
    }

    operator fun plus(that: Loss): Loss {
        val keys = (this.map.keys + that.map.keys).toList()
        val pairs = keys.map { Pair(it, this.map.getValue(it) + that.map.getValue(it)) }
        return Loss(mapOf(*pairs.toTypedArray()).withDefault { 0 })
    }

    operator fun times(multiplyBy: Int): Loss {
        return Loss(mapOf(*map.map { (key, value) -> Pair(key, value * multiplyBy) }.toTypedArray()).withDefault { 0 })
    }

    fun getValue(index: Int): Int = map.getValue(index)
    fun remove(ids: List<Int>): Loss =
            Loss(map.filter { (key, _) -> !ids.contains(key) })
}
