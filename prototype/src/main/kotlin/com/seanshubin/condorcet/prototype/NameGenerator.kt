package com.seanshubin.condorcet.prototype

import java.util.*

class NameGenerator(val random: Random, val givenNames: List<String>, val surnames: List<String>) {
    fun generateUniqueNames(names: List<String>, targetQuantity: Int): List<String> {
        val uniqueNames = names.distinct()
        if (uniqueNames.size == targetQuantity) {
            return uniqueNames
        } else {
            val namesNeeded = targetQuantity - uniqueNames.size
            return generateUniqueNames(uniqueNames + generateNames(namesNeeded), targetQuantity)
        }
    }

    fun generateNames(quantity: Int): List<String> = (1..quantity).map { generateName() }
    fun generateName(): String {
        val givenName = chooseRandom(givenNames)
        val surname = chooseRandom(surnames)
        return "$givenName $surname"
    }

    fun chooseRandom(choices: List<String>): String = choices.get(random.nextInt(choices.size))
}