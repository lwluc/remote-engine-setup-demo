package de.luc.weinbrecht.bpm.worker

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

interface Logging
fun <T : Any> getClassForLogging(javaClass: Class<T>): Class<*> {
    return javaClass.enclosingClass?.takeIf {
        it.kotlin.companionObject?.java == javaClass
    } ?: javaClass
}
inline fun <reified T : Logging> T.log(): Logger
        = LoggerFactory.getLogger(getClassForLogging(T::class.java))