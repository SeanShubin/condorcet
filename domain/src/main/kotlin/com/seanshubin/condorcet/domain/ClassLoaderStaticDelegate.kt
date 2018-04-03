package com.seanshubin.condorcet.domain

import java.io.InputStream
import java.net.URL
import java.util.*

object ClassLoaderStaticDelegate {
    fun getSystemResource(name: String): URL? = ClassLoader.getSystemResource(name)
    fun getSystemResources(name: String): Enumeration<URL> = ClassLoader.getSystemResources(name)
    fun getSystemResourceAsStream(name: String): InputStream? = ClassLoader.getSystemResourceAsStream(name)
}
