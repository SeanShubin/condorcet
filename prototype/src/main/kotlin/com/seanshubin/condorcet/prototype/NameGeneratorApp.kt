package com.seanshubin.condorcet.prototype

public fun main(args: Array<String>) {
    (1..50).map { DefaultNameGenerator.generateName() }.sorted().forEach { println(it) }
}
