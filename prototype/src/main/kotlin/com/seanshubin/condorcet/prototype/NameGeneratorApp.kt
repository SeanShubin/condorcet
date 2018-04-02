package com.seanshubin.condorcet.prototype

public fun main(args: Array<String>) {
    (1..50).forEach { println(DefaultNameGenerator.generateName()) }
}
