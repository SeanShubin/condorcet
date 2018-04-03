package com.seanshubin.condorcet.domain

import java.io.InputStream
import java.net.URL
import java.util.*

interface ClassLoaderStaticContract {
    fun registerAsParallelCapable(): Boolean
    fun getSystemResource(name: String): URL?
    fun getSystemResources(name: String): Enumeration<URL>
    fun getSystemResourceAsStream(name: String): InputStream?
    fun getClassLoader(caller: Class<*>?): ClassLoader?
    fun checkClassLoaderPermission(cl: ClassLoader, caller: Class<*>)
    fun loadLibrary(fromClass: Class<*>?, name: String,
                    isAbsolute: Boolean)

    fun findNative(loader: ClassLoader?, name: String): Long
}
