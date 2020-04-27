package com.github.igrmk.dull

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * File logger that throws out oldest half of logs whenever the file exceeds halvingSize parameter.
 * It is recommended to use it for relatively small log files because every time it halves a file it reads it as a text.
 * @param file File to log to
 * @param halvingSize The size of the file in bytes that initiates halving
 * @param newline New line sequence
 */
class FileLogger(
    private val file: File,
    private val halvingSize: Int,
    private val newline: String = System.lineSeparator()
) {
    private var writer: BufferedWriter? = null
    private var lock = ReentrantLock()

    private fun prepare() {
        if (file.length() > halvingSize) {
            writer?.close()
            val text = file.readText()
            writer = BufferedWriter(FileWriter(file, false))
            val half = text.indexOf(newline, text.length / 2)
            if (half >= 0) {
                writer!!.append(text.substring(half + newline.length)).flush()
            }
        }
        writer = writer ?: BufferedWriter(FileWriter(file, true))
    }

    /**
     * Appends a string to the log file.
     * If resulting file exceeds halvingSize parameter then throws out oldest half of logs.
     * @param text Text to append
     */
    fun append(text: String): Unit = lock.withLock {
        prepare()
        writer!!.append(text).append(newline)
    }

    /**
     * Returns full logs as a string
     */
    fun get(): String = lock.withLock {
        writer?.flush()
        return file.readText()
    }
}
