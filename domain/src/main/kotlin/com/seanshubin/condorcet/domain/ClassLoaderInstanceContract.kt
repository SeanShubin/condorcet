package com.seanshubin.condorcet.domain

import java.io.InputStream
import java.net.URL
import java.security.ProtectionDomain
import java.util.*

interface ClassLoaderInstanceContract {
    fun loadClass(name: String): Class<*>
    fun loadClass(name: String, resolve: Boolean): Class<*>
    fun getClassLoadingLock(className: String): Any
    fun findClass(name: String): Class<*>
    fun defineClass(name: String?, b: ByteArray, off: Int, len: Int,
                    protectionDomain: ProtectionDomain? = null): Class<*>

    fun defineClass(name: String, b: java.nio.ByteBuffer,
                    protectionDomain: ProtectionDomain): Class<*>

    fun resolveClass(c: Class<*>)
    fun findSystemClass(name: String): Class<*>
    fun findLoadedClass(name: String): Class<*>?
    fun setSigners(c: Class<*>, signers: Array<Any>)
    fun getResource(name: String): URL?
    fun getResources(name: String): Enumeration<URL>
    fun findResource(name: String): URL?
    fun findResources(name: String): Enumeration<URL>
    fun getResourceAsStream(name: String): InputStream?
    fun getParent(): ClassLoader?
    fun isAncestor(cl: ClassLoader): Boolean
    fun definePackage(name: String, specTitle: String,
                      specVersion: String, specVendor: String,
                      implTitle: String, implVersion: String,
                      implVendor: String, sealBase: URL): Package

    fun getPackage(name: String): Package?
    fun getPackages(): Array<Package>
    fun findLibrary(libname: String): String?
    fun setDefaultAssertionStatus(enabled: Boolean)
    fun setPackageAssertionStatus(packageName: String,
                                  enabled: Boolean)

    fun setClassAssertionStatus(className: String, enabled: Boolean)
    fun clearAssertionStatus()
    fun desiredAssertionStatus(className: String): Boolean
}




