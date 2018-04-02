package com.seanshubin.condorcet.prototype

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

object DefaultNameGenerator {
    private val charset: Charset = StandardCharsets.UTF_8
    private const val SEED: Long = 12345
    private val random: Random = Random(SEED)
    private val nameGeneratorFactory: NameGeneratorFactory = NameGeneratorFactory(random, charset)
    private val nameGenerator = nameGeneratorFactory.createNameGenerator()
    fun generateName(): String = nameGenerator.generateName()
}
