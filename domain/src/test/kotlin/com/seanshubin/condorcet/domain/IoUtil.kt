package com.seanshubin.condorcet.domain

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

object IoUtil {
    fun inputStreamToLines(inputStream: InputStream, charset: Charset): List<String> {
        val reader = BufferedReader(InputStreamReader(inputStream, charset))
        reader.use { reader ->
            return bufferedReaderToLines(reader)
        }
    }

    fun bufferedReaderToLines(reader: BufferedReader): List<String> {
        val mutableList = mutableListOf<String>()
        var line = reader.readLine()
        while (line != null) {
            mutableList.add(line)
            line = reader.readLine()
        }
        return mutableList
    }
}