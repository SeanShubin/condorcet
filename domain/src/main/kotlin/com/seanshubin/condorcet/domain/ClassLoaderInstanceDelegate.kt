package com.seanshubin.condorcet.domain

import java.io.InputStream
import java.net.URL
import java.util.*

class ClassLoaderInstanceDelegate(val classLoader: ClassLoader) {
    fun loadClass(name: String): Class<*> = classLoader.loadClass(name)
    fun getResource(name: String): URL? = classLoader.getResource(name)
    fun getResources(name: String): Enumeration<URL> = classLoader.getResources(name)
    fun getResourceAsStream(name: String): InputStream? = classLoader.getResourceAsStream(name)
    fun getParent(): ClassLoader? = classLoader.parent
    fun setDefaultAssertionStatus(enabled: Boolean) = classLoader.setDefaultAssertionStatus(enabled)
    fun setPackageAssertionStatus(packageName: String, enabled: Boolean) =
            classLoader.setPackageAssertionStatus(packageName, enabled)

    fun setClassAssertionStatus(className: String, enabled: Boolean) =
            classLoader.setClassAssertionStatus(className, enabled)

    fun clearAssertionStatus() = classLoader.clearAssertionStatus()
}
