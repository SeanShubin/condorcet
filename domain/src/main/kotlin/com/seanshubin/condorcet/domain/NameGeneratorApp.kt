package com.seanshubin.condorcet.domain

import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

fun main(args: Array<String>) {
    val charset = StandardCharsets.UTF_8
    val inputStream = Object().javaClass.classLoader.getResourceAsStream("/surnames.txt")
    val reader = BufferedReader(InputStreamReader(inputStream, charset))
    var line = reader.readLine()
    while (line != null) {
        println(line)
    }
}
/*
eligible voters
...
name confirm timestamp 1 a 2 b 2 c 3 d

-----------------

voted
...
did not vote
...
votes
confirm 1 a 2 b 2 c 3 d
tally
1st a
2nd b c
4th d
 */
