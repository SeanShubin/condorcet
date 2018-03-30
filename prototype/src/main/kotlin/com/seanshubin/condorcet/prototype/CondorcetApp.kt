package com.seanshubin.condorcet.prototype

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

public fun main(args: Array<String>) {
    val charset: Charset = StandardCharsets.UTF_8
    val seed: Long = 12345
    val random: Random = Random(seed)
    val nameGeneratorFactory: NameGeneratorFactory = NameGeneratorFactory(random, charset)
    val parser: Parser = Parser(nameGeneratorFactory)
    Runner(parser, charset).run()
}
